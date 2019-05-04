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

import static java.util.Comparator.comparingInt;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

@Service(value="GitService")
public class GitServiceImpl implements GitService {

    private GitHelper gh ;
    private SentiCal sc;

    @Autowired
    GitDao gitDao;

    @Autowired
    SentiDao sentiDao;

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
        List<MessageSenti> mlist;
        int gid=gitDao.GetProjectId(owner,repo);
        mlist=sentiDao.GetMessages(gid);
        if(mlist.size()!=0)
            return mlist;

        List<Commits> clist= gh.getCommits(owner, repo);

        mlist = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

        double high = 0;
        double low = 0;
        int[] score;

        int standf=0;

        for (int i = clist.size() - 1; i >= 0;i--) {
            Commits c = clist.get(i);
            if(!c.getMessage().startsWith("Merge")){
                long target = c.getDate().getTime();

                score = sc.senti_strength(c.getMessage());

                high = score[0];
                low = score[1];
                String date = sdf.format(target);
                mlist.add(new MessageSenti(gid, high, low,date, c.getMessage(),c.getSha()));
            }
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

        List<Commits> clist= gh.getCommits(owner, repo);
        double low = 0;
        double high = 0;

        int[] score;

        int standf=0;

        for (int i = clist.size() - 1; i >= 0;i--) {
            Commits c = clist.get(i);

            if(!c.getMessage().startsWith("Merge")){
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
        List<Commits> commitsList=gh.getCommits(owner, repo);
        Map<String, List<MessageSentihht>> map=new HashMap<>();
        for (Commits commits:commitsList){
            Set<String> keys=map.keySet();
            if (!keys.contains(commits.getAuthor())){

                List<MessageSentihht> list=new ArrayList<>();
                int[] ints=sc.senti_strength(commits.getMessage());
                MessageSentihht messageSenti=new MessageSentihht(ints[0],ints[1],commits.getDate().toString(),commits.getMessage());
                list.add(messageSenti);
                map.put(commits.getAuthor(),list);

            }else{
                List<MessageSentihht> list=map.get(commits.getAuthor());
                int[] ints=sc.senti_strength(commits.getMessage());
                MessageSentihht messageSenti=new MessageSentihht(ints[0],ints[1],commits.getDate().toString(),commits.getMessage());
                list.add(messageSenti);

            }
        }
        Comparator<List<MessageSentihht>> comparator=new Comparator<List<MessageSentihht>>() {


            @Override
            public int compare(List<MessageSentihht> o1, List<MessageSentihht> o2) {
                return o2.size()-o1.size();
            }
        };
        Map<String, List<MessageSentihht>> sorted = map.entrySet().stream()
                .sorted(comparingByValue(comparator)).collect(toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a,b) -> { throw new AssertionError(); },
                        LinkedHashMap::new
                ));

        return sorted;
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
    public Map<String, List<String>> getCommitRelatedClasses(String owner, String repo) {
        return gh.getCommitChangedFile(owner,repo);
    }

    @Override
    public Map<String, List<ClassSenti>> getClassSenti(String owner, String repo) {
        int gid=gitDao.GetProjectId(owner,repo);
        List<ClassSenti> cslist=sentiDao.GetCodeSenti(gid);

        Map<String, List<ClassSenti>> map=new HashMap<>();
        if(cslist.size()!=0){

            for(ClassSenti c:cslist){
                if(!map.containsKey(c.getName())){
                    List<ClassSenti> l=new ArrayList<>();
                    l.add(c);
                    map.put(c.getName(),l);
                }else{
                    map.get(c.getName()).add(c);
                }
            }

            return map;
        }
        List<ClassSenti> toStore=new ArrayList<>();

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
                    map.get(name).add(new ClassSenti(gid,name, f(high), f(low),sdf.format(c.getDate()),
                            ("Add:"+add+"\n"+"Del:"+del).replaceAll("\n","<br>")));
                    high=0;
                    low=0;
                }

            }

        }

        for(String name:map.keySet()){
            toStore.addAll(map.get(name));
        }

        sentiDao.insertCodeSenti(toStore);

        return map;
    }

    @Override
    public Map<String, List<String>> getClassCode(String owner, String repo) {
        return gh.getClassCode(owner, repo);
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
    @Override
    public List<List<String>> getTopClasses(Map<String, List<String>> map, List<MessageSenti> mlist, String owner, String repo) {
        List<String> presentJava=gh.getPresentJava(owner,repo);

        Map<String, CaltopHigh> res1=new HashMap<>();
        Map<String, CaltopLow> res2=new HashMap<>();

        for(MessageSenti m:mlist){
            int high=(int)m.getHigh();
            int low=(int)m.getLow();

            for(String c:map.get(m.getSha())){
                if(presentJava.contains(c)){
                    if(!res1.containsKey(c)){
                        res1.put(c,new CaltopHigh(c,high));
                        res2.put(c,new CaltopLow(c,low));
                    }else{
                        res1.get(c).add(high);
                        res2.get(c).add(low);
                    }
                }
            }
        }



        List<String> topHigh=new ArrayList<>();
        List<String> topLow=new ArrayList<>();

        List<CaltopHigh> cl1=new ArrayList<>(res1.values());
        List<CaltopLow> cl2=new ArrayList<>(res2.values());
        Collections.sort(cl1);
        Collections.sort(cl2);

        for(int i=0;i<10&&i<cl1.size();i++){
            topHigh.add(cl1.get(i).getName());
            topLow.add(cl2.get(i).getName());
        }

        List<List<String>> l=new ArrayList<>();
        l.add(topHigh);
        l.add(topLow);

        return l;
    }

    private double f(double i) {
        return Double.parseDouble(String.format("%.2f", i));
    }
}


