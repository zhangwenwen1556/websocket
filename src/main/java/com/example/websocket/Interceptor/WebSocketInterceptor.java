package com.example.websocket.Interceptor;

import com.example.websocket.context.SystemContextHolder;
import com.example.websocket.context.UserSystemContext;
import com.example.websocket.entity.Token;
import com.example.websocket.service.TokenService;
import com.example.websocket.service.UserService;
import com.example.websocket.utils.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@Component
public class WebSocketInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    private static final Set<String> urlSet = CollectionUtil.asSet("/login", "/");

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String token = httpServletRequest.getParameter("token");
        System.out.print(httpServletRequest.getRequestURI());
        if (urlSet.contains(httpServletRequest.getRequestURI())) {
            return true;
        } else if (token != null) {
            // 需要对用户进行处理

            // 判断session是否过期 -- 暂时不做，系统所有的异常处理弄好了在做
            Token tokenQuery = tokenService.getTokenById(token);
            if (tokenQuery == null) {
                // TODO 报错，让重新登录
                return false;
            }
            UserSystemContext userSystemContext = new UserSystemContext();
            userSystemContext.setUserId(tokenQuery.getUserId());
            userSystemContext.setToken(token);
            SystemContextHolder.put(userSystemContext);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
