package com.itheima.mapper.system;

import com.itheima.domain.system.User;

import java.util.List;

public interface UserMapper {
    //查询所有
    List<User> findAll();

    //新增
    void insert(User user);

    //批量删除
    void deleteByids(String[] ids);

    //根据id查询
    User findById(String id);

    //修改
    void update(User user);
}
