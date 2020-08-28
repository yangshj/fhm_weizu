package com.weizu.dao.chatroom;


import com.weizu.pojo.chatroom.ChatRoomMemberBean;

import java.util.List;

public interface ChatRoomMemberDao {

    /** 插入 */
    Integer insertChatRoomMember(ChatRoomMemberBean bean) throws Exception;

    /** 修改 */
    Integer updateChatRoomMember(ChatRoomMemberBean bean) throws Exception;

    /** 删除 */
    void deleteChatRoomMember(ChatRoomMemberBean bean) throws Exception;

    /** 通过id获取 */
    ChatRoomMemberBean getChatRoomMemberById(Long id) throws Exception;

    /** 通过条件获取 */
    List<ChatRoomMemberBean> getChatRoomMemberByCondition(ChatRoomMemberBean bean) throws Exception;

    /** 获取所有 */
    List<ChatRoomMemberBean> getAllChatRoomMember(ChatRoomMemberBean bean) throws Exception;

}
