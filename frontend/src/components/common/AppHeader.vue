<template>
    <div class="header">
        <div class="header-container">
            <div class="app-name">
                <!-- <router-link to="/">
                    <i class="el-icon-s-shop"></i>
                    校园跳蚤市场
                </router-link> -->
                <a @click="()=>{$router.back()}" class="back-link">
                    <i class="el-icon-s-shop"></i>
                        校园跳蚤市场
                </a>
            </div>
            <div class="search-container">
                <el-input 
                    placeholder="搜索心仪的物品..." 
                    v-model="searchValue" 
                    @keyup.enter.native="searchIdle"
                    prefix-icon="el-icon-search"
                    clearable>
                    <el-button slot="append" icon="el-icon-search" @click="searchIdle">搜索</el-button>
                </el-input>
            </div>
            <div class="action-buttons">
                <el-button type="primary" icon="el-icon-plus" class="publish-btn" @click="toRelease">发布闲置/公告</el-button>
                <el-button type="info" icon="el-icon-chat-dot-round" class="message-btn" @click="toMessage">消息</el-button>
                
                <router-link v-if="!isLogin" class="user-name-text login-btn" to="/login">
                    <i class="el-icon-user"></i> 登录
                </router-link>
                
                <el-dropdown trigger="click" v-else class="user-dropdown">
                    <div class="user-info">
                        <div class="user-name">{{nicknameValue?nicknameValue:nickname}}</div>
                        <el-avatar :size="32" :src="avatarValue?avatarValue:avatar" class="user-avatar"></el-avatar>
                    </div>
                    <el-dropdown-menu slot="dropdown" class="dropdown-menu">
                        <el-dropdown-item>
                            <div @click="toMe" class="dropdown-item">
                                <i class="el-icon-user-solid"></i> 个人中心
                            </div>
                        </el-dropdown-item>
                        <el-dropdown-item divided>
                            <div @click="loginOut" class="dropdown-item logout">
                                <i class="el-icon-switch-button"></i> 退出登录
                            </div>
                        </el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>
    </div>
</template>
<script>

    export default {
        name: 'Header',
        props: ['searchInput','nicknameValue','avatarValue'],
        data() {
            return {
                searchValue: this.searchInput,
                nickname:'登录',
                avatar:'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
                isLogin:false
            };
        },
        created(){
            // console.log("header");
            if(! this.$globalData.userInfo.nickname){
                this.$api.getUserInfo().then(res=>{
                    console.log('Header getUserInfo:',res);
                    if(res.status_code===1){
                        this.nickname=res.data.nickname;
                        this.avatar=res.data.avatar;
                        res.data.signInTime=res.data.signInTime.substring(0,10);
                        this.$globalData.userInfo=res.data;
                        this.isLogin=true;
                    }
                })
            }else {
                this.nickname=this.$globalData.userInfo.nickname;
                this.avatar=this.$globalData.userInfo.avatar;
                this.isLogin=true;
            }
        },
        methods: {
            searchIdle() {
                if(this.searchValue && this.searchValue.trim() !== '') {
                    if ('/search' !== this.$route.path) {
                        this.$router.push({path: '/search', query: {searchValue: this.searchValue}});
                    } else {
                        this.$router.replace({path: '/search', query: {searchValue: this.searchValue}});
                        this.$router.go(0);
                    }
                } else {
                    // this.$message.warning('请输入搜索关键词');
                    this.$message({
                    message: '请输入搜索关键词',
                    type: 'warning',
                    duration: 1000 // 设置显示时间为1秒（单位为毫秒）
                    });
                }
            },
            toMe() {
                if ('/me' !== this.$route.path) {
                    this.$router.push({path: '/me'});
                }
            },
            toMessage(){
                if ('/message' !== this.$route.path) {
                    this.$router.push({path: '/message'});
                }
            },
            toRelease(){
                if ('/release' !== this.$route.path) {
                    this.$router.push({path: '/release'});
                }
            },
            loginOut(){
                this.$api.logout().then(res=>{
                    if(res.status_code===1){
                        this.$globalData.userInfo={};
                        console.log("login out");
                        this.$message.success('退出登录成功');

                    
                        // //强制刷新组件
                        // this.$nextTick(()=>{
                        //     this.nickname='登录';
                        //     this.avatar='https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png';
                        //     this.isLogin=false;
                        // })

                    
                        if ('/index' === this.$route.path) {
                            this.$router.go(0);
                        }else {
                            this.$router.push({path: '/index'});
                        }
                    }else {
                        this.$message.error('网络或系统异常，退出登录失败！');
                    }
                });

            },
            goBack(){
                if (window.history.length <= 1) {
                // 如果没有历史记录，跳转到首页
                    this.$router.push('/');
                } else {
                // 否则返回上一页
                this.$router.back();
                }
            }
        },
    };
