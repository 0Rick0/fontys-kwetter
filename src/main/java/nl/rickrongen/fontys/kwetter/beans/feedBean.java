package nl.rickrongen.fontys.kwetter.beans;

import nl.rickrongen.fontys.kwetter.Domain.Kwet;
import nl.rickrongen.fontys.kwetter.Service.KwetterService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by rick on 3/22/17.
 */
@Named("feedBean")
@RequestScoped
public class feedBean {

    @Inject
    private KwetterService service;

    public List<Kwet> getFeed(){
        String username = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        return service.getUserFeed(service.getUser(username), 0, 10);  //todo improve
    }

    public List<String> getTrendingTags(){
        return service.getTrendingKwets();
    }
}
