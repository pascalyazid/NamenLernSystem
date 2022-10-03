<template>
  <main>
    <button v-on:click="update">Bilder updaten</button>
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
            console.log(response)
          })
    }
  }
}
</script>

<style scoped>

</style>