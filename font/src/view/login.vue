<template>
  <a-form :model="formState" autocomplete="off" @finish="onFinish" @finishFailed="onFinishFailed">
    <a-form-item label="Username" name="username">
      <a-input v-model:value="formState.username" />
    </a-form-item>
    <a-form-item label="Password" name="password">
      <a-input-password v-model:value="formState.password" />
    </a-form-item>
    <a-form-item>
      <a-button type="primary" html-type="submit">提交</a-button>
    </a-form-item>
  </a-form>
</template>

<script setup lang="ts">

import {reactive} from "vue";
import http from "@/utils/http.ts";

interface FormState {
  username: string;
  password: string;
  remember: boolean;
}

const formState = reactive<FormState>({
  username: '',
  password: '',
  remember: true,
})

const onFinish = (value: any) => {
  http.post('/test/login',value).then(res => {
    console.log('res:>>>', res)
  })
  console.log('onFinish', value)
}

const onFinishFailed = (errorInfo: any) => {
  console.log('Failed:', errorInfo);
};

</script>

<style scoped>

</style>