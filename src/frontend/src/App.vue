<template>
	<header>
		<h1>NamenLernSystem</h1>

			<nav>
				<router-link to="/">home</router-link>
				<router-link to="/lernen">lernen</router-link>
			</nav>
		</header>

	<main>
		<router-view> <Home /> </router-view>
	</main>

		<footer>footer</footer>
	</div>
</template>

<script>
import Home from './components/Home.vue';

export default {
	name: 'App',
	components: { Home },
};
</script>

<style>
#app {
	font-family: Avenir, Helvetica, Arial, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	text-align: center;
	color: #2c3e50;
}

header {
	display: flex;
	justify-content: space-between;
	align-items: center;
}

nav > a {
	margin-right: 5px;
}

main {
	flex-grow: auto;
}

#root {
	display: flex;
	justify-content: space-between;
	flex-direction: column;
	min-height: 100vh;
}
</style>
