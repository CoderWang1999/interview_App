package com.itheima.service.store.Course;

import com.itheima.domain.store.Course;

import java.util.List;

public interface CourseService {
    //查询所有
    List<Course> findAll(String pageNum, String pageSize);

    //新增
    void save(Course course);

    //根据id查询
    Course findByid(String id);

    //修改
    void update(Course course);

    //批量删除
    boolean deleteByIds(String ids);

    //不带分页的查询所有
    List<Course> list();
}
