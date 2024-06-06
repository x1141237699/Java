package com.xiao.oasystem.webSocket;

import cn.hutool.json.JSONUtil;
import com.xiao.oasystem.pojo.entity.SocketMessage;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Component
@ServerEndpoint("/chat/{userName}")
public class WebSocket {

    private String userName;

    private Session session;

    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();

    public synchronized static void publicMessage(String userName,String message,boolean flag) {
        for (WebSocket item : webSocketSet) {
            Session session = item.session;
            if (flag){
                session.getAsyncRemote().sendText(message);
            }else {
                Session currentUser = sessionMap.get(userName);
                if (!session.getId().equals(currentUser.getId())){
                    session.getAsyncRemote().sendText(message);
                }
            }
        }
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userName") String userName) {
        this.userName = userName;
        this.session = session;
        sessionMap.put(userName, session);
        webSocketSet.add(this);
    }

    @OnMessage
    public void onMessage(String message) {
        if (JSONUtil.isTypeJSONObject(message)) {
            SocketMessage socketMessage = JSONUtil.toBean(message, SocketMessage.class);
            publicMessage(socketMessage.getSendOutUser(),socketMessage.getSendOutUser()+": "+socketMessage.getMessage(),false);
        }
    }

    @OnClose
    public void onClose() {
        if (sessionMap.containsKey(userName)) {
            sessionMap.remove(userName);
            webSocketSet.remove(this);
        }
    }

    @OnError
    public void onError(Throwable error) {
        log.error(error.getMessage());
    }
}
