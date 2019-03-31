package com.weizu.dao.other;

import com.fh.dao.DaoSupport;
import com.weizu.pojo.other.ImageTextBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("imageTextDaoImpl")
public class ImageTextDaoImpl extends DaoSupport implements ImageTextDao {


    @Override
    public ImageTextBean findImageTextById(Long id) throws Exception {
        return (ImageTextBean) this.findForObject("com.weizu.imageText.findImageTextById", id);
    }

    @Override
    public List<ImageTextBean> findTeamByCondition(ImageTextBean bean) throws Exception {
        return (List<ImageTextBean>) this.findForList("com.weizu.imageText.findTeamByCondition", bean);
    }

    @Override
    public Integer insertImageText(ImageTextBean bean) throws Exception {
        return (Integer) this.save("com.weizu.imageText.insertImageText", bean);
    }

    @Override
    public Integer updateImage(ImageTextBean bean) throws Exception {
        return (Integer) this.update("com.weizu.imageText.updateImage", bean);
    }

    @Override
    public void delete(Long id) throws Exception {
        this.delete("com.weizu.imageText.delete", id);
    }

    @Override
    public void deleteLogic(Long id) throws Exception {
        this.delete("com.weizu.imageText.deleteLogic", id);
    }

    @Override
    public List<ImageTextBean> getAllImageTextByCondition(ImageTextBean bean) throws Exception {
        return (List<ImageTextBean>) this.findForList("com.weizu.imageText.getAllImageTextByCondition", bean);
    }

    @Override
    public List<ImageTextBean> loadMoreByCondition(ImageTextBean bean) throws Exception {
        return (List<ImageTextBean>) this.findForList("com.weizu.imageText.loadMoreByCondition", bean);
    }
}
