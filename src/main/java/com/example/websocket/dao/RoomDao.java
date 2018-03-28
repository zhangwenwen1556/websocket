package com.example.websocket.dao;

import com.example.websocket.entity.Room;
import com.example.websocket.utils.MongoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class RoomDao extends MongoUtil{

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建房间
     * @param room
     */
    public void createRoom(Room room) {
        mongoTemplate.save(room);
    }

    /**
     * 根据Id修改
     * @param update
     * @param id
     */
    public void updateRoomById(Update update, String id) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(id)), update, Room.class);
    }

    /**
     * 根据ids查询
     * @param ids
     * @param fieldNames
     * @return
     */
    public List<Room> findByIds(Set<String> ids, String... fieldNames) {
        Query query = Query.query(Criteria.where("id").in(ids));
        includeFields(query, fieldNames);
        return mongoTemplate.find(query, Room.class);
    }
}
