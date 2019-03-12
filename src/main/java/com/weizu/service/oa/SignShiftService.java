package com.weizu.service.oa;

import com.weizu.pojo.oa.SignShiftBean;

import java.util.List;

public interface SignShiftService {

    /** 查找签到班次 */
    SignShiftBean findSignShiftById(SignShiftBean bean) throws Exception;

    /** 通过条件查找签到班次 */
    List<SignShiftBean> findSignShiftByCondition(SignShiftBean bean) throws Exception;

    /** 插入签到班次 */
    Integer insertSignShift(SignShiftBean bean) throws Exception;

    /** 修改签到班次 */
    Integer updateSignShift(SignShiftBean bean) throws Exception;

    /** 删除签到班次 */
    void deleteSignShift(SignShiftBean bean) throws Exception;

    /** 删除团队下的所有签到班次 */
    void deleteByTeamId(Long teamId) throws Exception;
}
