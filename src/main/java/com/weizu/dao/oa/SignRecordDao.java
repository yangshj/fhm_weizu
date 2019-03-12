package com.weizu.dao.oa;

import com.weizu.pojo.oa.SignRecordBean;

import java.util.List;

public interface SignRecordDao {

    /** 查找打卡记录 */
    SignRecordBean findSignRecordById(SignRecordBean bean) throws Exception;

    /** 通过条件查找打卡记录 */
    List<SignRecordBean> findSignRecordByCondition(SignRecordBean bean) throws Exception;

    /** 插入打卡记录 */
    Integer insertSignRecord(SignRecordBean bean) throws Exception;

    /** 修改打卡记录 */
    Integer updateSignRecord(SignRecordBean bean) throws Exception;

    /** 删除打卡记录 */
    void deleteSignRecord(SignRecordBean bean) throws Exception;
}
