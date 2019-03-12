package com.weizu.service.oa;

import com.weizu.pojo.oa.SignSchemeBean;

import java.util.List;

public interface SignSchemeService {

    /** 查找签到方案 */
    SignSchemeBean findSignSchemeById(SignSchemeBean bean) throws Exception;

    /** 通过条件查找签到方案 */
    List<SignSchemeBean> findSignSchemeByCondition(SignSchemeBean bean) throws Exception;

    /** 插入签到方案 */
    Integer insertSignScheme(SignSchemeBean bean) throws Exception;

    /** 修改签到方案 */
    Integer updateSignScheme(SignSchemeBean bean) throws Exception;

    /** 删除签到方案 */
    void deleteSignScheme(SignSchemeBean bean) throws Exception;
}
