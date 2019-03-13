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
import com.weizu.util.DistanceUtil;
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
import java.util.Date;
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
    @Autowired
    private SignRecordService signRecordService;

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
                    // 正常上班
                    SignShiftBean work = new SignShiftBean();
                    work.setTeamId(teamId);
                    work.setName("正常上班");
                    work.setStartTime("09:00");
                    work.setWorkHour(9);
                    work.setEndTime("18:00");
                    signShiftService.insertSignShift(work);
                    // 休息
                    SignShiftBean rest = new SignShiftBean();
                    rest.setTeamId(teamId);
                    rest.setName("休息");
                    rest.setStartTime("00:00");
                    rest.setWorkHour(0);
                    rest.setEndTime("00:00");
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

    @RequestMapping(value="/createOrEditScheme")
    @ResponseBody
    public void createOrEditScheme(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CreateOrEditTeamRE re = new CreateOrEditTeamRE();
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String teamId = request.getParameter("teamId");
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String distanceLimit = request.getParameter("distanceLimit");
            String signAddress = request.getParameter("signAddress");
            String latitude = request.getParameter("latitude");
            String longitude = request.getParameter("longitude");
            String monday = request.getParameter("monday");
            String tuesday = request.getParameter("tuesday");
            String wednesday = request.getParameter("wednesday");
            String thursday = request.getParameter("thursday");
            String friday = request.getParameter("friday");
            String saturday = request.getParameter("saturday");
            String sunday = request.getParameter("sunday");
            if(teamId==null){
                re.setResult(ResultHelper.FAIL);
                re.setMsg("尚未选择团队");
                return;
            }
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                UserInfoBean userInfoBean = userInfoService.findUserByOpenId(userOpenInfo.getOpenId());
                SignSchemeBean bean = new SignSchemeBean();
                bean.setName(name);
                bean.setTeamId(Long.parseLong(teamId));
                bean.setDistanceLimit(Integer.parseInt(distanceLimit));
                bean.setSignAddress(signAddress);
                bean.setLatitude(Double.parseDouble(latitude));
                bean.setLongitude(Double.parseDouble(longitude));
                bean.setMonday(Long.parseLong(monday));
                bean.setTuesday(Long.parseLong(tuesday));
                bean.setWednesday(Long.parseLong(wednesday));
                bean.setThursday(Long.parseLong(thursday));
                bean.setFriday(Long.parseLong(friday));
                bean.setSaturday(Long.parseLong(saturday));
                bean.setSunday(Long.parseLong(sunday));
                // 创建
                if(StringUtil.isEmpty(id)){
                    signSchemeService.insertSignScheme(bean);
                }
                // 修改
                else {
                    bean.setId(Long.parseLong(id));
                    signSchemeService.updateSignScheme(bean);
                }
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

    @RequestMapping(value="/createOrEditShifts")
    @ResponseBody
    public void createOrEditShifts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CreateOrEditTeamRE re = new CreateOrEditTeamRE();
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String teamId = request.getParameter("teamId");
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            String workHour = request.getParameter("workHour");
            String startLimit = request.getParameter("startLimit");
            String endLimit = request.getParameter("endLimit");
            if(teamId==null){
                re.setResult(ResultHelper.FAIL);
                re.setMsg("尚未选择团队");
                return;
            }
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                UserInfoBean userInfoBean = userInfoService.findUserByOpenId(userOpenInfo.getOpenId());
                SignShiftBean bean = new SignShiftBean();
                bean.setName(name);
                bean.setTeamId(Long.parseLong(teamId));
                bean.setStartTime(startTime);
                bean.setEndTime(endTime);
                bean.setWorkHour(Integer.parseInt(workHour));
                // 创建
                if(StringUtil.isEmpty(id)){
                    signShiftService.insertSignShift(bean);
                }
                // 修改
                else {
                    bean.setId(Long.parseLong(id));
                    signShiftService.updateSignShift(bean);
                }
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

    @RequestMapping(value="/getShiftsInfo")
    @ResponseBody
    public void getShiftsInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GetShiftsInfoRE re = new GetShiftsInfoRE();
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String id = request.getParameter("id");
            if(StringUtil.isEmpty(id)){
                re.setMsg("缺少id");
                return;
            }
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                SignShiftBean query = new SignShiftBean();
                query.setId(Long.parseLong(id));
                re.setSignShiftBean(signShiftService.findSignShiftById(query));
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

    @RequestMapping(value="/getSignShiftsByTeamId")
    @ResponseBody
    public void getSignShiftsByTeamId(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    @RequestMapping(value="/deleteScheme")
    @ResponseBody
    public String deleteScheme(HttpServletRequest request, HttpServletResponse response){
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
                    SignSchemeBean bean = new SignSchemeBean();
                    bean.setId(Long.parseLong(id));
                    signSchemeService.deleteSignScheme(bean);
                    re.setResult(ResultHelper.SUCCESS);
                }
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


    @RequestMapping(value="/deleteShifts")
    @ResponseBody
    public String deleteShifts(HttpServletRequest request, HttpServletResponse response){
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
                    SignShiftBean bean = new SignShiftBean();
                    bean.setId(Long.parseLong(id));
                    signShiftService.deleteSignShift(bean);
                    re.setResult(ResultHelper.SUCCESS);
                }
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

    @RequestMapping(value="/getAllShifts")
    public void getAllShifts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GetAllShiftsRE re = new GetAllShiftsRE();
        try{
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String teamId = request.getParameter("teamId");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                SignShiftBean query = new SignShiftBean();
                query.setTeamId(Long.parseLong(teamId));
                List<SignShiftBean> list = signShiftService.findSignShiftByCondition(query);
                re.setListData(list);
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

    @RequestMapping(value="/switchScheme")
    public void switchScheme(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BaseRE re = new BaseRE();
        try{
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String schemeId = request.getParameter("schemeId");
            String teamId = request.getParameter("teamId");
            if(StringUtil.isEmpty(teamId) || StringUtil.isEmpty(schemeId)){
                return;
            }
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                UserInfoBean userInfoBean = userInfoService.findUserByOpenId(userOpenInfo.getOpenId());
                EmployeeBean query = new EmployeeBean();
                query.setUserId(userInfoBean.getId());
                SignSchemeBean bean = new SignSchemeBean();
                bean.setTeamId(Long.parseLong(teamId));
                bean.setChecked(0);
                // 全部取消选中
                signSchemeService.batchUpdateCheckedByCondition(bean);
                bean.setChecked(1);
                bean.setId(Long.parseLong(schemeId));
                // 更新选中
                signSchemeService.batchUpdateCheckedByCondition(bean);
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

    // 签到
    @RequestMapping(value="/signSubmit")
    public void signSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GetEmployeeListByTeamRE re = new GetEmployeeListByTeamRE();
        try{
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String teamId = request.getParameter("teamId");
            String signType = request.getParameter("signType");
            String locationInfo = request.getParameter("locationInfo");
            String signTime = request.getParameter("signTime");
            String latitude = request.getParameter("latitude");
            String longitude = request.getParameter("longitude");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                // 距离校验
                SignSchemeBean schemeQuery = new SignSchemeBean();
                schemeQuery.setChecked(CheckedEnum.CHECKED.getIndex());
                schemeQuery.setTeamId(Long.parseLong(teamId));
                List<SignSchemeBean> listScheme = signSchemeService.findSignSchemeByCondition(schemeQuery);
                if(listScheme==null || listScheme.size()==0){
                    re.setResult(ResultHelper.FAIL);
                    re.setMsg("您还没有创建打卡方案哦");
                    return;
                }
                SignSchemeBean scheme = listScheme.get(0);
                Integer distanceLimit = scheme.getDistanceLimit();
                Double distance = DistanceUtil.getDistance(Double.parseDouble(longitude), Double.parseDouble(latitude), scheme.getLongitude(), scheme.getLatitude());
                if(distance.intValue()>distanceLimit){
                    re.setResult(ResultHelper.FAIL);
                    re.setMsg("您当前位置无效，超过"+distanceLimit+"米");
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
                    SignRecordBean bean = new SignRecordBean();
                    bean.setEmployeeId(employeeId);
                    bean.setSignTime(new Date());
                    bean.setTeamId(Long.parseLong(teamId));
                    bean.setSignType(Integer.parseInt(signType));
                    bean.setLocationInfo(locationInfo);
                    bean.setLatitude(Double.parseDouble(latitude));
                    bean.setLongitude(Double.parseDouble(longitude));
                    signRecordService.insertSignRecord(bean);
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
