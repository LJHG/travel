package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    CategoryService categoryService = new CategoryServiceImpl();

    public void getAllCategories(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Category> list = categoryService.findAll();
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(writeInJson(list));
    }

}
