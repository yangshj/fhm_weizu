package com.weizu.dao.chatroom;

import com.fh.dao.DaoSupport;
import com.weizu.pojo.chatroom.ChatRoomMemberBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 *
 * @author : 杨帅军 (shuaijun.yang@ucarinc.com)
 * @since : 2020/8/28 15:39:51
 **/
@Repository("chatRoomMemberDaoImpl")
public class ChatRoomMemberDaoImpl extends DaoSupport implements ChatRoomMemberDao {

    @Override
    public Integer insertChatRoomMember(ChatRoomMemberBean bean) throws Exception {
        return (Integer) this.save("com.weizu.chatRoomMember.insertChatRoomMember", bean);
    }

    @Override
    public Integer updateChatRoomMember(ChatRoomMemberBean bean) throws Exception {
        return (Integer) this.update("com.weizu.chatRoomMember.updateChatRoomMember",bean);
    }

    @Override
    public void deleteChatRoomMember(ChatRoomMemberBean bean) throws Exception {
        this.delete("com.weizu.chatRoomMember.deleteChatRoomMember",bean);
    }

    @Override
    public ChatRoomMemberBean getChatRoomMemberById(Long id) throws Exception {
        return (ChatRoomMemberBean) this.findForObject("com.weizu.chatRoomMember.getChatRoomMemberById", id);
    }

    @Override
    public List<ChatRoomMemberBean> getChatRoomMemberByCondition(ChatRoomMemberBean bean) throws Exception {
        return (List<ChatRoomMemberBean>) this.findForList("com.weizu.chatRoomMember.getChatRoomMemberByCondition",bean);
    }

    @Override
    public List<ChatRoomMemberBean> getAllChatRoomMember(ChatRoomMemberBean bean) throws Exception {
        return (List<ChatRoomMemberBean>) this.findForList("com.weizu.chatRoomMember.getAllChatRoomMember",bean);
    }
}
