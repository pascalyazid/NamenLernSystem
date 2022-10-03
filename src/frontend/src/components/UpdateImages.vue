<template>
  <main>
    <p>Bilder hochladen/aktualisieren</p>
    <button v-on:click="update">Aktualisieren</button>
  </main>
</template>

<script>
import axios from "axios";

export default {
  name: "UpdateImages",

  methods: {
    update() {
      axios.get('/images/readFiles')
          .then((response) => {
            console.log(response)
          })
    }
  }
}
</script>

<style scoped>

</style>