package com.coeui.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.auth0.AuthenticationController;
import com.coeui.security.AppConfig;


@Controller
public class LoginController {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

  
    @Autowired
    private AuthenticationController controller;

    @Autowired
    private AppConfig appConfig;
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    protected String login(final HttpServletRequest req) {
        logger.debug("Performing login");
        
     //   String redirectUri = req.getScheme() + "://" + req.getServerName();
        
        logger.info(" req.getScheme() {}:::::::::::", req.getScheme()); 
        
        String redirectUri =    "https://" + req.getServerName();
        
        logger.info("redirectUri::::::before {}::::::::::::::::",redirectUri);
        
        if ((req.getScheme().equals("http") && req.getServerPort() != 80) || (req.getScheme().equals("https") && req.getServerPort() != 443)) {
            redirectUri += ":" + req.getServerPort();
        }
        
        redirectUri += "/callback";
    
        logger.info("redirectUri after {}::::::::::::::::",redirectUri);
        
        String authorizeUrl = controller.buildAuthorizeUrl(req, redirectUri)
               // .withAudience(String.format("https://%s/userinfo", appConfig.getDomain()))
                .withAudience(String.format("http://coe.demo.com/api/v1/coe/", appConfig.getDomain()))                
                .withScope("openid profile read:school write:school read:getstudentbyschool")
               //.withScope("openid profile email")
                .build();
        return "redirect:" + authorizeUrl;
    }

}
