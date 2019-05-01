package com.senti.serivce;

import com.senti.model.GithubUser;
import com.senti.model.codeComment.ClassSenti;
import com.senti.model.codeComment.Commits;
import com.senti.model.codeComment.MessageSenti;

import java.util.List;
import java.util.Map;

public interface GitService {
    boolean ProjectDeal(String owner,String repo);

    List<MessageSenti> getCommitSenti(String owner, String repo);

    Map<String,List<MessageSenti>> getCommitSentiSortbyAuthor(String owner,String repo);

    GithubUser findGithubUserbyName(String name);


    Map<String,List<ClassSenti>> getClassSenti(String owner, String repo);

    Map<String,List<String>> getClassCode(String owner, String repo);

    List<MessageSenti> getCommitSentibyAuthor(String owner, String repo, String author);
}
