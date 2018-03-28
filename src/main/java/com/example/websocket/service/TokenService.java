package com.example.websocket.service;

import com.example.websocket.dao.TokenDao;
import com.example.websocket.entity.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private TokenDao tokenDao;

    public String getToken(String userId) {
        /**
         * 创建token的时候需要检查一下是否有可用的token,
         * 如果有可用的token的话拿过来直接用，
         * 如果没有可用的token的话，直接创建一个新的
         */
        Token tokenQuery = tokenDao.findNormalToken(userId);
        if (tokenQuery == null) {
            Token token = new Token();
            String tokenId = UUID.randomUUID().toString();
            token.setToken(tokenId);
            token.setUserId(userId);
            token.setCreateTime(new Date());
            // 一个token的过期时间是一周

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, 7);
            token.setExpirationDate(calendar.getTime());
            tokenDao.createToken(token);
            return tokenId;
        }
        return tokenQuery.getToken() ;
    }

    /**
     * 根据tokenId查询token详情
     * @param tokenId
     * @return
     */
    public Token getTokenById(String tokenId) {
        return tokenDao.findNormalTokenById(tokenId);
    }
}
