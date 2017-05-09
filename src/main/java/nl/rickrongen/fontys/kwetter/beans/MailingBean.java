package nl.rickrongen.fontys.kwetter.beans;

import nl.rickrongen.fontys.kwetter.Domain.Kwet;
import nl.rickrongen.fontys.kwetter.interceptors.QualifiedEvent;

import javax.enterprise.event.Observes;
import javax.inject.Named;

/**
 * Created by rick on 4/10/17.
 */
@Named
public class MailingBean {
    public void listenToPost(@Observes @QualifiedEvent("PostKwet") Kwet kwet){
        System.out.println("Sending mail out for kwet with text " + kwet.getText());
    }
}
