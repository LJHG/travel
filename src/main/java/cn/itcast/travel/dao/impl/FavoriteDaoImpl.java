package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Favorite findFavoriteByUidAndRid(int uid, int rid) {
        String sql = "select * from tab_favorite where uid = ? and rid = ?";
        Favorite favorite = null;
        try {
            favorite = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Favorite>(Favorite.class),uid,rid);
        } catch (DataAccessException e) {
        }
        return favorite;

    }

    @Override
    public int findFavoriteCountByRid(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ?";
        return jdbcTemplate.queryForObject(sql,Integer.class,rid);
    }

    @Override
    public void addFavorite(int uid, int rid) {
        String sql = "insert into tab_favorite values(?,?,?)";
        try {
            jdbcTemplate.update(sql,rid,new Date(),uid);
        } catch (DataAccessException e) {
            //避免重复插入时报错
        }
    }
}
