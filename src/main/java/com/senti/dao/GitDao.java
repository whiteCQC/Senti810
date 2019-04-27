package com.senti.dao;

import com.senti.model.GitProject;
import org.apache.ibatis.annotations.Param;

public interface GitDao {
    void addProject(@Param("owner") String onwer, @Param("repo") String repo);

    int GetProjectId(@Param("owner") String onwer, @Param("repo") String repo);
}
