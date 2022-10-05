<template>
  <main>

    <p v-on:click="update" id="download">Bilder updaten</p>
  </main>
</template>

<script>
import axios from "axios";

export default {
  name: "UpdateImages",

  methods: {
    update() {
      axios.get('/students/readFiles')
          .then((response) => {
            if(response.status == 200){
              alert("Bilder aktualisiert")
            }
          })
          .catch((error) => {
        if(error.response.status == 400) {
          alert("Fehler beim aktualisieren")
        }
      })
    }
  }
}
</script>

<style scoped>
#download {
  padding-top: 3px;
  font-family: Avenir, Helvetica, Arial, sans-serif;
}
</style>