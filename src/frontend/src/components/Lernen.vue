<template>
	<div id="score" v-if="amountCorrect >= 0">{{ amountCorrect }}/{{ activeIndex }}</div>
	<div class="container">
		<div class="auswahlContainer">
			<h2>Klassenauswahl</h2>
			<div class="inputContainer">
				<div v-for="classe in classes" v-bind:key="classe">
					<input v-model="filter[classe.toLowerCase()]" type="checkbox" :id="classe" />
					<label for="classe">{{ classe }}</label>
				</div>
			</div>
			<div class="buttonContainer">
				<button style="padding: 5px 20px" @click="filterStudents">start</button>
			</div>
		</div>

		<div class="cardContainer" v-for="student in visibleStudents" v-bind:key="student.id">
			<div>
				<img :src="student.img" alt="alt" />
			</div>

			<div class="optionContainer">
				<div v-if="!newStudent" class="nameContainer">
					<p
						@click="setSelectedIndex(stu.index)"
						v-for="stu in activeNames"
						v-bind:key="stu.uuid + Date.now()"
						:class="optionStyle(stu.index)"
					>
						{{ stu.name }}
					</p>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import axios from 'axios';
import { v4 } from 'uuid';

export default {
	name: 'GabrielLernen',
	data() {
		return {
			data: [],
			visibleStudents: [],
			activeIndex: -1,
			activeNames: [],
			selectedIndex: -1,
			answer: true,
			newStudent: true,
			filter: {},
			amountCorrect: -1,
			wrongStudents: [],
			classes: new Set(),
		};
	},

	async mounted() {
		const res = await axios.get('/students/list');

		const data = await res.data;

		this.data = data.map((e) => ({ ...e, img: '/students/load?id=' + e.id }));

		for (let i = 0; i < data.length; i++) {
			this.visibleStudents.push(this.data[i]);

			this.classes.add(this.data[i].className);
		}

		document
			.querySelector(':root')
			.style.setProperty(
				'--translate-value',
				-100 / (this.visibleStudents.length + 1) + '%'
			);

		this.setNames();
	},

	methods: {
		filterStudents() {
			this.visibleStudents = this.visibleStudents.filter((e) =>
				this.filter[e.className.toLowerCase()] ? e : undefined
			);

			document
				.querySelector(':root')
				.style.setProperty('--translate-value', `calc(-1 * ${100}vw)`);

			this.visibleStudents.sort(() => Math.random() - Math.random());

			this.nextStudent();
		},

		setNames() {
			this.activeNames.push({
				name:
					firstToUpper(this.visibleStudents[this.activeIndex].firstName) +
					' ' +
					firstToUpper(this.visibleStudents[this.activeIndex].lastName),
				index: this.activeIndex,
				id: this.visibleStudents[this.activeIndex].id,
				uuid: v4(),
			});

			const addedIds = [];

			for (let i = 0; i < 4; i++) {
				const index = rng(0, this.visibleStudents.length);

				if (
					this.visibleStudents[index].id == this.visibleStudents[this.activeIndex].id ||
					addedIds.includes(this.visibleStudents[index].id)
				) {
					i -= 1;
					continue;
				}

				addedIds.push(this.visibleStudents[index].id);

				this.activeNames.push({
					name:
						firstToUpper(this.data[index].firstName) +
						' ' +
						firstToUpper(this.data[index].lastName),
					index,
					id: this.data[index].id,
					uuid: v4(),
				});
			}

			this.activeNames.sort(() => Math.random() - Math.random());
		},

		setSelectedIndex(index) {
			if (this.answer == null) {
				this.selectedIndex = index;
				this.newStudent = false;

				this.checkAnswer();

				setTimeout(() => {
					this.nextStudent();
				}, 2000);
			}
		},

		checkAnswer() {
			this.answer = this.activeIndex == this.selectedIndex;
		},

		nextStudent() {
			if (this.answer == null) {
				alert('bitte ein Namen auswählen und checken!!!');
				return;
			}

			if (this.answer) {
				this.amountCorrect++;
			} else {
				if (this.visibleStudents > 0)
					this.visibleStudents.push(this.visibleStudents[this.activeIndex]);
			}

			this.newStudent = true;
			const ctnr = document.querySelector('.container');

			ctnr.classList.remove('animation');
			ctnr.offsetWidth;
			setTimeout(() => {
				this.newStudent = false;
			}, 500);

			document
				.querySelector(':root')
				.style.setProperty(
					'--current-translate-value',
					`calc(-1 * calc(${100 * (this.activeIndex + 1)}vw))`
				);

			this.activeIndex++;
			this.answer = null;
			this.activeNames = [];
			this.setNames();
			ctnr.classList.add('animation');
		},

		optionStyle(index) {
			let css = '';

			if (this.newStudent) return;

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
	return Math.ceil(Math.random() * (max - min) + min) - 1;
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

#score {
	position: fixed;
	top: 15%;
	left: 50%;
	transform: translateX(-50%);
	z-index: 10;
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

.inputContainer {
	width: max-content;
	flex-grow: 1;
	max-height: 50%;
	max-width: 50%;
}

.inputContainer > div {
	flex-grow: 1;
	width: 100%;
	height: fit-content;
	display: flex;
	justify-content: space-evenly;
}

.inputContainer > div > label {
	margin: 10px;
	font-size: 20px;
}
.container > div,
.auswahlContainer {
	padding: 15px;
	margin: 20px;
	width: calc(100vw - 70px);
	height: 80vh;
	display: flex;
	align-items: center;
	justify-content: space-between;
	box-shadow: rgb(0 0 0 / 24%) 0px 3px 8px;
	border-radius: 15px;
}

.auswahlContainer {
	display: flex;
	flex-direction: column;
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
	padding: 15px 20px;
	font-size: 22.5px;
	cursor: pointer;
	margin: 12.5px;
	border-radius: 10px;
}

.nameContainer > p:hover {
	background-color: #d0d8dd;
}

.selectedName {
	background-color: #334155;
	color: #fff;
	box-sizing: border-box;
}

.selectedName:hover {
	background-color: #334155;
}

.correct {
	background-color: green !important;
}

.wrong {
	background-color: red !important;
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
