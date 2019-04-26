package com.senti.dao;

import com.senti.model.codeComment.ClassSenti;
import com.senti.model.codeComment.MessageSenti;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SentiDao {
    void insertMessages(List<MessageSenti> list);

    List<MessageSenti> GetMessages(@Param("gid") int gid);

    void insertCodeSenti(List<ClassSenti> list);

    List<ClassSenti> GetCodeSenti(@Param("gid") int gid);
}
