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
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class GitHelper {
    private final static String loaclPathprefix = "../Git/projects/";
    private final static String urlprefix = "https://github.com/";

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
        fd=new FileDeal();
    }

    public boolean downloadProject(String owner, String repo) {// download the target git project.if already
        // downloaded,pass.
        String url = urlprefix + owner + "/" + repo + ".git";
        String localpath = loaclPathprefix + owner + "\\" + repo;

        File file = new File(localpath);
        if (file.exists()) {
//			try {
//				Git git= Git.open(new File(localpath));
//			    git.pull().call();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}catch (GitAPIException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
            System.out.println("it has been downloaded before");
            return true;
        } else {
            CloneCommand cc = Git.cloneRepository().setURI(url);
            try {
                cc.setDirectory(file).call();
            } catch (InvalidRemoteException e) {
                e.printStackTrace();
            } catch (TransportException e) {
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

    public List<Commits> getCommits(String owner, String repo) {
        String localRepoGitConfig = loaclPathprefix + owner + "\\" + repo + "\\.git";
        Git git;
        List<Commits> cList = new ArrayList<>();

        try {
            git = Git.open(new File(localRepoGitConfig));
            Iterable<RevCommit> iterable = git.log().call();

            Iterator<RevCommit> iter = iterable.iterator();

            while (iter.hasNext()) {
                RevCommit commit = iter.next();
                String name = commit.getAuthorIdent().getName();

                int time = commit.getCommitTime();
                long millions = new Long(time).longValue() * 1000;
                c.setTimeInMillis(millions);
                String dateString = sdf.format(c.getTime());

                String commitID = commit.getName();
                String fullMessage = commit.getFullMessage();

                cList.add(new Commits(commitID, name, dateString, fullMessage));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cList;
    }

    public Map<String, List<ClassVariation>> getCommitClassVariation(String owner, String repo) {
        Map<String, List<ClassVariation>> map = new HashMap<>();

        Git git = null;
        String localRepoGitConfig = loaclPathprefix + owner + "\\" + repo + "\\.git";

        String filepath=loaclPathprefix+owner+"\\"+repo;
        List<String> presentJava=fd.getPresentJava(filepath);

        List<RevCommit> rv = new ArrayList<>();
        try {
            git = Git.open(new File(localRepoGitConfig));
            Iterable<RevCommit> iterable = git.log().call();
            Iterator<RevCommit> iter = iterable.iterator();

            while (iter.hasNext()) {
                rv.add(0,iter.next());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Repository repository=git.getRepository();

        RevCommit first=rv.get(0);

        c.setTimeInMillis(new Long(first.getCommitTime()).longValue() * 1000);
        String firstDate = sdf.format(c.getTime());
        try (TreeWalk treeWalk = new TreeWalk(repository)) {//处理第一次commit内容
            treeWalk.addTree(first.getTree());
            treeWalk.setRecursive(true);
            while (treeWalk.next()) {
               String filename=treeWalk.getPathString();
                ObjectId objectId = treeWalk.getObjectId(0);
                ObjectLoader loader = repository.open(objectId);
                String firstCodes=new String(loader.getBytes(),"UTF-8");
                if(filename.endsWith(".java")){
                    for(String JavaName:presentJava){
                        if(JavaName.endsWith("/"+filename)){
                            List<ClassVariation> cvl=new ArrayList<>();

                            cvl.add(new ClassVariation(JavaName,Timestamp.valueOf(firstDate),cc.getCommentOnlyInMethod(Arrays.asList(firstCodes.split("\n"))),new ArrayList<String>()));
                            map.put(JavaName,cvl);
                        }
                    }
                }

            }
        }catch (Exception e){

        }


        ObjectReader reader = repository.newObjectReader();
        CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
        DiffEntry diffEntry=null;
        try {
            for(int i=1;i<rv.size();i++) {
                ObjectId old = repository.resolve(rv.get(i - 1).getName() + "^{tree}");
                ObjectId head = repository.resolve(rv.get(i).getName() + "^{tree}");
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
                String code=out.toString("UTF-8");

                c.setTimeInMillis(new Long(rv.get(i).getCommitTime()).longValue() * 1000);
                String date = sdf.format(c.getTime());
                splitCodes(code,date,map,presentJava);
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

    public Map<String, List<String>> getClassCode(String owner, String repo) {
        String filepath=loaclPathprefix+owner+"\\"+repo;
        return fd.getJavaFileContent(filepath);

    }

    private void splitCodes(String codes, String date, Map<String, List<ClassVariation>> map,List<String> presentNames) {
        String lines[] = codes.split("\n");
        String className = "";
        Map<String,Integer> codesMap=new HashMap<>();

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.startsWith("diff --git")) {
                line=line.replace("diff --git", "");

                if(line.contains(".java b/"))
                    line=line.substring(0,line.length()/2);
                className=line.substring(3);

                if (className.endsWith(".java")&&presentNames.contains(className)) {
                        codesMap.put(className, i+4);
                }
            }
        }
        List<String> codea = new ArrayList<>();
        List<String> coded = new ArrayList<>();
        List<String> commenta;
        List<String> commentd;

        for (String name:codesMap.keySet()) {
            for (int j = codesMap.get(name); j < lines.length; j++) {
                String line = lines[j];
                if (line.startsWith("diff --git"))
                    break;
                else {
                    if (line.startsWith("+"))
                        codea.add(line.substring(1));
                    else if (line.startsWith("1"))
                        coded.add(line.substring(1));
                }
            }

            commenta = cc.getCommentOnlyInMethod(codea);
            commentd = cc.getCommentOnlyInMethod(coded);

            Timestamp t=Timestamp.valueOf(date);

            if(commenta.size()!=0||commentd.size()!=0) {
                if (map.containsKey(name)) {
                    map.get(name).add(new ClassVariation(name,t, commenta, commentd));
                } else {
                    List<ClassVariation> l = new ArrayList<>();
                    l.add(new ClassVariation(name, t, commenta, commentd));
                    map.put(name, l);
                }
            }



            codea.clear();
            coded.clear();
        }

    }
}
