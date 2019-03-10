package com.weizu.dao.oa;

import com.weizu.pojo.oa.EmployeeTeamBean;

import java.util.List;

public interface EmployeeTeamDao {

    /** 插入员工团队关系 */
    Integer insertEmployeeTeam(EmployeeTeamBean bean) throws Exception;

    /** 查找员工团队关系 */
    List<EmployeeTeamBean> findEmployeeTeamByCondition(EmployeeTeamBean bean) throws Exception;

    /** 批量更新选中状态 */
    void batchUpdateNoCheckedByCondition(EmployeeTeamBean bean) throws Exception;

    /** 批量更新选中状态 */
    void updateCheckedByCondition(EmployeeTeamBean bean) throws Exception;

    /** 删除团队下的所有员工关系 */
    void deleteByTeamId(Long teamId) throws Exception;
}
