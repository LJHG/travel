package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteDaoImpl implements RouteDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * Find all info numbers in the db
     * @param cid
     * @return
     */
    @Override
    public int findAllCount(int cid) {
        String sql = "select count(*) from tab_route where cid=?";
        return jdbcTemplate.queryForObject(sql,Integer.class,cid);
    }


    /**
     * Return the infos of the current page
     * @param currentPage
     * @param pageSize
     * @param cid
     * @return
     */
    @Override
    public List<Route> findCurrentRouteList(int currentPage, int pageSize, int cid) {
        String sql = "select * from tab_route where cid=? limit ?,?";
        List<Route> list =null;
        try{
            list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),cid,(currentPage-1)*pageSize,pageSize);
        }catch (Exception e)
        {

        }
        return list;
    }
}
