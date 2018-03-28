package com.example.websocket.controller;

import com.example.websocket.entity.User;
import com.example.websocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提供数据支持
 */
@RestController
public class JsonController {

    @Autowired
    private UserService userService;

    /**
     * 用户后台登录
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public User login(@RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password) {
        return userService.login(userName, password);
    }

    /**
     * 添加User
     * @param user
     */
    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public User addUser(User user) {
        return userService.save(user);
    }
}
