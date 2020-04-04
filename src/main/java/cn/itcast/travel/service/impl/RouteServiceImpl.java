package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

import javax.sound.sampled.LineUnavailableException;
import java.util.List;

public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao = new RouteDaoImpl();

    /**
     * Give three params and return PageBean
     * @param cid
     * @param pageSize
     * @param currentPage
     * @return
     */
    @Override
    public PageBean<Route> routePageQuery(int cid, int pageSize, int currentPage) {
        //find all counts in dao
        int totalCount = routeDao.findAllCount(cid);
        //find all infos in dao
        List<Route> list= routeDao.findCurrentRouteList(currentPage,pageSize,cid);

        //set properties
        PageBean<Route> pb = new PageBean<Route>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        pb.setList(list);
        pb.setTotalCount(totalCount);
        pb.setTotalPages((totalCount%pageSize==0)?(totalCount/pageSize):((totalCount/pageSize) +1));

        return pb;
    }
}
