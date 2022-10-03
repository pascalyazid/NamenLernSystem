import { createApp } from 'vue';
import VueAxios from 'vue-axios';
import App from '@/App';
import axios from 'axios';


const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/home',
            name: 'home',
            component: HomeComponent
        },
        {
            path: '/',
            redirect: '/home'
        },
        {
            path: '/settings',
            name: 'settings',
            component: SettingsComponent
        }
});

const app = createApp(App);
app.use(VueAxios, axios);
app.mount('#app');
