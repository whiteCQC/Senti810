package com.senti.serivce.Impl;

import com.senti.dao.GitDao;
import com.senti.dao.SentiDao;
import com.senti.helper.GitHelper;
import com.senti.helper.SentiCal;
import com.senti.model.GithubUser;
import com.senti.model.codeComment.*;
import com.senti.serivce.GitService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

@Service(value = "GitService")
public class GitServiceImpl implements GitService {

    private GitHelper gh;
    private SentiCal sc;

    @Autowired
    GitDao gitDao;

    @Autowired
    SentiDao sentiDao;

    public GitServiceImpl() {
        gh = new GitHelper();
        sc = new SentiCal();
    }


    /**
     *
     * @param owner 项目所有者
     * @param repo 项目名称
     * @return 项目最初的下载存储处理
     */
    @Override
    public boolean ProjectDeal(String owner, String repo) {
        boolean res=gh.downloadProject(owner, repo);//调用Githelper尝试下载项目
        if(res){//项目存在或下载完成
            int gid=gitDao.GetProjectId(owner,repo);
            if(0==gid)//若数据库中没有项目则返回的Gid默认为0，然后进行存储
                gitDao.addProject(owner,repo);
        }
        return res;
    }

    /**
     *
     * @param owner 项目所有者
     * @param repo 项目名称
     * @return 获得项目的Commit的message情绪信息
     */
    @Override
    public List<MessageSenti> getCommitSenti(String owner, String repo) {
        List<MessageSenti> mlist;
        int gid = gitDao.GetProjectId(owner, repo);
        mlist = sentiDao.GetMessages(gid);//先尝试从数据库获取，若存在则直接返回，若没有则需要计算
        if (mlist.size() != 0)
            return mlist;

        List<Commits> clist = gh.getCommits(owner, repo);//每一次Commit的信息

        mlist = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

        double high = 0;//正面情绪
        double low = 0;//负面情绪
        int[] score;//SentiStrength的返回值

        for (int i = clist.size() - 1; i >= 0; i--) {
            Commits c = clist.get(i);
            /*
             考虑到Merge pull request为代码整合，应该是没有代码的变化的，
              但GitHelper中对这一块的代码变更的获取中还是会得到变更的代码，
              故暂先都获取
             */
//            if (!c.getMessage().startsWith("Merge pull request")) {
                long target = c.getDate().getTime();

                score = sc.senti_strength(c.getMessage());

                high = score[0];
                low = score[1];
                String date = sdf.format(target);

                mlist.add(new MessageSenti(gid, high, low, date, c.getMessage(), c.getSha()));
//            }
        }
        sentiDao.insertMessages(mlist);

        return mlist;
    }

    @Override
    public List<MessageSentihht> getCommitSentihht(String owner, String repo) {
        List<MessageSentihht> list;
//        int gid=gitDao.GetProjectId(owner,repo);
//        mlist=sentiDao.GetMessages(gid);
//        if(mlist.size()!=0)
//            return mlist;


        list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

        List<Commits> clist = gh.getCommits(owner, repo);
        double low = 0;
        double high = 0;

        int[] score;

        int standf = 0;

        for (int i = clist.size() - 1; i >= 0; i--) {
            Commits c = clist.get(i);

            if (!c.getMessage().startsWith("Merge")) {
                long target = c.getDate().getTime();

                score = sc.senti_strength(c.getMessage());


                high = score[0];
                low = score[1];
                String date = sdf.format(target);
                list.add(new MessageSentihht(high, low, date, c.getMessage()));
//                mlist.add(new MessageSenti(gid, high, low,date, c.getMessage(),c.getSha()));
            }
        }
//        sentiDao.insertMessages(mlist);


        return list;
    }

    @Override
    public Map<String, List<MessageSentihht>> getCommitSentiSortbyAuthor(String owner, String repo) {
        List<Commits> commitsList = gh.getCommits(owner, repo);
        Map<String, List<MessageSentihht>> map = new HashMap<>();
        for (Commits commits : commitsList) {
            Set<String> keys = map.keySet();
            if (!keys.contains(commits.getAuthor())) {

                List<MessageSentihht> list = new ArrayList<>();
                int[] ints = sc.senti_strength(commits.getMessage());
                MessageSentihht messageSenti = new MessageSentihht(ints[0], ints[1], commits.getDate().toString(), commits.getMessage());
                list.add(messageSenti);
                map.put(commits.getAuthor(), list);

            } else {
                List<MessageSentihht> list = map.get(commits.getAuthor());
                int[] ints = sc.senti_strength(commits.getMessage());
                MessageSentihht messageSenti = new MessageSentihht(ints[0], ints[1], commits.getDate().toString(), commits.getMessage());
                list.add(messageSenti);

            }
        }
        Comparator<List<MessageSentihht>> comparator = new Comparator<List<MessageSentihht>>() {


            @Override
            public int compare(List<MessageSentihht> o1, List<MessageSentihht> o2) {
                return o2.size() - o1.size();
            }
        };
        Map<String, List<MessageSentihht>> sorted = map.entrySet().stream()
                .sorted(comparingByValue(comparator)).collect(toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> {
                            throw new AssertionError();
                        },
                        LinkedHashMap::new
                ));

