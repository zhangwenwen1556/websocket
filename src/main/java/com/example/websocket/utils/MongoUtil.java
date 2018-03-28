package com.example.websocket.utils;

import org.springframework.data.mongodb.core.query.Query;

public class MongoUtil {

    protected void includeFields(Query query, String... fieldNames) {
        for (String fieldName : fieldNames) {
            query.fields().include(fieldName);
        }
    }
}
