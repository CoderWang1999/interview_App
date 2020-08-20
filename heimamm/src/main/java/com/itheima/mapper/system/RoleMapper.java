package com.itheima.mapper.system;

import com.itheima.domain.system.Role;

import java.util.List;

public interface RoleMapper {
    //遍历所有
    List<Role> findAll();

    //新增
    void insert(Role role);

    //批量删除
    void deleteByids(String[] ids);

    //根据id查询
    Role findById(String id);

    //修改
    void update(Role role);
}
