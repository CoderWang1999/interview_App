package com.itheima.controller.store;

import com.github.pagehelper.PageInfo;
import com.itheima.controller.BaseServlet;
import com.itheima.domain.store.Catalog;
import com.itheima.domain.store.Company;
import com.itheima.domain.store.Question;
import com.itheima.utils.BeanUtil;
import com.itheima.utils.UidUtil;
import com.mysql.jdbc.StringUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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
        List<Company> companyList = companyService.list();
        req.setAttribute("companyList", companyList);
        List<Catalog> catalogList = catalogService.list();
        req.setAttribute("catalogList", catalogList);
        req.getRequestDispatcher("/pages/store/question/add.jsp").forward(req, resp);
    }

    private void save(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //1.确认该操作是否支持文件上传操作，enctype="multipart/form-data"
        if (ServletFileUpload.isMultipartContent(req)) {
            //2.创建磁盘工厂对象
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //3.Servlet文件上传核心对象
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            //4.从request中读取数据
            List<FileItem> fileItems = fileUpload.parseRequest(req);
            // --处理form表单提交过来的普通数据
            //将数据获取到，封装成一个对象
            Question Question = BeanUtil.fillBean(fileItems, Question.class);
            // --处理form表单提交过来的文件数据
            for (FileItem item : fileItems) {
                //5.当前表单是否是文件表单
                if (!item.isFormField()) {
                    //判断前端页面有无上传文件
                    if (StringUtils.isNullOrEmpty(item.getName())) {
                        //没有就把图片设为null 跳出本次循环
                        Question.setPicture(null);
                        continue;
                    } else {
                        String picture = UidUtil.getUid();
                        //  从临时存储文件的地方将内容写入到指定位置
                        item.write(new File(this.getServletContext().getRealPath("upload"), picture));
                        Question.setPicture(picture);
                    }
                }
            }
            //调用业务层接口save
            questionService.save(Question);
        }
        //查询所有
        findAll(req, resp);
    }

    //修改
    private void toUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Company> companyList = companyService.list();
        req.setAttribute("companyList", companyList);
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

    private void update(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        //1.确认该操作是否支持文件上传操作，enctype="multipart/form-data"
        if (ServletFileUpload.isMultipartContent(req)) {
            //2.创建磁盘工厂对象
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //3.Servlet文件上传核心对象
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            //4.从request中读取数据
            List<FileItem> fileItems = fileUpload.parseRequest(req);
            // --处理form表单提交过来的普通数据
            //将数据获取到，封装成一个对象
            Question Question = BeanUtil.fillBean(fileItems, Question.class);
            System.out.println("000000000000000000000000000000");
            System.out.println(Question.getPicture());
            // --处理form表单提交过来的文件数据
            for (FileItem item : fileItems) {
                //5.当前表单是否是文件表单
                if (!item.isFormField()) {
                    //判断前端页面有无上传文件
                    if (StringUtils.isNullOrEmpty(item.getName())) {
                        //跳出本次循环
                        continue;
                    } else {
                        //图片名由UUID提供
                        String picture = UidUtil.getUid();
                        //  从临时存储文件的地方将内容写入到指定位置
                        item.write(new File(this.getServletContext().getRealPath("upload"), picture));
                        Question.setPicture(picture);
                    }
                }
            }
            System.out.println("=============================");
            System.out.println(Question);
            //调用业务层update方法
            questionService.update(Question);
            System.out.println("1111111111111111111111111111111");
        }
        /*        //获取页面数据封装到指定对象中
        Question Question = BeanUtil.fillBean(req, Question.class);
        //调用服务层的update方法
        questionService.update(Question);*/
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
    private void downloadReport(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //返回的数据类型为文件xlsx类型
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = new String("测试文件名.xlsx".getBytes(),"iso8859-1");
        response.addHeader("Content-Disposition","attachment;fileName="+fileName);

        //生成报告的文件，然后传递到前端页面
        ByteArrayOutputStream os = questionService.getReport();
        //获取产生响应的流对象
        ServletOutputStream sos = response.getOutputStream();
        //将数据从原始的字节流对象中提取出来写入到servlet对应的输出流中
        os.writeTo(sos);
        //将输出流刷新
        sos.flush();
        os.close();
    }
}
