package com.weizu.controller;


import com.alibaba.fastjson.JSON;
import com.fh.controller.base.BaseController;
import com.weizu.common.enums.CheckedEnum;
import com.weizu.helper.RightsHelper;
import com.weizu.pojo.addressBook.UserInfoBean;
import com.weizu.pojo.oa.*;
import com.weizu.helper.ResultHelper;
import com.weizu.helper.UserOpenInfo;
import com.weizu.helper.WeiXinMemoryCacheHelper;
import com.weizu.service.addressLockk.UserInfoService;
import com.weizu.service.oa.*;
import com.weizu.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
    @Autowired
    private SignSchemeService signSchemeService;
    @Autowired
    private SignShiftService signShiftService;

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
                // 创建
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
                        EmployeeBean bean = employeeList.get(0);
                        bean.setManagerRights(RightsHelper.getRights(teamId.intValue()).toString());
                        employeeService.updateEmployee(bean);
                    } else {
                        // 第一次自动创建员工
                        EmployeeBean bean = new EmployeeBean();
                        bean.setUserId(userInfoBean.getId());
                        bean.setNickName(userInfoBean.getNickName());
                        bean.setManagerRights(RightsHelper.getRights(teamId.intValue()).toString());
                        employeeService.insertEmployee(bean);
                        employeeId = bean.getId();
                    }
                    // 更新创建人
                    teamBean.setCreateEmp(employeeId);
                    teamService.updateTeam(teamBean);
                    // 添加关联关系表
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
                    // 第一次自动创建打卡班次
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                    // 正常上班
                    SignShiftBean work = new SignShiftBean();
                    work.setTeamId(teamId);
                    work.setName("正常上班");
                    work.setStartTime(format.parse("09:00"));
                    work.setWorkHour(9);
                    work.setEndTime(format.parse("18:00"));
                    signShiftService.insertSignShift(work);
                    // 休息
                    SignShiftBean rest = new SignShiftBean();
                    rest.setTeamId(teamId);
                    rest.setName("休息");
                    rest.setStartTime(format.parse("00:00"));
                    rest.setWorkHour(0);
                    rest.setEndTime(format.parse("00:00"));
                    signShiftService.insertSignShift(rest);
                    // 第一次自动创建打卡方案
                    SignSchemeBean signScheme = new SignSchemeBean();
                    signScheme.setTeamId(teamId);
                    signScheme.setName("周一至周五打卡方案");
                    signScheme.setChecked(CheckedEnum.CHECKED.getIndex());
                    signScheme.setDistanceLimit(500);
                    signScheme.setLatitude(Double.parseDouble(latitude));
                    signScheme.setLongitude(Double.parseDouble(longitude));
                    signScheme.setSignAddress(address);
                    signScheme.setMonday(work.getId());
                    signScheme.setTuesday(work.getId());
                    signScheme.setWednesday(work.getId());
                    signScheme.setThursday(work.getId());
                    signScheme.setFriday(work.getId());
                    signScheme.setSaturday(rest.getId());
                    signScheme.setSunday(rest.getId());
                    signSchemeService.insertSignScheme(signScheme);
                }
                // 修改
                else {
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
        GetTeamInfoRE re = new GetTeamInfoRE();
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

    @RequestMapping(value="/getSchemeInfo")
    @ResponseBody
    public void getSchemeInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GetSchemeInfoRE re = new GetSchemeInfoRE();
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String id = request.getParameter("id");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                SignSchemeBean query = new SignSchemeBean();
                query.setId(Long.parseLong(id));
                re.setSignSchemeBean(signSchemeService.findSignSchemeById(query));
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

    @RequestMapping(value="/getSignShifts")
    @ResponseBody
    public void getSignShifts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GetSignShiftsRE re = new GetSignShiftsRE();
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String teamId = request.getParameter("teamId");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
               if(StringUtil.isNotEmpty(teamId)){
                   SignShiftBean query = new SignShiftBean();
                   query.setTeamId(Long.parseLong(teamId));
                   re.setListData(signShiftService.findSignShiftByCondition(query));
                   re.setResult(ResultHelper.SUCCESS);
               } else {
                   re.setResult(ResultHelper.FAIL);
               }
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
                    Long teamId = Long.parseLong(id);
                    TeamBean teamBean = new TeamBean();
                    teamBean.setId(teamId);
                    // 删除团队
                    teamService.deleteTeam(teamBean);
                    // 删除关系表
                    employeeTeamService.deleteByTeamId(teamId);
                    // 删除方案
                    signSchemeService.deleteByTeamId(teamId);
                    // 删除班次
                    signShiftService.deleteByTeamId(teamId);
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


    @RequestMapping(value="/getAllScheme")
    public void getAllScheme(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GetAllSchemeRE re = new GetAllSchemeRE();
        try{
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String teamId = request.getParameter("teamId");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                SignSchemeBean query = new SignSchemeBean();
                query.setTeamId(Long.parseLong(teamId));
                List<SignSchemeBean> signSchemeBeanList =  signSchemeService.findSignSchemeByCondition(query);
                re.setListData(signSchemeBeanList);
                re.setResult(ResultHelper.SUCCESS);
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

    // 邀请新人加入团队
    @RequestMapping(value="/joinTeam")
    public void joinTeam(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BaseRE re = new BaseRE();
        try{
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String teamId = request.getParameter("teamId");
            String invitationCode = request.getParameter("invitationCode");
            String userName = request.getParameter("userName");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                TeamBean queryTeam = new TeamBean();
                queryTeam.setId(Long.parseLong(teamId));
                // 校验邀请码
                TeamBean teamBean = teamService.findTeamById(queryTeam);
                if(teamBean!=null){
                    if(!teamBean.getInvitationCode().equals(invitationCode)){
                        re.setMsg("邀请码错误");
                        return;
                    }
                } else {
                    return;
                }
                UserInfoBean userInfoBean = userInfoService.findUserByOpenId(userOpenInfo.getOpenId());
                EmployeeBean query = new EmployeeBean();
                query.setUserId(userInfoBean.getId());
                List<EmployeeBean> employeeBeanList =  employeeService.findEmployeeByCondition(query);
                Long employeeId = null;
                // 用户已经存在
                if(employeeBeanList!=null && employeeBeanList.size()>0){
                    employeeId = employeeBeanList.get(0).getId();
                    EmployeeTeamBean bean = new EmployeeTeamBean();
                    bean.setTeamId(Long.parseLong(teamId));
                    bean.setEmployeeId(employeeId);
                    List<EmployeeTeamBean> exits = employeeTeamService.findEmployeeTeamByCondition(bean);
                    if(exits!=null && exits.size()>0){
                        re.setMsg("已经加入该团队");
                        return;
                    }
                }
                // 创建用户
                else {
                    EmployeeBean bean = new EmployeeBean();
                    bean.setUserId(userInfoBean.getId());
                    bean.setName(userName);
                    bean.setNickName(userInfoBean.getNickName());
                    employeeService.insertEmployee(bean);
                    employeeId = bean.getId();
                }
                // 加入团队
                EmployeeTeamBean bean = new EmployeeTeamBean();
                bean.setEmployeeId(employeeId);
                List<EmployeeTeamBean> exits = employeeTeamService.findEmployeeTeamByCondition(bean);
                EmployeeTeamBean join = new EmployeeTeamBean();
                join.setTeamId(Long.parseLong(teamId));
                join.setEmployeeId(employeeId);
                // 第一次加入团队，默认选中
                if(exits!=null && exits.size()>0){
                    join.setChecked(0);
                } else {
                    join.setChecked(1);
                }
                employeeTeamService.insertEmployeeTeam(join);
                re.setResult(ResultHelper.SUCCESS);
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

    /**
     * 是否已经加入团队
     * success: 未加入
     * false: 已经加入
     */
    @RequestMapping(value="/alreadyJoinTeam")
    public void alreadyJoinTeam(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
                Long employeeId = null;
                // 用户已经存在
                if(employeeBeanList!=null && employeeBeanList.size()>0){
                    employeeId = employeeBeanList.get(0).getId();
                    EmployeeTeamBean bean = new EmployeeTeamBean();
                    bean.setTeamId(Long.parseLong(teamId));
                    bean.setEmployeeId(employeeId);
                    List<EmployeeTeamBean> exits = employeeTeamService.findEmployeeTeamByCondition(bean);
                    if(exits!=null && exits.size()>0){
                        // 已经加入该团队
                        return;
                    }
                }
                re.setResult(ResultHelper.SUCCESS);
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

    // 获取团队下的员工列表
    @RequestMapping(value="/getEmployeeListByTeam")
    public void getEmployeeListByTeam(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GetEmployeeListByTeamRE re = new GetEmployeeListByTeamRE();
        try{
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String teamId = request.getParameter("teamId");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                EmployeeTeamBean query = new EmployeeTeamBean();
                query.setTeamId(Long.parseLong(teamId));
                List<EmployeeInfo> employeeBeanList =  employeeService.getEmployeeInfoByTeam(query);
                re.setListData(employeeBeanList);
                re.setResult(ResultHelper.SUCCESS);
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
