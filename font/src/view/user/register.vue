<script setup lang="ts">
import {reactive, ref} from "vue";
import {CountdownInst, FormInst, FormItemRule, FormRules} from "naive-ui";
import http from "@/utils/http.ts";

interface UserRegister {
  email: string;
  username: string;
  password: string;
  confirmPassword: string;
  accountType: number; // 1.邮箱 2.用户名
  verificationCode: string;
}

const formState = reactive<UserRegister>({
  email: '',
  username: '',
  password: '',
  confirmPassword: '',
  accountType: 1, // 1.邮箱 2.用户名
  verificationCode: ''
})
const loginForm = ref<FormInst | null>(null)

let Registering = ref(false)

const rules: FormRules = {
  email: [{
    required: true,
    validator: (_: FormItemRule, value: string): boolean | Error => {
      if (!value) {
        return new Error('邮箱不能为空')
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
    }
  }],
  username: [
    {
      required: true,
      validator: (_: FormItemRule, value: string): boolean | Error => {
        if (!value) {
          return new Error('账号不能为空')
        }
        let emailRegex = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/;
        if (emailRegex.test(value)) {
          return new Error('邮箱格式不正确')
        }
        if (value.length < 6) {
          return new Error('邮箱长度不得低于6位')
        }
        if (value.length > 30) {
          return new Error('邮箱长度不得打于31位')
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
  ],
  confirmPassword: [
    {
      required: true,
      validator: (_: FormItemRule, value: string): boolean | Error => {
        if (value !== formState.password) {
          return new Error('密码不一致')
        }
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
  ],
  // verificationCode: [
  //   {
  //     required: true,
  //     validator: (_: FormItemRule, value: number): boolean | Error => {
  //       if (!value) {
  //         return new Error('验证码不能为空')
  //       }
  //       const str: string = value.toString()
  //       if (str.length !== 6) {
  //         return new Error('验证码格式错误')
  //       }
  //       return true
  //     },
  //     trigger: ['input', 'blur']
  //   }
  // ]
}


const countdownRef = ref<CountdownInst | null>()

const isValidInput = (inputValue: string): boolean => {
  let emailRegex = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/;
  let usernameRegex = /^[a-zA-Z0-9_.-]+$/;
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

const handlerRegister = (): void => {
  loginForm.value?.validate((errors) => {
    if (errors) {
      // 校验失败
      console.log(errors)
    } else {
      // 校验通过 进入注册
      register()
    }
  })

}

const register = (): void => {
  const vo = {
    email: formState.email,
    username: formState.username,
    password: formState.password,
    verificationCode: formState.verificationCode
  };
  console.log('vo >>>', vo)
  // http.post('/user/generatorVerifyCode', vo).then(res => {
  //   console.log('res >>> ', res)
  // })
}

const emailSendCountDown = ref({
  switcher: false,
  duration: 5 * 1000
})

/**
 * 倒计时结束回调函数
 */
const handlerCountDownFinished = () => {
  emailSendCountDown.value.switcher = false
  countdownRef.value?.reset()
}

/**
 * 倒计时显示转化为秒
 * @param props props: { hours: number, minutes: number, seconds: number, milliseconds: number }
 */
const renderCountdown = (props: { seconds: number }) => {
  return `${props.seconds}秒`;
};

const handleSendEmailBtn = (): void => {
  loginForm.value?.validate().then(() => {
    http.post('/user/generatorVerifyCode', null, {
      params: {
        email: formState.email
      }
    })
    emailSendCountDown.value.switcher = true
  })
}

</script>

<template>
  <div class="main-container">
    <div class="login-form">
      <n-form ref="loginForm" :model="formState" size="large" style="width: 500px" label-width="auto" :rules="rules">
        <n-form-item label="邮箱" path="email">
          <n-input round v-model:value="formState.email" placeholder="请输入邮箱地址">
          </n-input>
        </n-form-item>
        <n-form-item label="账号" path="username">
          <n-input round v-model:value="formState.username" placeholder="请输入自定义账号"/>
        </n-form-item>
        <n-form-item label="密码" path="password">
          <n-input round type="password" v-model:value="formState.password" autocomplete="password"
                   placeholder="此处输入密码"/>
        </n-form-item>
        <n-form-item label="确认密码" path="confirmPassword">
          <n-input round type="password" v-model:value="formState.confirmPassword" placeholder="确认密码"/>
        </n-form-item>
        <n-form-item label="验证码" path="verificationCode">
          <n-input round type="password" v-model:value="formState.verificationCode" placeholder="请输入验证码">
            <template #suffix>
              <n-button strong secondary round :disabled="emailSendCountDown.switcher"
                        @click="handleSendEmailBtn">
                <div v-if="emailSendCountDown.switcher">
                  <n-countdown ref="countdown" :duration="emailSendCountDown.duration"
                               :active="emailSendCountDown.switcher"
                               :render="renderCountdown"
                               :on-finish="handlerCountDownFinished"/>
                </div>
                <div v-else>
                  发送
                </div>
              </n-button>
            </template>
          </n-input>
        </n-form-item>
        <n-form-item>
          <n-button strong round style="width: 100%;" :loading="Registering" @click="handlerRegister">注 册</n-button>
        </n-form-item>
      </n-form>
    </div>
  </div>
</template>

<style scoped lang="scss">
.main-container {
  height: 100%;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;

  .login-form {
    padding: 20px;
    background-color: #dadada;
    border-radius: 10px;
    box-shadow: 0 2px 12px 0 #2f3542;
    transition: width 0.3s ease, height 0.3s ease;
  }

}
</style>