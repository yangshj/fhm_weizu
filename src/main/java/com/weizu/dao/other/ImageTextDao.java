package com.weizu.dao.other;

import com.weizu.pojo.other.ImageTextBean;

import java.util.List;

public interface ImageTextDao {

    /** 查找图文消息 */
    ImageTextBean findImageTextById(Long id) throws Exception;

    /** 通过条件查找图文消息 */
    List<ImageTextBean> findTeamByCondition(ImageTextBean bean) throws Exception;

    /** 插入图文消息 */
    Integer insertImageText(ImageTextBean bean) throws Exception;

    /** 修改通图文消息 */
    Integer updateImage(ImageTextBean bean) throws Exception;

    /** 删除图文消息 */
    void delete(Long id) throws Exception;

    /** 逻辑删除图文消息 */
    void deleteLogic(Long id) throws Exception;

    /** 获取所有图文消息 */
    List<ImageTextBean> getAllImageTextByCondition(ImageTextBean bean) throws Exception;

    /** 对应小程序加载更多 */
    List<ImageTextBean> loadMoreByCondition(ImageTextBean bean) throws Exception;

}
