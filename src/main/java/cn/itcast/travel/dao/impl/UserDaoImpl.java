package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User FindUser(User user) {

        String sql = "select * from tab_user where username =?";

        User target = null;
        try {
            target = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),user.getUsername());
        } catch (Exception e) {
        }
        return target;
    }

    @Override
    public void SaveUser(User user) {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
        return;
    }

    @Override
    public User findUserByCode(String code) {
        String sql = "select * from tab_user where code = ?";
        User user  = null;
        try {
            user = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),code);
        } catch (Exception e) {

        }

        return user;


    }

    /**
     * Set user's status to 'Y'
     * If nothing goes wrong, return true
     * @param user
     * @return
     */
    @Override
    public boolean setUserStatus(User user) {
        try {
            String sql = "update tab_user set status = 'Y' where uid = ?";
            jdbcTemplate.update(sql,user.getUid());
            return true;
        } catch (Exception e) {
            return false;
        }


    }


    /**
     * Find user by username
     * @param username
     * @return
     */
    @Override
    public User findUserByUsername(String username) {
        String sql = "select * from tab_user where username =?";
        User ans = null;
        try{
            ans = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username);
        } catch (Exception e) {

        }
        return ans;

    }


}
