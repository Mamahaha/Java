package org.led.study.javaxwebsocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class WebSocketController {
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("login")
    public String login(@RequestParam("user") String user, @RequestParam("password") String password) {
        System.out.println(user + " is trying to login with password " + password + "; session: " + request.getSession().getId());
        if (user.equals("xtp") && password.equals("xtp")) {
            return request.getSession().getId();
        } else {
            return null;
        }
    }
}
