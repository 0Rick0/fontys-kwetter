package nl.rickrongen.fontys.kwetter.RestApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.rickrongen.fontys.kwetter.Domain.Kwet;
import nl.rickrongen.fontys.kwetter.Domain.User;
import nl.rickrongen.fontys.kwetter.Service.KwetterService;
import nl.rickrongen.fontys.kwetter.interceptors.Log;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

/**
 * Created by rick on 3/13/17.
 */
@Path("/kwets")
@Log
@DeclareRoles({"users", "admins"})
public class KwetterKwetsApi {

    private static final String[] extraUser = new String[]{"user", "/user/{username}"};
    private static final String[] extraKwet = new String[]{"kwet", "/kwets/byid/{id}"};
    private static final String[] extraTag = new String[]{"bytag", "/kwets/bytag/{tag}"};
    private static final String[] extraTrends = new String[]{"trends", "/kwets/trends"};
    private static final String[] extraLike = new String[]{"like", "/kwets/like/{id}"};
    private static final String[] extraSearch = new String[]{"search", "/kwets/search?query={query}"};

    @Inject
    private KwetterService service;
    @Context
    UriInfo uriInfo;
    private URI normalUri;

    @GET
    @Path("/byuser/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public SuccesObject getKwetsByUser(@PathParam("username") String usersname,
                                       @QueryParam("start") @DefaultValue("0") int start,
                                       @QueryParam("count") @DefaultValue("10") int count) {
        List<Kwet> kwetsOfUser = service.getKwetsOfUser(usersname, start, count);
        SuccesObject<List<Kwet>> succesObject = new SuccesObject<>(kwetsOfUser != null, kwetsOfUser);
        succesObject.setSelf(getSelfUrl());
        succesObject.setNext(normalUri.getPath() + String.format("?start=%d&count=%d", start + count, count));
        succesObject.setPrevious(normalUri.getPath() + String.format("?start=%d&count=%d", Math.max(start - count, 0), count));
        succesObject.addExtra(extraUser, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraKwet, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraLike, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraSearch, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraTag, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraTrends, uriInfo.getBaseUriBuilder());
        return succesObject;
    }

    @GET
    @Path("/feed/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public SuccesObject getFeedOfUser(@PathParam("username") String username,
                                      @QueryParam("start") @DefaultValue("0") int start,
                                      @QueryParam("count") @DefaultValue("10") int count) {
        User u = service.getUser(username);
        List<Kwet> feed = service.getUserFeed(u, start, count);
        SuccesObject<List<Kwet>> succesObject = new SuccesObject<>(true, feed);
        succesObject.setSelf(getSelfUrl());
        succesObject.setNext(normalUri.getPath() + String.format("?start=%d&count=%d", start + count, count));
        succesObject.setPrevious(normalUri.getPath() + String.format("?start=%d&count=%d", Math.max(start - count, 0), count));
        succesObject.addExtra(extraUser, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraKwet, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraLike, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraSearch, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraTag, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraTrends, uriInfo.getBaseUriBuilder());
        return succesObject;
    }

    @GET
    @Path("/byid/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public SuccesObject getKwetById(@PathParam("id") int id) {
        Kwet k = service.getKwetById(id);
        SuccesObject<Kwet> succesObject = new SuccesObject<>(k != null, k);
        succesObject.setSelf(getSelfUrl());
        succesObject.addExtra(extraUser, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraLike, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraSearch, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraTag, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraTrends, uriInfo.getBaseUriBuilder());
        return succesObject;
    }

    @GET
    @Path("/search")
    @Produces("application/json")
    public SuccesObject searchKwet(@QueryParam("query") String query,
                                   @QueryParam("start") @DefaultValue("0") int start,
                                   @QueryParam("count") @DefaultValue("10") int count) {
        if (query == null || query.isEmpty())
            return new SuccesObject<>(false, "No query Specified!");
        List<Kwet> kwets = service.searchKwets(query, start, count);
        SuccesObject<List<Kwet>> succesObject = new SuccesObject<>(kwets != null, kwets);
        succesObject.setSelf(getSelfUrl());
        succesObject.setNext(normalUri.getPath() + String.format("?query=%s&start=%d&count=%d", query, start + count, count));
        succesObject.setPrevious(normalUri.getPath() + String.format("?query=%s&start=%d&count=%d", query, Math.max(start - count, 0), count));
        succesObject.addExtra(extraUser, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraKwet, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraLike, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraTag, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraTrends, uriInfo.getBaseUriBuilder());
        return succesObject;
    }

