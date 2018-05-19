<template>
  <div class="hello">
    <button class=”Search__button” @click="callRestService()">CALL Spring Boot REST backend service</button>
    <h1>{{ response }}</h1>
  </div>
</template>

<script>
  import {AXIOS} from './http-common'

  export default {
    name: 'HelloWorld',
    data() {
      return {
        response: [],
        errors: []
      }
    },

    methods: {
      callRestService() {
        AXIOS.get(`api/v1/mobsters`)
        .then(response => {
          // JSON responses are automatically parsed.
          this.response = response.data
        })
        .catch(e => {
          this.errors.push(e)
        })
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    display: inline-block;
    margin: 0 10px;
  }

  a {
    color: #42b983;
  }
</style>
