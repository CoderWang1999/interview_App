package com.itheima.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.controller.BaseServlet;
import com.itheima.domain.system.Role;
import com.itheima.utils.BeanUtil;
import com.itheima.utils.UidUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/system/role/*")
public class RoleServlet extends BaseServlet {
    //遍历所有企业信息
    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据得到当前页和每页显示条目数
        String pageNum = req.getParameter("pageNum");
        String pageSize = req.getParameter("pageSize");
        //调用service层的findAll
        List<Role> list = roleService.findAll(pageNum, pageSize);
        //将数据交由pageinfo处理
        PageInfo<Role> pageInfo = new PageInfo<Role>(list);
        //将pageinfo存入请求域
        req.setAttribute("page", pageInfo);
        //请求妆发到list.jsp
        req.getRequestDispatcher("/pages/system/role/list.jsp").forward(req, resp);
    }

    //新增
    private void insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取唯一id
        String id = UidUtil.getUid();
        //获取页面数据并使用BeanUtil封装成Role对象
        Role role = BeanUtil.fillBean(req, Role.class);
        role.setId(id);
        //调用service层的insert
        roleService.insert(role);
        //查询所有
        findAll(req, resp);
    }

    //不带分页的遍历
    private void list(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //调用service层的list方法
        List<Role> list = roleService.list();
        //将查询结果封装到JSON对象中
        String Json = objectMapper.writeValueAsString(list);
        //将Json数据带到页面
        resp.getWriter().write(Json);
    }

    //批量删除
    private void deleteByids(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据
        String ids = req.getParameter("ids");
        //调用service层的deleteByids方法
        boolean flag = roleService.deleteByids(ids);
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
        Role role = roleService.findById(id);
        //将查询结果存入请求域
        req.setAttribute("role",role);
        //请求转发到updatejsp
        req.getRequestDispatcher("/pages/system/role/update.jsp").forward(req,resp);
    }
    //根据id修改
    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据并封装成role对象
        Role role = BeanUtil.fillBean(req, Role.class);
        //调用service层的update方法
        roleService.update(role);
        //查询所有
        findAll(req,resp);
    }
}
