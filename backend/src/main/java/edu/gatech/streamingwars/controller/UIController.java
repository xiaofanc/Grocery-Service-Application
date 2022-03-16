package edu.gatech.streamingwars.controller;

import edu.gatech.streamingwars.service.StoreRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UIController {

    @Autowired
    StoreRpcService storeRpcService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "index";
    }

    @GetMapping("/customermain")
    public String customermain() {
        return "customermain";
    }

    @GetMapping("/managermain")
    public String managermain() {
        return "managermain";
    }

    @GetMapping("/pilotmain")
    public String pilotmain() {
        return "pilotmain";
    }
}


