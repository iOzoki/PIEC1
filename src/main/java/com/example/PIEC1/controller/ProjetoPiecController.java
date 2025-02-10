package com.example.PIEC1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProjetoPiecController {

    @GetMapping("/")
    public ModelAndView getHome() {
        ModelAndView view = new ModelAndView("index");
        return view;
    }
    @GetMapping("/application")
    public ModelAndView getApplication() {
        ModelAndView view = new ModelAndView("application");
        return view;
    }
}
