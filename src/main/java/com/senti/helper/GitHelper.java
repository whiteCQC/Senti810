package com.senti.helper;

import com.senti.model.codeComment.ClassVariation;
import com.senti.model.codeComment.Commits;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  主要为使用JGit的GitHub文件操作
 */
public class GitHelper {
    private final static String loaclPathprefix = "../Git/projects/";//相对路径，在本地建立的GitHub项目下载的路径
    private final static String urlprefix = "https://github.com/";
//    private static String REGEX_CHINESE = "[\u4e00-\u9fa5]";

//	public final static int NotExist=-1;
//	public final static int DownloadBefore=0;
//	public final static int SuccessDownload=1;

    CodeComments cc;
    Calendar c;
    SimpleDateFormat sdf;
    FileDeal fd;

    public GitHelper() {
        cc = new CodeComments();
        c = Calendar.getInstance();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fd = new FileDeal();
    }

    /**
     *
     * @param owner
     * @param repo
     * @return 项目下载（注：暂未实现项目的pull功能，即项目下载后若发生后续的更新，将无法获取）
     */
    public boolean downloadProject(String owner, String repo) {
        String url = urlprefix + owner + "/" + repo + ".git";
        String localpath = loaclPathprefix + owner + "/" + repo;

        File file = new File(localpath);
        if (file.exists()) {//GitHub项目已经创建过
            System.out.println("it has been downloaded before");
            return true;
        } else {
            CloneCommand cc = Git.cloneRepository().setURI(url);
            try {
                System.out.println("start download");
                cc.setDirectory(file).call();//项目的clone过程
                System.out.println("downloading");
            } catch (InvalidRemoteException e) {
                e.printStackTrace();
            } catch (TransportException e) {//GitHub项目不存在，删除之前新建的项目文件夹
                fd.deleteDirectory(new File(loaclPathprefix + owner));
                System.out.println("not exist!");
                return false;
            } catch (GitAPIException e) {
                e.printStackTrace();
            }
            System.out.println(owner + "/" + repo + "---download success!");
            return true;
        }
    }

