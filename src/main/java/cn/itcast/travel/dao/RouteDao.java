package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    int findAllCount(int cid,String rname);
    List<Route> findCurrentRouteList(int currentPage,int pageSize,int cid,String rname);
    Route findOneByRid(int rid);
}
