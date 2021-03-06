package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteService {

    PageBean<Route> routePageQuery(int cid,int pageSize,int currentPage,String rname);
    Route findOne(int rid);

}
