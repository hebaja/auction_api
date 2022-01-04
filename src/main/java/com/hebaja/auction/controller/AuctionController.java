package com.hebaja.auction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auction")
public class AuctionController {

    @GetMapping("register")
    public String register() {
        return "/auction/register.xhtml";
    }

    @GetMapping("auctioneer")
    public String list() {
        return "/auction/auctioneer.xhtml";
    }
    
    @GetMapping("command")
    public String command() {
        return "/auction/command.xhtml";
    }
 }
