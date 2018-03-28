package com.example.websocket.dao;

import com.example.websocket.entity.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class TokenDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建token
     * @param token
     */
    public void createToken(Token token) {
        mongoTemplate.save(token);
    }

    /**
     * 获取用户有用的token
     * @param userId
     * @return
     */
    public Token findNormalToken(String userId) {
        Query query = Query.query(Criteria.where("userId").is(userId).and("expirationDate").gte(new Date()));
        return mongoTemplate.findOne(query, Token.class);
    }

    /**
     * 根据tokenId查询
     * @param token
     * @return
     */
    public Token findNormalTokenById(String token) {
        Criteria criteria = Criteria.where("token").is(token);
        criteria.and("expirationDate").gte(new Date());
        Query query = new Query(criteria);
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        return mongoTemplate.findOne(query, Token.class);
    }
}
