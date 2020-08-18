package com.itheima.service.store.Course;

import com.github.pagehelper.PageHelper;
import com.itheima.domain.store.Course;
import com.itheima.mapper.store.CourseMapper;
import com.itheima.utils.MapperUtils;
import com.mysql.jdbc.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CourseServiceImpl implements CourseService {
    //遍历所有
    @Override
    public List<Course> findAll(String pageNum, String pageSize) {
        SqlSession session =null;
        try {
            //获取session对象
            session = MapperUtils.getsession();
            //获取代理对象
            CourseMapper mapper = session.getMapper(CourseMapper.class);
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
            List<Course> list = mapper.findAll();
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
    public void save(Course course) {
        //获取session对象
        SqlSession session = MapperUtils.getsession();
        //获取代理对象
        CourseMapper mapper = session.getMapper(CourseMapper.class);
        //执行sql语句
        mapper.save(course);
        //提交事务并释放资源
        MapperUtils.close(session);
    }

    //根据id查询
    @Override
    public Course findByid(String id) {
        //获取session对象
        SqlSession session = MapperUtils.getsession();
        //获取代理对象
        CourseMapper mapper = session.getMapper(CourseMapper.class);
        //执行sql语句
        Course course = mapper.findByid(id);
        //提交事务并释放资源
        MapperUtils.close(session);
        return course;
    }

    //更新
    @Override
    public void update(Course course) {
        //获取session对象
        SqlSession session = MapperUtils.getsession();
        //获取代理对象
        CourseMapper mapper = session.getMapper(CourseMapper.class);
        //执行sql语句
        mapper.update(course);
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
            CourseMapper mapper = session.getMapper(CourseMapper.class);
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

    //不带分页的查询所有
    @Override
    public List<Course> list() {
        //获取session对象
        SqlSession session = MapperUtils.getsession();
        //获取代理对象
        CourseMapper mapper = session.getMapper(CourseMapper.class);
        //执行sql语句
        List<Course> list = mapper.findAll();
        return list;
    }
}
