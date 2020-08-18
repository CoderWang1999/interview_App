package com.itheima.mapper.store;

import com.itheima.domain.store.Course;

import java.util.List;

public interface CourseMapper {
    //查询所有
    List<Course> findAll();

    //新增
    void save(Course course);

    //根据id查询
    Course findByid(String id);

    //修改
    void update(Course course);

    //批量删除
    void deleteByIds(String[] split);
}
