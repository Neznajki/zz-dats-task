package com.zz.dats.kindergarten.controller.rest.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    @GetMapping("")
    public ModelAndView homepage() {
        return new ModelAndView("layout");
    }
}
