package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * Find all info numbers in the db
     * @param cid
     * @param rname
     * @return
     */
    @Override
    public int findAllCount(int cid,String rname) {

        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if(cid != 0)
        {
            sb.append("and cid = ? ");
            params.add(cid);
        }
        if(rname != null && rname.length() > 0 && !rname.equals("null"))
        {
            sb.append("and rname like ? ");
            params.add("%"+rname+"%");
        }
        sql = sb.toString();
        return jdbcTemplate.queryForObject(sql,Integer.class,params.toArray());
    }


    /**
     * Return the infos of the current page
     * @param currentPage
     * @param pageSize
     * @param cid
     * @param rname
     * @return
     */
    @Override
    public List<Route> findCurrentRouteList(int currentPage, int pageSize, int cid,String rname) {
        String sql = "select * from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();
        if(cid != 0)
        {
            sb.append("and cid = ? ");
            params.add(cid);
        }
        if(rname != null && rname.length() > 0 && !rname.equals("null"))
        {
            sb.append("and rname like ? ");
            params.add("%"+rname+"%"); //注意这里的写法,不要把%%写到上面去了
        }

        sb.append("limit ?,?");
        params.add((currentPage-1)*pageSize);
        params.add(pageSize);

        List<Route> list =null;
        sql = sb.toString();
        try{
            list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
        }catch (Exception e)
        {
        }
        return list;
    }

    @Override
    public Route findOneByRid(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }

}
