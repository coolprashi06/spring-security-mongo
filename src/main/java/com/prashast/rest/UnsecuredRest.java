package com.prashast.rest;

import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Controller
@Path("/unsecured")
public class UnsecuredRest {

    @Path("/")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getUnsecuredContent(){
        return "hey, this is unsecured content";
    }
}
