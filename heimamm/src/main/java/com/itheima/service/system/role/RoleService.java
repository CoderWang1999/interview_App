package com.itheima.service.system.role;

import com.itheima.domain.store.Company;
import com.itheima.domain.system.Role;

import java.util.List;

public interface RoleService {
    //遍历所有
    List<Role> findAll(String pageNum, String pageSize);

    //不带分页的遍历
    List<Role> list();

    //新增
    void insert(Role role);

    //批量删除
    boolean deleteByids(String ids);

    //根据id查询
    Role findById(String id);

    //修改
    void update(Role role);
}
