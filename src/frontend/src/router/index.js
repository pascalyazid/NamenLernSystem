import { createRouter, createWebHistory } from 'vue-router';
import Resultat from '../views/Resultat.vue';
import Lernen from '../views/Lernen.vue';

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
];

const router = createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes,
});

export default router;
