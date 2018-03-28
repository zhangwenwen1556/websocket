package com.example.websocket.dao;

import com.example.websocket.entity.UserRoom;
import com.example.websocket.utils.MongoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class RoomUserDao extends MongoUtil {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建用户房间
     * @param userRoom
     */
    public void save(UserRoom userRoom) {
        mongoTemplate.save(userRoom);
    }

    /**
     * 更新最后的修改时间
     * @param roomId
     */
    public void updateLastDate(String roomId, Update update){
        mongoTemplate.updateFirst(Query.query(Criteria.where("roomId").is(roomId)), update.set("lastUpdateDate", new Date()), UserRoom.class);
    }

    /**
     * 根据用户的Id获取所有的房间
     * @param userId
     */
    public void findByUserId(String userId, String... fieldNames) {
        Query query = Query.query(Criteria.where("userId").is(userId));
        query.with(new Sort(Sort.Direction.DESC, "lastUpdateDate"));
        includeFields(query, fieldNames);
        mongoTemplate.find(query, UserRoom.class);
    }
}
