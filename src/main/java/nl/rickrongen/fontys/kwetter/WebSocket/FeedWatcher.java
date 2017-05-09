package nl.rickrongen.fontys.kwetter.WebSocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.rickrongen.fontys.kwetter.Domain.Kwet;
import nl.rickrongen.fontys.kwetter.Domain.User;
import nl.rickrongen.fontys.kwetter.Service.KwetterService;
import nl.rickrongen.fontys.kwetter.interceptors.Log;
import nl.rickrongen.fontys.kwetter.interceptors.QualifiedEvent;

import javax.annotation.Resource;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

/**
 * Created by rick on 4/12/17.
 */
@ServerEndpoint("/ws/feed/{username}")
@Log
public class FeedWatcher {

//    @Inject
//    private KwetterService service;
    private static final Map<String, List<Session>> userSessions = new HashMap<>();
    @Resource(lookup = "jms/KwetPostFactory")
    private ConnectionFactory jmsFactory;
    @Resource(lookup = "jms/KwetPost")
    private Queue queue;

    public void listenToPost(@Observes @QualifiedEvent("PostKwet") Kwet kwet) throws JsonProcessingException {
        //first build the data object
        ObjectMapper om = new ObjectMapper();
//        String kwetstr = om.writeValueAsString(kwet);
        WsMessage m = new WsMessage<>("newkwet", kwet);
        String message = om.writeValueAsString(m);//"{\"type\":\"newkwet\",\"data\":" + kwetstr + "}";

        //iterate over the followers of the sending user
        for (Iterator<String> it = kwet.getKwetBy().getFollowedBy().stream().map(User::getUsername).iterator(); it.hasNext(); ) {
            String follower = it.next();
            //Check if the user is active
            if (!userSessions.containsKey(follower)) continue;

            //If so, send a message to each active session
            for (Session s : userSessions.get(follower)) {
                try {
                    s.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException{
        System.out.println("Getting info from " + username);
        //make sure there are not concurrency issues
        synchronized (userSessions) {
            if (!userSessions.containsKey(username)) {
                userSessions.put(username, new ArrayList<>());
            }
            userSessions.get(username).add(session);
        }
    }

    @OnMessage
    public void textMessage(Session session, String msg, @PathParam("username") String username) throws IOException {
        ObjectMapper om = new ObjectMapper();
        JavaType kwetType = om.getTypeFactory().constructParametricType(WsMessage.class, DeserializableKwet.class);
        WsMessage<DeserializableKwet> kwet = om.readValue(msg, kwetType);

        if(!kwet.getType().equals("postKwet")){
            session.getBasicRemote().sendText("{\"type\":\"error\",\"text\":\"Only know postKwet command!\"}");
            return;
        }

        if(!kwet.getData().getUsername().equals(username) && !session.getUserPrincipal().getName().equals(username)){
            session.getBasicRemote().sendText("{\"type\":\"error\",\"text\":\"You may only send for your self, and must include login information!\"}");
            return;
        }

        JMSContext context = jmsFactory.createContext();
        JMSProducer producer = context.createProducer();
        producer.send(queue, username + ";" + kwet.getData().getText());
        context.close();
        //service.postKwet(service.getUser(username), kwet.getData().getText()); //NOTE a response will automatically be send when it is posted
    }

    @OnClose
    public void close(Session session, CloseReason reason, @PathParam("username") String username) {
        synchronized (userSessions){
            if(userSessions.get(username).size() == 1){
                userSessions.remove(username);
            }else {
                userSessions.get(username).remove(session);
            }
        }
    }
}
