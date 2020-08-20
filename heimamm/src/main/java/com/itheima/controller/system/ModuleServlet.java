package com.itheima.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.controller.BaseServlet;
import com.itheima.domain.system.Module;
import com.itheima.utils.BeanUtil;
import com.itheima.utils.UidUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/system/module/*")
public class ModuleServlet extends BaseServlet {
    //遍历所有企业信息
    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据得到当前页和每页显示条目数
        String pageNum = req.getParameter("pageNum");
        String pageSize = req.getParameter("pageSize");
        //调用service层的findAll
        List<Module> list = moduleService.findAll(pageNum, pageSize);
        //将数据交由pageinfo处理
        PageInfo<Module> pageInfo = new PageInfo<Module>(list);
        //将pageinfo存入请求域
        req.setAttribute("page", pageInfo);
        //请求妆发到list.jsp
        req.getRequestDispatcher("/pages/system/module/list.jsp").forward(req, resp);
    }

    //新增
    private void insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取唯一id
        String id = UidUtil.getUid();
        //获取页面数据并使用BeanUtil封装成Module对象
        Module module = BeanUtil.fillBean(req, Module.class);
        module.setId(id);
        //调用service层的insert
        moduleService.insert(module);
        //查询所有
        findAll(req, resp);
    }

    //不带分页的遍历
    private void list(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //调用service层的list方法
        List<Module> moduleList = moduleService.list();
        //将list存入请求域
        req.getSession().setAttribute("moduleList",moduleList);
        //请求转发
        resp.sendRedirect(req.getContextPath()+"/pages/system/module/add.jsp");
    }

    //批量删除
    private void deleteByids(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据
        String ids = req.getParameter("ids");
        //调用service层的deleteByids方法
        boolean flag = moduleService.deleteByids(ids);
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
        Module module = moduleService.findById(id);
        //将查询结果存入请求域
        req.setAttribute("module",module);
        //调用service层的list方法
        List<Module> moduleList = moduleService.list();
        //将list存入会话域
        req.setAttribute("moduleList",moduleList);
        //请求转发到updatejsp
        req.getRequestDispatcher("/pages/system/module/update.jsp").forward(req,resp);
    }
    //根据id修改
    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据并封装成module对象
        Module module = BeanUtil.fillBean(req, Module.class);
        //调用service层的update方法
        moduleService.update(module);
        //查询所有
        findAll(req,resp);
    }
}
