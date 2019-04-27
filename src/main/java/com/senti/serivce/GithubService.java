package com.senti.serivce;

import com.senti.model.codeComment.Commits;

import java.util.List;

public interface GithubService {

    List<Commits> getAllCommits(String owner,String repo);

}
