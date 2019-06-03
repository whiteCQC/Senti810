package com.senti.dao;

import com.senti.model.GitProject;
import com.senti.model.codeComment.ClassNote;
import com.senti.model.codeComment.relatedClasses;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GitDao {
    /**
     *
     * @param onwer 所有者
     * @param repo 项目名称
     * 添加GitHub项目信息
     */
    void addProject(@Param("owner") String onwer, @Param("repo") String repo);

    /**
     *
     * @param onwer
     * @param repo
     * @return 获得项目的ID
     */
    int GetProjectId(@Param("owner") String onwer, @Param("repo") String repo);

    /**
     *
     * @param classNote
     * 添加代码类的评论
     */
    void addNote(ClassNote classNote);

    /**
     *
     * @param userid
     * @param gid
     * @return 获得指定用户在指定项目中Commit代码模块的所有留言
     */
    List<ClassNote> getNotes(@Param("userid")int userid,@Param("gid")int gid);

    /**
     *
     * @param gid
     * @return 获得指定项目所有的Commit的对应涉及类
     */
    List<relatedClasses> getrelatedClasses(@Param("gid")int gid);

    /**
     *
     * @param list
     * 添加Commit的对应涉及类的信息
     */
    void addrelatedClasses(List<relatedClasses> list);
}
