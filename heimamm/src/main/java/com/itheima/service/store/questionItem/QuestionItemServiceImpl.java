package com.itheima.service.store.questionItem;

import com.github.pagehelper.PageHelper;
import com.itheima.domain.store.QuestionItem;
import com.itheima.mapper.store.QuestionItemMapper;
import com.itheima.utils.MapperUtils;
import com.mysql.jdbc.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class QuestionItemServiceImpl implements QuestionItemService {
    //遍历所有
    @Override
    public List<QuestionItem> findAll(String questionId) {
        SqlSession session =null;
        try {
            //获取session对象
            session = MapperUtils.getsession();
            //获取代理对象
            QuestionItemMapper mapper = session.getMapper(QuestionItemMapper.class);
            //执行Sql语句
            List<QuestionItem> list = mapper.findAll(questionId);
            //返回结果
            return list;
        }catch (Exception e) {
            throw new RuntimeException("查询失败！");
        }finally {
            //提交事务并释放资源
            MapperUtils.close(session);
        }
    }



    //新增
    @Override
    public void save(QuestionItem questionItem) {
        //获取session对象
        SqlSession session = MapperUtils.getsession();
        //获取代理对象
        QuestionItemMapper mapper = session.getMapper(QuestionItemMapper.class);
        //执行sql语句
        mapper.save(questionItem);
        //提交事务并释放资源
        MapperUtils.close(session);
    }

    //根据id查询
    @Override
    public QuestionItem findByid(String id) {
        //获取session对象
        SqlSession session = MapperUtils.getsession();
        //获取代理对象
        QuestionItemMapper mapper = session.getMapper(QuestionItemMapper.class);
        //执行sql语句
        QuestionItem questionItem = mapper.findByid(id);
        //提交事务并释放资源
        MapperUtils.close(session);
        return questionItem;
    }

    //更新
    @Override
    public void update(QuestionItem questionItem) {
        //获取session对象
        SqlSession session = MapperUtils.getsession();
        //获取代理对象
        QuestionItemMapper mapper = session.getMapper(QuestionItemMapper.class);
        //执行sql语句
        mapper.update(questionItem);
        //提交事务并释放资源
        MapperUtils.close(session);
    }

    //批量删除
    @Override
    public boolean deleteByIds(String ids) {
        SqlSession session =null;
        boolean flag=false;//默认删除失败
        try {
            //获取session对象
            session = MapperUtils.getsession();
            //获取代理对象
            QuestionItemMapper mapper = session.getMapper(QuestionItemMapper.class);
            //将ids转成int类型的数组
            String[] split = ids.split(",");
            //执行sql语句
            mapper.deleteByIds(split);
            //执行到这里说明删除成功
            flag=true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //提交事务并释放资源
            MapperUtils.close(session);
        }
        return flag;
    }
}
