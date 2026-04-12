<template>
  <div class="login">
    <div class="login-overlay"></div>
    <div class="login-shell">
      <div class="brand-panel">
        <div class="brand-tag">WEST PLAN</div>
        <h2 class="brand-title">{{ title }}</h2>
        <p class="brand-subtitle">西部计划综合管理平台</p>
        <ul class="brand-points">
          <li>统一门户，快速登录</li>
          <li>业务流程，集中处理</li>
          <li>数据看板，实时掌握</li>
        </ul>
      </div>

      <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
        <h3 class="title">欢迎登录</h3>
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            type="text"
            auto-complete="off"
            placeholder="账号"
          >
            <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            auto-complete="off"
            placeholder="密码"
            @keyup.enter.native="handleLogin"
          >
            <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
          </el-input>
        </el-form-item>
        <el-form-item prop="code" v-if="captchaEnabled">
          <el-input
            v-model="loginForm.code"
            auto-complete="off"
            placeholder="验证码"
            style="width: 63%"
            @keyup.enter.native="handleLogin"
          >
            <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
          </el-input>
          <div class="login-code">
            <img :src="codeUrl" @click="getCode" class="login-code-img"/>
          </div>
        </el-form-item>
        <el-checkbox v-model="loginForm.rememberMe" class="remember-check">记住密码</el-checkbox>
        <el-form-item style="width:100%;">
          <el-button
            :loading="loading"
            size="medium"
            type="primary"
            style="width:100%;"
            @click.native.prevent="handleLogin"
          >
            <span v-if="!loading">登 录</span>
            <span v-else>登 录 中...</span>
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="el-login-footer">
      <span>{{ footerContent }}</span>
    </div>
  </div>
</template>

<script>
import { getCodeImg } from "@/api/login"
import Cookies from "js-cookie"
import { encrypt, decrypt } from '@/utils/jsencrypt'
import defaultSettings from '@/settings'

export default {
  name: "Login",
  data() {
    return {
      title: process.env.VUE_APP_TITLE,
      footerContent: defaultSettings.footerContent,
      codeUrl: "",
      loginForm: {
        username: "",
        password: "",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
        ],
        code: [{ required: true, trigger: "change", message: "请输入验证码" }]
      },
      loading: false,
      // 验证码开关
      captchaEnabled: true,
      // 注册开关
      register: false,
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {
    this.getCode()
    this.getCookie()
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img
          this.loginForm.uuid = res.uuid
        }
      })
    },
    getCookie() {
      const username = Cookies.get("username")
      const password = Cookies.get("password")
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        ...this.loginForm,
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 })
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 })
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 })
          } else {
            Cookies.remove("username")
            Cookies.remove("password")
            Cookies.remove('rememberMe')
          }
          this.$store.dispatch("Login", this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || "/" }).catch(()=>{})
          }).catch(() => {
            this.loading = false
            if (this.captchaEnabled) {
              this.getCode()
            }
          })
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.login {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  min-height: 100vh;
  background-image: url("../assets/images/login-background.jpg");
  background-size: cover;
  background-position: center;
  overflow: hidden;
}

.login-overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(120deg, rgba(8, 31, 67, 0.72), rgba(16, 72, 68, 0.58));
}

.login-shell {
  position: relative;
  z-index: 1;
  width: min(980px, calc(100% - 32px));
  display: grid;
  grid-template-columns: 1.1fr 0.9fr;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 18px 50px rgba(5, 15, 35, 0.35);
}

.brand-panel {
  color: #f2f7ff;
  padding: 42px 40px;
  background: linear-gradient(145deg, rgba(6, 38, 83, 0.86), rgba(6, 65, 55, 0.76));
  backdrop-filter: blur(2px);
}

.brand-tag {
  display: inline-block;
  padding: 6px 12px;
  margin-bottom: 14px;
  border-radius: 14px;
  background: rgba(255, 255, 255, 0.12);
  letter-spacing: 1px;
  font-size: 12px;
}

.brand-title {
  margin: 0;
  font-size: 30px;
  line-height: 1.28;
}

.brand-subtitle {
  margin: 12px 0 22px;
  color: rgba(242, 247, 255, 0.86);
}

.brand-points {
  margin: 0;
  padding-left: 18px;
  line-height: 1.95;
}

.title {
  margin: 0 auto 26px auto;
  text-align: center;
  color: #203046;
  letter-spacing: 1px;
}

.login-form {
  border-radius: 0;
  background: rgba(255, 255, 255, 0.96);
  padding: 36px 30px 18px;
  .el-input {
    height: 42px;
    input {
      height: 42px;
      border-radius: 8px;
    }
  }
  .input-icon {
    height: 42px;
    width: 14px;
    margin-left: 2px;
  }
}
.remember-check {
  margin: 0 0 18px 0;
}

.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 42px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #f0f4ff;
  font-family: "Segoe UI", sans-serif;
  font-size: 12px;
  letter-spacing: 1px;
}
.login-code-img {
  height: 42px;
}

@media (max-width: 960px) {
  .login-shell {
    grid-template-columns: 1fr;
  }
  .brand-panel {
    display: none;
  }
  .login-form {
    padding: 28px 22px 14px;
  }
}
</style>
