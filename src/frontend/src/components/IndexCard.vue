<template>
  <div>
    <div class="scene scene--card">
      <div
          class="card"
          @click="cardOne == 'start' ? (cardOne = 'flipped' ) : (cardOne = 'start' )"
          v-bind:class="{ flipme: cardOne == 'flipped' }">
        <div class="card__face card__face--front">
          <img :src="'/students/load?id=' + id" class="imageS">
        </div>
        <div class="card__face card__face--back">
          <p>{{ firstName }} {{ lastName }}</p>

        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "IndexCard",
  props: ['firstName', 'lastName', 'id'],
  data() {
    return {
      cardOne: ""
    };
  },
}
</script>

<style scoped>

body {
  font-family: sans-serif;
}

.scene {
  width: 200px;
  height: 260px;
  margin: 40px 0;
  perspective: 600px;
}

.card {
  width: 100%;
  height: 100%;
  transition: transform 1s;
  transform-style: preserve-3d;
  cursor: pointer;
  position: relative;
}

.card__face {
  position: absolute;
  width: 100%;
  height: 100%;
  line-height: 260px;
  color: white;
  text-align: center;
  font-weight: bold;
  font-size: 15px;
  backface-visibility: hidden;
}

.card__face--front {
  background: none;
}

.card__face--back {
  background: darkslategrey;
  transform: rotateY(-180deg);
}

.flipme {
  transform: rotateY(-180deg);
}

.imageS {
  width: 100%;
  height: 100%;
}
</style>