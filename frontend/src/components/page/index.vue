<template>
    <div>
        <app-head></app-head>
        <app-body>
            <div style="min-height: 85vh;">

                <!-- 首页轮播图 -->
                <div class="banner-container" v-if="bannerList.length > 0">
                    <el-carousel :interval="4000" type="card" height="250px">
                        <el-carousel-item v-for="banner in bannerList" :key="banner.id" @click.native="goToLink(banner.linkUrl)">
                            <el-image :src="banner.imageUrl" fit="cover" class="banner-image">
                                <div slot="error" class="image-slot">
                                    <i class="el-icon-picture-outline"></i>
                                </div>
                            </el-image>
                            <div class="banner-title">{{ banner.title }}</div>
                        </el-carousel-item>
                    </el-carousel>
                </div>

                <!-- 页面标题和介绍 -->
                <div class="page-header">
                    <h1 class="page-title">校园二手闲置<span class="highlight">跳匝市场</span></h1>
                    <p class="page-description">发现校园里的二手宝贝，让闲置物品焕发新生</p>
                </div>

                
                <!-- 分类标签 -->
                <div class="category-tabs">
                    <el-tabs v-model="labelName" type="border-card" @tab-click="handleClick" class="custom-tabs">
                        <el-tab-pane label="全部" name="0">
                            <i class="el-icon-s-grid tab-icon"></i> 全部
                        </el-tab-pane>
                        <el-tab-pane label="数码科技" name="1">
                            <i class="el-icon-mobile-phone tab-icon"></i> 数码科技
                        </el-tab-pane>
                        <el-tab-pane label="生活用品" name="2">
                            <i class="el-icon-toilet-paper tab-icon"></i> 生活用品
                        </el-tab-pane>
                        <el-tab-pane label="运动相关" name="3">
                            <i class="el-icon-basketball tab-icon"></i> 运动相关
                        </el-tab-pane>
                        <el-tab-pane label="图书笔记" name="4">
                            <i class="el-icon-notebook-2 tab-icon"></i> 图书笔记
                        </el-tab-pane>
                        <el-tab-pane label="公告展示" name="5">
                            <i class="el-icon-bell tab-icon"></i> 公告展示
                        </el-tab-pane>
                    </el-tabs>
                </div>

                <!-- 商品卡片列表 -->
                <div class="items-container">
                    <el-row :gutter="20">
                        <el-col :xs="24" :sm="12" :md="6" v-for="(idle, index) in idleList" :key="index">
                            <div class="item-card" @click="toDetails(idle)">
                                <div class="item-image-container">
                                    <el-image
                                        class="item-image"
                                        :src="idle.imgUrl"
                                        fit="cover">
                                        <div slot="error" class="image-slot">
                                            <i class="el-icon-picture-outline"></i>
                                            <!-- <span>暂无图片</span> -->
                                            <span>商家很懒，没有上传图片</span>
                                        </div>
                                    </el-image>
                                    <div class="item-tag" v-if="getItemCategory(idle.idleLabel)">
                                        {{getItemCategory(idle.idleLabel)}}
                                    </div>
                                </div>
                                <div class="item-content">
                                    <h3 class="item-title">{{idle.idleName}}</h3>
                                    <div class="item-meta">
                                        <div class="item-price" v-show="idle.idlePrice !== 0">¥{{idle.idlePrice}}</div>
                                        <div class="item-place"><i class="el-icon-location"></i> {{idle.idlePlace}}</div>
                                    </div>
                                    <div class="item-time"><i class="el-icon-time"></i> {{idle.timeStr}}</div>
                                    <div class="user-info">
                                        <el-avatar :size="30" :src="idle.user.avatar"></el-avatar>
                                        <div class="user-nickname">{{idle.user.nickname}}</div>
                                    </div>
                                </div>
                            </div>
                        </el-col>
                    </el-row>
                </div>

                <!-- 分页 -->
                <div class="pagination-container">
                    <el-pagination
                        background
                        @current-change="handleCurrentChange"
                        :current-page.sync="currentPage"
                        :page-size="12"
                        layout="prev, pager, next, jumper"
                        :total="totalItem">
                    </el-pagination>
                </div>
            </div>
        </app-body>
        <app-foot></app-foot>
    </div>
</template>