        return sorted;
    }


    @Override
    public GithubUser findGithubUserbyName(String name) {

        GithubUser githubUser;
        try {
            String url = "https://api.github.com/search/users?q=" + name + "+in:name";
            Document doc = Jsoup.connect(url).ignoreContentType(true).get();
            JSONParser parser = new JSONParser();

            JSONObject json1 = (JSONObject) parser.parse(doc.body().text());
            Integer count = Integer.parseInt(json1.get("total_count").toString());
            if (count == 0) return null;
            JSONArray json2 = (JSONArray) json1.get("items");


            JSONObject jsonObject = (JSONObject) json2.get(0);
            String username = jsonObject.get("login").toString();
            String avatar_url = jsonObject.get("avatar_url").toString();
            githubUser = new GithubUser(name, username, avatar_url);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return githubUser;
    }

    /**
     *
     * @param owner 项目所有者
     * @param repo 项目名称
     * @return 获得指定项目每次Commit涉及到的java类
     */
    @Override
    public Map<String, List<String>> getCommitRelatedClasses(String owner, String repo) {
        int gid = gitDao.GetProjectId(owner, repo);

        List<relatedClasses> list = gitDao.getrelatedClasses(gid);//先尝试从数据库直接获取

        Map<String, List<String>> map = new HashMap<>();
        if (list.size() == 0) {//数据库无数据，需要先进行计算
            map = gh.getCommitChangedFile(owner, repo);
            List<relatedClasses> toStore = new ArrayList<>();
            for (String k : map.keySet()) {//Key为Commit的SHA
                for (String s : map.get(k)) {
                    toStore.add(new relatedClasses(gid, k, s));
                }
            }

            gitDao.addrelatedClasses(toStore);//存储数据

        } else {
            for (relatedClasses r : list) {//数据库已经有算好的数据，进行按Commit的SHA归类
                if (!map.containsKey(r.getSha())) {
                    List<String> ad = new ArrayList<>();
                    ad.add(r.getName());
                    map.put(r.getSha(), ad);
                } else {
                    map.get(r.getSha()).add(r.getName());
                }
            }

        }


        return map;
    }

    /**
     *
     * @param owner 项目所有者
     * @param repo 项目名称
     * @return 获得指定项目的，代码类，以代码类路径为key
     */
    @Override
    public Map<String,List<String>> getClassRelatedCommits(String owner, String repo){
        int gid = gitDao.GetProjectId(owner, repo);

        List<relatedClasses> list = gitDao.getrelatedClasses(gid);

        Map<String, List<String>> map = new HashMap<>();

        for(relatedClasses r : list){
            if (!map.containsKey(r.getName())) {
                List<String> ad = new ArrayList<>();
                ad.add(r.getSha());
                map.put(r.getName(), ad);
            } else {
                map.get(r.getName()).add(r.getSha());
            }
        }


        return map;
    }

    @Override
    public List<MessageSentihht> getCommitSentibyAuthor(String owner, String repo, String author) {
        List<Commits> commitsList = gh.getCommits(owner, repo);
        List<MessageSentihht> result = new ArrayList<>();

        for (Commits commits : commitsList) {
            if (!commits.getAuthor().equals(author)) continue;
            int[] ints = sc.senti_strength(commits.getMessage());
            MessageSentihht messageSenti = new MessageSentihht(ints[0], ints[1], commits.getDate().toString(), commits.getMessage());
            result.add(messageSenti);

        }
        return result;

    }

    /**
     *
     * @param map relatedclasses，以sha为key，commit涉及的所有class
     * @param mlist
     * @param classes 现有代码类
     * @return 高情绪推荐排行
     */
    @Override
    public List<List<String>> getTopClasses(Map<String, List<String>> map, List<MessageSenti> mlist, List<String> classes) {
        Map<String, CaltopHigh> res1 = new HashMap<>();//计算正面情绪
        Map<String, CaltopLow> res2 = new HashMap<>();//计算负面情绪

        for (MessageSenti m : mlist) {//遍历每次Commit
            int high = (int) m.getHigh();
            int low = (int) m.getLow();

            List<String> temp = map.get(m.getSha());//该次Commit涉及类
            if (temp != null) {
                for (String c : temp) {
                    if (classes.contains(c)) {//需要是现有java类
                        if (!res1.containsKey(c)) {
                            res1.put(c, new CaltopHigh(c, high));
                            res2.put(c, new CaltopLow(c, low));
                        } else {
                            res1.get(c).add(high);
                            res2.get(c).add(low);
                        }
                    }
                }
            }

        }


        List<String> topHigh = new ArrayList<>();//正面情绪的类名集合
        List<String> topLow = new ArrayList<>();
        List<String> topHighScore = new ArrayList<>();//平均情绪值集合
        List<String> topLowScore = new ArrayList<>();

        List<CaltopHigh> cl1 = new ArrayList<>(res1.values());
        List<CaltopLow> cl2 = new ArrayList<>(res2.values());
        Collections.sort(cl1);
        Collections.sort(cl2);

        for (int i = 0; i < 10 && i < cl1.size(); i++) {
            topHigh.add(cl1.get(i).getName());
            topLow.add(cl2.get(i).getName());

            topHighScore.add(String.valueOf(cl1.get(i).getScoreHigh()));
            topLowScore.add(String.valueOf(cl2.get(i).getScoreLow()));
        }

        List<List<String>> l = new ArrayList<>();
        l.add(topHigh);
        l.add(topLow);
        l.add(topHighScore);
        l.add(topLowScore);

        return l;
    }

    /**
     *
     * @param owner
     * @param repo
     * @param start
     * @param end
     * @return 获得根据时间段的对应Commit情绪信息
     */
    @Override
    public List<MessageSenti> getCommitSentiWithTime(String owner, String repo, String start, String end) {
        String s = start.replace("年", "-").replace("月", "-").replace("日", "-");
        String e = end.replace("年", "-").replace("月", "-").replace("日", "-");

        List<MessageSenti> res = sentiDao.GetMessagesWithTime(gitDao.GetProjectId(owner, repo), s, e);
        return res;
    }

    /**
     *
     * @param owner
     * @param repo
     * @return 指定项目的代码类情绪
     */
    @Override
    public Map<String, List<ClassSenti>> getClassSenti(String owner, String repo) {
        int gid = gitDao.GetProjectId(owner, repo);
        List<ClassSenti> cslist = sentiDao.GetCodeSenti(gid);//先尝试从数据库直接获取

        Map<String, List<ClassSenti>> map = new HashMap<>();
        if (cslist.size() != 0) {

            for (ClassSenti c : cslist) {
                if (!map.containsKey(c.getName())) {
                    List<ClassSenti> l = new ArrayList<>();
                    l.add(c);
                    map.put(c.getName(), l);
                } else {
                    map.get(c.getName()).add(c);
                }
            }

            return map;
        }
        List<ClassSenti> toStore = new ArrayList<>();

        Map<String, List<ClassVariation>> clist = gh.getCommitClassVariation(owner, repo);

        List<CombineSenti> swings = new ArrayList<>();

        List<ClassVariation> cvlist = null;
        ClassVariation c = null;

        double high = 0;//正面情绪
        double low = 0;//负面情绪
        int[] score;//SentiStrength返回值

        int sh = 0;
        int sl = 0;

        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        List<String> calist;//添加的注释
        List<String> cdlist;//删除的注释
        StringBuilder sb = new StringBuilder();

        for (String name : clist.keySet()) {
            cvlist = clist.get(name);
            int size = cvlist.size();

            map.put(name, new ArrayList<ClassSenti>());
            for (int i = 0; i < size; i++) {
                c = cvlist.get(i);
                calist = c.getCommentAdd();
                cdlist = c.getCommentDelete();

                String date = sdf.format(c.getDate());

                sb.setLength(0);

                /*
                 *  这里的正面值是用增加注释的正面情绪值减去删除注释的负面情绪值，负面值对应
                 */
                for (String ca : calist) {//将增加的注释用换行连接
                    sb.append("\n" + ca);
                }
                String add = sb.toString();
                score = sc.senti_strength(add);
                high += score[0];
                low += score[1];

                score = sc.senti_strength(add + c.getCommit());//这里计算的是将注释和Commit的message结合
                sh += score[0];
                sl += score[1];


                sb.setLength(0);
                for (String cd : cdlist) {
                    sb.append("\n" + cd);
                }
                String del = sb.toString();
                score = sc.senti_strength(del);
                high -= score[1];
                low -= score[0];

                score = sc.senti_strength(del + c.getCommit());
                sh -= score[1];
                sl -= score[0];

                swings.add(new CombineSenti(gid, sh, sl, date, name));

                sh = 0;
                sl = 0;

                int total = calist.size() + cdlist.size();
                if (total != 0) {
                    String comment=("Add:" + add + "\n" + "Del:" + del).replaceAll("\n", "<br>");//存储的注释内容，Add开头为添加，Del之后的为删除的，<br>是为了界面上的换行
                    map.get(name).add(new ClassSenti(gid, name, f(high), f(low), date,comment,c.getSha()));
                    high = 0;
                    low = 0;
                }

            }

        }

        sentiDao.addCombineSenti(swings);

        for (String name : map.keySet()) {
            toStore.addAll(map.get(name));
        }

        sentiDao.insertCodeSenti(toStore);

        return map;
    }

    /**
     *
     * @param owner
     * @param repo
     * @return 项目所有现有类的代码
     */
    @Override
    public Map<String, List<String>> getClassCode(String owner, String repo) {
        return gh.getClassCode(owner, repo);
    }


    /**
     *
     * @param note
     * @param time
     * @param classname
     * @param owner
     * @param repo
     * @param userid
     * 添加用户指定项目的对应类的留言
     */
    @Override
    public void addNote(String note, String time, String classname, String owner, String repo, int userid) {
        int gid = gitDao.GetProjectId(owner, repo);
        ClassNote cn = new ClassNote(userid, gid, classname, note, time);

        gitDao.addNote(cn);
    }

    /**
     *
     * @param owner
     * @param repo
     * @param userid
     * @return 获得用户在指定项目中的所有留言，Key为类
     */
    @Override
    public Map<String, List<ClassNote>> getNotes(String owner, String repo, int userid) {
        int gid = gitDao.GetProjectId(owner, repo);
        List<ClassNote> allnotes = gitDao.getNotes(userid, gid);

        Map<String, List<ClassNote>> notes = new HashMap<>();
        for (ClassNote c : allnotes) {
            if (!notes.containsKey(c.getClassname())) {
                List<ClassNote> list = new ArrayList<>();
                list.add(c);
                notes.put(c.getClassname(), list);
            } else {
                notes.get(c.getClassname()).add(c);
            }

        }

        return notes;
    }

    /**
     *
     * @param owner
     * @param repo
     * @return Commit的message结合代码注释的情绪值偏向占比的正面和负面排行
     */
    @Override
    public List<List<String>> getTopCombines(String owner, String repo) {
        int gid=gitDao.GetProjectId(owner,repo);

        return getTopCombines(sentiDao.getCombineSenti(gid));
    }

    /**
     *
     * @param owner
     * @param repo
     * @param start
     * @param end
     * @return Commit的message结合代码注释的情绪值偏向占比的正面和负面排行（指定时间段）
     */
    @Override
    public List<List<String>> getTopCombinesWithTime(String owner, String repo, String start, String end) {
        int gid=gitDao.GetProjectId(owner,repo);

        String s = start.replace("年", "-").replace("月", "-").replace("日", "-");
        String e = end.replace("年", "-").replace("月", "-").replace("日", "-");

        return getTopCombines(sentiDao.getCombineSentiWithTime(gid,s,e));
    }

    private List<List<String>> getTopCombines(List<CombineSenti> slist){
        Map<String,List<CombineSenti>> map=new HashMap<>();

        for(CombineSenti sw:slist){
            if(map.containsKey(sw.getName())){
                map.get(sw.getName()).add(sw);
            }else{
                List<CombineSenti> list=new ArrayList<>();
                list.add(sw);
                map.put(sw.getName(),list);
            }
        }

        List<RatioHigh> ch=new ArrayList<>();
        List<RatioLow> cl=new ArrayList<>();

        for(String name:map.keySet()){
            List<CombineSenti> list=map.get(name);

            RatioHigh rh=new RatioHigh(name);
            RatioLow rl=new RatioLow(name);
            for(CombineSenti cs:list){
                int score=cs.getHigh()+cs.getLow();
                rh.add(score);
                rl.add(score);
            }
            ch.add(rh);
            cl.add(rl);

        }

        Collections.sort(ch);
        Collections.sort(cl);

        List<List<String>> res=new ArrayList<>();

        List<String> hnames=new ArrayList<>();
        List<String> lnames=new ArrayList<>();
        List<String> hv=new ArrayList<>();
        List<String> lv=new ArrayList<>();
        for(int i=0;i<10&&i<ch.size();i++){
            hnames.add(ch.get(i).getName());
            lnames.add(cl.get(i).getName());

            hv.add(ch.get(i).getRatioString());
            lv.add(cl.get(i).getRatioString());
        }

        res.add(hnames);
        res.add(lnames);
        res.add(hv);
        res.add(lv);

        return res;
    }

    private double f(double i) {
        return Double.parseDouble(String.format("%.2f", i));
    }
}


