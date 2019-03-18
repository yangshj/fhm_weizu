package com.weizu.websocket;

import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * websocket处理器：功能实现的核心代码编写类
 * @author cm
 */
public class MyWebSocketHandler implements WebSocketHandler {

    /**
     * 定义一个全局的初始化值count=0,记录连接数
     */
    private static int count = 0;

    /**
     * 记录所有的客户端连接
     */

    private volatile static List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList());


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        sessions.remove(session);
        if (session.isOpen()){
            session.close();
        }
        count = count-1;
        System.out.println(count);
    }

    /**
     * 建立连接后的操作
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        count++;
        sessions.add(session);
        session.sendMessage(new TextMessage("你好"));
        System.out.println("服务端--你好");

    }

    /**
     * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String data = message.getPayload().toString();
        System.out.println("客户端" + message.getPayload().toString());
        try {
                if (session.isOpen()) {
                    synchronized (session) {
                        session.sendMessage(new TextMessage("" + data));
                        System.out.println("服务端" + data);
                    }

                } else {
                    sessions.remove(session);
                }
        } catch (IOException e) {
            e.printStackTrace();
            sessions.remove(session);
        }
    }

    /**
     * 消息传输错误处理
     * @param session
     * @param throwable
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
        if(session.isOpen()){
            sessions.remove(session);
            session.close();
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


}
