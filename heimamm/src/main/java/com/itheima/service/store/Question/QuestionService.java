package com.itheima.service.store.Question;

import com.itheima.domain.store.Question;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public interface QuestionService {
    //查询所有
    List<Question> findAll(String pageNum, String pageSize);

    //新增
    void save(Question Question);

    //根据id查询
    Question findByid(String id);

    //修改
    void update(Question Question);

    //批量删除
    boolean deleteByIds(String ids);

    ByteArrayOutputStream getReport() throws IOException;
}
