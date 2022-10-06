<template>
  <div style="text-align: center; width: -moz-available">
    <p v-for="entry in historyEntries.slice().reverse()" v-bind:key="entry">{{entry.message}}</p>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: 'HistoryView',
  data() {
    return {
      historyEntries: [],
    };
  },
  async mounted() {
    const res = await axios.get('/students/listHistory');

    const data = await res.data;
    this.historyEntries = data;
  }
};
</script>