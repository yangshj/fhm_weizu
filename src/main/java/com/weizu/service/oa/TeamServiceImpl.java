package com.weizu.service.oa;

import com.weizu.dao.oa.TeamDao;
import com.weizu.pojo.addressBook.WeChatAPPBean;
import com.weizu.pojo.oa.EmployeeTeamBean;
import com.weizu.pojo.oa.TeamBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("teamServiceImpl")
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamDao teamDao;

    @Override
    public TeamBean findTeamById(TeamBean bean) throws Exception {
        return teamDao.findTeamById(bean);
    }

    @Override
    public List<TeamBean> findTeamByCondition(TeamBean bean) throws Exception {
        return teamDao.findTeamByCondition(bean);
    }

    @Override
    public Integer insertTeam(TeamBean bean) throws Exception {
        return teamDao.insertTeam(bean);
    }

    @Override
    public Integer updateTeam(TeamBean bean) throws Exception {
        return teamDao.updateTeam(bean);
    }

    @Override
    public void deleteTeam(TeamBean bean) throws Exception {
        teamDao.deleteTeam(bean);
    }

    @Override
    public List<TeamBean> getAllTeam(WeChatAPPBean bean) throws Exception {
        return teamDao.getAllTeam(bean);
    }

    @Override
    public List<TeamBean> getAllTeamByEmployeeId(Long employeeId) throws Exception {
        EmployeeTeamBean teamBean = new EmployeeTeamBean();
        teamBean.setEmployeeId(employeeId);
        return teamDao.getAllTeamByEmployeeId(teamBean);
    }
}
