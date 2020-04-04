package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();

    public void queryPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currentPageStr = req.getParameter("currentPage");
        String pageSizeStr = req.getParameter("pageSize");
        String cidStr = req.getParameter("cid");

        //turn String into int
        int currentPage=1;
        int pageSize = 5;
        int cid = 1;

        if(currentPageStr != null && currentPageStr.length() >0)
        {
            currentPage = Integer.parseInt(currentPageStr);
        }
        if(pageSizeStr != null && pageSizeStr.length() >0)
        {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if(cidStr != null && cidStr.length() >0)
        {
            cid = Integer.parseInt(cidStr);
        }

        PageBean<Route> pb = routeService.routePageQuery(cid,pageSize,currentPage);

        //write in json
        resp.setContentType("application/json;charset=utf-8");
        String json = writeInJson(pb);
        resp.getWriter().write(json);
    }
}
