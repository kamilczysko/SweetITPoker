package com.walczak.itpoker.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Redirector {
    @RequestMapping(value = "/join/{.*}")
    public String redirectPokerPage() {
        return "forward:/";
    }

    @RequestMapping(value = "")
    public String redirectErrorPage() {
        return "forward:/";
    }

    @RequestMapping(value = "/create")
    public String redirectJoinRoomPage() {
        return "forward:/";
    }

    @RequestMapping(value = "/game")
    public String redirectGameRoomPage() {
        return "forward:/";
    }

}
