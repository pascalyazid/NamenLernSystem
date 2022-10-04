import { createRouter, createWebHistory } from 'vue-router';
import Resultat from '../views/Resultat.vue';
import Lernen from '../views/Lernen.vue';
import Test from '@/views/Test';
import Download from '../views/Download.vue';
import Home from '../views/Home.vue'

const routes = [
	{
		path: '/resultat',
		name: 'GabrielResultat',
		component: Resultat,
	},
	{
		path: '/lernen',
		name: 'GabrielLernen',
		component: Lernen,
	},
	{
		path: '/test',
		name: 'TestComponent',
		component: Test,
	},
	{
		path: '/download',
		name: 'DownloadView',
		component: Download,
	},
	{
		path: '/',
		name: 'HomeView',
		component: Home,
	},
];

const router = createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes,
});

export default router;
