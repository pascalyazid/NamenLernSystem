<template>
  <div class="home">
    <div class="homecard">
        <a href="/lernen">
          <img src="/lernen.png" />
          <p>Lernen</p>
        </a>
    </div>
    <div class="homecard">
      <a href="/test">
        <img src="/testen.png" />
        <p>Testen</p>
      </a>
    </div>
    <div class="homecard">
      <a href="/download">
        <img src="/download.png" />
        <p>Download</p>
      </a>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomeView'
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