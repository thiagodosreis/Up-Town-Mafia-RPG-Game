package com.nuworldgamming.uptownmafia.rest.exception;

import javax.ws.rs.core.Response;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ErrorMessage {
	public String code;
	public String description;
	public String link;
	public Response.Status status;
	
	 public ErrorMessage(String code,String description, String link, Response.Status status) {
	 this.code = code;
	 this.description = description;
	 this.link = link;
	 this.status = status;
	 }
}
