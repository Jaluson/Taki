<template>
  <div class="main-container">
    <div class="form-container">
      <div class="login-form" v-if="isLogin">
        <n-form :model="formState" label-placement="left" label-width="auto">
          <n-form-item label="账号" path="username">
            <n-input round v-model:value="formState.username" placeholder="此处输入账号或邮箱"/>
          </n-form-item>
          <n-form-item label="密码" path="password">
            <n-input round type="password" v-model:value="formState.password" placeholder="此处输入密码"/>
          </n-form-item>
          <n-form-item>
            <n-button strong round>
              登 录
            </n-button>
            <n-button strong round @click="isLogin = !isLogin">
              注册账号
            </n-button>
          </n-form-item>
        </n-form>
      </div>
      <div class="register-form" v-if="!isLogin">
        <n-form :model="registerFrom" label-placement="left" label-width="auto">
          <n-form-item label="账号" path="username">
            <n-input round v-model:value="registerFrom.username" placeholder="此处输入账号或邮箱"/>
          </n-form-item>
          <n-form-item label="密码" path="password">
            <n-input round type="password" v-model:value="registerFrom.password" placeholder="此处输入密码"/>
          </n-form-item>
          <n-form-item>
            <n-button strong round>
              登 录
            </n-button>
            <n-button strong round @click="isLogin = !isLogin">
              返回登录
            </n-button>
          </n-form-item>
        </n-form>
      </div>
    </div>
  </div>

</template>

<script setup lang="ts">

import {reactive, ref} from "vue";

// import http from "@/utils/http.ts";

interface FormState {
  username: string;
  password: string;
  remember: boolean;
}

const isLogin = ref(true);

const formState = reactive<FormState>({
  username: '',
  password: '',
  remember: true,
})

const registerFrom = reactive({
  username: '',
  password: '',
})

</script>

<style scoped>

.main-container{
  height: 100%;
}

.form-container {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;

  > * {
    padding: 20px;
    background-color: #ffffff;
    border-radius: 10px;
    box-shadow: 0 2px 12px 0 #2f3542;

    transform: rotateY(360deg);
    transition: transform 3s ease; /* 过渡时间为3秒 */
  }
}
</style>