<template>
  <p>Test</p>
  <p>

  </p>
  <IndexCard :firstName="students[index].firstName" :lastName="students[index].lastName" :id="students[index].id"
             v-if="normal"
  />
  <IndexCard :firstName="wrongs1[index].firstName" :lastName="wrongs1[index].lastName" :id="wrongs1[index].id"
             v-if="repeat1"
  />
  <IndexCard :firstName="wrongs2[index].firstName" :lastName="wrongs2[index].lastName" :id="wrongs2[index].id"
             v-if="repeat2"
  />
  <div>
    <button
        v-if="index != size -1 "
        @click=" index == size -1 ? evaluate() : rightU()">Richtig
    </button>
    <button
        v-if="index != size -1"
        @click=" index == size -1? evaluate() : wrongU()">Falsch
    </button>
  </div>
  <div class="result-item">
    {{ result }}
    <button
        v-if="index == size -1"
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
    wrongs1: [],
    wrongs2: [],
    index: 0,
    right: 0,
    wrong: 0,
    normal: true,
    wrong1: true,
    wrong2: false,
    repeat1: false,
    repeat2: false,
    result: '',
    size: 0,
    testStudent: {}
  }),

  async mounted() {
    const res = await axios.get('/students/list');
    this.students = await res.data;
    this.students.push('');
    this.wrongs1 = new Array();
    this.wrongs2 = new Array();
    this.size = this.students.length;
  },
  methods: {
    evaluate() {
      this.result = "Resultat: " + (this.right / (this.wrong + this.right) * 100) + "%"
    },
    wrongU() {
      if (this.index != this.size) {
        this.wrong++;
        this.result = "Resultat: " + (this.right / (this.wrong + this.right) * 100) + "%"
        if(this.wrong1){
          this.wrongs2.push(this.students[this.index])
        }
        else {
          this.wrongs1.push(this.students[this.index])
        }
        console.log("index:" + this.index)

        this.index++;
      }

    },
    rightU() {
      if (this.index != this.size) {
        this.right++;
        this.index++;
        this.result = "Resultat: " + (this.right / (this.wrong + this.right) * 100) + "%"
        console.log("index:" + this.index)
      }
    },

    loadWrong() {
      this.normal = false;
      this.index = 0
      this.right = 0
      this.wrong = 0
      this.result = ''

      if(this.wrong1) {
        this.size = this.wrongs2.length
        this.repeat2 = true;
        this.wrong1 = false;
        this.wrong2 = true;
      }
      else {
        this.size = this.wrongs1.length
        this.repeat1 = true;
        this.wrong2 = false;
        this.wrong1 = true;
      }
      console.log("size: " + this.size)
    }
  }
}
</script>

<style scoped>

</style>