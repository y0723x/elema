import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './style/index.css'
import ElementPlus from './utils/component/elementPlus'
import { postRequest, getRequest, putRequest, deleteRequest } from '@/utils/request'

createApp(App).use(store).use(router).use(ElementPlus).mount('#app')