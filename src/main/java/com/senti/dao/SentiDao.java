package com.senti.dao;

import com.senti.model.codeComment.ClassSenti;
import com.senti.model.codeComment.MessageSenti;
import com.senti.model.codeComment.CombineSenti;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SentiDao {
    /**
     *
     * @param list
     * 存储Commit的Message的情绪信息
     */
    void insertMessages(List<MessageSenti> list);

    /**
     *
     * @param gid
     * @return 获得指定项目的Commit的Message的情绪信息
     */
    List<MessageSenti> GetMessages(@Param("gid") int gid);

    /**
     *
     * @param id
     * @param start
     * @param end
     * @return 获得指定项目的Commit的Message的情绪信息（指定时间）
     */
    List<MessageSenti> GetMessagesWithTime(@Param("gid")int id,String start,String end);

    /**
     *
     * @param list
     * 存储代码类的注释的情绪信息
     */
    void insertCodeSenti(List<ClassSenti> list);

    /**
     *
     * @param gid
     * @return 获得指定项目的代码类注释的情绪
     */
    List<ClassSenti> GetCodeSenti(@Param("gid") int gid);

    /**
     *
     * @param gid
     * @return 获得将Commit的message和代码类注释结合的情绪
     */
    List<CombineSenti> getCombineSenti(@Param("gid")int gid);

    /**
     *
     * @param id
     * @param start
     * @param end
     * @return 获得将Commit的message和代码类注释结合的情绪（指定时间）
     */
    List<CombineSenti> getCombineSentiWithTime(@Param("gid")int id, String start, String end);

    /**
     *
     * @param list
     * 存储将Commit的message和代码类注释结合的情绪信息
     */
    void addCombineSenti(List<CombineSenti> list);
}
