package com.itheima.mapper.system;

import com.itheima.domain.system.Module;

import java.util.List;

public interface ModuleMapper {
    //遍历所有
    List<Module> findAll();

    //新增
    void insert(Module module);

    //批量删除
    void deleteByids(String[] ids);

    //根据id查询
    Module findById(String id);

    //修改
    void update(Module module);
}
