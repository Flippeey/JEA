/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websockets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Flippey
 */
@ServerEndpoint("/echo-socket/{pathParam}")
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Singleton
public class ZwetserWebsocketEndpoint {

     private static final Logger LOG = Logger.getLogger(ZwetserWebsocketEndpoint.class.getName());
    static {
        LOG.setLevel(Level.ALL);
    }
    
    /** {@code this}' EJB-view, for e.g. {@link Asynchronous} method invocation */
    @EJB
    private ZwetserWebsocketEndpoint delegate;
    
    /**
     * Callback for 'open'-event.
     * @param session the websocket's session
     * @param conf get the encoders/decoders
     * @param pathParam for demo purposes only: a parameter obtained from the path
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig conf, @PathParam("pathParam") String pathParam) {
        System.out.println("OPENED THE SHITTY SEBWOCKET");
        LOG.log(Level.FINE, "opened session {0}, pathParam={1}", new Object[]{session,pathParam});
    }

    
    /**
     * @param runnable the runnable to run
     * @param times the number of times to repeat
     * @param delay the delay between each run
     */
    @Asynchronous
    public void runRepeatedly(Runnable runnable, int times, long delay){
        try {
            Thread.sleep(delay);
            runnable.run();
            if(1<times){
                delegate.runRepeatedly(runnable, times-1, (long) 1.3*delay);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ZwetserWebsocketEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Echoes back the message 5 times.
     * @param session
     * @param message 
     */
    @OnMessage
    public void onMessage(final Session session, final String message) {
        final Runnable runnable = new Runnable(){
            @Override
            public void run() {
                try {
                    session.getBasicRemote().sendObject(message);
                } catch (IOException | EncodeException t) {
                    LOG.log(Level.SEVERE, "error in asynchronous runnable execution", t);
                }
            }
        };
        //invoke on this' EJB view, otherwise we have no asynch invocation
        delegate.runRepeatedly(runnable, 5, 1500);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        LOG.log(Level.SEVERE, "an error occured in session " + session, error);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("CLOSED THE SHITTY SEBWOCKET");
        LOG.log(Level.FINE, "closed session {0}", session);
    }
    
}
