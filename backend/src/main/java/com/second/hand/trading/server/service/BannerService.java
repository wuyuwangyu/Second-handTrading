package com.second.hand.trading.server.service;


import com.second.hand.trading.server.model.BannerModel;

import java.util.List;

public interface BannerService {
    /**
     * 获取所有启用的轮播图
     * @return 轮播图列表
     */
    List<BannerModel> getAllActiveBanners();

    /**
     * 获取所有轮播图（管理员使用）
     * @return 轮播图列表
     */
    List<BannerModel> getAllBanners();

    /**
     * 添加轮播图
     * @param bannerModel 轮播图对象
     * @return 是否添加成功
     */
    boolean addBanner(BannerModel bannerModel);

    /**
     * 更新轮播图
     * @param bannerModel 轮播图对象
     * @return 是否更新成功
     */
    boolean updateBanner(BannerModel bannerModel);

    /**
     * 删除轮播图
     * @param id 轮播图ID
     * @return 是否删除成功
     */
    boolean deleteBanner(Long id);

    /**
     * 根据ID获取轮播图
     * @param id 轮播图ID
     * @return 轮播图对象
     */
    BannerModel getBannerById(Long id);
}
