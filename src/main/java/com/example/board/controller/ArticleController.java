package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ArticleController {

    @GetMapping("articles")
    public String articles(ModelMap map){
        map.addAttribute("articles", List.of());
        return "articles/index";
    }

}
