package com.rootech.msolver.common.mqtt;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/push/")
public class PushSocket {

    @OnMessage
    public void onMessage(String message, final Session session) {

        System.out.println("Message from " + session.getId() + ": " + message);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    session.getBasicRemote().sendText("Push");
                } catch (IOException ex) {
                    Logger.getLogger(PushSocket.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, 3 * 1000);
    }
}