    @GET
    @Path("/bytag/{tag}")
    @Produces(MediaType.APPLICATION_JSON)
    public SuccesObject getKwetsByTag(@PathParam("tag") String tag,
                                      @QueryParam("start") @DefaultValue("0") int start,
                                      @QueryParam("count") @DefaultValue("10") int count) {
        List<Kwet> found = service.getKwetsByTag(tag, start, count);
        SuccesObject<List<Kwet>> succesObject = new SuccesObject<>(found != null, found);
        succesObject.setSelf(getSelfUrl());
        succesObject.setNext(normalUri.getPath() + String.format("?start=%d&count=%d", start + count, count));
        succesObject.setPrevious(normalUri.getPath() + String.format("?start=%d&count=%d", Math.max(start - count, 0), count));
        succesObject.addExtra(extraUser, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraKwet, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraLike, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraSearch, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraTrends, uriInfo.getBaseUriBuilder());
        return succesObject;
    }

    @GET
    @Path("/like/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("users")
    public SuccesObject likeKwet(@PathParam("id") int id, @Context SecurityContext ctx) {
        if (ctx.getUserPrincipal() == null || ctx.getUserPrincipal().getName() == null)
            return new SuccesObject<>(false, "You are not logged in!");
        String username = ctx.getUserPrincipal().getName();//, @PathParam("username") String username
        Kwet k = service.getKwetById(id);
        User u = service.getUser(username);
        if (u == null || k == null)
            return new SuccesObject<>(false, "User or kwet not found!");
        SuccesObject<Boolean> succesObject = new SuccesObject<>(true, service.likeKwet(u, k));
        succesObject.setSelf(getSelfUrl());
        succesObject.addExtra(extraUser, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraKwet, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraSearch, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraTag, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraTrends, uriInfo.getBaseUriBuilder());
        return succesObject;
    }

    @GET
    @Path("/trends")
    @Produces("application/json")
    public SuccesObject getTrends() {
        SuccesObject<List<String>> succesObject = new SuccesObject<>(true, service.getTrendingKwets());
        succesObject.setSelf(getSelfUrl());
        succesObject.addExtra(extraUser, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraKwet, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraLike, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraSearch, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraTag, uriInfo.getBaseUriBuilder());
        return succesObject;
    }

    @Resource(lookup = "jms/KwetPostFactory")
    private ConnectionFactory jmsFactory;
    @Resource(lookup = "jms/KwetPost")
    private Queue queue;

    @POST
    @Path("/")
//    @Path("/{username}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("users")
    public SuccesObject postKwet(@FormParam("text") String text, @Context SecurityContext ctx) throws JsonProcessingException {
        if (ctx.getUserPrincipal() == null || ctx.getUserPrincipal().getName() == null)
            return new SuccesObject<>(false, "You are not logged in!" + new ObjectMapper().writeValueAsString(ctx));
        String username = ctx.getUserPrincipal().getName();//@PathParam("username") String username,

        JMSContext context = jmsFactory.createContext();
        JMSProducer producer = context.createProducer();
        producer.send(queue, username + ";" + text);
        context.close();
        SuccesObject<Object> succesObject = new SuccesObject<>(true, null);
        succesObject.setSelf(getSelfUrl());
        succesObject.addExtra(extraUser, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraKwet, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraLike, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraSearch, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraTag, uriInfo.getBaseUriBuilder());
        succesObject.addExtra(extraTrends, uriInfo.getBaseUriBuilder());
        return succesObject;
        //replace with JMS
        /*
        User u = service.getUser(username);
        if(u == null)
            return new SuccesObject<>(false, "User not found");
        Kwet k = service.postKwet(u, text);
        return new SuccesObject<>(k != null, k);*/
    }

    private String getSelfUrl(){
        normalUri = uriInfo.getRequestUri();
        if(normalUri.getQuery() == null)
            return normalUri.getPath();
        return normalUri.getPath() + "?" + normalUri.getQuery();
    }

}
