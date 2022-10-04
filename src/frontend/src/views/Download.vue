<template>
<p>HTML-Page downloaden:</p>
  <button @click="download">
    Download
  </button>
</template>


<script>

import axios from "axios";

export default {
  name: 'DownloadView',
  methods: {
      async download() {
        const res = await axios.get('/students/list');

        const data = await res.data;
        var htmlData = "<div style='display: flex; flex-wrap: wrap; justify-content: space-between;'>";
        for (let i = 0; i < data.length; i++) {
          htmlData += "<div><img src='" + data[i].path + "' style='width: 150px; height: 150px; object-fit: cover'/> " +
              "<p style='width: min-content'>" + data[i].firstName + " " + data[i].lastName + "</div></p><br/>";
        }
        htmlData += "</div>"
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
  }
};
</script>