<script>
    import AppHead from '../common/AppHeader.vue';
    import AppBody from '../common/AppPageBody.vue'
    import AppFoot from '../common/AppFoot.vue'

    export default {
        name: "index",
        components: {
            AppHead,
            AppBody,
            AppFoot,
        },
        data() {
            return {
                labelName: '0',
                idleList: [],
                currentPage: 1,
                totalItem: 1,
                categories: {
                    '1': '数码科技',
                    '2': '生活用品',
                    '3': '运动相关',
                    '4': '图书笔记',
                    '5': '公告'
                },
                bannerList:[]
            };
        },
        created() {
            this.findIdleTiem(1);
            this.fetchBannerList();
        },
        watch:{
            $route(to,from){
                this.labelName=to.query.labelName;
                let val=parseInt(to.query.page)?parseInt(to.query.page):1;
                this.currentPage=parseInt(to.query.page)?parseInt(to.query.page):1;
                this.findIdleTiem(val);
            }
        },
        methods: {
            findIdleTiem(page){
                const loading = this.$loading({
                    lock: true,
                    text: '加载数据中',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                if(this.labelName>0){
                    this.$api.findIdleTiemByLable({
                        idleLabel:this.labelName,
                        page: page,
                        nums: 8
                    }).then(res => {
                        console.log(res);
                        let list = res.data.list;
                        for (let i = 0; i < list.length; i++) {
                            list[i].timeStr = list[i].releaseTime.substring(0, 10) + " " + list[i].releaseTime.substring(11, 19);
                            let pictureList = JSON.parse(list[i].pictureList);
                            list[i].imgUrl = pictureList.length > 0 ? pictureList[0] : '';
                        }
                        this.idleList = list;
                        this.totalItem=res.data.count;
                        console.log(this.totalItem);
                    }).catch(e => {
                        console.log(e)
                        this.$message.error('获取数据失败，请稍后重试');
                    }).finally(()=>{
                        loading.close();
                    })
                }else{
                    this.$api.findIdleTiem({
                        page: page,
                        nums: 12 //图片显示的数量
                    }).then(res => {
                        console.log(res);
                        let list = res.data.list;
                        for (let i = 0; i < list.length; i++) {
                            list[i].timeStr = list[i].releaseTime.substring(0, 10) + " " + list[i].releaseTime.substring(11, 19);
                            let pictureList = JSON.parse(list[i].pictureList);
                            list[i].imgUrl = pictureList.length > 0 ? pictureList[0] : '';
                        }
                        this.idleList = list;
                        this.totalItem=res.data.count;
                        console.log(this.totalItem);
                    }).catch(e => {
                        console.log(e)
                        this.$message.error('获取数据失败，请稍后重试');
                    }).finally(()=>{
                        loading.close();
                    })
                }
            },
            handleClick(tab, event) {
                console.log(this.labelName);
                this.$router.replace({query: {page: 1,labelName:this.labelName}});
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.$router.replace({query: {page: val,labelName:this.labelName}});
            
            
                // 平滑滚动到顶部 抖动问题，暂时不处理（this.$nextTick）
                window.scrollTo({
                    top: 500,
                    left: 0,
                    behavior: 'smooth'
                });
            },
            toDetails(idle) {
                this.$router.push({path: '/details', query: {id: idle.id}});
                // window.open(`/idle/details?id=${idle.id}`, '_blank');
            },
            getItemCategory(label) {
                return this.categories[label] || '';
            },

            // 获取轮播图列表
            async fetchBannerList() {
                try {
                    const response = await this.$api.getAllBanners();
                    if (response.status_code === 1) {
                        // 只显示启用的轮播图
                        this.bannerList = response.data.filter(banner => banner.status === 1);
                    }
                } catch (error) {
                    console.error('获取轮播图失败:', error);
                }
            },

            // 跳转到轮播图链接
            goToLink(url) {
                if (url) {
                    window.open(url, '_blank');
                }
            }
        }
    }
</script>

<style scoped>
    .page-header {
        position: relative;
        text-align: center;
        padding: 20px 0;
        margin-bottom: 20px;
        /* background: linear-gradient(to right, #f0f9ff, #e6f7ff); */
        border-radius: 8px;
    }

    .page-header::before {
        content: '';
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-image: url(../../img/7.jpg);
        background-size: cover;
        background-position: center;
    }
    
    .page-title {
        font-size: 32px;
        font-weight: 600;
        /* color: #303133; */
        color: #409EFF;
        margin-bottom: 5px;
    }
    
    .highlight {
        color: #409EFF;
    }
    
    .page-description {
        font-size: 16px;
        color: #606266;
        /* color: #13227a; */
        margin: 10px 0 0;
    }

     /* 轮播图样式 */
    .banner-container {
        margin-bottom: 20px;
        border-radius: 12px;
        overflow: hidden;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    }
    
    .banner-image {
        width: 100%;
        height: 100%;
        object-fit: cover;
    }
    
    .banner-title {
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
        background: rgba(0, 0, 0, 0.5);
        color: white;
        padding: 10px;
        font-size: 16px;
        text-align: center;
    }

    .category-tabs {
        margin-bottom: 20px;
    }
    
    /* .custom-tabs {
        border-radius: 8px;
        overflow: hidden;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    } */
    
    /* .tab-icon {
        margin-right: 5px;
    } */


    .custom-tabs {
        border-radius: 12px;
        overflow: hidden;
        box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
        border: none;
    }

    .custom-tabs ::v-deep .el-tabs__header {
        background-color: rgba(255, 255, 255, 0.1);
        border-radius: 12px 12px 0 0;
        margin: 0;
    }

    .custom-tabs ::v-deep .el-tabs__nav-wrap::after {
        background-color: transparent;
    }

    .custom-tabs ::v-deep .el-tabs__item {
        color: rgba(140, 140, 140, 0.8);
        font-weight: 500;
        transition: all 0.3s ease;
        padding: 0 25px;
        height: 50px;
        line-height: 50px;
    }

    .custom-tabs ::v-deep .el-tabs__item:hover {
        color: rgb(64, 158, 255) !important;
        background-color: rgba(0, 0, 0, 0.1);
    }

    .custom-tabs ::v-deep .el-tabs__item.is-active {
        /* color: rgb(64, 158, 255); */
        color: #13227a ;
        background-color: rgba(255, 255, 255, 0.15);
        border-bottom-color: transparent;
    }

    .custom-tabs ::v-deep .el-tabs__content {
        background-color: #ffffff;
        padding: 20px;
        border-radius: 0 0 12px 12px;
    }

    .tab-content {
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .tab-icon {
        font-size: 18px;
        margin-right: 8px;
    }

    .tab-label {
        font-size: 15px;
    }


    .items-container {
        padding: 0 10px;
    }
    
    .item-card {
        height: 100%;
        border-radius: 8px;
        overflow: hidden;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        transition: all 0.3s;
        margin-bottom: 25px;
        background: #fff;
        cursor: pointer;
    }
    
    .item-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
    }
    
    /* .item-image-container {
        position: relative;
        height: 180px;
        overflow: hidden;
    } */

    .item-image-container {
        position: relative;
        height: 180px;
        overflow: hidden;
        border-radius: 12px;
        background: #ffffff;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
        transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
        cursor: pointer;
    }

    .item-image-container:hover {
        transform: translateY(-4px);
        box-shadow: 0 8px 25px rgba(0, 0, 0, 0.12);
    }
    
    .item-image {
        width: 100%;
        height: 100%;
        transition: transform 0.3s;
    }
    
    .item-card:hover .item-image {
        transform: scale(1.05);
    }
    
    /* .item-tag {
        position: absolute;
        top: 10px;
        right: 10px;
        background: rgba(64, 158, 255, 0.8);
        color: white;
        padding: 2px 8px;
        border-radius: 12px;
        font-size: 12px;
    } */
    
    .item-tag {
        position: absolute;
        top: 10px;
        right: 10px;
        /* background: linear-gradient(135deg, #409eff, #1a73e8); */
        background:rgba(255, 255, 255, 0.1);
        color: #13227a;
        padding: 4px 10px;
        border-radius: 15px;
        font-size: 12px;
        font-weight: 500;
        box-shadow: 0 2px 8px rgba(169, 169, 169, 0.3);
        backdrop-filter: blur(10px);
        border: 1px solid rgba(255, 255, 255, 0.2);
        transition: all 0.3s ease;
        z-index: 2;
    }

    /* .item-tag:hover {
        transform: scale(1.05);
        box-shadow: 0 4px 12px rgba(26, 65, 104, 0.4);
    } */


    .item-content {
        padding: 15px;
    }
    
    .item-title {
        font-size: 16px;
        font-weight: 600;
        color: #303133;
        margin: 0 0 10px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        line-height: 1.4;
    }
    
    .item-meta {
        display: flex;
        justify-content: space-between;
        margin-bottom: 8px;
        min-height: 26.52px;
    }
    
    .item-price {
        font-size: 18px;
        font-weight: 600;
        color: #f56c6c;
    }
    
    .item-place {
        font-size: 13px;
        color: #606266;
    }
    
    .item-time {
        font-size: 12px;
        color: #909399;
        margin-bottom: 10px;
    }
    
    .user-info {
        display: flex;
        align-items: center;
        padding-top: 10px;
        border-top: 1px solid #f0f0f0;
    }
    
    .user-nickname {
        margin-left: 10px;
        font-size: 14px;
        color: #606266;
    }
    
    .pagination-container {
        display: flex;
        justify-content: center;
        padding: 30px 0;
    }
    
    /* 图片加载失败 */
    /* .item-image-container ::v-deep .image-slot {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        width: 100%;
        height: 100%;
        background: #f5f7fa;
        color: #909399;
    } */

    .item-image-container ::v-deep .image-slot {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        width: 100%;
        height: 100%;
        background: linear-gradient(135deg, #f8fafc 0%, #e2e8f0 100%);
        color: #64748b;
        border-radius: 12px;
        transition: all 0.3s ease;
    }

    .image-slot:hover {
        background: linear-gradient(135deg, #f0f9ff 0%, #e0f2fe 100%);
        color: #0369a1;
        /* color: #13227a; */
    }

    .image-slot i {
        font-size: 40px;
        margin-bottom: 8px;
        color: #94a3b8;
        transition: all 0.3s ease;
    }

    .image-slot:hover i {
        color: #0ea5e9;
        transform: scale(1.1);
    }

    .image-slot span {
        font-size: 14px;
        font-weight: 500;
    }

    /* 响应式调整 */
    @media (max-width: 768px) {
        .page-title {
            font-size: 24px;
        }
        
        .page-description {
            font-size: 14px;
        }
        
        .item-image-container {
            height: 150px;
        }
    }
</style>