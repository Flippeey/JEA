/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import Authentication.AuthAccessElement;
import Authentication.AuthLoginElement;
import domain.ApplicationUser;
import java.util.Properties;
import javax.annotation.security.PermitAll;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import service.KwetterService;

/**
 *
 * @author Flippey
 */
@Path("/auth")
@RequestScoped
@PermitAll
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationAPI {

    @Inject
    KwetterService authService;

    @POST
    @Path("/login")
    public AuthAccessElement login(@Context HttpServletRequest request, AuthLoginElement loginElement) {
        AuthAccessElement accessElement = authService.login(loginElement);
        System.out.println(request.getPathInfo());
        if (accessElement != null) {
            request.getSession().setAttribute(AuthAccessElement.PARAM_AUTH_ID, accessElement.getAuthId());
            request.getSession().setAttribute(AuthAccessElement.PARAM_AUTH_TOKEN, accessElement.getAuthToken());
        }
        return accessElement;
    }

    @POST
    @Path("/getSessionUser")
    public ApplicationUser getSessionUser(@Context HttpServletRequest request, AuthAccessElement auth) {
        System.out.println(request.getPathInfo());
        ApplicationUser u = authService.findByIdAndToken(auth.getAuthId(), auth.getAuthToken());
        System.out.println("find user: " + u.getId());
        return u;
    }

    @POST
    @Path("/importTweets")
    public String startTweetBatch() {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Properties props = new Properties();
        props.setProperty("jsonUrl", "https://copy.com/UocpHQmXKKaXoyTk");
        jobOperator.start("TweetJob", props);
        return "Started Batch";
    }
}
