package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    CategoryDao categoryDao = new CategoryDaoImpl();

    /**
     * User redis to optimise
     * @return
     */
    @Override
    public List<Category> findAll() {
        Jedis jedis = null;
        try{
            jedis = JedisUtil.getJedis();
        } catch (Exception e) {
            //didn't open redis or whatever
            System.out.println("没有开redis吧");
            return categoryDao.findAllCategory();
        }

        Set<String> categorySet = jedis.zrange("category",0,-1);
        if(categorySet==null || categorySet.size() == 0)
        {
            //Can't find in redis, find it in database and store it in redis
            List<Category> categoryList = categoryDao.findAllCategory();
            for (Category category : categoryList) {
                jedis.zadd("category",category.getCid(),category.getCname());
            }
            return categoryList;
        }
        else
        {
            //Find it in redis, turn Set to List
            List<Category> categoryList = new ArrayList<Category>();
            for (String s : categorySet) {
                Category temp = new Category();
                temp.setCname(s);
                //这里没有set id 无所谓吧
                categoryList.add(temp);
            }
            return categoryList;
        }
    }
}
