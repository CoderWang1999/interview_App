package com.itheima.service.store.catalog;

import com.itheima.domain.store.Catalog;

import java.util.List;

public interface CatalogService {
    //查询所有
    List<Catalog> findAll(String pageNum, String pageSize);

    //新增
    void save(Catalog catalog);

    //根据id查询
    Catalog findByid(String id);

    //修改
    void update(Catalog catalog);

    //批量删除
    boolean deleteByIds(String ids);
}
