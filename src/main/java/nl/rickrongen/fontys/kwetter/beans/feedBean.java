package nl.rickrongen.fontys.kwetter.beans;

import nl.rickrongen.fontys.kwetter.Domain.Kwet;
import nl.rickrongen.fontys.kwetter.Domain.User;
import nl.rickrongen.fontys.kwetter.Service.KwetterService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static nl.rickrongen.fontys.kwetter.Util.*;

/**
 * Created by rick on 3/22/17.
 */
@Named("feedBean")
@RequestScoped
public class feedBean {

    @Inject
    private KwetterService service;

    private String username;
    private User user;

    public feedBean() {
        Principal userPrincipal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if(userPrincipal!=null)
            username = userPrincipal.getName();
        else
            username = null;
    }

    public List<Kwet> getFeed() {
        if(username == null)
            return Collections.emptyList();

        Map<String, String> requestParameters = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int start = 0, count = 25;

        if(requestParameters.containsKey("start"))
            start = tryParseInt(requestParameters.get("start"), start);
        if(requestParameters.containsKey("count"))
            start = tryParseInt(requestParameters.get("count"), count);
        return service.getUserFeed(getUser(), start, count);  //todo improve
    }

    public List<Kwet> getMentions(){
        if(username == null)
            return Collections.emptyList();

        Map<String, String> requestParameters = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int start = 0, count = 25;

        if(requestParameters.containsKey("start"))
            start = tryParseInt(requestParameters.get("start"), start);
        if(requestParameters.containsKey("count"))
            start = tryParseInt(requestParameters.get("count"), count);
        return service.getMentions(getUser(), start, count);  //todo improve
    }


    public List<Kwet> getKwetsInTag() {
        Map<String, String> requestParameters = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (!requestParameters.containsKey("tag")) {
            return Collections.emptyList();
        }

        int start = 0, count = 25;

        if(requestParameters.containsKey("start"))
            start = tryParseInt(requestParameters.get("start"), start);
        if(requestParameters.containsKey("count"))
            start = tryParseInt(requestParameters.get("count"), count);

        return service.getKwetsByTag(requestParameters.get("tag"), start, count);
    }

    public List<String> getTrendingTags() {
        return service.getTrendingKwets();
    }

    public User getUser() {
        if (user == null)
            user = service.getUser(username);
        return user;
    }

    public String getUsername() {
        return username;
    }

    public void logout() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        ec.redirect(ec.getApplicationContextPath() );
    }

    public boolean isAdmin() {
        return getUser() != null && getUser().getGroups().stream().anyMatch(group -> group.getName().equals("admins"));
    }
}
