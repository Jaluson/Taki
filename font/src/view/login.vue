<template>
  <div class="main-container">
    <div class="login-form">
      <n-form :model="formState" label-placement="left" label-width="auto" :rules="rules">
        <n-form-item label="账号" path="username">
          <n-input round v-model:value="formState.username" :placeholder="isLogin?'请输入账号/邮箱地址':'自定义账号'"/>
        </n-form-item>
        <n-form-item label="密码" path="password">
          <n-input round type="password" v-model:value="formState.password" placeholder="此处输入密码"/>
        </n-form-item>
        <n-form-item label="确认密码" path="confirmPassword" v-if="!isLogin">
          <n-input round type="password" v-model:value="formState.confirmPassword" placeholder="此处输入密码"/>
        </n-form-item>
        <n-form-item label="邮箱" path="email" v-if="!isLogin">
          <n-input round v-model:value="formState.email" placeholder="此处输入邮箱地址"/>
        </n-form-item>
        <n-form-item label="验证码" path="verifyCode" v-if="!isLogin">
          <n-input round v-model:value="formState.verifyCode" placeholder="此处输入邮箱收到的验证码"/>
        </n-form-item>
        <n-form-item>
          <n-button strong round> {{ isLogin ? "登录" : "注册" }}</n-button>
          <n-button strong round @click="isLogin = !isLogin"> {{ isLogin ? "注册账号" : "前往登录" }}</n-button>
        </n-form-item>
      </n-form>
    </div>
  </div>

</template>

<script setup lang="ts">

import {reactive, ref} from "vue";
import {FormItemRule, FormRules} from "naive-ui";

// import http from "@/utils/http.ts";

interface FormState {
  email: string;
  username: string;
  password: string;
  confirmPassword: string;
  verifyCode: string;
  remember: boolean;
}

const isLogin = ref(true);

const formState = reactive<FormState>({
  email: '',
  username: '',
  password: '',
  confirmPassword: '',
  verifyCode: '',
  remember: true
})

const rules: FormRules = {
  email: [
    {
      required: !isLogin,
      validator: (_: FormItemRule, value: string): boolean | Error => {
        if (!value) {
          return new Error('邮箱地址不能为空')
        }
        if (isValidEmail(value)) {
          return true
        } else {
          return new Error('邮箱格式不正确')
        }
      },
      trigger: ['input', 'blur']
    }

  ]
}

const isValidEmail = (email: string) => {
  let emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
}
</script>

<style scoped>


.main-container {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;

  .login-form {
    padding: 20px;
    background-color: #ffffff;
    border-radius: 10px;
    box-shadow: 0 2px 12px 0 #2f3542;
    transition: width 0.3s ease, height 0.3s ease; /* 添加过渡效果 */

    :deep(.n-form-item-label__text) {
      text-align: center;
    }

    :deep(.n-form-item-blank) {
      justify-content: space-evenly;
    }
  }

}
</style>