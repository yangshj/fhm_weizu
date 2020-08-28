package com.weizu.dao.chatroom;

import com.fh.dao.DaoSupport;
import com.weizu.pojo.chatroom.ChatRoomBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @author : 杨帅军 (shuaijun.yang@ucarinc.com)
 * @since : 2020/8/28 15:33:36
 **/
@Repository("chatRoomDaoImpl")
public class ChatRoomDaoImpl extends DaoSupport implements ChatRoomDao{

    @Override
    public Integer insertChatRoom(ChatRoomBean bean) throws Exception {
        return (Integer) this.save("com.weizu.chatRoom.insertChatRoom", bean);
    }

    @Override
    public Integer updateChatRoom(ChatRoomBean bean) throws Exception {
        return (Integer) this.update("com.weizu.chatRoom.updateChatRoom",bean);
    }

    @Override
    public void deleteChatRoom(ChatRoomBean bean) throws Exception {
        this.delete("com.weizu.chatRoom.deleteChatRoom",bean);
    }

    @Override
    public ChatRoomBean getChatRoomById(Long id) throws Exception {
        return (ChatRoomBean) this.findForObject("com.weizu.chatRoom.getChatRoomById", id);
    }

    @Override
    public List<ChatRoomBean> getChatRoomByCondition(ChatRoomBean bean) throws Exception {
        return (List<ChatRoomBean>) this.findForList("com.weizu.chatRoom.getChatRoomByCondition",bean);
    }

    @Override
    public List<ChatRoomBean> getAllChatRoom(ChatRoomBean bean) throws Exception {
        return (List<ChatRoomBean>) this.findForList("com.weizu.chatRoom.getAllChatRoom",bean);
    }
}
