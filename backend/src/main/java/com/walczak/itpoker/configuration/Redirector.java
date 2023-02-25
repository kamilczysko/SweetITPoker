package com.walczak.itpoker.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Redirector {
//    @GetMapping(value = "/join/{roomId}")
//    public String redirectPokerPage(@PathVariable("roomId") String roomId) {
//        return "forward:/";
//    }

    @RequestMapping(value = "/ohno")
    public String redirectErrorPage() {
        return "forward:/";
    }

    @RequestMapping(value = "/")
    public String redirectJoinRoomPage() {
        return "forward:/";
    }

//    @RequestMapping(value = "/game/")
//    public String redirectGameRoomPage() {
//        return "forward:/";
//    }

}