</script>

<style scoped>
    .header {
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        width: 100%;
        height: 100px;
        background: #ffffff;
        display: flex;
        justify-content: center;
        border-bottom: 1px solid #ebeef5;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        z-index: 1000;
    }

    .header-container {
        width: 1200px;
        max-width: 95%;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .app-name {
        flex-shrink: 0;
        margin-right: 15px;
    }

    .app-name a {
        color: #66b1ff;
        font-size: 20px;
        font-weight: 600;
        text-decoration: none;
        display: flex;
        align-items: center;
        transition: all 0.3s;
    }

    .app-name a:hover {
        color: #13227a;
    }

    .app-name i {
        margin-right: 8px;
        padding: 3.5px 0 0 0;
        font-size: 24px;
    }

    .search-container {
        flex-grow: 1;
        max-width: 500px;
        margin: 0 20px;
    }

    .search-container ::v-deep .el-button {
    background-color: #409EFF;
    border-color: #409EFF;
    color: white;
    border-radius: 0 20px 20px 0;
    border: 1px solid #409EFF;
    }

    .search-container ::v-deep .el-button:hover {
    background-color: #13227a;
    border-color: #66b1ff;
    color: white;
    }

    /* 美化输入框 */
    .search-container ::v-deep .el-input__inner {
    border-radius: 20px 0 0 20px;
    }

    .search-container ::v-deep .el-input-group__append{
        border-radius: 0 20px 20px 0;
        background-color: #fff;
    }

    .action-buttons {
        display: flex;
        align-items: center;
        flex-shrink: 0;
    }

    .publish-btn, .message-btn {
        margin-right: 10px;
        border-radius: 20px;
        transition: all 0.3s;
    }

    .publish-btn:hover, .message-btn:hover {
        transform: translateY(-2px);
        background-color:  #13227a;
        box-shadow: 0 4px 8px rgba(64, 158, 255, 0.3);
    }

    .user-name-text {
        font-size: 16px;
        font-weight: 600;
        color: #409EFF;
        cursor: pointer;
        text-decoration: none;
        display: flex;
        align-items: center;
        transition: all 0.3s;
    }

    .user-name-text:hover {
        color: #66b1ff;
    }

    .login-btn {
        background-color: #409EFF;
        border-color: #409EFF;
        color: white !important;
        border-radius: 20px;
        /* padding: 8px 16px; */
        padding: 6px 14px;
        transition: all 0.3s;
        text-decoration: none;
        display: flex;
        align-items: center;
    }

    .login-btn:hover {
        background-color: #66b1ff;
        border-color: #66b1ff;
        color: white !important;
        transform: translateY(-2px);
        box-shadow: 0 4px 8px rgba(64, 158, 255, 0.3);
        background-color: #13227a;
    }

    .login-btn i {
        margin-right: 5px;
    }

    .user-dropdown {
        cursor: pointer;
    }

    .user-info {
        display: flex;
        align-items: center;
        padding: 0 5px;
        border-radius: 20px;
        transition: all 0.3s;
    }

    .user-info:hover {
        background-color: #f5f7fa;
    }

    .user-name {
        font-size: 15px;
        color: #409EFF;
        margin-right: 8px;
    }

    .user-avatar {
        border: 2px solid #ebeef5;
    }

    .dropdown-menu {
        min-width: 130px;
    }

    .dropdown-item {
        display: flex;
        align-items: center;
        font-size: 14px;
    }

    .dropdown-item i {
        margin-right: 5px;
    }

    .logout {
        color: #f56c6c;
    }

    /* 响应式调整 */
    @media (max-width: 992px) {
        .app-name a {
            font-size: 18px;
        }
        
        .search-container {
            max-width: 300px;
        }
        
        .publish-btn, .message-btn {
            padding: 9px 12px;
        }
    }

    @media (max-width: 768px) {
        .app-name a span {
            display: none;
        }
        
        .app-name i {
            margin-right: 0;
            font-size: 22px;
        }
        
        .search-container {
            max-width: 200px;
        }
        
        .publish-btn, .message-btn {
            padding: 7px 10px;
            font-size: 12px;
        }
        
        .user-name {
            font-size: 14px;
            margin-right: 5px;
        }
    }
</style>
