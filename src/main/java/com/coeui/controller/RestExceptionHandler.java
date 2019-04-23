package com.coeui.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import com.coeui.model.ErrorResponse;

@RestControllerAdvice
public class RestExceptionHandler {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@ExceptionHandler(value = { IllegalArgumentException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> badRequest(Exception ex, HttpServletRequest request) {
		logger.error("Request raised: {}, Status: {}, Error Message: {}", request.getRequestURL(),
				HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getLocalizedMessage());
		
	}

	@ExceptionHandler(value = { ResourceAccessException.class })
	@ResponseStatus(HttpStatus.BAD_GATEWAY)
	public ErrorResponse ResourceAccessException(Exception ex, HttpServletRequest request) {
		logger.error("Request raised: {}, Status: {}, Error Message: {}", request.getRequestURL(),HttpStatus.BAD_GATEWAY.value(), ex.getLocalizedMessage());
		return new ErrorResponse(502, "Bad Gateway", "Network is unreachable");
	}

	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse unknownException(Exception ex, HttpServletRequest request) {
		logger.error("Request raised: {}, Status: {}, Error Message: {}", request.getRequestURL(),HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getLocalizedMessage());
		return new ErrorResponse(500, "Internal Server Error",ex.getLocalizedMessage());
	}

}
