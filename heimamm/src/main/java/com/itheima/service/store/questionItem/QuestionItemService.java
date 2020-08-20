package com.itheima.service.store.questionItem;

import com.itheima.domain.store.QuestionItem;

import java.util.List;

public interface QuestionItemService {

    //新增
    void save(QuestionItem cuestionItem);

    //根据id查询
    QuestionItem findByid(String id);

    //修改
    void update(QuestionItem cuestionItem);

    //批量删除
    boolean deleteByIds(String ids);

    //查询所有
    List<QuestionItem> findAll(String questionId);
}
