package com.itheima.controller.store;

import com.github.pagehelper.PageInfo;
import com.itheima.controller.BaseServlet;
import com.itheima.domain.store.Course;
import com.itheima.utils.BeanUtil;
import com.itheima.utils.UidUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/store/Course/*")
public class CourseServlet extends BaseServlet {
    //遍历所有企业信息
    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据得到当前页和每页显示条目数
        String pageNum = req.getParameter("pageNum");
        String pageSize = req.getParameter("pageSize");
        //调用service层的findAll
        List<Course> list = courseService.findAll(pageNum,pageSize);
        //将数据交由pageinfo处理
        PageInfo<Course> pageInfo=new PageInfo<Course>(list);
        //将pageinfo存入请求域
        req.setAttribute("page",pageInfo);
        //请求妆发到list.jsp
        req.getRequestDispatcher("/pages/store/course/list.jsp").forward(req,resp);
    }
    //新增企业
    private void save(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据封装到指定对象中
        String id = UidUtil.getUid();
        Course course = BeanUtil.fillBean(req, Course.class,"yyyy-MM-dd");
        course.setId(id);
        course.setCreateTime(new Date());
        //调用服务层的save方法
        courseService.save(course);
        //查询所有
        findAll(req,resp);
    }

    //根据id查询
    private void toUpdate(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据
        String id = req.getParameter("id");
        //调用service层的findByid
        Course course = courseService.findByid(id);
        System.out.println(course);
        //将查询到的id放入作用域
        req.setAttribute("course",course);
        //请求转发到update.jsp
        req.getRequestDispatcher("/pages/store/course/update.jsp").forward(req,resp);
    }

    //修改
    private void update(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据封装到指定对象中
        Course course = BeanUtil.fillBean(req, Course.class,"yyyy-MM-dd");
        //调用服务层的update方法
        courseService.update(course);
        //查询所有
        findAll(req,resp);
    }

    //批量删除
    private void deleteByIds(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据
        String ids = req.getParameter("ids");
        //调用service层的deleteByIds方法
        boolean flag = courseService.deleteByIds(ids);
        //删除成功后查询所有
        if (flag){
            //查询所有
            findAll(req,resp);
        }
        //失败后跳转到错误页面
        else {
            req.getRequestDispatcher("/pages/error.jsp").forward(req,resp);
        }
    }
}
