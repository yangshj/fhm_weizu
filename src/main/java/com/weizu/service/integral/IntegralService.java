package com.weizu.service.integral;

import com.weizu.pojo.addressBook.WeChatAPPBean;
import com.weizu.pojo.integral.IntegralBean;

import java.util.List;

public interface IntegralService {

    /** 插入积分 */
    Integer insertIntegral(IntegralBean bean) throws Exception;

    /** 修改积分 */
    Integer updateIntegral(IntegralBean bean) throws Exception;

    /** 删除积分 */
    void deleteIntegral(IntegralBean bean) throws Exception;

    /** 通过用户id获取积分 */
    List<IntegralBean> getIntegralByUserId(Long userId) throws Exception;

    /** 通过条件获取积分 */
    List<IntegralBean> getIntegralByCondition(IntegralBean bean) throws Exception;

    /** 获取所有积分信息 */
    List<IntegralBean> getAllIntegral(IntegralBean bean) throws Exception;
}
