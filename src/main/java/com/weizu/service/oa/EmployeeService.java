package com.weizu.service.oa;

import com.weizu.pojo.oa.EmployeeBean;

import java.util.List;

public interface EmployeeService {

    /** 查找员工 */
    EmployeeBean findEmployeeById(EmployeeBean bean) throws Exception;

    /** 通过条件查找员工 */
    List<EmployeeBean> findEmployeeByCondition(EmployeeBean bean) throws Exception;

    /** 插入员工 */
    Integer insertEmployee(EmployeeBean bean) throws Exception;

    /** 修改通员工 */
    Integer updateEmployee(EmployeeBean bean) throws Exception;

    /** 删除员工 */
    void deleteEmployee(EmployeeBean bean) throws Exception;

    /** 获取所有员工 */
    List<EmployeeBean> getAllEmployee() throws Exception;
}
