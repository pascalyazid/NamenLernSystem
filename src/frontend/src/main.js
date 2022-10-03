import { createApp } from 'vue';
import VueAxios from 'vue-axios';
import App from '@/App';
import axios from 'axios';
import Router from './router/index';

const app = createApp(App);
app.use(VueAxios, axios);
app.use(Router);
app.mount('#app');
