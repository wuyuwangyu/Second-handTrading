<template>
  <div class="login-sms">
      <van-nav-bar
        title="会员登录"
        left-arrow
        @click-left="onClickLeft"
      />
      <div class="login-content">
        <div class="login-title-warp">
          <div class="login-title">
            <div class="login-title-text">手机号登录</div>
            <div class="login-title-tip">未注册的手机号登陆后将自动注册</div>
          </div>
        </div>
        <div class="login-form">
          <div class="phone-input">
            <input type="text" v-model="login_form.phone" name="" id="" placeholder="请输入手机号码">
          </div>
          <div class="code-input">
            <div class="code-text">
              <input type="text" v-model="login_form.verifyCode" name="" id="" placeholder="请输入图形验证码">
            </div>
            <div ref="code-img" class="code-btn" @click="Code()">
              <span class="code-placeholder"></span>
            </div>
          </div>
          <div class="captcha-input">
            <div class="captcha-text">
              <input type="text" v-model="login_form.SmsCode" name="" id="" placeholder="请输入短信验证码">
            </div>
            <div class="captcha-btn"
            @click="SmsCode()"
            :disabled="second < totalSeconds"
            >
              {{ second === totalSeconds ? '获取验证码' : `重新获取(${second}s)` }}
            </div>
          </div>
          <div class="login-btn">
            <button @click="login()">登 录</button>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
import { Toast } from 'vant'
import { getVerifyCode, getSmsCode, loginBySms } from '@/api/login'
import { mapMutations } from 'vuex'
export default {
  name: 'LoginPage',
  data () {
    return {
      res: { base64Image: '', code: '' },
      login_form: {
        phone: '', // 手机号
        verifyCode: '', // 图形验证码
        SmsCode: '' // 短信验证码
      },
      alert: '错误提示的文案',
      totalSeconds: 60, // 倒计时总秒数
      second: 60, // 当前倒计时的秒数
      timer: null, // 定时器 id
      isSendingSms: true, // 是否正在发送短信验证码
      isFetchingCode: false // 添加防重复点击标志
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
    ...mapMutations(['setUserInfo']),
    // 返回
    onClickLeft () {
      Toast('返回')// 轻提示
      this.$router.back()// 返回
    },
    // 获取图形验证码
    async Code () {
      // 防止重复点击
      if (this.isFetchingCode) {
        return
      }
      try {
        const response = await getVerifyCode()
        this.res = response.data
        console.log('获取验证码')
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
      } finally {
        this.isFetchingCode = false
      }
    },
    // 登录校验
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

      // 登录时额外校验短信验证码
      if (type === 'login' && !this.login_form.SmsCode) {
        Toast('请输入短信验证码')
        return false
      }

      return true
    },
    // 重置表单
    resetForm () {
      this.login_form = {
        phone: '',
        verifyCode: '',
        SmsCode: ''
      }
    },
    // 定时器
    getTimer () {
      // 先检查是否已有定时器在运行
      if (this.timer !== null) {
        return // 如果已有定时器，直接返回
      }
      this.timer = setInterval(() => {
        this.second--
        console.log('正在倒计时...')
        if (this.second <= 0) {
          clearInterval(this.timer)
          this.timer = null
          this.second = this.totalSeconds
          this.isSendingSms = true
        }
      }, 1000)
    },
    // 获取短信验证码
    async SmsCode () {
      if (!this.isSendingSms) {
        return
      }
      if (!this.validateForm('sms') || this.second < this.totalSeconds) {
        return
      }

      try {
        const response = await getSmsCode({ phone: this.login_form.phone, verifyCode: this.login_form.verifyCode })
        console.log('获取短信验证码')
        console.log(response)
        Toast('验证码已发送,请注意查收')
        this.isSendingSms = false
        this.getTimer()
      } catch (err) {
        console.error(err)
      }
    },
    // 登录
    async login () {
      // 使用统一校验函数，传入'login'类型
      if (!this.validateForm('login')) {
        return
      }
      console.log('登录')
      try {
        // 实现登录逻辑
        const loginData = {
          phone: this.login_form.phone,
          smsCode: this.login_form.SmsCode,
          verifyCode: this.login_form.verifyCode
        }

        const response = await loginBySms(loginData)
        console.log(response)
        if (response.msg === 'success' && response.code === 200) {
          Toast('登录成功')
          this.$store.commit('user/setUserInfo', response.data)

          // // 设置带有过期时间的 Cookie
          // this.setCookieWithExpiration('userToken', response.data.token, 7) // 7天过期

          this.resetForm()
          this.$router.push('/user')
        // this.$router.go(-1)
        } else if (response.code !== 200) {
          Toast(response.msg || '登录失败，网络异常')
          this.Code()
        } else {
          Toast(response.msg || '登录失败，请稍后重试')
          this.Code()
        }
      } catch (err) {
        console.log(err)
        Toast('登录失败，服务器异常' || err)
      }
    },
    // 添加设置 Cookie 的方法
    setCookieWithExpiration (name, value, days) {
      const expires = new Date()
      expires.setTime(expires.getTime() + days * 24 * 60 * 60 * 1000)
      document.cookie = `${name}=${value};expires=${expires.toUTCString()};path=/`
    },

    // 读取 Cookie 的方法
    getCookie (name) {
      const nameEQ = name + '='
      const ca = document.cookie.split(';')
      for (let i = 0; i < ca.length; i++) {
        let c = ca[i]
        while (c.charAt(0) === ' ') c = c.substring(1, c.length)
        if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length)
      }
      return null
    }
  },
  destroyed () {
    if (this.timer) {
      clearInterval(this.timer)
      this.timer = null
    }
    if (this.codeTimer) {
      clearInterval(this.codeTimer)
      this.codeTimer = null
    }
    // 使用钩子函数，在组件销毁时清除定时器
  }
}
</script>

<style lang="less" scoped>
.login-sms {
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
  height: 100px;
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
      width: 120px;    // 与后端WIDTH一致
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
  .captcha-input{
    display: flex;
    justify-content: space-between;
    padding-top: 20px;
    .captcha-text{
      flex: 1;
    }
    .captcha-btn{
      width: 120px;
      height: 60px;
      display: flex;
      align-items: center;
      justify-content: center;
      color: #ff8e3d;
      font-size: 18px;
      border-radius: 6px;
      cursor: pointer;
      user-select: none;

      &[disabled] {
        // background-color: #f5f5f5;
        color: #999999;
        cursor: not-allowed;
        opacity: 0.6;

        &:active {
          opacity: 0.6;
        }
      }

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
}
</style>
