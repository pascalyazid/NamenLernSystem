<template>
  <select name="class" id="classNames"
          @change="updateClass($event)"
  >
    <option
        v-for="(className, i) in this.classes" :key="i"
        :value="className"
    >
      {{ className }}
    </option>
  </select>
  <IndexCard :firstName="students[index].firstName" :lastName="students[index].lastName" :id="students[index].id"
             v-if="students != null && !finish"
  />
  <button v-if="finish"
          @click="reloadP"
  >
    Neustart
  </button>

  <div>
    <button
        v-if="index != size -1 "
        @click=" index == size -1 ? evaluate() : rightU()">Richtig
    </button>
    <button
        v-if="index != size -1"
        @click=" index == size -1 ? evaluate() : wrongU()">Falsch
    </button>
  </div>
  <div class="result-item">
    {{ result }}
    <button
        v-if="index == size -1 "
        @click="loadWrong()">Falsche lernen
    </button>
  </div>
</template>

<script>


import IndexCard from "@/components/IndexCard";
import axios from "axios";

export default {
  name: "TestComponent",
  components: {IndexCard},

  data: () => ({
    students: [''],
    studentsAll: [],
    wrongs1: [],
    wrongs2: [],
    classes: [],
    index: 0,
    right: 0,
    wrong: 0,
    normal: true,
    wrong1: true,
    wrong2: false,
    finish: false,
    result: '',
    size: 0,
    testStudent: {}
  }),

  async mounted() {
    const res = await axios.get('/students/list');
    this.students = await res.data;
    //this.students.push("")
    this.studentsAll = this.students;
    this.wrongs1 = new Array();
    this.wrongs2 = new Array();
    this.classes = new Array();

    for (let i in this.students) {
      if (!this.classes.includes(this.students[i].className) && typeof this.students[i].className !== 'undefined') {
        this.classes.push(this.students[i].className);
      }
    }
    console.log(this.classes);
    this.students = this.studentsAll.filter(item => item.className === this.classes[0])
    this.size = this.students.length;
    console.log(this.students)
  },
  methods: {
    updateClass(event) {
      let className = event.target.value
      this.students = this.studentsAll.filter(item => item.className === className)
      console.log(this.students)
      this.size = this.students.length;
    },
    evaluate() {
      this.result = "Resultat: " + (this.right / (this.wrong + this.right) * 100) + "%"
    },
    wrongU() {
      if (this.index < this.size - 1) {
        this.wrong++;
        this.result = "Resultat: " + (this.right / (this.wrong + this.right) * 100) + "%"
        this.wrongs1.push(this.students[this.index])
        console.log("index:" + this.index)
        console.log("size:" + this.size)
      } else if (this.wrongs1.length == 0) {
        this.finish = true;
      }
      this.index++;

    },
    rightU() {
      if (this.index < this.size - 1) {
        this.right++;
        this.result = "Resultat: " + (this.right / (this.wrong + this.right) * 100) + "%"
        console.log("index:" + this.index)
      } else if (this.wrongs1.length == 0) {
        this.finish = true;
      }
      this.index++;
    },

    loadWrong() {
      console.log(this.wrongs1)
      this.students = this.wrongs1;
      this.students.push("");
      this.size = this.students.length;
      this.index = 0
      this.right = 0
      this.wrong = 0
      this.result = ''
      this.wrongs1 = new Array();
      console.log("size: " + this.size)
    },
    reloadP() {
      location.reload();
    }
  }
}
</script>

<style scoped>

</style>