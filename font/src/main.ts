import { createApp } from 'vue'
import './style.css'
import App from './App.vue'

import router from "./router";

// 通用字体
import 'vfonts/Lato.css'
// 引入 Naive UI 样式
// import 'naive-ui/dist/index.css'

const app = createApp(App)
app.use(router)


app.mount('#app')
