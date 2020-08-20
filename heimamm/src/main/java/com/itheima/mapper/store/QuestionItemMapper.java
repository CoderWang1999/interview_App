package com.itheima.mapper.store;

import com.itheima.domain.store.QuestionItem;

import java.util.List;

public interface QuestionItemMapper {
    //查询所有
    List<QuestionItem> findAll(String questionId);

    //新增
    void save(QuestionItem questionItem);

    //根据id查询
    QuestionItem findByid(String id);

    //修改
    void update(QuestionItem questionItem);

    //批量删除
    void deleteByIds(String[] split);

}
