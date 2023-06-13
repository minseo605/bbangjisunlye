import { createApp } from 'vue';
import store from "@/sciprts/store"
import router from "@/sciprts/router"
import App from './App.vue';


createApp(App).use(store).use(router).mount('#app')
