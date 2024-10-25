package com.yosekit.creditmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "redirect:/applications/create";
    }

    @GetMapping("/_/loadFragment/{tabName}")
    public String loadFragment(@PathVariable String tabName) {
        return "fragments/" + tabName + " :: content";
    }
}
