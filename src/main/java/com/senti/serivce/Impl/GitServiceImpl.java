package com.senti.serivce.Impl;

import com.senti.helper.GitHelper;
import com.senti.helper.SentiCal;
import com.senti.model.GithubUser;
import com.senti.model.codeComment.ClassSenti;
import com.senti.model.codeComment.ClassVariation;
import com.senti.model.codeComment.Commits;
import com.senti.model.codeComment.MessageSenti;
import com.senti.serivce.GitService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(value="GitService")
public class GitServiceImpl implements GitService {

    private GitHelper gh ;
    private SentiCal sc;

    public GitServiceImpl(){
        gh= new GitHelper();
        sc = new SentiCal();
    }

    @Override
    public boolean ProjectDeal(String owner, String repo) {
        return (gh.downloadProject(owner, repo));
    }

    @Override
    public List<MessageSenti> getCommitSenti(String owner, String repo) {
        List<Commits> clist = gh.getCommits(owner, repo);

        List<MessageSenti> mlist = new ArrayList<>();

//        Timestamp t1 = clist.get(clist.size() - 1).getDate();
//        long mon = 30 * 24 * 60 * 60 * 1000l;

        int count = 0;
//        long time = t1.getTime() + mon;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

        double high = 0;
        double low = 0;
        int[] score;

        for (int i = clist.size() - 1; i >= 0;i--) {
            Commits c = clist.get(i);
            long target = c.getDate().getTime();
            score = sc.senti_strength(c.getMessage());
            high = score[0];
            low = score[1];
            String date=sdf.format(target);
            mlist.add(new MessageSenti(high, low, count, date,c.getMessage()));

//            if (target <= time) {
//                count++;
//                score = sc.senti_strength(c.getMessage());
//                high += score[0];
//                low += score[1];
//
//                i--;
//            } else {
//                if (count != 0) {
//                    Timestamp t = new Timestamp(time + mon / 2);
//                    mlist.add(new MessageSenti(f(high / count), f(low / count), count, sdf.format(t)));
//                    high = 0;
//                    low = 0;
//                    count = 0;
//                }
//                time += mon;
//            }

        }
//        if (count != 0) {
//            Timestamp t = new Timestamp(time + mon / 2);
//            mlist.add(new MessageSenti(f(high / count), f(low / count), count, sdf.format(t)));
//        }

        return mlist;
    }

    @Override
    public Map<String, List<MessageSenti>> getCommitSentiSortbyAuthor(String owner, String repo) {
        List<Commits> commitsList=gh.getCommits(owner, repo);
        Map<String, List<MessageSenti>> map=new HashMap<>();
        for (Commits commits:commitsList){
            Set<String> keys=map.keySet();
            if (!keys.contains(commits.getAuthor())){

                List<MessageSenti> list=new ArrayList<>();
                int[] ints=sc.senti_strength(commits.getMessage());
                MessageSenti messageSenti=new MessageSenti(ints[0],ints[1],0,commits.getDate().toString(),commits.getMessage());
                list.add(messageSenti);
                map.put(commits.getAuthor(),list);

            }else{
                List<MessageSenti> list=map.get(commits.getAuthor());
                int[] ints=sc.senti_strength(commits.getMessage());
                MessageSenti messageSenti=new MessageSenti(ints[0],ints[1],0,commits.getDate().toString(),commits.getMessage());
                list.add(messageSenti);

            }
        }
        return map;
    }



    @Override
    public GithubUser findGithubUserbyName(String name) {

        GithubUser githubUser;
        try {
            String url="https://api.github.com/search/users?q="+name+"+in:name";
            Document doc = Jsoup.connect(url).ignoreContentType(true).get();
            JSONParser parser = new JSONParser();

            JSONObject json1 = (JSONObject) parser.parse(doc.body().text());
            Integer count=Integer.parseInt(json1.get("total_count").toString());
            if (count==0) return null;
            JSONArray json2 = (JSONArray) json1.get("items");


            JSONObject jsonObject=(JSONObject) json2.get(0);
            String username=jsonObject.get("login").toString();
            String avatar_url=jsonObject.get("avatar_url").toString();
            githubUser= new GithubUser(name,username,avatar_url);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return githubUser;
    }

    @Override
    public Map<String, List<ClassSenti>> getClassSenti(String owner, String repo) {
        Map<String, List<ClassSenti>> map = new HashMap<>();

        Map<String, List<ClassVariation>> clist = gh.getCommitClassVariation(owner, repo);

        List<ClassVariation> cvlist = null;
        ClassVariation c = null;

        double high = 0;
        double low = 0;
        int[] score;

        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        List<String> calist;
        List<String> cdlist;
        StringBuilder sb=new StringBuilder();

        for (String name : clist.keySet()) {
            cvlist = clist.get(name);
            int size = cvlist.size();

            map.put(name, new ArrayList<ClassSenti>());
            for (int i = 0; i < size; i++) {
                c = cvlist.get(i);
                calist = c.getCommentAdd();
                cdlist = c.getCommentDelete();

                sb.setLength(0);

                for (String ca : calist) {
                    sb.append("\n"+ca);
                }
                String add=sb.toString();
                score = sc.senti_strength(add);
                high += score[0];
                low += score[1];


                sb.setLength(0);
                for (String cd : cdlist) {
                    sb.append("\n"+cd);
                }
                String del=sb.toString();
                score = sc.senti_strength(del);
                high -= score[1];
                low -= score[0];


                int total=calist.size()+cdlist.size();
                if (total != 0){
                    map.get(name).add(new ClassSenti(name, f(high), f(low), sdf.format(c.getDate()),
                            ("Add:"+add+"\n"+"Del:"+del).replaceAll("\n","<br>")));
                    high=0;
                    low=0;
                }

            }

        }

        return map;
    }

    @Override
    public Map<String, List<String>> getClassCode(String owner, String repo) {
        return gh.getClassCode(owner, repo);
    }

    private double f(double i) {
        return Double.parseDouble(String.format("%.2f", i));
    }
}
