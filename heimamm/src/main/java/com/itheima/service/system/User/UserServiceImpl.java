package com.itheima.service.system.User;

import com.github.pagehelper.PageHelper;
import com.itheima.domain.system.Dept;
import com.itheima.domain.system.User;
import com.itheima.mapper.system.DeptMapper;
import com.itheima.mapper.system.UserMapper;
import com.itheima.utils.MapperUtils;
import com.itheima.utils.UidUtil;
import com.mysql.jdbc.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class UserServiceImpl implements UserService {
    //查询所有
    @Override
    public List<User> findAll(String pageNum, String pageSize) {
        SqlSession session = null;
        try {
            //获取Sqlsession对象
            session = MapperUtils.getsession();
            //获取代理对象
            UserMapper mapper = session.getMapper(UserMapper.class);
            //设置默认首页
            int cp=1;
            //设置默认每页显示条数
            int ps=5;
            //如果获取到的数据不为空字符串并且有值则赋值
            if (!StringUtils.isNullOrEmpty(pageNum)){
                cp=Integer.parseInt(pageNum);
            }if (!StringUtils.isNullOrEmpty(pageSize)){
                ps=Integer.parseInt(pageSize);
            }
            //设置分页参数
            PageHelper.startPage(cp,ps);
            //执行sql语句
            List<User> list = mapper.findAll();
            //返回结果
            return list;
        } catch (Exception e) {
            throw new RuntimeException("查询失败！");
        } finally {
            //提交事务并释放资源
            MapperUtils.close(session);
        }
    }

    //新增
    @Override
    public void insert(User user) {
        //获取Sqlsession对象
        SqlSession session = MapperUtils.getsession();
        //获取代理对象
        UserMapper mapper = session.getMapper(UserMapper.class);

        //获取唯一id
        String id = UidUtil.getUid();
        user.setId(id);
        System.out.println("user = " + user);

        //执行sql语句
        mapper.insert(user);
        //提交事务释放资源
        MapperUtils.close(session);
    }

    @Override
    public boolean deleteByids(String ids) {
        SqlSession session=null;
        boolean flag=false;//默认删除失败
        try {
            //获取session对象
            session=MapperUtils.getsession();
            //获取代理对象
            UserMapper mapper = session.getMapper(UserMapper.class);
            //处理lds
            String[] Ids = ids.split(",");
            //执行sql语句
            mapper.deleteByids(Ids);
            //执行到这里说明删除成功
            flag=true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //提交事务释放资源
            MapperUtils.close(session);
        }
        return flag;
    }

    //根据id查询
    @Override
    public User findById(String id) {
        //获取Sqlsession对象
        SqlSession session = MapperUtils.getsession();
        //获取代理对象
        UserMapper mapper = session.getMapper(UserMapper.class);
        //执行sql语句
        User user = mapper.findById(id);
        //提交事务释放资源
        MapperUtils.close(session);
        //返回查询结果
        return user;
    }

    //修改
    @Override
    public void update(User user) {
        //获取Sqlsession对象
        SqlSession session = MapperUtils.getsession();
        //获取代理对象
        UserMapper mapper = session.getMapper(UserMapper.class);
        //执行sql语句
        mapper.update(user);
        //提交事务释放资源
        MapperUtils.close(session);
    }
}
