<template>
  <div class="login">
      <van-nav-bar
        title="会员登录"
        left-arrow
        @click-left="onClickLeft"
      />
      <div class="login-content">
        <div class="login-title-warp">
          <div class="login-title">
            <div class="login-title-text">账号密码登录</div>
          </div>
        </div>
        <div class="login-form">
          <div class="phone-input">
            <input type="text" v-model="login_form.phone" name="" id="" placeholder="请输入手机号码">
          </div>
          <div class="password-input">
            <div class="password-text">
              <input type="text" v-model="login_form.password" name="" id="" placeholder="请输入密码">
            </div>
          </div>
          <div class="code-input">
            <div class="code-text">
              <input type="text" v-model="login_form.verifyCode" name="" id="" placeholder="请输入图形验证码">
            </div>
            <div ref="code-img" class="code-btn" @click="Code()">
               <span class="code-placeholder"></span>
            </div>
          </div>
          <div class="login-btn">
            <button @click="login()">登 录</button>
          </div>
          <div class="help-model">
            <ul>
              <li><a href="">忘记密码</a></li>
              <span class="line">|</span>
              <li><a href="">立即注册</a></li>
            </ul>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
import { getVerifyCode, loginByAccount } from '@/api/login'
import { Toast } from 'vant'
export default {
  name: 'LoginPage',
  data () {
    return {
      res: { base64Image: '' },
      login_form: {
        phone: '', // 手机号
        password: '', // 密码
        verifyCode: '' // 图形验证码
      },
      alert: '错误提示的文案'
    }
  },
  created () {
    try {
      this.Code()
    } catch (err) {
      console.log(err)
    }
  },
  methods: {
    onClickLeft () {
      Toast('返回')// 轻提示
      this.$router.back()// 返回
    },
    // 获取图形验证码
    async Code () {
      try {
        console.log('获取验证码')
        const response = await getVerifyCode()
        this.res = response.data
        console.log(response)
        if (this.res.base64Image) {
          this.$refs['code-img'].style.backgroundImage = `url(${this.res.base64Image})`
        } else {
          // 验证码获取失败时的处理
          this.$refs['code-img'].style.backgroundImage = 'none'
          this.$refs['code-img'].style.backgroundColor = '#f5f5f5'
          Toast('验证码获取失败')
        }
      } catch (err) {
        console.log(err)
      }
    },
    validateForm (type) {
      // 校验手机号是否为空
      if (!this.login_form.phone) {
        Toast('请输入手机号')
        return false
      }

      // 校验手机号格式
      const phoneRegex = /^1[3-9]\d{9}$/
      if (!phoneRegex.test(this.login_form.phone)) {
        Toast('请输入正确的手机号格式')
        return false
      }

      // 校验图形验证码是否为空
      if (!this.login_form.verifyCode) {
        Toast('请输入图形验证码')
        return false
      }

      return true
    },
    async login () {
      console.log('登录')
      // 使用统一校验函数，传入'login'类型
      if (!this.validateForm('login')) {
        return
      }

      // 实现登录逻辑
      const loginData = {
        phone: this.login_form.phone,
        password: this.login_form.password,
        verifyCode: this.login_form.verifyCode
      }

      try {
        const response = (await loginByAccount(loginData))
        console.log(response)
        if (response.msg === 'success' && response.code === 200) {
          Toast('登录成功')
          this.resetForm()
          // this.$router.go(-1)
        } else if (response.code !== 200) {
          Toast(response.msg || '登录失败，网络异常')
        } else {
          Toast(response.msg || '登录失败，请稍后重试')
        }
      } catch (err) {
        Toast('登录失败，请稍后重试')
        console.log(err)
      }
    },
    // 重置表单
    resetForm () {
      this.login_form = {
        phone: '',
        verifyCode: '',
        SmsCode: ''
      }
    }
  }
}
</script>

<style lang="less" scoped>
.login {
  :deep(.van-nav-bar__content) {
    height: 80px;
  }

  :deep(.van-nav-bar__title) {
    font-size: 20px;
    color: #7c7c7c;
  }

  :deep(.van-icon) {
    font-size: 25px;
    color: #868686;
  }
}
.login-content{
  padding: 25px;
}
.login-title{
  display: flex;
  flex-direction: column;
  text-align:left;
  width: 100%;
  // height: 100px;
  .login-title-text{
    font-size: 30px;
    font-weight: 500;
    padding-top: 25px;
  }
  .login-title-tip{
    font-size: 16px;
    color: #d3d3d3;
    padding-top: 10px;
  }
}
.login-form{
  display: flex;
  flex-direction: column;
  // background-color: pink;
  input{
    width: 100%;
    height: 60px;
    padding-left: 10px;
    border: 0px;
    &::placeholder{
      color: #d3d3d3;
    }
    // 兼容不同浏览器的写法
    &::-webkit-input-placeholder {
      color: #999999;
    }
    &::-moz-placeholder {
      color: #999999;
    }
    &:-ms-input-placeholder {
      color: #999999;
    }
  }
  .phone-input{
    display: flex;
    padding-top: 30px;
  }
  .code-input{
    display: flex;
    justify-content: space-between;
    padding-top: 20px;
    .code-text{
      flex: 1;
    }
    .code-btn{
      // width: 100px;
      // background-color: aquamarine;
      width: 100px;    // 与后端WIDTH一致
      height: 60px;   // 与后端HEIGHT一致
      background-size: contain;
      background-repeat: no-repeat;
      background-position: center;
      .code-placeholder{
        display: flex;
        align-items: center;
        justify-content: center;
        color: rgb(105, 105, 105);
        background-color: #999999;
      }
    }
  }
  .password-input{
    display: flex;
    justify-content: space-between;
    padding-top: 20px;
    .password-text{
      flex: 1;
    }
    .password-btn{
      width: 100px;
      height: 60px;
      display: flex;
      align-items: center;
      justify-content: center;
      color: #ff8e3d;
      font-size: 18px;
      border-radius: 6px;
      cursor: pointer;
      user-select: none;

      &:active {
        opacity: 0.8;
      }
    }
  }
  .login-btn{
    display: flex;
    justify-content: center;
    padding-top: 45px;
    button{
      width: 100%;
      height: 50px;
      border: 0px;
      border-radius: 25px;
      color: #fff;
      background: linear-gradient(to right, #ffc672, #ff8e3d);
      font-size: 20px;
    }
  }
  .help-model{
    display: flex;
    justify-content: center;
    margin-top: 20px;
    ul {
      display: flex;
      align-items: center;
      list-style: none;
      padding: 0;
      margin: 0;

      li {
        a {
          color: #ff8e3d;
          font-size: 16px;
          text-decoration: none;

          &:hover {
            text-decoration: underline;
          }
        }
      }

      .line {
        color: #d3d3d3;
        margin: 0 15px;
        font-size: 16px;
      }
    }
  }
}
</style>
