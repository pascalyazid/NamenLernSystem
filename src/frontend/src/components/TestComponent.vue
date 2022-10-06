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
    <p
        style="padding-bottom: 30px"
    >{{ students[index].note }}</p>
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

    <div style="padding-top: 50px">
      <form v-on:submit.prevent="uploadNote"
            v-on:submit="clearNote"
            style="display: flex; justify-content: center; align-items: center; flex-direction: column"
      >
        <div class="form-control">
          <input type="text" v-model="form.note" name="note" :placeholder="students[index].note" id="addNote">
        </div>
        <div class="form-control">
          <input type="submit" value="Add Note">
        </div>
      </form>
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
    resultMessage: '',
    size: 0,
    relearn: false,
    testStudent: {},
    form: {
      note: ''
    }
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
      if (typeof event !== 'undefined') {
        let className = event.target.value
        this.index = 0;
        if (className != "all") {
          this.students = this.studentsAll.filter(item => item.className === className)
          this.size = this.students.length;
          this.yes = true
          this.no = true
          this.right = 0;
          this.wrong = 0;
          this.result = 'Resultat: '
        } else {
          this.students = this.studentsAll;
          this.size = this.students.length;
          this.yes = true
          this.no = true
          this.right = 0;
          this.wrong = 0;
          this.result = 'Resultat: '
        }
      } else {
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
      let currentdate = new Date();
      let dateTime = ((currentdate.getHours() < 10) ? "0" : "") + currentdate.getHours() + ":" + ((currentdate.getMinutes() < 10) ? "0" : "") + currentdate.getMinutes()
          + "@" + currentdate.getDate() + "/" + currentdate.getMonth() + "/" + currentdate.getFullYear()
      let e = document.getElementById("classNames");
      let className = e.options[e.selectedIndex].text;

      this.result = "Resultat: " + (this.right / (this.wrong + this.right) * 100).toFixed(2) + "%"
      if (this.wrongs.length == 0) {
        this.finish = true;
        this.yes = false
        this.no = false

        this.uploadResult(dateTime + " - " + "Karteikarten "
            + ((this.right / (this.wrong + this.right) * 100).toFixed(2) + "%")
            + " korrekt. " + " [" + className +  "] ")
      }

      if (this.finish) {
        this.yes = false
        this.no = false

      }
      if (this.index == this.size - 1 && !this.finish) {
        this.yes = false
        this.no = false
        this.relearn = true;
        this.uploadResult(dateTime + " - " + "Karteikarten"
            + ((this.right / (this.wrong + this.right) * 100).toFixed(2) + "%")
            + " korrekt, " + "Klasse: " + className)
      }
    },
    wrongU() {
      this.clearNote()
      if (this.index < this.size - 1) {
        this.wrong++;
        this.result = "Resultat: " + (this.right / (this.wrong + this.right) * 100).toFixed(2) + "%"
        this.wrongs.push(this.students[this.index])

      } else if (this.wrongs.length == 0) {
        this.finish = true;
      }
      if (this.finish) {
        this.index = this.size
      }
      this.index++;

    },
    rightU() {
      this.clearNote()
      if (this.index < this.size - 1) {
        this.right++;
        this.result = "Resultat: " + (this.right / (this.wrong + this.right) * 100).toFixed(2) + "%"
      } else if (this.wrongs.length == 0) {
        this.finish = true;
      }
      if (this.finish) {
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
    },
    uploadNote(e) {
      e.preventDefault();
      let formData = new FormData();
      formData.append('note', this.form.note);
      formData.append('id', this.students[this.index].id)
      let noteField = document.getElementById("addNote")
      axios.post("/students/updateNote", formData)
          .then((response) => {
            if (response.status == 200) {
              //alert("Notiz gespeichert");
              noteField.value = '';
              this.students[this.index].note = this.form.note;
            }
          })
          .catch((error) => {
            if (error.response.status == 400) {
              alert("Notiz konnte nicht gespeichert werden")
            }
          })
    },
    clearNote() {
      let noteField = document.getElementById("addNote")
      noteField.value = " "
    },

    uploadResult(message) {
      let formData = new FormData();
      formData.append('message', message);
      axios.post("/students/writeHistory", formData)
          .then((response) => {
            if (response.status == 200) {
              console.log("Log saved")
            }
          })
          .catch((error) => {
            if (error.response.status == 400) {
              console.log("Error saving Log")
            }
          })
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