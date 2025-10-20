<template>
  <div class="details-container">
    <div class="header">
      <el-page-header @back="goBack" content="商品详情">
      </el-page-header>
    </div>

    <div class="content" v-loading="loading">
      <div class="product-info" v-if="itemInfo && itemInfo.id">
        <el-card class="product-card">
          <div slot="header" class="clearfix">
            <span>{{ itemInfo.idleName }}</span>
          </div>
          <div class="product-details">
            <el-carousel height="300px" v-if="previewImages.length > 0">
              <el-carousel-item v-for="(img, index) in previewImages" :key="index">
                <el-image
                    :src="img"
                    fit="cover"
                    style="width: 100%; height: 300px;">
                </el-image>
              </el-carousel-item>
            </el-carousel>
            <div v-else class="no-image">
              <i class="el-icon-picture-outline"></i>
              <span>暂无图片</span>
            </div>

            <div class="info-item">
              <span class="label">价格：</span>
              <span class="price">¥ {{ itemInfo.idlePrice }}</span>
            </div>

            <div class="info-item">
              <span class="label">分类：</span>
              <span>{{ getLabelName(itemInfo.idleLabel) }}</span>
            </div>

            <div class="info-item">
              <span class="label">状态：</span>
              <span>{{ getStatusText(itemInfo.idleStatus) }}</span>
            </div>

            <div class="info-item">
              <span class="label">发布时间：</span>
              <span>{{ formatTime(itemInfo.releaseTime) }}</span>
            </div>

            <div class="info-item">
              <span class="label">地点：</span>
              <span>{{ itemInfo.idlePlace }}</span>
            </div>

            <div class="info-item description">
              <span class="label">描述：</span>
              <span>{{ itemInfo.idleDetails }}</span>
            </div>
          </div>
        </el-card>

        <div class="seller-info" v-if="itemInfo.user">
          <el-card>
            <div slot="header">卖家信息</div>
            <div class="seller-details">
              <el-avatar :src="itemInfo.user.avatar" :size="50"></el-avatar>
              <span class="seller-name">{{ itemInfo.user.nickname }}</span>
            </div>
          </el-card>
        </div>

        <div class="action-buttons">
          <el-button type="primary" @click="contactSeller">联系卖家</el-button>
          <el-button v-if="isOwner" @click="editItem">编辑商品</el-button>
        </div>
      </div>

      <div v-else-if="!loading" class="no-data">
        <el-empty description="未找到该商品信息"></el-empty>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
  name: 'Details',
  data() {
    return {
      itemId: null,
      itemInfo: null,
      loading: true,
      labels: {
        1: '数码科技',
        2: '生活用品',
        3: '运动相关',
        4: '图书笔记',
        5: '公告'
      },
      statusMap: {
        1: '在售',
        2: '已售',
        3: '下架'
      }
    };
  },

  computed: {
    ...mapState(['userInfo']),

    isOwner() {
      return this.itemInfo && this.userInfo && this.itemInfo.userId === this.userInfo.id;
    },

    previewImages() {
      if (this.itemInfo && this.itemInfo.pictureList) {
        try {
          // 根据你的index.vue中的处理方式，pictureList是JSON字符串
          const pictures = JSON.parse(this.itemInfo.pictureList);
          return Array.isArray(pictures) ? pictures : [];
        } catch (e) {
          return [];
        }
      }
      return [];
    }
  },

  created() {
    this.itemId = this.$route.query.id;
    if (this.itemId) {
      this.fetchItemDetails();
    } else {
      this.loading = false;
    }
  },

  methods: {
    goBack() {
      this.$router.go(-1);
    },

    async fetchItemDetails() {
      this.loading = true;
      try {
        const response = await this.$api.getIdleInfo({id: this.itemId});
        if (response.code === 200) {
          this.itemInfo = response.data;
        } else {
          this.$message.error('获取商品信息失败');
        }
      } catch (error) {
        this.$message.error('请求出错：' + error.message);
      } finally {
        this.loading = false;
      }
    },

    getLabelName(label) {
      return this.labels[label] || '未知分类';
    },

    getStatusText(status) {
      return this.statusMap[status] || '未知状态';
    },

    formatTime(time) {
      if (!time) return '';
      return time.replace('T', ' ');
    },

    contactSeller() {
      if (!this.userInfo) {
        this.$message.warning('请先登录');
        this.$router.push('/login');
        return;
      }

      // 跳转到聊天页面或打开聊天窗口
      this.$message.info('联系卖家功能待实现');
    },

    editItem() {
      // 跳转到编辑页面
      this.$router.push(`/release?id=${this.itemId}`);
    }
  }
};
</script>

<style scoped>
.details-container {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

.header {
  margin-bottom: 20px;
}

.product-card {
  margin-bottom: 20px;
}

.product-details {
  padding: 10px;
}

.info-item {
  margin-bottom: 15px;
  display: flex;
  align-items: flex-start;
}

.label {
  font-weight: bold;
  width: 80px;
  flex-shrink: 0;
}

.price {
  color: #f56c6c;
  font-size: 18px;
  font-weight: bold;
}

.description {
  align-items: flex-start;
}

.description .label {
  align-self: flex-start;
}

.seller-info {
  margin-bottom: 20px;
}

.seller-details {
  display: flex;
  align-items: center;
}

.seller-name {
  margin-left: 15px;
  font-size: 16px;
}

.action-buttons {
  text-align: center;
  padding: 20px 0;
}

.no-data {
  text-align: center;
  padding: 50px 0;
}

.no-image {
  height: 300px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: #f5f7fa;
  color: #909399;
}

</style>
