<template>
  <div id="karteiKarten">
    <select name="class" id="classNames"
            @change="updateClass($event)"
    >
      <option value="all">
        Alle
      </option>
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
    <div>
      <button id="btn_1"
              v-if="yes"
              @click=" index == size -1 ? evaluate() : rightU()">
        <img src="@/assets/green.jpg" id="button_1">
      </button>
      <button id="btn_1"
              v-if="no"
              @click=" index == size -1 ? evaluate() : wrongU()">
        <img src="@/assets/red.jpg" id="button_2">
      </button>
    </div>
    <button v-if="finish" @click="reloadP">
      Neustart
    </button>

    <div>


    </div>
    <div class="result-item">
      {{ result }}
      <button
          v-if="relearn"
          @click="loadWrong()">Falsche lernen
      </button>
    </div>
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
    wrongs: [],
    classes: [],
    index: 0,
    right: 0,
    wrong: 0,
    yes: true,
    no: true,
    finish: false,
    result: 'Resultat:',
    size: 0,
    relearn: false,
    testStudent: {}
  }),

  async mounted() {
    const res = await axios.get('/students/list');
    this.students = await res.data;
    this.studentsAll = this.students;
    this.wrongs = new Array();
    this.classes = new Array();

    for (let i in this.students) {
      if (!this.classes.includes(this.students[i].className) && typeof this.students[i].className !== 'undefined') {
        this.classes.push(this.students[i].className);
      }
    }
    this.size = this.students.length;
  },
  methods: {
    updateClass(event) {
      if(typeof event !== 'undefined') {
        let className = event.target.value
        this.index = 0;
        if (className != "all"){
          this.students = this.studentsAll.filter(item => item.className === className)
          this.size = this.students.length;
          this.yes = true
          this.no = true
          this.right = 0;
          this.wrong = 0;
          this.result = 'Resultat: '
        }else {
          this.students = this.studentsAll;
          this.size = this.students.length;
          this.yes = true
          this.no = true
          this.right = 0;
          this.wrong = 0;
          this.result = 'Resultat: '
        }
      }
      else {
        this.students = this.studentsAll;
        this.size = this.students.length;
        this.yes = true
        this.no = true
        this.right = 0;
        this.wrong = 0;
        this.result = 'Resultat: '
      }

    },
    evaluate() {
      this.result = "Resultat: " + (this.right / (this.wrong + this.right) * 100).toFixed(2)  + "%"
      if (this.wrongs.length == 0) {
        this.finish = true;
        this.yes = false
        this.no = false
      }

      if(this.finish) {
        this.yes = false
        this.no = false
      }
      if(this.index == this.size -1 && !this.finish) {
        this.yes = false
        this.no = false
        this.relearn = true;
      }
    },
    wrongU() {
      if (this.index < this.size - 1) {
        this.wrong++;
        this.result = "Resultat: " + (this.right / (this.wrong + this.right) * 100).toFixed(2)  + "%"
        this.wrongs.push(this.students[this.index])

      } else if (this.wrongs.length == 0) {
        this.finish = true;
      }
      if(this.finish) {
        this.index = this.size
      }
      this.index++;

    },
    rightU() {
      if (this.index < this.size - 1) {
        this.right++;
        this.result = "Resultat: " + (this.right / (this.wrong + this.right) * 100).toFixed(2)  + "%"
      } else if (this.wrongs.length == 0) {
        this.finish = true;
      }
      if(this.finish) {
        this.index = this.size
      }
      this.index++;
    },

    loadWrong() {
      this.relearn = false;
      this.students = this.wrongs;
      this.size = this.students.length;
      this.index = 0
      this.right = 0
      this.wrong = 0
      this.result = ''
      this.wrongs = new Array();
      this.yes = true
      this.no = true
    },
    reloadP() {
      this.index = 0
      this.right = 0
      this.wrong = 0
      this.result = ''
      //this.wrongs = new Array();
      let select = document.getElementById("classNames");
      select.selectedIndex = 0;
      this.updateClass()
      this.finish = false
    }
  }
}
</script>

<style scoped>
#btn_1 {
  background-color: white;
  border: none;
}
#button_1 {
  width: 50px;
  height: 50px;
  border: none;
}
#button_2 {
  width: 50px;
  height: 50px;
  border: none;
}

#btn_1 img {
  border-radius: 0;
  padding-inline: 10px;
}

select {
  margin-bottom: 1em;
  padding: .25em;
  border: 0;
  border-bottom: 2px solid currentcolor;
  font-weight: normal;
  letter-spacing: .15em;
  border-radius: 0;
}

#karteiKarten {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
}
</style>