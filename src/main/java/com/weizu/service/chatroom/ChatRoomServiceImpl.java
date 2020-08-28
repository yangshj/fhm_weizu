package com.weizu.service.chatroom;

import com.weizu.dao.chatroom.ChatRoomDao;
import com.weizu.pojo.chatroom.ChatRoomBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 *
 * @author : 杨帅军 (shuaijun.yang@ucarinc.com)
 * @since : 2020/8/28 16:55:04
 **/
@Service
public class ChatRoomServiceImpl implements ChatRoomService{

    @Autowired
    private ChatRoomDao chatRoomDao;

    @Override
    public Integer insertChatRoom(ChatRoomBean bean) throws Exception {
        return chatRoomDao.insertChatRoom(bean);
    }

    @Override
    public Integer updateChatRoom(ChatRoomBean bean) throws Exception {
        return chatRoomDao.updateChatRoom(bean);
    }

    @Override
    public void deleteChatRoom(ChatRoomBean bean) throws Exception {
        chatRoomDao.deleteChatRoom(bean);
    }

    @Override
    public ChatRoomBean getChatRoomById(Long id) throws Exception {
        return chatRoomDao.getChatRoomById(id);
    }

    @Override
    public List<ChatRoomBean> getChatRoomByCondition(ChatRoomBean bean) throws Exception {
        return chatRoomDao.getChatRoomByCondition(bean);
    }

    @Override
    public List<ChatRoomBean> getAllChatRoom(ChatRoomBean bean) throws Exception {
        return chatRoomDao.getAllChatRoom(bean);
    }
}
