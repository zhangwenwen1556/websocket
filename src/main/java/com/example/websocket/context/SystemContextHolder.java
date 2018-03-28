package com.example.websocket.context;

public class SystemContextHolder {

    public static ThreadLocal<UserSystemContext> userThread = new ThreadLocal<>();

    /**
     * 设置用户线程变量
     *
     * @param userContext 线程变量
     */
    public static void put(UserSystemContext userContext) {
        userThread.set(userContext);
    }

    /**
     * @return 用户线程变量
     */
    public static UserSystemContext getUserContext() {
        return userThread.get();
    }

}
