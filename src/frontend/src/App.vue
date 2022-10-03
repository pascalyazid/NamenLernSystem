<template>
	<header>
		<h1>NamenLernSystem</h1>

			<nav>
				<router-link to="/">home</router-link>
				<router-link to="/lernen">lernen</router-link>
			</nav>
		</header>

		<main>
			<router-view />
		</main>

		<footer>footer</footer>
	</div>
</template>

<script>
export default {
	name: 'App',
};
</script>

<style>
#app {
	font-family: Avenir, Helvetica, Arial, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	color: #2c3e50;
	display: flex;
	justify-content: space-between;
	flex-direction: column;
	min-height: 100vh;
	padding: 0 5px;
}

* {
	padding: 0;
	margin: 0;
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
	flex: auto;
	margin: 5px 0;
}

footer {
	text-align: center;
	margin: 2px 0;
}
</style>
