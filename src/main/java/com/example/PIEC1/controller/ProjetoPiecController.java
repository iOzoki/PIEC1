package com.example.PIEC1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProjetoPiecController {

    @GetMapping("/home")
    public ModelAndView getHome() {
        ModelAndView view = new ModelAndView();
        view.setViewName("index");
        return view;
    }
}
