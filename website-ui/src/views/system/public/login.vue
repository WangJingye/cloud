<template>
  <div class="custom-bg"
       :style="'background-image: linear-gradient(rgba(0, 0, 0, 0), rgba(0, 0, 0, 0.3)), url('+require('../../../assets/images/login-background.jpg')+');'">
    <div class="login-head">
      <h3>{{title}}</h3>
    </div>
    <div class="login-box">
      <div class="login-title">用户登录</div>
      <a-form-model class="login-form" :model="data" onsubmit="return false;">
        <a-form-model-item>
          <a-input v-model="data.username" placeholder="用户名">
            <a-icon slot="prefix" type="user" style="color:rgba(0,0,0,.25)"/>
          </a-input>
        </a-form-model-item>
        <a-form-model-item>
          <a-input v-model="data.password" type="password" placeholder="密码">
            <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25)"/>
          </a-input>
        </a-form-model-item>
        <a-form-model-item>
          <a-button
            style="width: 100%"
            type="primary"
            @click="handleSubmit"
            :disabled="data.username === '' || data.password === ''"
          >
            登录
          </a-button>
        </a-form-model-item>
      </a-form-model>
    </div>
  </div>
</template>
<script>
import {login} from '@/api/system/public/login'

export default {
  name: 'login',
  data () {
    return {
      data: {
        username: '',
        password: ''
      },
      title: '后台管理系统',
      layout: 'empty'
    }
  },
  methods: {
    handleSubmit (e) {
      e.preventDefault()
      login({username: this.data.username, password: this.data.password}).then(result => {
        this.$cookies.set('userInfo', result.data, 24 * 3600)
        this.$router.push({
          path: '/'
        })
      })
    }
  }
}
</script>

<style scoped>
  .login-head {
    width: 20rem;
    text-align: center;
    margin: 20vh auto 2rem auto;
  }

  .login-head h3 {
    color: #FFFFFF;
    font-size: 1.75rem;
  }

  .login-box {
    color: #FFFFFF;
    width: 20rem;
    padding: 1rem;
    margin: auto;
    border-radius: 0.25rem;
    border: 1px solid #74B5C9;
    box-shadow: 0 0 10px #3FBEEB;
  }

  .login-box .login-title {
    text-align: center;
    font-size: 20px;
    margin-bottom: 1rem;
  }

  .captcha-box {
    position: relative;
  }

  .captcha-box img {
    cursor: pointer;
    top: 0;
    position: absolute;
    right: 0;
    padding: 0.25rem;
    height: calc(1.5em + 0.5rem);
  }

  .custom-bg {
    opacity: 1;
    background-repeat: no-repeat;
    background-size: cover;
    height: 100%;
    left: 0;
    margin: 0;
    padding: 0;
    position: fixed;
    top: 0;
    transition: opacity 700ms;
    width: 100%;
  }

  @media (min-width: 768px) {
    .custom-bg {
      background-position: center center;
    }
  }
</style>
