<template>
  <v-app id="inspire" style="background-color: #e3e3e3">
    <router-view></router-view>
  </v-app>
</template>

<script>
import AccountsDashboard from "./components/AccountsDashboard.vue";
import { mapGetters } from 'vuex'

export default {
  name: "app",
  methods: {
   checkCurrentLogin () {
      if (!this.currentUser && this.$route.path !== '/') {
        console.log('not logged in')
        this.$router.push('/?redirect=' + this.$route.path)
      }
    }
  },
  computed: {
    ...mapGetters({ currentUser: 'currentUser'})
  },
  components: {
    AccountsDashboard
  },
  updated() {
    this.checkCurrentLogin();
  },
  created() {
    this.checkCurrentLogin();
  }
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
