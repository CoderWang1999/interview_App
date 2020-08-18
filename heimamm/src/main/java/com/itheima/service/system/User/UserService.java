package com.itheima.service.system.User;

import com.itheima.domain.system.Dept;
import com.itheima.domain.system.User;

import java.util.List;

public interface UserService {

    //查询所有
    List<User> findAll(String pageNum, String pageSize);

    //新增
    void insert(User user);

    //批量删除
    boolean deleteByids(String ids);

    //根据id查询
    User findById(String id);

    //修改
    void update(User user);
}
