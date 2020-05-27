package com.tartu.library.common.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GreetingsController {

    @RequestMapping("/")
    @ResponseBody
    public String home(){
        return "Welcome to the Tartu Book Library";
    }
}
