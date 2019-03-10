package com.weizu.dao.oa;

import com.fh.dao.DaoSupport;
import com.weizu.pojo.oa.EmployeeTeamBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("employeeTeamDaoImpl")
public class EmployeeTeamDaoImpl extends DaoSupport implements EmployeeTeamDao {


    @Override
    public Integer insertEmployeeTeam(EmployeeTeamBean bean) throws Exception {
        return (Integer) this.save("com.weizu.oa.employeeTeam.insertEmployeeTeam", bean);
    }

    @Override
    public List<EmployeeTeamBean> findEmployeeTeamByCondition(EmployeeTeamBean bean) throws Exception {
        return (List<EmployeeTeamBean>) findForList("com.weizu.oa.employeeTeam.findEmployeeTeamByCondition", bean);
    }

    @Override
    public void batchUpdateNoCheckedByCondition(EmployeeTeamBean bean) throws Exception {
        this.update("com.weizu.oa.employeeTeam.batchUpdateNoCheckedByCondition", bean);
    }

    @Override
    public void updateCheckedByCondition(EmployeeTeamBean bean) throws Exception {
        this.update("com.weizu.oa.employeeTeam.updateCheckedByCondition", bean);
    }

    @Override
    public void deleteByTeamId(Long teamId) throws Exception {
        this.delete("com.weizu.oa.employeeTeam.deleteEmployeeTeamByTeamId", teamId);
    }
}
