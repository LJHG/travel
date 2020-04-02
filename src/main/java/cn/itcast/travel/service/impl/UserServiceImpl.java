package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl  implements UserService {


    private UserDao userDao = new UserDaoImpl();
    /**
     * register user
     * @param user
     * @return
     */
    @Override
    public boolean RegisterUser(User user) {
        //在数据库中查询是否有已存在的用户
        User queryUser = userDao.FindUser(user);
        if(queryUser != null)
        {
            //already exists
            return false;
        }
        else{
            //set the status to N
            user.setStatus("N");

            //generate and set the code
            String code = UuidUtil.getUuid();
            user.setCode(code);

            userDao.SaveUser(user);

            //send the email
            String content = "<a href='http://localhost/travel/activateUserServlet?code="+code+"'>激活邮箱</a>";
            MailUtils.sendMail("674478778@qq.com",content,"Activate your account!");
            return true;
        }

    }

    /**
     * Activate the account according to the code
     * if code does not exist return false
     * else return true
     * @param code
     * @return
     */
    @Override
    public boolean activate(String code) {
        User user = null;
        user  = userDao.findUserByCode(code);
        if(user == null)
        {
            return false;
        }
        else
        {
            //set the user's status to Y
            return userDao.setUserStatus(user);
        }
    }

    /**
     * use username and password to login
     * @param username
     * @param password
     * @return
     */
    @Override
    public ResultInfo login(String username, String password) {
        User user = userDao.findUserByUsername(username);
        ResultInfo resultInfo = new ResultInfo();
        if(user == null)
        {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户不存在");
            return resultInfo;
        }
        else if(user.getStatus() == null || user.getStatus().equals("N"))
        {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户未激活");
            return resultInfo;
        }
        else if( !user.getPassword().equals(password))
        {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("密码错误");
            return resultInfo;
        }
        else
        {
            resultInfo.setFlag(true);
            resultInfo.setData(user);
            return resultInfo;
        }

    }
}
