package com.itheima.controller.store;

import com.itheima.controller.BaseServlet;
import com.itheima.domain.store.QuestionItem;
import com.itheima.utils.BeanUtil;
import com.itheima.utils.UidUtil;
import com.mysql.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@WebServlet("/store/questionItem/*")
public class QuestionItemServlet extends BaseServlet {
    //遍历所有
    private void findAll(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据
        String questionId = req.getParameter("questionId");
        //调用service层的findAll
        List<QuestionItem> list = questionItemService.findAll(questionId);
        //将查询结果放入域对象
        req.setAttribute("questionId",questionId);
        req.setAttribute("list",list);
        for (QuestionItem questionItem : list) {
        }
        //请求妆发到list.jsp
        req.getRequestDispatcher("/pages/store/questionItem/list.jsp").forward(req,resp);
    }
    //新增
    private void save(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据封装到指定对象中
            String id = UidUtil.getUid();
            QuestionItem questionItem = BeanUtil.fillBean(req, QuestionItem.class);
            questionItem.setId(id);
            questionItem.setPicture(null);
            //调用服务层的save方法
            questionItemService.save(questionItem);
        //查询所有
        findAll(req,resp);
    }

    //根据id查询
    private void toUpdate(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据
        String id = req.getParameter("id");
        //调用service层的findByid
        QuestionItem questionItem = questionItemService.findByid(id);
        //将查询到的id放入作用域
        req.setAttribute("questionItem",questionItem);
        //查询所有
        findAll(req,resp);
    }

    private void saveOrUpdate(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //将数据获取到，封装成一个对象
        QuestionItem questionItem = BeanUtil.fillBean(request,QuestionItem.class);
        System.out.println(questionItem.getId());
        //如果页面传递了当前数据的id，则为修改业务，否则为添加业务
        if(StringUtils.isNullOrEmpty(questionItem.getId())){
            save(request,response);
        }else {
            update(request,response);
        }
    }
    //修改
    private void update(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据封装到指定对象中
        QuestionItem questionItem = BeanUtil.fillBean(req, QuestionItem.class);
        //调用服务层的update方法
        questionItemService.update(questionItem);
        //查询所有
        findAll(req,resp);
    }

    //批量删除
    private void deleteByIds(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据
        String ids = req.getParameter("ids");
        //调用service层的deleteByIds方法
        boolean flag = questionItemService.deleteByIds(ids);
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
