package nl.rickrongen.fontys.kwetter.beans;

import nl.rickrongen.fontys.kwetter.Domain.Kwet;
import nl.rickrongen.fontys.kwetter.Domain.User;
import nl.rickrongen.fontys.kwetter.Service.KwetterService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by rick on 3/27/17.
 */
@Named("poster")
@RequestScoped
public class KwetBean {

    @Inject
    private KwetterService service;

    private String text;
    private String result;
    private Kwet newKwet;

    public void postKwet(){
        String username = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        User user = service.getUser(username);
        if(user == null){
            result = "post.fail";
            return;
        }
        Kwet kwet = service.postKwet(user, text);

        if(kwet != null){
            newKwet = kwet;
            result = "post.success";
            text = ""; //clear form
            return;
        }
        result = "post.fail";
    }

    public String getResult() {
        return result;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
