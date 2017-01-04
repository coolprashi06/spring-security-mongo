package com.prashast.rest;

import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Controller
@Path("/admin")
public class AdminRest {

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String getAdminContent(){
        return "hey, you've reached admin page";
    }
}
