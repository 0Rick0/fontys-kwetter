package nl.rickrongen.fontys.kwetter.beans;

import nl.rickrongen.fontys.kwetter.Domain.Kwet;
import nl.rickrongen.fontys.kwetter.Domain.User;
import nl.rickrongen.fontys.kwetter.Service.KwetterService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import static nl.rickrongen.fontys.kwetter.Util.*;

/**
 * Created by rick on 3/31/17.
 */
@RequestScoped
@Named("user")
public class UserBean {

    @Inject
    private KwetterService service;

    public String getUsername(){
        Map<String, String> requestParameters = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if(requestParameters.containsKey("username")){
            return requestParameters.get("username");
        }
        Principal userPrincipal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if(userPrincipal == null){
            throw new IllegalStateException("No username parameter or logged in user!");
        }
        return userPrincipal.getName();
    }

    public User getUser(){
        return service.getUser(getUsername());
    }

    public List<Kwet> getKwetList(){
        Map<String, String> requestParameters = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int start = 0, count = 20;
        if(requestParameters.containsKey("start"))
            start = tryParseInt(requestParameters.get("start"), start);
        if(requestParameters.containsKey("count"))
            count = tryParseInt(requestParameters.get("count"), count);

        return service.getKwetsOfUser(getUsername(), start, count);
    }
}
