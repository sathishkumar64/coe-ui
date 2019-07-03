package com.coeui.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.coeui.security.TokenAuthentication;


@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
	@Value(value = "${apikey}")
	private String apikey;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    protected String home(final Model model, final Authentication authentication,HttpSession session) {
       
    	logger.info("Index page....");
    	
    	logger.info("Api key {}:::::::::::",apikey);
    	
    	session.setAttribute("x-api-key",apikey);
    	
        if (authentication instanceof TokenAuthentication) { 
            TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;
            model.addAttribute("profile", tokenAuthentication.getClaims());
        }
        return "index";
    }
}
