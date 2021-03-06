package com.weizu.dao.oa;

import com.fh.dao.DaoSupport;
import com.weizu.pojo.addressBook.WeChatAPPBean;
import com.weizu.pojo.oa.EmployeeBean;
import com.weizu.pojo.oa.EmployeeInfo;
import com.weizu.pojo.oa.EmployeeTeamBean;
import com.weizu.pojo.oa.TeamBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("employeeDaoImpl")
public class EmployeeDaoImpl extends DaoSupport implements EmployeeDao {


    @Override
    public EmployeeBean findEmployeeById(EmployeeBean bean) throws Exception {
        return (EmployeeBean) this.findForObject("com.weizu.oa.employee.findEmployeeById", bean);
    }

    @Override
    public List<EmployeeBean> findEmployeeByCondition(EmployeeBean bean) throws Exception {
        return (List<EmployeeBean>) this.findForList("com.weizu.oa.employee.findEmployeeByCondition", bean);
    }

    @Override
    public Integer insertEmployee(EmployeeBean bean) throws Exception {
        return (Integer) this.save("com.weizu.oa.employee.insertEmployee", bean);
    }

    @Override
    public Integer updateEmployee(EmployeeBean bean) throws Exception {
        return (Integer) this.update("com.weizu.oa.employee.updateEmployee", bean);
    }

    @Override
    public void deleteEmployee(EmployeeBean bean) throws Exception {
        this.delete("com.weizu.oa.employee.deleteEmployee", bean);
    }

    @Override
    public List<EmployeeBean> getAllEmployee(WeChatAPPBean weChatAPPBean) throws Exception {
        return (List<EmployeeBean>) this.findForList("com.weizu.oa.employee.getAllEmployee",weChatAPPBean);
    }

    @Override
    public List<EmployeeInfo> getEmployeeInfoByTeam(EmployeeTeamBean bean) throws Exception {
        return (List<EmployeeInfo>) this.findForList("com.weizu.oa.employee.getEmployeeInfoByTeam",bean);
    }
}
