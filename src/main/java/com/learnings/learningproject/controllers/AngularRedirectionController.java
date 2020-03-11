package com.learnings.learningproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AngularRedirectionController {

    @RequestMapping(path = "/site")
    public String redirect() {
        return "forward:/site/index.html";
    }
}
