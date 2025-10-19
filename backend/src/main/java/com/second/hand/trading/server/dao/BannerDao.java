package com.second.hand.trading.server.dao;

import com.second.hand.trading.server.model.BannerModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BannerDao {
    /**
     * 获取所有启用的轮播图，按排序字段升序排列
     * @return 轮播图列表
     */
    List<BannerModel> getAllActiveBanners();

    /**
     * 根据ID获取轮播图
     * @param id 轮播图ID
     * @return 轮播图对象
     */
    BannerModel getBannerById(@Param("id") Long id);

    /**
     * 添加轮播图
     * @param bannerModel 轮播图对象
     * @return 影响行数
     */
    int insertBanner(BannerModel bannerModel);

    /**
     * 更新轮播图
     * @param bannerModel 轮播图对象
     * @return 影响行数
     */
    int updateBanner(BannerModel bannerModel);

    /**
     * 删除轮播图
     * @param id 轮播图ID
     * @return 影响行数
     */
    int deleteBanner(@Param("id") Long id);

    /**
     * 获取所有轮播图（包括启用和禁用）
     * @return 轮播图列表
     */
    List<BannerModel> getAllBanners();
}
