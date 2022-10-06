<template>
	<div id="score" v-if="activeStudentIndex >= 0">
		{{ numOfCorrectAnswers }}/{{ activeStudentIndex }}
		<span v-if="activeStudentIndex >= selectedClassLength">{{ ' ' }}repetition</span>
	</div>
	<div class="container">
		<div class="auswahlContainer">
			<h2>Klassenauswahl</h2>
			<div class="inputContainer">
				<div v-for="classe in classes" v-bind:key="classe">
					<input v-model="filter[classe.toLowerCase()]" type="checkbox" :id="classe" />
					<label :for="classe">{{ classe }}</label>
				</div>
			</div>
			<div class="buttonContainer">
				<button style="padding: 2px 20px" @click="startLearnungApp">start</button>
			</div>
		</div>

		<div
			class="cardContainer"
			v-for="student in visibleStudentsOrder"
			v-bind:key="student"
		>
			<div>
				<img :src="students[student].img" alt="alt" />
			</div>

			<div class="optionContainer">
				<div class="nameContainer">
					<p
						@click="selectName(stu)"
						v-for="stu in students[student].names"
						v-bind:key="stu"
						:class="optionStyle(stu)"
					>
						{{ getFullStudentName(stu) }}
					</p>
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
			classes: new Set(),
			selectedClassLength: 0,
			filter: {},

			data: [],
			students: {},
			visibleStudentsOrder: [],

			currentCorrectStudentID: null,
			selectedStudentID: null,

			activeStudentIndex: -1,
			activeNames: [],
			selectedIndex: -1,
			answered: false,
			newStudent: true,

			numOfCorrectAnswers: 0,
		};
	},

	async mounted() {
		const res = await axios.get('/students/list');

		const data = await res.data;

		this.data = data.map((e) => {
			this.classes.add(e.className);

			return { ...e, img: '/students/load?id=' + e.id };
		});

		for (let i = 0; i < this.data.length; i++) {
			this.students[this.data[i].id] = this.data[i];
			this.visibleStudentsOrder.push(this.data[i].id);
		}

		for (let i = 0; i < this.data.length; i++) {
			this.students[this.data[i].id].names = this.setNames(this.data[i]);
		}

		document.querySelector(':root').style.setProperty('--translate-value', `-${100}vw`);
	},

	methods: {
		startLearnungApp() {
			this.visibleStudentsOrder = this.visibleStudentsOrder
				.filter((e) => {
					if (this.filter[this.students[e].className.toLowerCase()]) {
						return e;
					}

					return undefined;
				})
				.sort(() => Math.random() - Math.random());

			this.selectedClassLength = this.visibleStudentsOrder.length;

			this.nextStudent();
		},

		setNames(student) {
			const names = [];

			names.push(student.id);

			while (names.length < 5) {
				const i = rng(0, this.visibleStudentsOrder.length);

				if (names.includes(this.students[this.visibleStudentsOrder[i]].id)) continue;

				names.push(this.students[this.visibleStudentsOrder[i]].id);
			}

			return names.sort(() => Math.random() - Math.random());
		},

		selectName(id) {
			this.answered = true;
			this.selectedStudentID = id;

			setTimeout(() => {
				this.nextStudent();
			}, 2000);
		},

		nextStudent() {
			const container = document.querySelector('.container');

			container.classList.remove('animation');
			container.offsetWidth;
			container.classList.add('animation');

			document
				.querySelector(':root')
				.style.setProperty(
					'--current-translate-value',
					`-${100 * (this.activeStudentIndex + 1)}vw`
				);

			this.answered = false;

			if (
				this.selectedStudentID == this.currentCorrectStudentID &&
				this.activeStudentIndex >= 0
			) {
				this.numOfCorrectAnswers++;
			} else {
				if (this.activeStudentIndex >= 0) {
					this.visibleStudentsOrder.push(this.currentCorrectStudentID);

					this.students[this.currentCorrectStudentID].names = this.students[
						this.currentCorrectStudentID
					].names.sort(() => Math.random() - Math.random());
				}
			}

			this.activeStudentIndex++;

			this.currentCorrectStudentID = this.visibleStudentsOrder[this.activeStudentIndex];
			this.selectedStudentID = null;
		},

		optionStyle(id) {
			let css = '';

			if (this.answered) {
				if (id == this.selectedStudentID && id !== this.currentCorrectStudentID) {
					css += 'wrong ';
				}

				if (id == this.currentCorrectStudentID) css += 'correct ';

				css += 'noselect ';
			}

			return css;
		},

		getFullStudentName(id) {
			return (
				firstToUpper(this.students[id].firstName) +
				' ' +
				firstToUpper(this.students[id].lastName)
			);
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
	animation: next 0.5s ease-in-out forwards;
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
	margin: 30px;
	width: calc(100vw - 90px);
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

.correct {
	color: #fff;
	box-sizing: border-box;
	background-color: green !important;
}

.wrong {
	color: #fff;
	box-sizing: border-box;
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
