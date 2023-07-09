package com.walczak.itpoker.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Redirector {
    @RequestMapping(value = "/join/{.*}")
    public String redirectPokerPage() {
        return "forward:/";
    }

    @RequestMapping(value = "/poker/{.*}")
    public String redirectErrorPage() {
        return "forward:/";
    }

    @RequestMapping(value = "")
    public String redirectJoinRoomPage() {
        return "forward:/";
    }

}
