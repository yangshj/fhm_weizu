package com.weizu.service.oa;

import com.weizu.pojo.oa.EmployeeTeamBean;

import java.util.List;

public interface EmployeeTeamService {

    /** 插入员工团队关系 */
    Integer insertEmployeeTeam(EmployeeTeamBean bean) throws Exception;

    /** 插入员工团队关系 */
    List<EmployeeTeamBean> findEmployeeTeamByCondition(EmployeeTeamBean bean) throws Exception;

    /** 批量更新未选中状态 */
    void batchUpdateNoCheckedByCondition(EmployeeTeamBean bean) throws Exception;

    /** 批量更新选中状态 */
    void updateCheckedByCondition(EmployeeTeamBean bean) throws Exception;


    /** 删除团队下的所有员工关系 */
    void deleteByTeamId(Long teamId) throws Exception;
}
