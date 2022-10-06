<template>
	<header>
		<h1>NamenLernSystem</h1>
		<nav>
			<p @click="toggle(1)">Home</p>
			<p @click="toggle(4)">Multiple-Choice</p>
			<p @click="toggle(2)">Karteikarten</p>
			<p @click="toggle(3)">Download</p>
			<UpdateImages />
		</nav>
	</header>
	<main>
		<Home v-if="showHome" />
		<TestComponent v-if="showTestComponent" />
		<Download v-if="showDownload" />
		<Lernen v-if="showLernen" />
	</main>

	<footer>© Quick-IT AG</footer>
</template>

<script>
import UpdateImages from '@/components/UpdateImages';
import Home from '@/components/Home';
import TestComponent from '@/components/TestComponent';
import Download from '@/components/Download';
import Lernen from '@/components/Lernen';

export default {
	name: 'App',
	components: { UpdateImages, TestComponent, Home, Download, Lernen },

	mounted() {
		this.emitter.on('toggle', (num) => {
			this.toggle(num);
		});
	},

	data: () => ({
		showHome: true,
		showTestComponent: false,
		showDownload: false,
		showLernen: false,
	}),
	methods: {
		toggle(num) {
			switch (num) {
				case 1:
					this.showHome = true;
					this.showTestComponent = false;
					this.showDownload = false;
					this.showLernen = false;
					break;
				case 2:
					this.showHome = false;
					this.showTestComponent = true;
					this.showDownload = false;
					this.showLernen = false;
					break;
				case 3:
					this.showHome = false;
					this.showTestComponent = false;
					this.showDownload = true;
					this.showLernen = false;
					break;
				case 4:
					this.showHome = false;
					this.showTestComponent = false;
					this.showDownload = false;
					this.showLernen = true;
					break;
			}
		},
	},
};
</script>

<style>
#app {
	font-family: Avenir, Helvetica, Arial, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	color: #c3e50;
	display: flex;
	justify-content: space-between;
	flex-direction: column;
	min-height: 100vh;
	overflow-x: hidden;
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

nav {
	display: flex;
	align-items: center;
}

nav > * {
	cursor: pointer;
}

nav > p:hover {
	text-decoration: underline;
}

nav > p::after {
	content: '|';
	margin: 0 7px;
	font-weight: 800;
}

main {
	flex: auto;
	margin: 5px 0;
	display: flex;
	align-items: center;
}

main > * {
	cursor: pointer;
}

footer {
	text-align: center;
	margin: px 0;
}
</style>
