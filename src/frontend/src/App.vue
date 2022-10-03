<template>
	<header>
		<h1>NamenLernSystem</h1>

		<nav>‹router-link :to="{ name: 'home' }"›‹/router-link›</nav>
	</header>

	<main>
		<router-view> <Home /> <UpdateImages/> </router-view>
	</main>

	<footer>footer</footer>
</template>

<script>
import Home from './components/Home.vue';
import UpdateImages from "@/components/UpdateImages";

export default {
	name: 'App',
	components: {UpdateImages, Home },
};
</script>

<style>
#app {
	font-family: Avenir, Helvetica, Arial, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	text-align: center;
	color: #2c3e50;
	margin-top: 60px;
}
</style>
