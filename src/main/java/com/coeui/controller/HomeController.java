package com.coeui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.coeui.security.TokenAuthentication;


@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    protected String home(final Model model, final Authentication authentication) {
       
    	logger.info("Index page....");
        if (authentication instanceof TokenAuthentication) { 
            TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;
            model.addAttribute("profile", tokenAuthentication.getClaims());
        }
        return "index";
    }
}
