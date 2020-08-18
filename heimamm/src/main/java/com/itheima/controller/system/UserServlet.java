package com.itheima.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.controller.BaseServlet;
import com.itheima.domain.system.Dept;
import com.itheima.domain.system.User;
import com.itheima.utils.BeanUtil;
import com.itheima.utils.UidUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/system/User/*")
public class UserServlet extends BaseServlet {
    //遍历所有用户信息
    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据得到当前页和每页显示条目数
        String pageNum = req.getParameter("pageNum");
        String pageSize = req.getParameter("pageSize");
        //调用service层的findAll
        List<User> list = userService.findAll(pageNum, pageSize);
        //将数据交由pageinfo处理
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        //将pageinfo存入请求域
        req.setAttribute("page", pageInfo);
        //请求妆发到list.jsp
        req.getRequestDispatcher("/pages/system/user/list.jsp").forward(req, resp);
    }

    //新增
    private void insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据并使用BeanUtil封装成Dept对象
        User user = BeanUtil.fillBean(req, User.class,"yyyy-MM-dd");
        //调用service层的insert
        userService.insert(user);
        //查询所有
        findAll(req, resp);
    }
    //批量删除
    private void deleteByids(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据
        String ids = req.getParameter("ids");
        //调用service层的deleteByids方法
        boolean flag = userService.deleteByids(ids);
        if (flag) {
            //删除成功之后查询所有
            findAll(req, resp);
        }else {
            //删除失败掉转到错误页面
            req.getRequestDispatcher("/pages/error.jsp").forward(req,resp);
        }
    }

    //根据id查询
    private void findById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据
        String id = req.getParameter("id");
        //调用service层的findById方法
        User user = userService.findById(id);
        //将查询结果存入请求域
        req.setAttribute("user",user);
        //请求转发到aupdatejsp
        req.getRequestDispatcher("/pages/system/user/update.jsp").forward(req,resp);
    }
    //根据id修改
    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据并封装成dept对象
        User user = BeanUtil.fillBean(req, User.class,"yyyy-MM-dd");
        System.out.println(user);
        //调用service层的update方法
        userService.update(user);
        //查询所有
        findAll(req,resp);
    }
}
