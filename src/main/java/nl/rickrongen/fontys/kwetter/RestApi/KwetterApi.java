package nl.rickrongen.fontys.kwetter.RestApi;

import nl.rickrongen.fontys.kwetter.Domain.User;
import nl.rickrongen.fontys.kwetter.Service.KwetterService;

import javax.inject.Inject;
import javax.ws.rs.*;

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
    public User getUserByName(@PathParam("username") String username){
        return service.getUser(username);
    }

    @POST
    @Path("user")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public boolean addUser(@FormParam("username") String username, @FormParam("password") String password, @FormParam("fullname") String fullname, @FormParam("location") String location, @FormParam("website") String website, @FormParam("biography") String biography){
        return service.addUser(username, fullname, password, location, website, biography);
    }

}