    /**
     *
     * @param owner 项目所有者
     * @param repo 项目名称
     * @return 获得指定项目所有的Commit信息
     */
    public List<Commits> getCommits(String owner, String repo) {
        String localRepoGitConfig = loaclPathprefix + owner + "/" + repo + "/.git";//本地的.git文件路径
        Git git;
        List<Commits> cList = new ArrayList<>();

        try {
            git = Git.open(new File(localRepoGitConfig));
            Iterable<RevCommit> iterable = git.log().call();

            Iterator<RevCommit> iter = iterable.iterator();

            while (iter.hasNext()) {
                RevCommit commit = iter.next();
                String name = commit.getAuthorIdent().getName();//Commit提交的作者

                int time = commit.getCommitTime();
                long millions = new Long(time).longValue() * 1000;
                c.setTimeInMillis(millions);
                String dateString = sdf.format(c.getTime());

                String commitID = commit.getName();//Commit的SHA
                String fullMessage = commit.getFullMessage();//提交的Message

                cList.add(new Commits(commitID, name, dateString, fullMessage));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cList;
    }

    /**
     *
     * @param owner
     * @param repo
     * @return 以类的路径为key，获得所有类的历次修改版本的变动信息
     */
    public Map<String, List<ClassVariation>> getCommitClassVariation(String owner, String repo) {
        Map<String, List<ClassVariation>> map = new HashMap<>();

        Git git = null;
        String localRepoGitConfig = loaclPathprefix + owner + "/" + repo + "/.git";

        String filepath = loaclPathprefix + owner + "/" + repo;
        List<String> presentJava = fd.getPresentJava(filepath);//获得项目当前版本下现有的类

        List<RevCommit> rv = new ArrayList<>();
        try {
            git = Git.open(new File(localRepoGitConfig));
            Iterable<RevCommit> iterable = git.log().call();
            Iterator<RevCommit> iter = iterable.iterator();

            while (iter.hasNext()) {
                rv.add(0, iter.next());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Repository repository = git.getRepository();

        RevCommit first = rv.get(0);//第一次的Commit信息

        c.setTimeInMillis(new Long(first.getCommitTime()).longValue() * 1000);
        String firstDate = sdf.format(c.getTime());
        try (TreeWalk treeWalk = new TreeWalk(repository)) {//处理第一次commit内容，第一次的修改内容即为该版本下的所有内容
            treeWalk.addTree(first.getTree());
            treeWalk.setRecursive(true);
            while (treeWalk.next()) {
                String filename = treeWalk.getPathString();
                ObjectId objectId = treeWalk.getObjectId(0);
                ObjectLoader loader = repository.open(objectId);
                String firstCodes = new String(loader.getBytes(), "UTF-8");
                if (filename.endsWith(".java")) {
                    for (String JavaName : presentJava) {
                        if (JavaName.endsWith("/" + filename)) {
                            List<ClassVariation> cvl = new ArrayList<>();

                            cvl.add(new ClassVariation(JavaName, Timestamp.valueOf(firstDate),
                                    cc.getCommentOnlyInMethod(Arrays.asList(firstCodes.split("\n")))
                                    , new ArrayList<String>(),first.getFullMessage(),first.getName()));
                            map.put(JavaName, cvl);
                        }
                    }
                }

            }
        } catch (Exception e) {

        }

        //后续的Commit中的修改内容需要将前一版本内容和当前版本的内容进行对比进行获取
        ObjectReader reader = repository.newObjectReader();
        CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
        DiffEntry diffEntry = null;
        try {
            for (int i = 1; i < rv.size(); i++) {
                ObjectId old = repository.resolve(rv.get(i - 1).getName() + "^{tree}");//前一版本
                ObjectId head = repository.resolve(rv.get(i).getName() + "^{tree}");//当前版本
                oldTreeIter.reset(reader, old);
                CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
                newTreeIter.reset(reader, head);
                List<DiffEntry> diffs = git.diff()
                        .setNewTree(newTreeIter)
                        .setOldTree(oldTreeIter)
                        .call();

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                DiffFormatter df = new DiffFormatter(out);
                df.setRepository(git.getRepository());
                for (int j = 0; j < diffs.size(); j++) {
                    diffEntry = diffs.get(j);
                    df.format(diffEntry);
                }
                String code = out.toString("UTF-8");

                c.setTimeInMillis(new Long(rv.get(i).getCommitTime()).longValue() * 1000);
                String date = sdf.format(c.getTime());
                splitCodes(code, date, map, presentJava,rv.get(i).getFullMessage(),rv.get(i).getName());
                df.close();
            }

        } catch (IncorrectObjectTypeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

        return map;

    }

    /**
     *
     * @param owner
     * @param repo
     * @return 获得当前版本的类的代码
     */
    public Map<String, List<String>> getClassCode(String owner, String repo) {
        String filepath = loaclPathprefix + owner + "/" + repo;
        return fd.getJavaFileContent(filepath);

    }

    /**
     *
     * @param codes 目标代码
     * @param date 修改日期
     * @param map 进行信息存储的map
     * @param presentNames 当前版本的类
     * @param commit Commit的SHA
     *  对变动的代码进行处理，获得对应Commit中类的修改的注释
     */
    private void splitCodes(String codes, String date, Map<String, List<ClassVariation>> map, List<String> presentNames,String commit,String sha) {
        String lines[] = codes.split("\n");//按行处理
        String className = "";
        Map<String, Integer> codesMap = new HashMap<>();

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.startsWith("diff --git")) {//一个文件修改的开始标志
                line = line.replace("diff --git", "");

                if (line.contains(".java b/"))
                    line = line.substring(0, line.length() / 2);
                className = line.substring(3);
                if (className.endsWith(".java") && presentNames.contains(className)) {//需要为java文件以及是当前版本存在的类
                    codesMap.put(className, i + 4);//记录该类需要读取的修改代码的开始行数
                }
            }
        }
        List<String> codea = new ArrayList<>();//增加的代码
        List<String> coded = new ArrayList<>();//删除的代码
        List<String> commenta;//增加的注释
        List<String> commentd;//删除的注释

        for (String name : codesMap.keySet()) {
            for (int j = codesMap.get(name); j < lines.length; j++) {
                String line = lines[j];
                if (line.startsWith("diff --git"))//代表这个类的修改代码已经读取完
                    break;
                else {
                    if (line.startsWith("+"))//增加的代码
                        codea.add(line.substring(1));
                    else if (line.startsWith("-"))//删除的代码
                        coded.add(line.substring(1));
                }
            }

            commenta = cc.getCommentOnlyInMethod(codea);//筛选出注释
            commentd = cc.getCommentOnlyInMethod(coded);

            Timestamp t = Timestamp.valueOf(date);

            if (commenta.size() != 0 || commentd.size() != 0) {//将信息添加到map中
                if (map.containsKey(name)) {
                    map.get(name).add(new ClassVariation(name, t, commenta, commentd,commit,sha));
                } else {
                    List<ClassVariation> l = new ArrayList<>();
                    l.add(new ClassVariation(name, t, commenta, commentd,commit,sha));
                    map.put(name, l);
                }
            }


            codea.clear();
            coded.clear();
        }

    }

    /**
     *
     * @param owner
     * @param repo
     * @return 获得对应项目当前版本的类
     */
    public List<String> getPresentJava(String owner,String repo){
        String filepath = loaclPathprefix + owner + "/" + repo;
        List<String> presentJava = fd.getPresentJava(filepath);
        return presentJava;
    }

    /**
     *
     * @param owner
     * @param repo
     * @return 获得每次Commit涉及到的类，key为Commit的SHA
     */
    public Map<String,List<String>> getCommitChangedFile(String owner, String repo){
        Map<String,List<String>> map=new HashMap<>();

        Git git = null;
        String localRepoGitConfig = loaclPathprefix + owner + "/" + repo + "/.git";

        List<RevCommit> rv = new ArrayList<>();//所有的Commit信息
        try {
            git = Git.open(new File(localRepoGitConfig));
            Iterable<RevCommit> iterable = git.log().call();
            Iterator<RevCommit> iter = iterable.iterator();

            while (iter.hasNext()) {
                rv.add(0, iter.next());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Repository repository = git.getRepository();

        RevCommit first = rv.get(0);

        try (TreeWalk treeWalk = new TreeWalk(repository)) {//处理第一次commit内容
            treeWalk.addTree(first.getTree());
            treeWalk.setRecursive(true);

            List<String> firsts=new ArrayList<>();
            while (treeWalk.next()) {
                String filename = treeWalk.getPathString();
                if(filename.endsWith(".java"))//文件需要是java文件
                    firsts.add(filename);

            }
            map.put(first.getName(),firsts);
        } catch (Exception e) {
            e.printStackTrace();
        }


        for(int i=1;i<rv.size();i++){//处理第一次以后的Commit内容
            if(!rv.get(i).getFullMessage().startsWith("Merge")){
                List<String> list=listDiffFile(repository,git,rv.get(i).getName(),rv.get(i-1).getName());
                map.put(rv.get(i).getName(),list);
            }
        }

        return map;
    }


    /**
     *
     * @param repository
     * @param git
     * @param oldCommit 前一版本的Commit的SHA
     * @param newCommit 当前的Commit的SHA
     * @return 修改的java类
     */
    private List<String> listDiffFile(Repository repository, Git git, String oldCommit, String newCommit) {
        List<String> list=new ArrayList<>();
        try{
            final List<DiffEntry> diffs = git.diff()
                    .setOldTree(prepareTreeParser(repository, oldCommit))
                    .setNewTree(prepareTreeParser(repository, newCommit))
                    .call();
            for (DiffEntry diff : diffs) {
                String path=diff.getNewPath();
//                if(path.endsWith(".java"))
                    list.add(path);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return list;
    }

    private static AbstractTreeIterator prepareTreeParser(Repository repository, String objectId) throws IOException {
        // from the commit we can build the tree which allows us to construct the TreeParser
        //noinspection Duplicates
        try (RevWalk walk = new RevWalk(repository)) {
            RevCommit commit = walk.parseCommit(repository.resolve(objectId));
            RevTree tree = walk.parseTree(commit.getTree().getId());

            CanonicalTreeParser treeParser = new CanonicalTreeParser();
            try (ObjectReader reader = repository.newObjectReader()) {
                treeParser.reset(reader, tree.getId());
            }

            walk.dispose();

            return treeParser;
        }
    }










}
