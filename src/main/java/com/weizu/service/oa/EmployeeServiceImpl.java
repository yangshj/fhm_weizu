package com.weizu.service.oa;

import com.weizu.dao.oa.EmployeeDao;
import com.weizu.dao.oa.TeamDao;
import com.weizu.pojo.oa.EmployeeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("employeeServiceImpl")
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public EmployeeBean findEmployeeById(EmployeeBean bean) throws Exception {
        return employeeDao.findEmployeeById(bean);
    }

    @Override
    public List<EmployeeBean> findEmployeeByCondition(EmployeeBean bean) throws Exception {
        return employeeDao.findEmployeeByCondition(bean);
    }

    @Override
    public Integer insertEmployee(EmployeeBean bean) throws Exception {
        return employeeDao.insertEmployee(bean);
    }

    @Override
    public Integer updateEmployee(EmployeeBean bean) throws Exception {
        return employeeDao.updateEmployee(bean);
    }

    @Override
    public void deleteEmployee(EmployeeBean bean) throws Exception {
        employeeDao.deleteEmployee(bean);
    }

    @Override
    public List<EmployeeBean> getAllEmployee() throws Exception {
        return employeeDao.getAllEmployee();
    }
}
