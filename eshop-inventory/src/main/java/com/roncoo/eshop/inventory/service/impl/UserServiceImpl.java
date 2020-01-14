package com.roncoo.eshop.inventory.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.eshop.inventory.dao.RedisDao;
import com.roncoo.eshop.inventory.mapper.UserMapper;
import com.roncoo.eshop.inventory.model.User;
import com.roncoo.eshop.inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private RedisDao redisDao;


    @Override
    public User findUserInfo() {
        return userMapper.findUserInfo();
    }

    @Override
    public User getCacheUserInfo() {
        redisDao.set("cached_user","{\"age\":25,\"name\",\"lisi\"}");

        String cached_user = redisDao.get("cached_user");
        JSONObject userJsonObject = JSONObject.parseObject(cached_user);
        User user= new User();
        user.setName(userJsonObject.getString("name"));
        user.setAge(userJsonObject.getInteger("age"));
        return user;
    }
}
