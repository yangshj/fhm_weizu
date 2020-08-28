package com.weizu.service.chatroom;

import com.weizu.dao.chatroom.ChatRoomMemberDao;
import com.weizu.pojo.chatroom.ChatRoomMemberBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 *
 * @author : 杨帅军 (shuaijun.yang@ucarinc.com)
 * @since : 2020/8/28 16:57:30
 **/
@Service
public class ChatRoomMemberServiceImpl implements ChatRoomMemberService {

    @Autowired
    private ChatRoomMemberDao chatRoomMemberDao;

    @Override
    public Integer insertChatRoomMember(ChatRoomMemberBean bean) throws Exception {
        return chatRoomMemberDao.insertChatRoomMember(bean);
    }

    @Override
    public Integer updateChatRoomMember(ChatRoomMemberBean bean) throws Exception {
        return chatRoomMemberDao.updateChatRoomMember(bean);
    }

    @Override
    public void deleteChatRoomMember(ChatRoomMemberBean bean) throws Exception {
        chatRoomMemberDao.deleteChatRoomMember(bean);
    }

    @Override
    public ChatRoomMemberBean getChatRoomMemberById(Long id) throws Exception {
        return chatRoomMemberDao.getChatRoomMemberById(id);
    }

    @Override
    public List<ChatRoomMemberBean> getChatRoomMemberByCondition(ChatRoomMemberBean bean) throws Exception {
        return chatRoomMemberDao.getChatRoomMemberByCondition(bean);
    }

    @Override
    public List<ChatRoomMemberBean> getAllChatRoomMember(ChatRoomMemberBean bean) throws Exception {
        return chatRoomMemberDao.getAllChatRoomMember(bean);
    }
}
