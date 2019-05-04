package com.senti.serivce;

import com.senti.model.GithubUser;
import com.senti.model.codeComment.*;

import java.util.List;
import java.util.Map;

public interface GitService {
    boolean ProjectDeal(String owner,String repo);

    List<MessageSenti> getCommitSenti(String owner, String repo);

    List<MessageSentihht> getCommitSentihht(String owner, String repo);

    Map<author,List<MessageSentihht>> getCommitSentiSortbyAuthor(String owner, String repo);

    GithubUser findGithubUserbyName(String name);

    Map<String,List<String>> getCommitRelatedClasses(String owner, String repo);

    Map<String,List<ClassSenti>> getClassSenti(String owner, String repo);

    Map<String,List<String>> getClassCode(String owner, String repo);

    List<MessageSentihht> getCommitSentibyAuthor(String owner, String repo, String author);

    List<List<String>> getTopClasses(Map<String,List<String>> map,List<MessageSenti> mlist,String owner,String repo);

    List<MessageSentihht> getCommitSentihht(String owner, String repo, String date);

    Map<author, List<MessageSentihht>> getCommitSentiSortbyAuthor(String owner, String repo, String date);
}
