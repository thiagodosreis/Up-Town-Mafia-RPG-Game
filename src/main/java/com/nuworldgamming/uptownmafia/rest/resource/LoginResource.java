package com.nuworldgamming.uptownmafia.rest.resource;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.nuworldgamming.uptownmafia.rest.domain.*;
import com.nuworldgamming.uptownmafia.rest.exception.ErrorMessage;
import com.nuworldgamming.uptownmafia.rest.exception.NotFoundException;


@Path("login")
public class LoginResource {
	
	// ConcurrentHashMap - A hash table supporting full concurrency of retrievals and 
	// adjustable expected concurrency for updates.
	static private Map<Integer, Login> loginDB = new ConcurrentHashMap<Integer, Login>();
	// An AtomicInteger is used in applications such as atomically incremented counters
	private static AtomicInteger idCounter = new AtomicInteger();
	
	@POST
	@Consumes({ "application/json" })
	public Login createPodcast(Login login){
		login.id = idCounter.incrementAndGet();
		loginDB.put(login.id, login);
		return loginDB.get(login.id);
	}
	
	@GET
	@Path("{id}")
	@Produces({ "application/json" })
	public Login getLogin(@PathParam("id") int id) {
		Login login = loginDB.get(id);
		
		if (login == null) {
			ErrorMessage errorMessage = new ErrorMessage("1001", "Login not found!", "http://localhost:8080/lab3/error1001.jsp",
					 Response.Status.NOT_FOUND);
			 throw new NotFoundException(errorMessage);
		 }
		
		return login;
	}
	
	@POST
	@Path("{id}")
	@Produces({ "application/json" })
	public Login autenticateLogin(@PathParam("id") int id, @QueryParam("user") String user, @QueryParam("password") String password) {
		Login current = loginDB.get(id);
		
		if (current != null && current.getUser().equals(user) && current.getPassword().equals(password))
		{
			current.token = new BigInteger(130, new SecureRandom()).toString(32);
			return current;
		}
		else{
			ErrorMessage errorMessage = new ErrorMessage("1001", "Login not found!", "http://localhost:8080/lab3/error1001.jsp",
					 Response.Status.UNAUTHORIZED);
			 throw new NotFoundException(errorMessage);
		 }
	}
	
	@GET
	@Produces({ "application/json" })
	public Collection<Login> getAll() {
		List<Login> loginList = new ArrayList<Login>(loginDB.values());
		return loginList;
	}
	
	@PUT
	@Path("{id}")
	@Consumes({ "application/json" })
	public Login updateLogin(@PathParam("id") int id, Login login) {
		Login current = loginDB.get(id);
		
		if(current != null)
		{
			current.setUser(login.getUser());
			current.setPassword(login.getPassword());
			current.setToken(login.getToken());
			current.setName(login.getName());
			
			loginDB.put(id, current);
			return loginDB.get(id);
		}
		else
		{
			ErrorMessage errorMessage = new ErrorMessage("1001", "Login not found!", "http://localhost:8080/lab3/error1001.jsp",
					 Response.Status.NOT_FOUND);
			 throw new NotFoundException(errorMessage);
		}
		
		
		
	}
	
	@DELETE
	@Path("{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public void deleteLogin(@PathParam("id") int id) {
	 loginDB.remove(id);
	}
}
