<template>
  <div class="home">
    <div class="homecard" @click="toggle(4)">
          <img src="/lernen.png" />
          <p>Multiple-Choise</p>
    </div>
    <div class="homecard" @click="toggle(2)">

        <img src="/testen.png" />
        <p>Karteikarten</p>
    </div>
    <div class="homecard" @click="toggle(3)">
        <img src="/download.png" />
        <p>Download</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomeView',
  methods: {
    toggle(num) {
      this.emitter.emit("toggle", num);
    }
  }
};
</script>

<style>


.home {
  display: flex;
  justify-content: space-between;
  width: -moz-available;
  margin-left: 5%;
  margin-right: 5%;
  height: 100%;
}

.homecard {
  width: 27%;
  box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
  border-radius: 15px;
  padding: 2%;
  text-align: center;
  font-size: 1.4vw;
}

.homecard img {
  width: 95%;
  height: 95%;
}

.homecard a {
  text-decoration: none;
  color: black;
}

</style>