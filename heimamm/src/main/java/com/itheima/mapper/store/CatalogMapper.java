package com.itheima.mapper.store;

import com.itheima.domain.store.Catalog;

import java.util.List;

public interface CatalogMapper {
    //查询所有
    List<Catalog> findAll();

    //新增
    void save(Catalog catalog);

    //根据id查询
    Catalog findByid(String id);

    //修改
    void update(Catalog catalog);

    //批量删除
    void deleteByIds(String[] split);
}
