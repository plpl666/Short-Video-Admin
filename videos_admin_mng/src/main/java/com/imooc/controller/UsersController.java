package com.imooc.controller;

import com.imooc.bean.AdminUser;
import com.imooc.pojo.Users;
import com.imooc.service.UsersService;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.PagedResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UsersController {

    @Resource
    private UsersService usersService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public IMoocJSONResult login(String username, String password,
                                 HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return IMoocJSONResult.errorMsg("用户名或密码不能为空!");
        } else if (username.equals("admin") && password.equals("admin")) {
            String token = UUID.randomUUID().toString();
            AdminUser user = new AdminUser(username,password,token);
            request.getSession().setAttribute("user",user);
            return IMoocJSONResult.ok();
        } else {
            return IMoocJSONResult.errorMsg("用户名或密码错误!");
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        return "login";
    }

    @GetMapping("/showUserList")
    public String showList() {
        return "users/usersList";
    }

    @PostMapping("/queryUserList")
    @ResponseBody
    public PagedResult queryUserList(Users user, Integer page) {
        return usersService.queryUserList(user,page,10);
    }

}
