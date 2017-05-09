package nl.rickrongen.fontys.kwetter.beans;

import nl.rickrongen.fontys.kwetter.Service.KwetterService;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by rick on 5/1/17.
 */
@MessageDriven(mappedName = "jms/KwetPost", activationConfig =  {
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class KwetPostBean implements MessageListener{

    @Inject
    private KwetterService service;

    @Override
    public void onMessage(Message message) {
        if(!(message instanceof TextMessage)){
            System.out.println(message.toString() + " is not a text message");
            return;
        }
        TextMessage tm = (TextMessage) message;
        String[] parts;
        try {
            parts = tm.getText().split(";", 2);
            tm.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
            return;
        }
        System.out.println(String.format("Posting kwet %s for user %s", parts[1], parts[0]));
        try{
            service.postKwet(service.getUser(parts[0]), parts[1]);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(String.format("Failed to post kwet %s for user %s", parts[1], parts[0]));
        }
    }
}
