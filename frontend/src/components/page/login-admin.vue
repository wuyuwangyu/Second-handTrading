<template>
    <div class="login-container">
        <el-card class="box-card">
            <div class="login-body">
                <div class="login-title">管理后台系统</div>
                <el-form ref="form" :model="userForm">
                    <el-input placeholder="请输入管理员账号" v-model="userForm.accountNumber" class="login-input">
                        <template slot="prepend">
                            <div class="el-icon-user-solid"></div>
                        </template>
                    </el-input>
                    <el-input placeholder="请输入管理员密码" v-model="userForm.adminPassword" class="login-input"
                              @keyup.enter.native="login"  show-password>
                        <template slot="prepend">
                            <div class="el-icon-lock"></div>
                        </template>
                    </el-input>
                    <div class="login-submit">
                        <el-button type="primary" @click="login" style="width: 100%;">登录</el-button>
                    </div>
                    <div class="other-submit">
                        <router-link to="/login" class="sign-in-text">返回主页</router-link>
                    </div>
                </el-form>
            </div>
        </el-card>
    </div>
</template>

<script>
    export default {
        name: "login",
        data() {
            return {
                userForm: {
                    accountNumber: '',
                    adminPassword: ''
                },
            };
        },
        methods: {
            login() {
                this.$api.adminLogin({
                    accountNumber: this.userForm.accountNumber,
                    adminPassword: this.userForm.adminPassword
                }).then(res => {
                    console.log(res);
                    if (res.status_code === 1) {
                        console.log(res);
                        // 保存管理员信息
                        const adminInfo = {
                            id: res.data.id,
                            role: res.data.role,
                            adminName: res.data.adminName
                        };
                        sessionStorage.setItem('admin', JSON.stringify(adminInfo));
                        sessionStorage.setItem('adminName', res.data.adminName);
                        console.log(adminInfo);


                        this.$sta.isLogin = true;
                        this.$sta.adminName=res.data.adminName;
                        this.$router.replace({path:'/platform-admin'});
                    } else {
                        this.$message.error('登录失败，账号或密码错误！');
                    }
                });

            }
        }
    }
</script>

<style scoped>
    .login-container {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        width: 100%;
      background-image: url("../../img/4.png");
      background-size: 100% 100%;
    }

    .box-card{        
        background-color: rgba(255, 255, 255, .7)
    }

    .login-body {
        padding: 30px;
        width: 400px;
        height: 100%;
        border-radius: 10px;

    }

    .login-title {
        padding-bottom: 30px;
        text-align: center;
        font-weight: 600;
        font-size: 20px;
        color: #409EFF;
        cursor: pointer;
    }

    .login-input {
        margin-bottom: 20px;
    }

    .login-submit {
        display: flex;
        justify-content: center;
    }

    /* .el-button:hover{
        background-color: #13227a;
    } */

    .sign-in-text {
        color: #409EFF;
        font-size: 16px;
        font-weight: 500;
        text-decoration: none;
        line-height:28px;
    }

    .sign-in-text:hover{
        color: #13227a;
    }

    .other-submit{
        display:flex;
        justify-content: space-between;
        margin-top: 35px;
    }
</style>