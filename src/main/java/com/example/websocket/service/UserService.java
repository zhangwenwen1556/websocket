package com.example.websocket.service;

import com.example.websocket.dao.UserDao;
import com.example.websocket.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private static final Md5PasswordEncoder md5 = new Md5PasswordEncoder();

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenService tokenService;

    /**
     * 创建用户
     * @param user
     */
    public User save(User user){
        user.setId(UUID.randomUUID().toString());
        user.setPassword(md5.encodePassword(user.getPassword(), null));
        user.setStatus(User.STATUS_NORMAL);
        user.setCreateTime(new Date());
        userDao.save(user);
        return user;
    }

    /**
     * 列表
     */
    public List<User> listAll() {
        return userDao.listAll();
    }

    public User login(String userName, String password) {
        User user = userDao.findOneByUserName(userName);
        if (user == null) {
            // 报错，账户不存在
            return null;
        } else if (!user.getPassword().equals(md5.encodePassword(password, null))) {
            // 报错，密码错误
            return null;
        }
        // 获取用户的token
        String token = tokenService.getToken(user.getId());
        user.setToken(token);
        return user;
    }

    public User findUserById(String userId) {
        User user = userDao.findOnByUserId(userId, "name", "userName", "nickName", "sex");
        // 清空密码
        user.setPassword(null);
        return user;
    }

}
