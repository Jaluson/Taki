<script setup lang="ts">

import {reactive, ref} from "vue";
import {FormInst, FormItemRule, FormRules} from "naive-ui";

interface FormState {
  username: string;
  password: string;
  accountType: number; // 1.邮箱 2.用户名
  remember: boolean;
}

const isLogin = ref(true);
const logging = ref(false);

const formState = reactive<FormState>({
  username: '',
  password: '',
  accountType: 1,
  remember: true
})

const loginForm = ref<FormInst | null>(null)

const rules: FormRules = {
  username: [
    {
      required: true,
      validator: (_: FormItemRule, value: string): boolean | Error => {
        if (!value) {
          return new Error('账号不能为空')
        }
        if (!isValidInput(value)) {
          return new Error('账号格式不正确')
        }
        if (value.length < 6) {
          return new Error('账号长度不得低于6位')
        }
        if (value.length > 21) {
          return new Error('账号长度不得打于20位')
        }
        return true
      },
      trigger: ['input', 'blur']
    }
  ],
  password: [
    {
      required: true,
      validator: (_: FormItemRule, value: string): boolean | Error => {
        if (!value) {
          return new Error('密码不能是空的哦')
        }
        if (value.length < 6) {
          return new Error('密码长度不得低于6位')
        }
        if (value.length > 21) {
          return new Error('密码长度不得打于20位')
        }
        return true
      },
      trigger: ['input', 'blur']
    }
  ]
}

const isValidInput = (inputValue: string): boolean => {
  const emailRegex = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/;
  const usernameRegex = /^[a-zA-Z0-9_.-]+$/;
  if (emailRegex.test(inputValue)) {
    formState.accountType = 1
    return true;
  } else if (usernameRegex.test(inputValue)) {
    formState.accountType = 2
    return true;
  } else {
    return false;
  }
}

const login = (): void => {

  loginForm.value?.validate((errors: any) => {
    if (!errors) {
      console.log(111)
    } else {
      console.log(errors)
      console.log(222)
    }
  })
}

</script>

<template>
  <div class="main-container">
    <div class="login-form">
      <n-form ref="loginForm" :model="formState" size="large" style="width: 500px" label-width="auto" :rules="rules">
        <n-form-item label="账号" path="username">
          <n-input round v-model:value="formState.username" :placeholder="isLogin?'请输入账号/邮箱地址':'自定义账号'"/>
        </n-form-item>
        <n-form-item label="密码" path="password">
          <n-input round type="password" v-model:value="formState.password" placeholder="此处输入密码"/>
        </n-form-item>
        <n-form-item>
          <n-button strong round style="width: 100%;" :loading="logging" @click="login">登 录</n-button>
        </n-form-item>
        <n-form-item>
          <n-button strong round size="tiny" text :loading="logging" @click="login">注册一个账号</n-button>
        </n-form-item>
      </n-form>
    </div>
  </div>
</template>

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