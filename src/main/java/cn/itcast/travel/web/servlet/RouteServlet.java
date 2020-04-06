package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.MalformedParametersException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    public void queryPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currentPageStr = req.getParameter("currentPage");
        String pageSizeStr = req.getParameter("pageSize");
        String cidStr = req.getParameter("cid");
        String rname =  req.getParameter("rname");

        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        //turn String into int
        int currentPage=1;
        int pageSize = 5;
        int cid = 0;

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

        PageBean<Route> pb = routeService.routePageQuery(cid,pageSize,currentPage,rname);

        //write in json
        resp.setContentType("application/json;charset=utf-8");
        String json = writeInJson(pb);
        resp.getWriter().write(json);
    }

    public void findOne(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int rid = Integer.parseInt(req.getParameter("rid"));
        Route route = routeService.findOne(rid);

        String json = writeInJson(route);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }

    public void isFavorite(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int rid = Integer.parseInt(req.getParameter("rid"));
        User user  = (User) req.getSession().getAttribute("user");
        boolean flag = false;
        if(user != null)
        {
            int uid = user.getUid();
            flag =favoriteService.isFavorite(uid,rid);
        }

        String json = writeInJson(flag);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }

    public void addFavorite(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int rid = Integer.parseInt(req.getParameter("rid"));
        User user  = (User) req.getSession().getAttribute("user");
        int uid = user.getUid();
        favoriteService.addFavorite(uid,rid);
    }
}
