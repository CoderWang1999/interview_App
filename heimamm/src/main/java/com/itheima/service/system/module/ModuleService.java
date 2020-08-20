package com.itheima.service.system.module;

import com.itheima.domain.system.Module;

import java.util.List;

public interface ModuleService {
    //遍历所有
    List<Module> findAll(String pageNum, String pageSize);

    //不带分页的遍历
    List<Module> list();

    //新增
    void insert(Module module);

    //批量删除
    boolean deleteByids(String ids);

    //根据id查询
    Module findById(String id);

    //修改
    void update(Module module);
}
