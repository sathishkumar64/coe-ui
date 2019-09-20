package com.coeui.model;

public class ErrorResponse {
	private int status;
	private String message;
	private String detail;

	public ErrorResponse() {
		super();
	}

	public ErrorResponse(int status, String message, String detail) {
		super();
		this.status = status;
		this.message = message;
		this.detail = detail;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "ErrorResponse [status=" + status + ", message=" + message + ", detail=" + detail + "]";
	}

}