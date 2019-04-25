package com.senti.dao;

import com.senti.model.codeComment.MessageSenti;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageSentiDao {
    void insertMessages(List<MessageSenti> list);

    List<MessageSenti> GetMessages(@Param("gid") int gid);
}
