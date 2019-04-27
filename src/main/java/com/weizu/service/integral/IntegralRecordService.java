package com.weizu.service.integral;

import com.weizu.pojo.integral.IntegralRecordBean;

import java.util.List;

public interface IntegralRecordService {

    /** 插入积分记录 */
    Integer insertIntegralRecord(IntegralRecordBean bean) throws Exception;

    /** 修改积分记录 */
    Integer updateIntegralRecord(IntegralRecordBean bean) throws Exception;

    /** 删除积分记录 */
    void deleteIntegralRecord(IntegralRecordBean bean) throws Exception;

    /** 通过积分id获取积分记录 */
    List<IntegralRecordBean> getIntegralRecordByIntegralId(Long integralId) throws Exception;

}
