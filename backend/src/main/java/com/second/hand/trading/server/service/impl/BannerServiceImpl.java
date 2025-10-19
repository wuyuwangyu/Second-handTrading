
package com.second.hand.trading.server.service.impl;

import com.second.hand.trading.server.dao.BannerDao;
import com.second.hand.trading.server.model.BannerModel;
import com.second.hand.trading.server.service.BannerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    @Resource
    private BannerDao bannerDao;

    @Override
    public List<BannerModel> getAllActiveBanners() {
        return bannerDao.getAllActiveBanners();
    }

    @Override
    public List<BannerModel> getAllBanners() {
        return bannerDao.getAllBanners();
    }

    @Override
    public boolean addBanner(BannerModel bannerModel) {
        bannerModel.setCreateTime(new Date());
        if (bannerModel.getStatus() == null) {
            bannerModel.setStatus((byte) 1); // 默认启用
        }
        return bannerDao.insertBanner(bannerModel) > 0;
    }

    @Override
    public boolean updateBanner(BannerModel bannerModel) {
        return bannerDao.updateBanner(bannerModel) > 0;
    }

    @Override
    public boolean deleteBanner(Long id) {
        return bannerDao.deleteBanner(id) > 0;
    }

    @Override
    public BannerModel getBannerById(Long id) {
        return bannerDao.getBannerById(id);
    }
}