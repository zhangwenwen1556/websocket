package com.example.websocket.job;

import com.example.websocket.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 项目完成以后执行的job
 */
@Configuration
public class BeforeStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // 判断系统有没有管理原账户
        createAdminAccount();
    }

    /**
     * 判断管理员账户是否存在，如果不存在的话就创建
     */
    private void createAdminAccount(){
        Query query = new Query(Criteria.where("status").ne(User.STATUS_DELETE).and("userRole").is(User.ROLE_ADMIN));
        List<User> userList = mongoTemplate.find(query, User.class);
        // 如果没有的话，就创建一个管理员账号
        if (userList == null || userList.size() == 0) {
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setUserName("zhangwenwen");
            Md5PasswordEncoder md5 = new Md5PasswordEncoder();
            user.setPassword(md5.encodePassword("112233", null));
            user.setStatus(User.STATUS_NORMAL);
            user.setUserRole(User.ROLE_ADMIN);
            user.setName("张文文");
            user.setNickName("疙瘩");
            user.setSex("男");
            user.setCreateTime(new Date());
            mongoTemplate.save(user);
        }
    }

}
