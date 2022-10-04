<template>
	<div class="container">
		<div
			class="cardContainer"
			v-for="(student, index) in visibleStudents"
			v-bind:key="student.id"
		>
			<div>
				<img :src="student.img" alt="alt" />
			</div>

			<div class="optionContainer">
				<div class="nameContainer">
					<p
						@click="setSelectedIndex(stu.index)"
						v-for="stu in activeNames"
						v-bind:key="stu.index + index"
						:class="optionStyle(stu.index)"
					>
						{{ stu.name }}
					</p>
				</div>
				<div class="buttonContainer">
					<button :class="answer !== null ? 'noselect' : ''" @click="checkAnswer">
						check
					</button>
					<button @click="nextStudent">next</button>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import axios from 'axios';

export default {
	name: 'GabrielLernen',
	data() {
		return {
			data: [],
			visibleStudents: [],
			activeIndex: 0,
			activeNames: [],
			selectedIndex: -1,
			answer: null,
		};
	},

	async mounted() {
		const res = await axios.get('/students/list');

		const data = await res.data;

		console.log(data);

		this.data = data.map((e) => ({ ...e, img: '/students/load?id=' + e.id }));

		for (let i = 0; i < data.length; i++) {
			this.visibleStudents.push(this.data[i]);
		}

		document
			.querySelector(':root')
			.style.setProperty('--translate-value', -100 / data.length + '%');

		this.setNames();
	},

	methods: {
		setNames() {
			this.activeNames.push({
				name:
					firstToUpper(this.data[this.activeIndex].firstName) +
					' ' +
					firstToUpper(this.data[this.activeIndex].lastName),
				index: this.activeIndex,
			});

			const addedIndices = [];

			for (let i = 0; i < 4; i++) {
				const index = rng(0, this.data.length);

				if (index == this.activeIndex || addedIndices.includes(index)) {
					i -= 1;
					continue;
				}

				addedIndices.push(index);

				this.activeNames.push({
					name:
						firstToUpper(this.data[index].firstName) +
						' ' +
						firstToUpper(this.data[index].lastName),
					index,
				});
			}

			this.activeNames.sort(() => Math.random() - Math.random());
		},

		setSelectedIndex(index) {
			if (this.answer == null) this.selectedIndex = index;
		},

		checkAnswer() {
			this.answer = this.activeIndex == this.selectedIndex;
		},

		nextStudent() {
			if (this.answer == null) {
				alert('bitte ein Namen auswählen und checken!!!');
				return;
			}
			const ctnr = document.querySelector('.container');

			ctnr.classList.remove('animation');
			ctnr.offsetWidth;

			document
				.querySelector(':root')
				.style.setProperty(
					'--current-translate-value',
					(-100 / this.data.length) * this.activeIndex + '%'
				);
			this.activeIndex++;
			this.answer = null;
			this.activeNames = [];
			this.setNames();
			ctnr.classList.add('animation');
		},

		optionStyle(index) {
			let css = '';

			if (index == this.selectedIndex) {
				css += 'selectedName ';

				if (this.answer !== null) {
					css += this.answer ? 'correct ' : 'wrong ';
				}
			}

			if (this.answer !== null) {
				css += 'noselect ';

				if (index == this.activeIndex) css += 'correct selectedName ';
			}

			return css;
		},
	},
};

function rng(min, max) {
	return Math.round(Math.random() * (max - min) + min);
}

function firstToUpper(str) {
	return str.charAt(0).toUpperCase() + str.slice(1);
}
</script>

<style>
:root {
	--translate-value: 10%;
	--current-translate-value: 0%;
}

img {
	height: 450px;
	border-radius: 20px;
}

.container {
	display: flex;
	justify-content: space-between;
	width: max-content;
}

.container.animation {
	animation: next 0.75s ease-in-out forwards;
}

.container > div {
	padding: 15px;
	width: 95vw;
	background-color: aliceblue;
	border-radius: 20px;
	margin-right: 10px;
	height: 80vh;
	display: flex;
	align-items: center;
	justify-content: space-between;
	box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;
}

.optionContainer {
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	flex-grow: 1;
}

.nameContainer {
	display: flex;
	flex-direction: column;
	flex-grow: 2;
	justify-content: space-between;
	padding: 0 20px;
}

.nameContainer > p {
	padding: 20px;
	font-size: 22.5px;
	cursor: pointer;
	margin: 12.5px;
}

.selectedName {
	background-color: #334155;
	color: #fff;
	border-radius: 20px;
	box-sizing: border-box;
}

.correct {
	background-color: green;
}

.wrong {
	background-color: red;
}

.noselect {
	cursor: not-allowed !important;
	-webkit-touch-callout: none;
	-webkit-user-select: none;
	-khtml-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
}

.buttonContainer {
	display: flex;
	justify-content: end;
}

.buttonContainer > button {
	padding: 4px 10px;
	font-size: large;
	margin-left: 10px;
}

@keyframes next {
	from {
		transform: translateX(var(--current-translate-value));
	}
	to {
		transform: translateX(calc(var(--current-translate-value) + var(--translate-value)));
	}
}
</style>
