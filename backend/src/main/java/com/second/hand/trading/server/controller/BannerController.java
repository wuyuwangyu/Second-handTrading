package com.second.hand.trading.server.controller;

import com.second.hand.trading.server.model.BannerModel;
import com.second.hand.trading.server.service.BannerService;
import com.second.hand.trading.server.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RestController
@RequestMapping("/banner")
public class BannerController {

    @Resource
    private BannerService bannerService;

    /**
     * 获取所有启用的轮播图（前端展示用）
     * @return 轮播图列表
     */
    @GetMapping("/list")
    public ResultVo<List<BannerModel>> getAllActiveBanners() {
        List<BannerModel> banners = bannerService.getAllActiveBanners();
        return ResultVo.success(banners);
    }

    /**
     * 获取所有轮播图（管理员后台用）
     * @return 轮播图列表
     */
    @GetMapping("/admin/list")
    public ResultVo<List<BannerModel>> getAllBanners() {
        List<BannerModel> banners = bannerService.getAllBanners();
        return ResultVo.success(banners);
    }

    /**
     * 添加轮播图
     * @param bannerModel 轮播图对象
     * @return 是否添加成功
     */
    @PostMapping("/admin/add")
    public ResultVo<Boolean> addBanner(@RequestBody BannerModel bannerModel) {
        boolean result = bannerService.addBanner(bannerModel);
        return ResultVo.success(result);
    }

    /**
     * 更新轮播图
     * @param bannerModel 轮播图对象
     * @return 是否更新成功
     */
    @PutMapping("/admin/update")
    public ResultVo<Boolean> updateBanner(@RequestBody BannerModel bannerModel) {
        boolean result = bannerService.updateBanner(bannerModel);
        return ResultVo.success(result);
    }

    /**
     * 删除轮播图
     * @param id 轮播图ID
     * @return 是否删除成功
     */
    @DeleteMapping("/admin/delete/{id}")
    public ResultVo<Boolean> deleteBanner(@PathVariable Long id) {
        boolean result = bannerService.deleteBanner(id);
        return ResultVo.success(result);
    }

    /**
     * 根据ID获取轮播图详情
     * @param id 轮播图ID
     * @return 轮播图对象
     */
    @GetMapping("/admin/detail/{id}")
    public ResultVo<BannerModel> getBannerById(@PathVariable Long id) {
        BannerModel banner = bannerService.getBannerById(id);
        return ResultVo.success(banner);
    }
}
