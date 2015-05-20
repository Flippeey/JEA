/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import domain.ApplicationUser;
import domain.Follow;
import domain.Tweet;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import service.KwetterService;

/**
 *
 * @author Flippey
 */
@Path("users")
@Produces("application/json")
@Consumes("application/json")
@PermitAll
@RequestScoped
public class ZwetserRestAPI {

    @Inject
    private KwetterService kwetterService;

    @GET
    @RolesAllowed("USER")
    public List<ApplicationUser> getUsers() {
        System.out.println("get all users");
        return kwetterService.findAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("USER")
    public ApplicationUser getUser(@PathParam("id") long id) {
        ApplicationUser u = kwetterService.find(id);
        System.out.println("find user: " + u.getId());
        return u;
    }

    @GET
    @Path("/{id}/tweets")
    @RolesAllowed("USER")
    public List<Tweet> getTweetsByUser(@PathParam("id") long id) {
        System.out.println("get tweets by user: " + id);
        return kwetterService.getTweetsByUser(id);
    }

    @GET
    @Path("alltweets")
    @RolesAllowed("MODERATOR")
    public List<Tweet> getAllTweets() {
        System.out.println("get all tweets");
        return kwetterService.getAllTweets();
    }

    @GET
    @Path("/{id}/mentions")
    @RolesAllowed("USER")
    public List<Tweet> getMentions(@PathParam("id") String id) {
        System.out.println("get mentions by user: " + id);
        return kwetterService.getMentions(id);
    }

    @GET
    @Path("/{id}/timeline")
    @RolesAllowed("USER")
    public List<Tweet> getTimeLine(@PathParam("id") long id) {
        System.out.println("get tweets by user: " + id);
        return kwetterService.getTimeLine(id);
    }

    @POST
    @Path("/{id}/addTweet")
    @RolesAllowed("USER")
    public void addTweet(@PathParam("id") long id, Tweet t) {
        System.out.println("tweet: " + t.getPostedBy());
        kwetterService.addTweet(t);
    }

    @POST
    @Path("/{id}/addFollow")
    @RolesAllowed("USER")
    public void addFollow(@PathParam("id") long id, Follow f) {
        System.out.println("follow: " + f.toString());
        kwetterService.addFollow(f);
    }

    @GET
    @Path("/{id}/following")
    @RolesAllowed("USER")
    public List<ApplicationUser> getFollowing(@PathParam("id") long id) {
        System.out.println("get following by user: " + id);
        return kwetterService.getFollowing(id);
    }

    @GET
    @Path("/{id}/followers")
    @RolesAllowed("USER")
    public List<ApplicationUser> getFollowers(@PathParam("id") long id) {
        System.out.println("get followers by user: " + id);
        return kwetterService.getFollowers(id);
    }

    @GET
    @Path("/{id}/countfollowers")
    @RolesAllowed("USER")
    public int countFollowers(@PathParam("id") long id) {
        System.out.println("count followers by user: " + id);
        return kwetterService.countFollowers(id);
    }

    @GET
    @Path("/{id}/countFollowing")
    @RolesAllowed("USER")
    public int countFollowing(@PathParam("id") long id) {
        System.out.println("count following by user: " + id);
        return kwetterService.countFollowing(id);
    }

    @GET
    @Path("/{id}/countTweets")
    @RolesAllowed("USER")
    public int countTweets(@PathParam("id") long id) {
        System.out.println("count tweets by user: " + id);
        return kwetterService.countTweets(id);
    }

    @POST
    @Path("/{id}/deleteFollow")
    @RolesAllowed("USER")
    public void deleteFollow(@PathParam("id") long id, Follow f) {
        System.out.println("remove follow: " + f.toString());
        kwetterService.removeFollow(f);
    }

    @RolesAllowed({"ADMIN"})
    @DELETE
    @Path("/{id}/deleteUser")
    public void removeUser(ApplicationUser u) {
        System.out.println("remove user: " + u.getName());
        kwetterService.removeUser(u);
    }

    @RolesAllowed({"MODERATOR"})
    @POST
    @Path("/{id}/deleteTweet")
    public void removeTweet(@PathParam("id") long id, Tweet t) {
        System.out.println("remove tweet: " + t.getScreenName());
        kwetterService.removeTweet(t);
    }

    @PUT
    @Path("/{id}/updateTweet")
    @RolesAllowed("USER")
    public void updateTweet(@PathParam("id") long id, Tweet t) {
        System.out.println("update tweet: " + t.getMessage());
        kwetterService.updateTweet(t);
    }
}
