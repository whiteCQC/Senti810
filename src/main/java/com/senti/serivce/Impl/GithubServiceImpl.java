package com.senti.serivce.Impl;

import com.senti.model.codeComment.Commits;
import com.senti.serivce.GithubService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service(value="GithubService")
public class GithubServiceImpl implements GithubService {
    @Override
    public List<Commits> getAllCommits(String owner, String repo) {
        String url="https://api.github.com/repos/"+owner+"/"+repo+"/commits&page=2";
        List<Commits> commits=new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).ignoreContentType(true).get();
            JSONParser parser = new JSONParser();
            JSONArray jsonlist = (JSONArray) parser.parse(doc.body().text());
            System.out.println(jsonlist.size());
            for (int i=0;i<jsonlist.size();i++){
                JSONObject json=(JSONObject) jsonlist.get(i);
                JSONObject commit=(JSONObject)json.get("commit");
//                Commits c=new Commits();
                String n=((JSONObject) commit.get("author")).get("name").toString();
                System.out.println(n);
//                c.setAuthor(commit.get("author"));


            }
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }


        return  null;
    }
}
