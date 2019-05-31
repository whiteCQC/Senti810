package com.senti.dao;

import com.senti.model.GitProject;
import com.senti.model.codeComment.ClassNote;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GitDao {
    void addProject(@Param("owner") String onwer, @Param("repo") String repo);

    int GetProjectId(@Param("owner") String onwer, @Param("repo") String repo);

    void addNote(ClassNote classNote);

    List<ClassNote> getNotes(@Param("userid")int userid,@Param("gid")int gid);
}
