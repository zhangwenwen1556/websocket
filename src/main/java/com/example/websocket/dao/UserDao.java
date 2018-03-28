package com.example.websocket.dao;

import com.example.websocket.entity.User;
import com.example.websocket.utils.MongoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao extends MongoUtil {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存
     * @param user
     */
    public void save(User user) {
        mongoTemplate.save(user);
    }

    public List<User> listAll(){
        Query query = new Query(Criteria.where("status").ne(User.STATUS_DELETE));
        return mongoTemplate.find(query, User.class);
    }

    /**
     * 根据用户名登录明查用户的详细信息
     * @param userName
     * @return
     */
    public User findOneByUserName(String userName) {
        Query query = new Query(Criteria.where("status").ne(User.STATUS_DELETE).and("userName").is(userName));
        return mongoTemplate.findOne(query, User.class);
    }

    /**
     * 查询用户根据Id
     * @param userId
     * @return
     */
    public User findOnByUserId(String userId, String... fieldNames) {
        Query query = new Query(Criteria.where("status").ne(User.STATUS_DELETE).and("id").is(userId));
        includeFields(query, fieldNames);
        return mongoTemplate.findOne(query, User.class);
    }

}
