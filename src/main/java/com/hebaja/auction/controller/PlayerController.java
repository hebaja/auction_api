package com.hebaja.auction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/player")
public class PlayerController {

    @GetMapping("register")
    public String register() {
        return "/player/register.xhtml";
    }
    
    @GetMapping("command")
    public String command() {
    	return "/player/command.xhtml";
    }

 }
