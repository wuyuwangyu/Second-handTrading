<template>
  <div class="banner-management">
    <div class="header-section">
      <h2>轮播图管理</h2>
      <el-button type="primary" @click="openBannerDialog()">添加轮播图</el-button>
    </div>

    <!-- 轮播图列表 -->
    <el-table :data="bannerList" v-loading="tableLoading" style="width: 100%" border>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="title" label="标题"></el-table-column>
      <el-table-column label="图片预览" width="150">
        <template slot-scope="scope">
          <img :src="scope.row.imageUrl" class="preview-image" />
        </template>
      </el-table-column>
      <el-table-column prop="linkUrl" label="链接地址"></el-table-column>
      <el-table-column prop="sort" label="排序" width="80"></el-table-column>
      <el-table-column label="状态" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
            {{ scope.row.status === 1 ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" width="180">
        <template slot-scope="scope">
          {{ scope.row.createTime | formatDate }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button size="mini" @click="openBannerDialog(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="deleteBanner(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加/编辑轮播图表单 -->
    <el-dialog :title="bannerFormTitle" :visible.sync="bannerDialogVisible" width="500px">
      <el-form :model="bannerForm" :rules="bannerRules" ref="bannerForm" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="bannerForm.title"></el-input>
        </el-form-item>
        <el-form-item label="图片地址" prop="imageUrl">
          <!-- <el-input v-model="bannerForm.imageUrl"></el-input> -->
            <el-upload
                class="avatar-uploader"
                action="http://localhost:8080/file"
                :show-file-list="false"
                :on-success="handleUploadSuccess"
                :before-upload="beforeUpload">
                <img v-if="bannerForm.imageUrl" :src="bannerForm.imageUrl" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <!-- 可选：保留输入框作为备选 -->
            <el-input v-model="bannerForm.imageUrl" placeholder="或输入图片地址"></el-input>
        </el-form-item>
        <el-form-item label="链接地址" prop="linkUrl">
          <el-input v-model="bannerForm.linkUrl"></el-input>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="bannerForm.sort" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch
            v-model="bannerFormStatus"
            active-text="启用"
            inactive-text="禁用">
          </el-switch>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="bannerDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveBanner">保存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'BannerManagement',
  data() {
    return {
      bannerList: [],
      tableLoading: false,
      bannerDialogVisible: false,
      bannerFormTitle: '',
      bannerForm: {
        id: null,
        title: '',
        imageUrl: '',
        linkUrl: '',
        sort: 0,
        status: 1
      },
      bannerRules: {
        title: [
          { required: true, message: '请输入标题', trigger: 'blur' }
        ],
        imageUrl: [
          { required: true, message: '请输入图片地址', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    bannerFormStatus: {
      get() {
        return this.bannerForm.status === 1;
      },
      set(val) {
        this.bannerForm.status = val ? 1 : 0;
      }
    }
  },
  filters: {
    formatDate(date) {
      if (!date) return '';
      const d = new Date(date);
      return `${d.getFullYear()}-${(d.getMonth()+1).toString().padStart(2, '0')}-${d.getDate().toString().padStart(2, '0')} ${d.getHours().toString().padStart(2, '0')}:${d.getMinutes().toString().padStart(2, '0')}:${d.getSeconds().toString().padStart(2, '0')}`;
    }
  },
  mounted() {
    this.fetchBannerList();
  },
  methods: {


    handleUploadSuccess(response, file) {
        // 根据实际API响应结构调整
        if (response.status_code === 1) {
        this.bannerForm.imageUrl = response.data;
        this.$message.success('图片上传成功');
        } else {
        this.$message.error(response.message || '图片上传失败');
        }
    },
    
    beforeUpload(file) {
        const isJPG = file.type === 'image/jpeg' || file.type === 'image/png'||  file.type === 'image/gif' || file.type === 'image/jpg' || file.type === 'image/avif';
        const isLt2M = file.size / 1024 / 1024 < 10;
        
        if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG/PNG 格式!');
        }
        if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 10MB!');
        }
        return isJPG && isLt2M;
    },


    // 获取轮播图列表
    async fetchBannerList() {
      this.tableLoading = true;
      try {
        // 在main.js中已全局注册了api
        const response = await this.$api.getAllBanners();
        // 修改状态码检查方式
        if (response.status_code === 1) {
            this.bannerList = response.data;
        } else {
            this.$message.error(response.message || '获取轮播图列表失败');
        }
      } catch (error) {
        console.error('获取轮播图列表失败:', error);
        this.$message.error('获取轮播图列表失败');
      } finally {
        this.tableLoading = false;
      }
    },
    
    // 打开添加/编辑对话框
    openBannerDialog(row) {
      if (row) {
        // 编辑模式
        this.bannerFormTitle = '编辑轮播图';
        this.bannerForm = { ...row };
      } else {
        // 添加模式
        this.bannerFormTitle = '添加轮播图';
        this.bannerForm = {
          id: null,
          title: '',
          imageUrl: '',
          linkUrl: '',
          sort: 0,
          status: 1
        };
      }
      this.bannerDialogVisible = true;
    },
    
    // 保存轮播图
    saveBanner() {
      this.$refs.bannerForm.validate(async (valid) => {
        if (valid) {
          try {
            let response;
            if (this.bannerForm.id) {
                // 更新轮播图
                response = await this.$api.updateBanner(this.bannerForm);
            } else {
                // 添加轮播图
                response = await this.$api.addBanner(this.bannerForm);
            }

            // 修改状态码检查方式
            if (response.status_code === 1) {
            this.$message.success(this.bannerForm.id ? '更新成功' : '添加成功');
            this.bannerDialogVisible = false;
            this.fetchBannerList();
            } else {
            this.$message.error(response.message || (this.bannerForm.id ? '更新失败' : '添加失败'));
            }
          } catch (error) {
            console.error('保存轮播图失败:', error);
            this.$message.error(this.bannerForm.id ? '更新失败' : '添加失败');
          }
        }
      });
    },
    
    // 删除轮播图
    async deleteBanner(id) {
      try {
        await this.$confirm('确定要删除这个轮播图吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        });

        // 修改为使用 this.$api 方式调用
        const response = await this.$api.deleteBanner(id);
        // 修改状态码检查方式
        if (response.status_code === 1) {
        this.$message.success('删除成功');
        this.fetchBannerList();
        } else {
        this.$message.error(response.message || '删除失败');
        }
      } catch (error) {
        if (error !== 'cancel') {
          console.error('删除轮播图失败:', error);
          this.$message.error('删除失败');
        }
      }
    }
  }
}
</script>

<style scoped>
.banner-management {
	margin: 20px;
  padding: 20px;
  background-color: #fff;
  min-height: 85%;
  border-radius: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.header-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.preview-image {
  width: 100px;
  height: 50px;
  object-fit: cover;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
