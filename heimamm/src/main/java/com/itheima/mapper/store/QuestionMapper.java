package com.itheima.mapper.store;
import com.itheima.domain.store.Question;

import java.util.List;
public interface QuestionMapper {
    //查询所有
    List<Question> findAll();

    //新增
    void save(Question Question);

    //根据id查询
    Question findByid(String id);

    //修改
    void update(Question Question);

    //批量删除
    void deleteByIds(String[] split);
}
