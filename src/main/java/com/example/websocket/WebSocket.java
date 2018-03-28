package com.example.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@ServerEndpoint(value = "/websocket/{userid}/{useridjoin}")
@Component
public class WebSocket {

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static ConcurrentHashMap<String, Session> webSocketSet = new ConcurrentHashMap<String, Session>();

    // 房间对应的User  {roomId, [userId, userId]}
    private static ConcurrentHashMap<String, Set<String>> roomToUser = new ConcurrentHashMap<String, Set<String>>();

    // 用户对应的房间  {userId, roomId}
    private static ConcurrentHashMap<String, String> userToRoom = new ConcurrentHashMap<String, String>();

    //使用的roomService是在spring配置文件中创建了bean的id
    // private TikuService tikuService=(TikuService) ContextLoader.getCurrentWebApplicationContext().getBean("tikuService");

    @OnOpen
    public void onOpen(@PathParam("userid") String userid, @PathParam("useridjoin") String useridjoin, Session session, EndpointConfig config) {
        // 用户进来以后的房间处理
        room(userid, useridjoin, session);

    }

    // 用户进来以后的房间出来
    private void room(String userid, String useridjoin, Session session) {
        // 判断当前Id是否有房间
        if (!userToRoom.containsKey(userid)) {
            if (!"invite".equals(useridjoin) && userToRoom.containsKey(useridjoin)) {
                // 如果已经存在的话，说明该用户已经有session，房间已经创建，直接加入
                // 如果进入这里的话，说明这个人是来加入房间的
                String roomId = userToRoom.get(useridjoin);
                // 加入到房间中
                Set<String> userSet = roomToUser.get(roomId);
                if (userSet.size() == 2) {
                    // 如果有报错体系的话，给报错:"房间人数已满"
                    sendMessage("房间人数已满", session);
                    return;
                }
                userSet.add(userid);
                // 将加入的用户和房间绑定
                userToRoom.put(userid, roomId);
                // 将当前用户的session保存起来
                webSocketSet.put(userid, session);
            } else {
                // 如果进来的话说明当前用户是来创建房间的，
                // 为当前人创建房间Id
                String roomId = UUID.randomUUID().toString();
                userToRoom.put(userid, roomId);
                // 将当前人加入到房间中
                Set<String> userSet = new ConcurrentSkipListSet<>();
                userSet.add(userid);
                roomToUser.put(roomId, userSet);
                // 将用户的Session保存起来
                webSocketSet.put(userid, session);
                sendMessage(userid+"已创建并加入房间", session);
            }
        }

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        Map<String, String> map = session.getPathParameters();
        // 获取到用户的Id
        String userId = map.get("userid");
        // 去掉用户所使用的session
        webSocketSet.remove(userId);  //从set中删除
        // 去掉人物所在房间
        String roomId = userToRoom.get(userId);
        userToRoom.remove(userId);
        // 去掉当前人物的 id和房间对应
        Set<String> userIds = roomToUser.get(roomId);
        if (userIds.size() > 1) {
            // 如果大于1的话，说明房间里面有两个人，则将其中的一个人删除掉
            userIds.remove(userId);
        } else {
            // 如果房间里面只有一个人的话，则删除整个房间
            roomToUser.remove(roomId);
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        Map<String, String> map = session.getPathParameters();
        // 获取到用户的Id
        String userId = map.get("userid");
        // 获取到用户所在的房间
        String roomId = userToRoom.get(userId);
        // 获取到当前房间里面的所有人
        Set<String> userSet = roomToUser.get(roomId);
        // 循环给人发消息
        for (String user : userSet) {
            // 获取当前人在系统中保存的session
            Session sessionTo = webSocketSet.get(user);
            sendMessage(message, sessionTo);
        }

    }


    // 系统向房间里发送消息
    public void sysmessage(String roomId, String message){
        // 获取到当前房间里面的所有人
        Set<String> userSet = roomToUser.get(roomId);
        // 循环给人发消息
        for (String user : userSet) {
            // 获取当前人在系统中保存的session
            Session sessionTo = webSocketSet.get(user);
            sendMessage(message, sessionTo);
        }
    }

    // 给客户端发送信息
    private void sendMessage(String message, Session session) {
        session.getAsyncRemote().sendText(message);
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

}
