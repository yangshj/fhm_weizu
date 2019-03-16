package com.weizu.dao.oa;

import com.weizu.pojo.addressBook.AddressLookBean;
import com.weizu.pojo.addressBook.WeChatAPPBean;
import com.weizu.pojo.oa.EmployeeTeamBean;
import com.weizu.pojo.oa.TeamBean;

import java.util.List;

public interface TeamDao {

    /** 查找团队 */
    TeamBean findTeamById(TeamBean bean) throws Exception;

    /** 通过条件查找团队 */
    List<TeamBean> findTeamByCondition(TeamBean bean) throws Exception;

    /** 插入团队 */
    Integer insertTeam(TeamBean bean) throws Exception;

    /** 修改通团队 */
    Integer updateTeam(TeamBean bean) throws Exception;

    /** 删除团队 */
    void deleteTeam(TeamBean bean) throws Exception;

    /** 获取所有团队 */
    List<TeamBean> getAllTeam(WeChatAPPBean bean) throws Exception;

    /** 获取用户所属的所有团队 */
    List<TeamBean> getAllTeamByEmployeeId(EmployeeTeamBean bean) throws Exception;
}
