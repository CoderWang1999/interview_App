package com.itheima.controller.store;

import com.github.pagehelper.PageInfo;
import com.itheima.controller.BaseServlet;
import com.itheima.domain.store.Catalog;
import com.itheima.domain.store.Question;
import com.itheima.utils.BeanUtil;
import com.itheima.utils.UidUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/store/Question/*")
public class QuestionServlet extends BaseServlet {
    //遍历所有
    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据得到当前页和每页显示条目数
        String pageNum = req.getParameter("pageNum");
        String pageSize = req.getParameter("pageSize");
        //调用service层的findAll
        List<Question> list = questionService.findAll(pageNum, pageSize);
        //将数据交由pageinfo处理
        PageInfo<Question> pageInfo = new PageInfo<Question>(list);
        //将pageinfo存入请求域
        req.setAttribute("page", pageInfo);
        //请求妆发到list.jsp
        req.getRequestDispatcher("/pages/store/question/list.jsp").forward(req, resp);
    }

    //新增
    private void toAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Catalog> catalogList = catalogService.list();
        req.setAttribute("catalogList", catalogList);
        req.getRequestDispatcher("/pages/store/question/add.jsp").forward(req, resp);

    }

    private void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据封装到指定对象中
        String id = UidUtil.getUid();
        Question Question = BeanUtil.fillBean(req, Question.class);
        Question.setId(id);
        Question.setCreateTime(new Date());
        //调用服务层的save方法
        questionService.save(Question);
        //查询所有
        findAll(req, resp);
    }

    //根据id查询
    private void toUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Catalog> catalogList = catalogService.list();
        req.setAttribute("catalogList", catalogList);
        //获取页面数据
        String id = req.getParameter("id");
        //调用service层的findByid
        Question Question = questionService.findByid(id);
        //将查询到的id放入作用域
        req.setAttribute("question", Question);
        //请求转发到update.jsp
        req.getRequestDispatcher("/pages/store/question/update.jsp").forward(req, resp);
    }

    //修改
    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据封装到指定对象中
        Question Question = BeanUtil.fillBean(req, Question.class);
        //调用服务层的update方法
        questionService.update(Question);
        //查询所有
        findAll(req, resp);
    }

    //批量删除
    private void deleteByIds(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取页面数据
        String ids = req.getParameter("ids");
        //调用service层的deleteByIds方法
        boolean flag = questionService.deleteByIds(ids);
        //删除成功后查询所有
        if (flag) {
            //查询所有
            findAll(req, resp);
        }
        //失败后跳转到错误页面
        else {
            req.getRequestDispatcher("/pages/error.jsp").forward(req, resp);
        }
    }
}
