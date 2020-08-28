package com.weizu.service.chatroom;

import com.weizu.pojo.chatroom.ChatRoomBean;

import java.util.List;

/**
 * Description:
 *
 * @author : 杨帅军 (shuaijun.yang@ucarinc.com)
 * @since : 2020/8/28 15:49:09
 **/
public interface ChatRoomService {

    /** 插入 */
    Integer insertChatRoom(ChatRoomBean bean) throws Exception;

    /** 修改 */
    Integer updateChatRoom(ChatRoomBean bean) throws Exception;

    /** 删除 */
    void deleteChatRoom(ChatRoomBean bean) throws Exception;

    /** 通过id获取 */
    ChatRoomBean getChatRoomById(Long id) throws Exception;

    /** 通过条件获取 */
    List<ChatRoomBean> getChatRoomByCondition(ChatRoomBean bean) throws Exception;

    /** 获取所有聊天室 */
    List<ChatRoomBean> getAllChatRoom(ChatRoomBean bean) throws Exception;
}
