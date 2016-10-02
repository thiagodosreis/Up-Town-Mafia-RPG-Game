package com.nuworldgamming.uptownmafia.rest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class HelloMafia {
	 @GET
	 @Produces(MediaType.TEXT_HTML)
	 public String sayHtmlHello() {
		 return "Hello Guys! Welcome to Uptown Mafia API!";
	}
}
