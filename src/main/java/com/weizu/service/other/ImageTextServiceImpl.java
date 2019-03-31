package com.weizu.service.other;

import com.weizu.dao.other.ImageTextDao;
import com.weizu.pojo.other.ImageTextBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("imageTextDaoServiceImpl")
public class ImageTextServiceImpl implements ImageTextService {

    @Autowired
    private ImageTextDao imageTextDao;

    @Override
    public ImageTextBean findImageTextById(Long id) throws Exception {
        return imageTextDao.findImageTextById(id);
    }

    @Override
    public List<ImageTextBean> findTeamByCondition(ImageTextBean bean) throws Exception {
        return imageTextDao.findTeamByCondition(bean);
    }

    @Override
    public Integer insertImageText(ImageTextBean bean) throws Exception {
        return imageTextDao.insertImageText(bean);
    }

    @Override
    public Integer updateImage(ImageTextBean bean) throws Exception {
        return imageTextDao.updateImage(bean);
    }

    @Override
    public void delete(Long id) throws Exception {
        imageTextDao.delete(id);
    }

    @Override
    public void deleteLogic(Long id) throws Exception {
        imageTextDao.deleteLogic(id);
    }

    @Override
    public List<ImageTextBean> getAllImageTextByCondition(ImageTextBean bean) throws Exception {
        return imageTextDao.getAllImageTextByCondition(bean);
    }

    @Override
    public List<ImageTextBean> loadMoreByCondition(ImageTextBean bean) throws Exception {
        return imageTextDao.loadMoreByCondition(bean);
    }
}
