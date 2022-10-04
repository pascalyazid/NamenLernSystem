import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import Lernen from '../views/Lernen.vue';
import Test from "@/views/Test";
const routes = [
	{
		path: '/',
		name: 'GabrielHome',
		component: Home,
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
];
const router = createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes,
});
export default router;
