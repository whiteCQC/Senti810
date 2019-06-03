package com.senti.serivce;


import com.senti.model.codeComment.*;

import java.util.List;
import java.util.Map;

public interface GitService {
    boolean ProjectDeal(String owner, String repo);

    List<MessageSenti> getCommitSenti(String owner, String repo);

    List<MessageSentihht> getCommitSentihht(String owner, String repo);

    Map<author,List<MessageSentihht>> getCommitSentiSortbyAuthor(String owner, String repo);



    Map<String,List<String>> getCommitRelatedClasses(String owner, String repo);

    Map<String,List<String>> getClassRelatedCommits(String owner, String repo);

    List<MessageSentihht> getCommitSentibyAuthor(String owner, String repo, String author);

    List<List<String>> getTopClasses(Map<String, List<String>> map, List<MessageSenti> mlist, List<String> classes);

    List<MessageSenti> getCommitSentiWithTime(String owner, String repo, String start, String end);

    Map<String,List<ClassSenti>> getClassSenti(String owner, String repo);

    Map<String,List<String>> getClassCode(String owner, String repo);

    void addNote(String note,String time,String classname,String owner,String repo,int userid);

    Map<String,List<ClassNote>> getNotes(String owner, String repo, int userid);

    List<List<String>> getTopCombines(String owner,String repo);


//    List<List<String>> getTopClasses(Map<String,List<String>> map,List<MessageSenti> mlist,String owner,String repo);

    List<MessageSentihht> getCommitSentihht(String owner, String repo, String date);

    Map<author, List<MessageSentihht>> getCommitSentiSortbyAuthor(String owner, String repo, String date);

    List<List<String>> getTopCombinesWithTime(String owner,String repo,String start,String end);

}
