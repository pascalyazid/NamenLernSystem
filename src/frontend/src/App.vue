<template>
	<header>
		<h1>NamenLernSystem</h1>

		<nav>
			<router-link :to="{ name: 'GabrielHome' }">home</router-link>
			<router-link :to="{ name: 'GabrielLernen' }">lernen</router-link>
		</nav>
	</header>

	<main>
		<router-view> <Home /> </router-view>
		<router-view> <Lernen /> </router-view>
	</main>

	<footer>footer</footer>
</template>

<script>
import Home from './components/Home.vue';
import Lernen from './components/Lernen.vue';

export default {
	name: 'App',
	components: { Home, Lernen },
};
</script>

<style>
#app {
	font-family: Avenir, Helvetica, Arial, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	color: #2c3e50;
}
</style>
