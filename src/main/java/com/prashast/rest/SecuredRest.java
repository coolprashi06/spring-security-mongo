package com.prashast.rest;

import com.google.gson.annotations.Since;
import com.prashast.dto.User;
import com.prashast.service.SecuredRestService;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Controller
@Path("/account")
public class SecuredRest {

    @Autowired
    SecuredRestService securedRestService;

    @GET
    @Path("/getUsersByLastName/{lastName}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUser(@PathParam("lastName") String lastName){
        /*
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(username!= null){
            User user = securedRestService.getUser(username);
            System.out.println(user.toString());
            return user;
        }else {
            return null;
        }
        */

        return securedRestService.findUsersByLastName(lastName);
    }
}
