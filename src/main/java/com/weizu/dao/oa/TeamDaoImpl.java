package com.weizu.dao.oa;

import com.fh.dao.DaoSupport;
import com.weizu.pojo.addressBook.WeChatAPPBean;
import com.weizu.pojo.oa.EmployeeTeamBean;
import com.weizu.pojo.oa.TeamBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("teamDaoImpl")
public class TeamDaoImpl extends DaoSupport implements TeamDao {

    @Override
    public TeamBean findTeamById(TeamBean bean) throws Exception {
        return (TeamBean) this.findForObject("com.weizu.oa.team.findTeamById", bean);
    }

    @Override
    public List<TeamBean> findTeamByCondition(TeamBean bean) throws Exception {
        return (List<TeamBean>) this.findForList("com.weizu.oa.team.findTeamByCondition", bean);
    }

    @Override
    public Integer insertTeam(TeamBean bean) throws Exception {
        return (Integer) this.save("com.weizu.oa.team.insertTeam", bean);
    }

    @Override
    public Integer updateTeam(TeamBean bean) throws Exception {
        return (Integer) this.update("com.weizu.oa.team.updateTeam", bean);
    }

    @Override
    public void deleteTeam(TeamBean bean) throws Exception {
        this.delete("com.weizu.oa.team.deleteTeam", bean);
    }

    @Override
    public List<TeamBean> getAllTeam(WeChatAPPBean bean) throws Exception {
        return (List<TeamBean>) this.findForList("com.weizu.oa.team.getAllTeam", bean);
    }

    @Override
    public List<TeamBean> getAllTeamByEmployeeId(EmployeeTeamBean bean) throws Exception {
        return (List<TeamBean>) this.findForList("com.weizu.oa.team.getAllTeamByEmployeeId", bean);
    }
}
