package com.weizu.websocket;

import com.alibaba.fastjson.JSON;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.net.URI;
import java.util.*;

/**
 * websocket处理器：功能实现的核心代码编写类
 * @author cm
 */
public class MyWebSocketHandler implements WebSocketHandler {

    /**
     * 定义一个全局的初始化值count=0,记录连接数
     */
    private volatile static int count = 0;

    /**
     * 记录所有的客户端连接
     */
    private volatile static List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList());
    /**
     * 存储房间号对应的所有连接
     * Map<roomId, session>
     */
    private static Map<String, List<WebSocketSession>> sessionMap = new HashMap<>();

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String roomId = getRoomId(session);
        removeFromSessionMap(session);
        sessions.remove(session);
        try {
            if (session.isOpen()) {
                session.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        count--;
        // 实时推送房间人数
        sendRoomNumber(roomId);
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
        String roomId = getRoomId(session);
        addToSessionMap(roomId, session);
        System.out.println("总共连接数: "+sessions.size());
        // 实时推送房间人数
        sendRoomNumber(roomId);
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
        System.out.println("客户端: " + data);
        String roomId = getRoomId(session);
        sendMessageToRoom(roomId, data);
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
            String roomId = getRoomId(session);
            removeFromSessionMap(session);
            sessions.remove(session);
            session.close();
            // 实时推送房间人数
            sendRoomNumber(roomId);
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    // 获取roomId
    private String getRoomId(WebSocketSession session){
        URI url = session.getUri();
        String query = url.getQuery();
        System.out.println("url: "+ JSON.toJSONString(url));
        String roomId = query.substring(query.indexOf("roomId=")+7);
        return roomId;
    }

    // 将连接添加到SessionMap
    private synchronized void addToSessionMap(String roomId, WebSocketSession session){
        if(sessionMap.containsKey(roomId)){
            sessionMap.get(roomId).add(session);
        } else {
          List<WebSocketSession> list = new ArrayList<>();
          list.add(session);
          sessionMap.put(roomId, list);
        }
    }

    // 将连接从SessionMap删除
    private synchronized void removeFromSessionMap( WebSocketSession session){
        Set<String> keySet = sessionMap.keySet();
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            String roomId = it.next();
            if(sessionMap.containsKey(roomId)){
                List<WebSocketSession> list = sessionMap.get(roomId);
                list.remove(session);
            }
        }
    }

    // 给聊天室发消息
    private synchronized void sendMessageToRoom(String roomId, String data){
        if(sessionMap.containsKey(roomId)){
            List<WebSocketSession> list = sessionMap.get(roomId);
            for(WebSocketSession session : list){
                try {
                    if (session.isOpen()) {
                        synchronized (session) {
                            session.sendMessage(new TextMessage("" + data));
                            System.out.println("服务端: " + data);
                        }
                    } else {
                        sessions.remove(session);
                        removeFromSessionMap(session);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    removeFromSessionMap(session);
                    sessions.remove(session);
                }
            }
        }
    }

    // 发送房间人数
    private void sendRoomNumber(String roomId){
        if(sessionMap.containsKey(roomId)){
            List<WebSocketSession> list = sessionMap.get(roomId);
            for(WebSocketSession session : list){
                try {
                    if (session.isOpen()) {
                        synchronized (session) {
                            String data = "{ \"content\": " + list.size() + ",\"type\":\"roomNum\" }";
                            session.sendMessage(new TextMessage("" + data));
                            System.out.println("服务端: " + data);
                        }
                    } else {
                        sessions.remove(session);
                        removeFromSessionMap(session);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    removeFromSessionMap(session);
                    sessions.remove(session);
                }
            }
        }
    }

}
