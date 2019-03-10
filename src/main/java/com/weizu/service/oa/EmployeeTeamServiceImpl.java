package com.weizu.service.oa;

import com.weizu.dao.oa.EmployeeTeamDao;
import com.weizu.pojo.oa.EmployeeTeamBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("employeeTeamServiceImpl")
public class EmployeeTeamServiceImpl implements EmployeeTeamService {

    @Autowired
    private EmployeeTeamDao employeeTeamDao;

    @Override
    public Integer insertEmployeeTeam(EmployeeTeamBean bean) throws Exception {
        return employeeTeamDao.insertEmployeeTeam(bean);
    }

    @Override
    public List<EmployeeTeamBean> findEmployeeTeamByCondition(EmployeeTeamBean bean) throws Exception {
        return employeeTeamDao.findEmployeeTeamByCondition(bean);
    }

    @Override
    public void batchUpdateNoCheckedByCondition(EmployeeTeamBean bean) throws Exception {
        employeeTeamDao.batchUpdateNoCheckedByCondition(bean);
    }

    @Override
    public void updateCheckedByCondition(EmployeeTeamBean bean) throws Exception {
        employeeTeamDao.updateCheckedByCondition(bean);
    }

    @Override
    public void deleteByTeamId(Long teamId) throws Exception {
        employeeTeamDao.deleteByTeamId(teamId);
    }
}
