package nl.rickrongen.fontys.kwetter.RestApi;

import nl.rickrongen.fontys.kwetter.Domain.Kwet;
import nl.rickrongen.fontys.kwetter.Domain.User;
import nl.rickrongen.fontys.kwetter.Service.KwetterService;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by rick on 3/13/17.
 */
@Path("/kwets")
public class KwetterKwetsApi {

    @Inject
    private KwetterService service;

    @GET
    @Path("/byuser/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public SuccesObject getKwetsByUser(@PathParam("username") String usersname,
                                       @QueryParam("start") @DefaultValue("0") int start,
                                       @QueryParam("count") @DefaultValue("10") int count){
        List<Kwet> kwetsOfUser = service.getKwetsOfUser(usersname, start, count);
        return new SuccesObject<>(kwetsOfUser != null, kwetsOfUser);
    }

    @GET
    @Path("/byid/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public SuccesObject getKwetById(@PathParam("id") int id){
        Kwet k = service.getKwetById(id);
        return new SuccesObject<>(k != null, k);
    }

    @POST
    @Path("/{username}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public SuccesObject postKwet(@PathParam("username") String username, @FormParam("text") String text){
        User u = service.getUser(username);
        if(u == null)
            return new SuccesObject<>(false, "User not found");
        Kwet k = service.postKwet(u, text);
        return new SuccesObject<>(k != null, k);
    }

}