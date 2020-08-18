package com.itheima.service.store.Question;

import com.github.pagehelper.PageHelper;
import com.itheima.domain.store.Question;
import com.itheima.mapper.store.QuestionMapper;
import com.itheima.service.store.Question.QuestionService;
import com.itheima.utils.MapperUtils;
import com.mysql.jdbc.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {
    //遍历所有
    @Override
    public List<Question> findAll(String pageNum, String pageSize) {
        SqlSession session =null;
        try {
            //获取session对象
            session = MapperUtils.getsession();
            //获取代理对象
            QuestionMapper mapper = session.getMapper(QuestionMapper.class);
            //对获取的当前页码和每页显示条目数进行处理
            //默认当前页为1
            int pn=1;
            //默认每页显示10条数据
            int ps=10;
            if (!StringUtils.isNullOrEmpty(pageNum)){
                pn=Integer.parseInt(pageNum);
            }
            if (!StringUtils.isNullOrEmpty(pageSize)){
                ps=Integer.parseInt(pageSize);
            }
            //设置分页参数
            PageHelper.startPage(pn,ps);
            //执行Sql语句
            List<Question> list = mapper.findAll();
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
    public void save(Question Question) {
        //获取session对象
        SqlSession session = MapperUtils.getsession();
        //获取代理对象
        QuestionMapper mapper = session.getMapper(QuestionMapper.class);
        //执行sql语句
        mapper.save(Question);
        //提交事务并释放资源
        MapperUtils.close(session);
    }

    //根据id查询
    @Override
    public Question findByid(String id) {
        //获取session对象
        SqlSession session = MapperUtils.getsession();
        //获取代理对象
        QuestionMapper mapper = session.getMapper(QuestionMapper.class);
        //执行sql语句
        Question Question = mapper.findByid(id);
        //提交事务并释放资源
        MapperUtils.close(session);
        return Question;
    }

    //更新
    @Override
    public void update(Question Question) {
        //获取session对象
        SqlSession session = MapperUtils.getsession();
        //获取代理对象
        QuestionMapper mapper = session.getMapper(QuestionMapper.class);
        //执行sql语句
        mapper.update(Question);
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
            QuestionMapper mapper = session.getMapper(QuestionMapper.class);
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
