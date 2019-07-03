package com.coeui.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.Tokens;
import com.auth0.jwt.JWT;
import com.coeui.security.TokenAuthentication;

@Controller
public class CallbackController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	

	
	@Autowired
	private AuthenticationController controller;
	

	private final String redirectOnFail;
	
	private final String redirectOnSuccess;

	public CallbackController() {
		this.redirectOnFail = "/login";
		this.redirectOnSuccess = "/home";
	}

	@RequestMapping(value = "/callback", method = RequestMethod.GET)
	protected void getCallback(final HttpServletRequest req, final HttpServletResponse res, HttpSession session)
			throws ServletException, IOException {
		handle(req, res, session);
	}

	@RequestMapping(value = "/callback", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	protected void postCallback(final HttpServletRequest req, final HttpServletResponse res, HttpSession session)
			throws ServletException, IOException {
		handle(req, res, session);
	}

	private void handle(HttpServletRequest req, HttpServletResponse res, HttpSession session) throws IOException {
		try {
			Tokens tokens = controller.handle(req);
			
			logger.info("Access token {}:::::::::::",tokens.getAccessToken());
			logger.info("Id token {}:::::::::::",tokens.getIdToken());		
			session.setAttribute("accesstoken", tokens.getAccessToken());		

			TokenAuthentication tokenAuth = new TokenAuthentication(JWT.decode(tokens.getIdToken()));

			SecurityContextHolder.getContext().setAuthentication(tokenAuth);
			res.sendRedirect(redirectOnSuccess);
		} catch (AuthenticationException | IdentityVerificationException e) {
			e.printStackTrace();
			SecurityContextHolder.clearContext();
			res.sendRedirect(redirectOnFail);
		}
	}

}
