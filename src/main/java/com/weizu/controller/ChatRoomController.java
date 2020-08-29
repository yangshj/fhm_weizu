package com.weizu.controller;

import com.alibaba.fastjson.JSON;
import com.fh.controller.base.BaseController;
import com.weizu.helper.ResultHelper;
import com.weizu.helper.UserOpenInfo;
import com.weizu.helper.WeChatAppHelper;
import com.weizu.helper.WeiXinMemoryCacheHelper;
import com.weizu.pojo.addressBook.UserInfoBean;
import com.weizu.pojo.addressBook.UserInfoRE;
import com.weizu.pojo.addressBook.WeChatAPPBean;
import com.weizu.pojo.chatroom.ChatRoomBean;
import com.weizu.pojo.chatroom.CreateChatRoomRE;
import com.weizu.pojo.chatroom.GetAllChatRoomRE;
import com.weizu.service.chatroom.ChatRoomMemberService;
import com.weizu.service.chatroom.ChatRoomService;
import com.weizu.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping(value="/weizu/weixin/chatRoom")
public class ChatRoomController extends BaseController {

    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private ChatRoomMemberService chatRoomMemberService;

    /**
     *  获取所有聊天室
     */
    @RequestMapping(value="/getAllChatRoom")
    @ResponseBody
    public void getAllChatRoom(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GetAllChatRoomRE re = new GetAllChatRoomRE();
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            String sessionId = request.getParameter("sessionId");
            String appId = request.getParameter("appId");
            re.setResult(ResultHelper.FAIL);
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null) {
                WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
                if(weChatAPPBean==null){
                    re.setResult(ResultHelper.FAIL);
                    re.setMsg("无效的appId");
                    return;
                }
                UserInfoBean bean = new UserInfoBean();
                bean.setAppId(weChatAPPBean.getId());
                List<ChatRoomBean> list = chatRoomService.getAllChatRoom(null);
                re.setListData(list);
                re.setResult(ResultHelper.SUCCESS);
            } else {
                re.setResult(ResultHelper.SESSION_INVALID);
            }
        } catch (Exception e) {
            re.setResult(ResultHelper.FAIL);
            e.printStackTrace();
        } finally {
            PrintWriter writer = response.getWriter();
            writer.print(JSON.toJSONString(re));
            writer.flush();
        }
    }


    /**
     *  创建聊天室
     */
    @RequestMapping(value="/createChatRoom")
    @ResponseBody
    public void createChatRoom(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CreateChatRoomRE re = new CreateChatRoomRE();
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            String sessionId = request.getParameter("sessionId");
            String appId = request.getParameter("appId");
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            String roomId = request.getParameter("roomId");
            re.setResult(ResultHelper.FAIL);
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null) {
                WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
                if(weChatAPPBean==null){
                    re.setResult(ResultHelper.FAIL);
                    re.setMsg("无效的appId");
                    return;
                }
                ChatRoomBean param = new ChatRoomBean();
                param.setName(name);
                List<ChatRoomBean> exits = chatRoomService.getChatRoomByCondition(param);
                if(exits.size()>0 && StringUtil.isEmpty(roomId)){
                    re.setResult(ResultHelper.FAIL);
                    re.setMsg("房间名已经存在");
                    return;
                }
                ChatRoomBean bean = new ChatRoomBean();
                bean.setName(name);
                bean.setPassword(password);
                bean.setOwner(userOpenInfo.getUserId());
                if(StringUtil.isEmpty(roomId)){
                    chatRoomService.insertChatRoom(bean);
                    re.setRoomId(bean.getId());
                } else {
                    bean.setId(Long.parseLong(roomId));
                    chatRoomService.updateChatRoom(bean);
                }
                re.setResult(ResultHelper.SUCCESS);
            } else {
                re.setResult(ResultHelper.SESSION_INVALID);
            }
        } catch (Exception e) {
            re.setResult(ResultHelper.FAIL);
            e.printStackTrace();
        } finally {
            PrintWriter writer = response.getWriter();
            writer.print(JSON.toJSONString(re));
            writer.flush();
        }
    }
}
