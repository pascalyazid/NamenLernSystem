<template>
  <div id="download" style="top: 10%; position: absolute;">
  <h2>HTML-Page downloaden:</h2>
    <div v-for="(className,i) in this.classes" :key="i">
      <input type="checkbox"  :id="className" />
      <label :for="className">&nbsp;{{ className }}</label>
    </div>
  <button class="button" @click="download">
    Download
  </button>
  </div>
  <div class="inputContainer">

  </div>
</template>


<script>

import axios from "axios";

export default {
  name: 'DownloadView',
  data: () => ({
    classes: [],
    dataClasses: []
  }),
  async mounted() {
    const res = await axios.get('/students/list');
     let data = await res.data;
    for (let i in data) {
      if (!this.classes.includes(data[i].className) && typeof data[i].className !== 'undefined') {
        this.classes.push(data[i].className);
      }
    }
  },
  methods: {
      async download() {
        this.dataClasses = []
        for (let i = 0; i < this.classes.length; i++) {
          if (document.getElementById(this.classes[i]).checked){
            this.dataClasses.push(this.classes[i])
          }
        }
        if (this.dataClasses.length == 0){
          alert("Bitte wähle mindestens eine Klasse aus!")
        }else {
          const res = await axios.get('/students/list');

          const data = await res.data;
          var htmlData = "<!DOCTYPE html><head></head><body></body></head><div style='display: flex; flex-wrap: wrap; justify-content: space-between;'>";
          for (let i = 0; i < data.length; i++) {
            if (this.dataClasses.includes(data[i].className)){
              htmlData += "<div style='padding: 1%; background-color: #bcd0d7; margin-bottom: 2%; border-radius: 15px'><img src='" + data[i].path + "' style='width: 150px; height: 150px; object-fit: cover'/> " +
                  "<p style='width: min-content'>" + data[i].firstName + " " + data[i].lastName + "</div></p><br/>";
            }
          }
          htmlData += "</div></body>"
          const file = new File([htmlData], 'data.html', {
            type: 'text/plain',
          })
          const link = document.createElement('a')
          const url = URL.createObjectURL(file)

          link.href = url
          link.download = file.name
          document.body.appendChild(link)
          link.click()

          document.body.removeChild(link)
          window.URL.revokeObjectURL(url)
        }
      },
      classChange(event){
        let className = event.target
        console.log(className)
      }

  }
};
</script>
<style>
#download {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  cursor: auto;
}

.button {
  backface-visibility: hidden;
  background-color: #6277f1;
  border-radius: 6px;
  border-width: 0;
  box-shadow: rgba(50, 50, 93, .1) 0 0 0 1px inset,rgba(50, 50, 93, .1) 0 2px 5px 0,rgba(0, 0, 0, .07) 0 1px 1px 0;
  box-sizing: border-box;
  color: #fff;
  cursor: pointer;
  font-family: -apple-system,system-ui,"Segoe UI",Roboto,"Helvetica Neue",Ubuntu,sans-serif;
  font-size: 100%;
  height: 44px;
  line-height: 1.15;
  margin: 12px 0 0;
  outline: none;
  overflow: hidden;
  padding: 0 25px;
  position: relative;
  text-align: center;
  text-transform: none;
  transform: translateZ(0);
  transition: all .2s,box-shadow .08s ease-in;
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
  width: max-content;
}
</style>