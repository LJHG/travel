package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    private JdbcTemplate jdbcTemplate =  new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * Find all categories in the database
     * @return
     */
    @Override
    public List<Category> findAllCategory()
    {
        String sql = "select * from tab_category";
        List<Category> list = null;
        try{
            list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Category>(Category.class));
        } catch (Exception e) {

        }

        return list;
    }



}
