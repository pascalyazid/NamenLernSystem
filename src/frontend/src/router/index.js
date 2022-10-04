import { createRouter, createWebHistory } from 'vue-router';
import Resultat from '../views/Resultat.vue';
import Lernen from '../views/Lernen.vue';
import Test from '@/views/Test';

const routes = [
	{
		path: '/resultat',
		name: 'GabrielResultat',
		component: Resultat,
	},

	{ redirect: '/lernen', path: '/' },
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
];

const router = createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes,
});

export default router;
