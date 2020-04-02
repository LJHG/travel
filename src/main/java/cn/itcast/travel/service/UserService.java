package cn.itcast.travel.service;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;

public interface UserService {
    /**
     * register user
     * @param user
     * @return
     */
    public boolean RegisterUser(User user);
    public boolean activate(String code);

    ResultInfo login(String username, String password);
}
