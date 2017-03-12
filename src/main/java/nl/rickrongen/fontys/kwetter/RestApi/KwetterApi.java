package nl.rickrongen.fontys.kwetter.RestApi;

import nl.rickrongen.fontys.kwetter.Domain.User;
import nl.rickrongen.fontys.kwetter.Service.KwetterService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by rick on 3/8/17.
 */
@Path("api/kwetter")
public class KwetterApi {

    @Inject
    KwetterService service;

    @GET
    @Produces("text/plain")
    public String getClicheMessage(){
        return "Kwetter API root, nothing to do here :)";
    }

    @GET
    @Path("user/{username}")
    @Produces("application/json")
    public SuccesObject getUserByName(@PathParam("username") String username){
        try{
            User u = service.getUser(username);
            return new SuccesObject<>(u != null, u);
        }catch (Exception e){
            return new SuccesObject<>(false, e);
        }
    }

    @GET
    @Path("user/{username}/follow/{target}")
    @Produces("application/json")
    public SuccesObject follow(@PathParam("username") String username, @PathParam("target") String toFollow){
        try{
            User u = service.getUser(username);
            User target = service.getUser(toFollow);
            return new SuccesObject<>(u != null && target != null && service.toggleFollow(u, target), u);
        }catch (Exception e){
            return new SuccesObject<>(false, e);
        }
    }

    @POST
    @Path("user")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public SuccesObject addUser(@FormParam("username") String username, @FormParam("password") String password, @FormParam("fullname") String fullname, @FormParam("location") String location, @FormParam("website") String website, @FormParam("biography") String biography){
        return new SuccesObject<>(service.addUser(username, fullname, password, location, website, biography), null);
    }

}
