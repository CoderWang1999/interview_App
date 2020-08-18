package com.itheima.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.service.store.Company.CompanyService;
import com.itheima.service.store.Company.CompanyServiceImpl;
import com.itheima.service.store.Course.CourseService;
import com.itheima.service.store.Course.CourseServiceImpl;
import com.itheima.service.store.Question.QuestionService;
import com.itheima.service.store.Question.QuestionServiceImpl;
import com.itheima.service.store.catalog.CatalogService;
import com.itheima.service.store.catalog.CatalogServiceImpl;
import com.itheima.service.system.User.UserService;
import com.itheima.service.system.User.UserServiceImpl;
import com.itheima.service.system.dept.DeptService;
import com.itheima.service.system.dept.DeptServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

//抽取Servlet
public class BaseServlet extends HttpServlet {

    //初始化数据，只加载一次
    public ObjectMapper objectMapper;
    public CompanyService companyService;
    public DeptService deptService;
    public UserService userService;
    public CourseService courseService;
    public CatalogService catalogService;
    public QuestionService questionService;
    @Override
    public void init() throws ServletException {
        companyService=new CompanyServiceImpl();
        deptService = new DeptServiceImpl();
        objectMapper = new ObjectMapper();
        userService = new UserServiceImpl();
        courseService=new CourseServiceImpl();
        catalogService=new CatalogServiceImpl();
        questionService=new QuestionServiceImpl();
    }

    //根据url执行方法
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的URL
        StringBuffer url = req.getRequestURL();
        //截取URL获取方法名
        String methodName = url.substring(url.lastIndexOf("/") + 1);

        try {
            //获取方法对象
            Method method = this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //暴力反射
            method.setAccessible(true);
            //执行方法
            method.invoke(this,req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
