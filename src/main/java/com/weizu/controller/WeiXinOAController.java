package com.weizu.controller;


import com.alibaba.fastjson.JSON;
import com.fh.controller.base.BaseController;
import com.weizu.pojo.addressBook.UserInfoBean;
import com.weizu.pojo.oa.*;
import com.weizu.helper.ResultHelper;
import com.weizu.helper.UserOpenInfo;
import com.weizu.helper.WeiXinMemoryCacheHelper;
import com.weizu.service.addressLockk.UserInfoService;
import com.weizu.service.oa.EmployeeService;
import com.weizu.service.oa.EmployeeTeamService;
import com.weizu.service.oa.TeamService;
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
@RequestMapping(value="/weizu/weixin/oa/")
public class WeiXinOAController extends BaseController {

    @Autowired
    private TeamService teamService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeTeamService employeeTeamService;
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value="/createOrEditTeam")
    @ResponseBody
    public String createOrEditTeam(HttpServletRequest request, HttpServletResponse response){
        CreateOrEditTeamRE re = new CreateOrEditTeamRE();
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String invitationCode = request.getParameter("invitationCode");
            String teamInfo = request.getParameter("teamInfo");
            String latitude = request.getParameter("latitude");
            String longitude = request.getParameter("longitude");
            String address = request.getParameter("address");
            String contact = request.getParameter("contact");
            String contactPhone = request.getParameter("contactPhone");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                UserInfoBean userInfoBean = userInfoService.findUserByOpenId(userOpenInfo.getOpenId());
                TeamBean teamBean = new TeamBean();
                teamBean.setAddress(address);
                teamBean.setTeamInfo(teamInfo);
                teamBean.setInvitationCode(invitationCode);
                teamBean.setName(name);
                teamBean.setLatitude(Double.parseDouble(latitude));
                teamBean.setLongitude(Double.parseDouble(longitude));
                teamBean.setContact(contact);
                teamBean.setContactPhone(contactPhone);
                if(StringUtil.isEmpty(id)){
                    teamService.insertTeam(teamBean);
                    Long teamId = teamBean.getId();
                    re.setTeamId(teamId.toString());
                    EmployeeBean param = new EmployeeBean();
                    param.setUserId(userInfoBean.getId());
                    List<EmployeeBean> employeeList = employeeService.findEmployeeByCondition(param);
                    Long employeeId = null;
                    if(employeeList!=null && employeeList.size()>0){
                        employeeId = employeeList.get(0).getId();
                    } else {
                        EmployeeBean bean = new EmployeeBean();
                        bean.setUserId(userInfoBean.getId());
                        bean.setNickName(userInfoBean.getNickName());
                        employeeService.insertEmployee(bean);
                        employeeId = bean.getId();
                    }
                    EmployeeTeamBean query =  new EmployeeTeamBean();
                    query.setEmployeeId(employeeId);
                    List<EmployeeTeamBean> exits = employeeTeamService.findEmployeeTeamByCondition(query);
                    EmployeeTeamBean employeeTeamBean = new EmployeeTeamBean();
                    employeeTeamBean.setTeamId(teamId);
                    employeeTeamBean.setEmployeeId(employeeId);
                    // 第一次创建默认选中
                    if(exits!=null && exits.size()>0){
                        employeeTeamBean.setChecked(0);
                    } else {
                        employeeTeamBean.setChecked(1);
                    }
                    employeeTeamService.insertEmployeeTeam(employeeTeamBean);
                } else {
                    teamBean.setId(Long.parseLong(id));
                    teamService.updateTeam(teamBean);
                }
                re.setResult(ResultHelper.SUCCESS);
            } else {
                re.setResult(ResultHelper.SESSION_INVALID);
            }
            System.out.println("成功……");
        } catch (Exception e) {
            re.setResult(ResultHelper.FAIL);
            e.printStackTrace();
        }
        return JSON.toJSONString(re);
    }

    @RequestMapping(value="/getTeamInfo")
    @ResponseBody
    public void getTeamInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GetTeamInfo re = new GetTeamInfo();
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String id = request.getParameter("id");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                TeamBean bean = new TeamBean();
                bean.setId(Long.parseLong(id));
                re.setTeamBean(teamService.findTeamById(bean));
                re.setResult(ResultHelper.SUCCESS);
            } else {
                re.setResult(ResultHelper.SESSION_INVALID);
            }
            System.out.println("成功……");
        } catch (Exception e) {
            re.setResult(ResultHelper.FAIL);
            e.printStackTrace();
        } finally {
            PrintWriter writer = response.getWriter();
            writer.print(JSON.toJSONString(re));
            writer.flush();
        }

    }

    @RequestMapping(value="/deleteTeam")
    @ResponseBody
    public String deleteTeam(HttpServletRequest request, HttpServletResponse response){
        BaseRE re = new BaseRE();
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String id = request.getParameter("id");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                if(StringUtil.isNotEmpty(id)){
                    TeamBean teamBean = new TeamBean();
                    teamBean.setId(Long.parseLong(id));
                    // 删除团队
                    teamService.deleteTeam(teamBean);
                    // 删除关系表
                    employeeTeamService.deleteByTeamId(Long.parseLong(id));
                }
                re.setResult(ResultHelper.SUCCESS);
            } else {
                re.setResult(ResultHelper.SESSION_INVALID);
            }
            System.out.println("成功……");
        } catch (Exception e) {
            re.setResult(ResultHelper.FAIL);
            e.printStackTrace();
        }
        return JSON.toJSONString(re);
    }

    @RequestMapping(value="/getAllTeam")
    public void getAllTeam(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GetAllTeamRE re = new GetAllTeamRE();
        try{
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                UserInfoBean userInfoBean = userInfoService.findUserByOpenId(userOpenInfo.getOpenId());
                EmployeeBean query = new EmployeeBean();
                query.setUserId(userInfoBean.getId());
                List<EmployeeBean> employeeBeanList =  employeeService.findEmployeeByCondition(query);
                if(employeeBeanList!=null && employeeBeanList.size()>0){
                    Long employeeId = employeeBeanList.get(0).getId();
                    List<TeamBean> list = teamService.getAllTeamByEmployeeId(employeeId);
                    re.setListData(list);
                    re.setResult(ResultHelper.SUCCESS);
                }

            } else {
                re.setResult(ResultHelper.SESSION_INVALID);
            }
        }catch(Exception e){
            re.setResult(ResultHelper.FAIL);
            e.printStackTrace();
        } finally {
            PrintWriter writer = response.getWriter();
            writer.print(JSON.toJSONString(re));
            writer.flush();
        }
    }

    @RequestMapping(value="/switchTeam")
    public void switchTeam(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BaseRE re = new BaseRE();
        try{
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String teamId = request.getParameter("teamId");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                UserInfoBean userInfoBean = userInfoService.findUserByOpenId(userOpenInfo.getOpenId());
                EmployeeBean query = new EmployeeBean();
                query.setUserId(userInfoBean.getId());
                List<EmployeeBean> employeeBeanList =  employeeService.findEmployeeByCondition(query);
                if(employeeBeanList!=null && employeeBeanList.size()>0){
                    Long employeeId = employeeBeanList.get(0).getId();
                    EmployeeTeamBean bean = new EmployeeTeamBean();
                    bean.setEmployeeId(employeeId);
                    // 全部取消选中
                    employeeTeamService.batchUpdateNoCheckedByCondition(bean);
                    bean.setTeamId(Long.parseLong(teamId));
                    // 更新选中
                    employeeTeamService.updateCheckedByCondition(bean);
                    re.setResult(ResultHelper.SUCCESS);
                }

            } else {
                re.setResult(ResultHelper.SESSION_INVALID);
            }
        }catch(Exception e){
            re.setResult(ResultHelper.FAIL);
            e.printStackTrace();
        } finally {
            PrintWriter writer = response.getWriter();
            writer.print(JSON.toJSONString(re));
            writer.flush();
        }
    }
}
