package com.example.websocket.controller;

import com.example.websocket.context.SystemContextHolder;
import com.example.websocket.entity.User;
import com.example.websocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 控制页面跳转
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(){
        //System.out.print(SystemContextHolder.getUserContext().getUserId());
        return "login";
    }

    @RequestMapping(value = "main", method = RequestMethod.GET)
    public String main(Model model) {
        String userId = SystemContextHolder.getUserContext().getUserId();
        // 当前登录人信息
        User user = userService.findUserById(userId);
        // 所有用户列表
        List<User> userList = userService.listAll();
        model.addAttribute("user", user);
        model.addAttribute("userList", userList);
        model.addAttribute("token", SystemContextHolder.getUserContext().getToken());
        return "main";
    }

    /**
     * 到聊天的页面
     * @param model
     * @return
     */
    @RequestMapping(value = "chat", method = RequestMethod.GET)
    public String chat(Model model) {
        String userId = SystemContextHolder.getUserContext().getUserId();
        // 获取所有能聊天的人

        // 获取所有的房间

        return "chat";
    }

}
