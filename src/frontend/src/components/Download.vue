<template>
  <div id="download" style="top: 10%; position: absolute;">
  <p>HTML-Page downloaden:</p>
    <div v-for="(className,i) in this.classes" :key="i">
      <input type="checkbox"  :id="className" />
      <label :for="className">{{ className }}</label>
    </div>
  <button @click="download">
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
              htmlData += "<div><img src='" + data[i].path + "' style='width: 150px; height: 150px; object-fit: cover'/> " +
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
  height: 100%;
  width: 100%;
}
</style>