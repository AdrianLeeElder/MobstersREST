<template>
  <v-app id="inspire" style="background-color: #e3e3e3">
    <v-toolbar dark color="primary" app>
      <v-toolbar-side-icon @click.stop="drawer = !drawer"></v-toolbar-side-icon>
      <v-toolbar-title class="white--text">Mobsters Bot - By Adrian Elder</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon>
        <v-icon>more_vert</v-icon>
      </v-btn>
    </v-toolbar>
    <v-navigation-drawer v-model="drawer" app>
      <v-list>
        <v-list-group>
          <v-list-tile slot="activator">
            <v-list-tile-action>
              <v-icon>account_circle</v-icon>
            </v-list-tile-action>
            <v-list-tile-title>
              Mobsters
            </v-list-tile-title>
          </v-list-tile>
        </v-list-group>
      </v-list>
    </v-navigation-drawer>
    <v-content>
      <accounts-dashboard></accounts-dashboard>
    </v-content>
  </v-app>
</template>

<script>
import AccountsDashboard from './components/AccountsDashboard.vue'

export default {
  name: 'App',
  data: () => ({
    drawer: false
  }),
  components: {
    AccountsDashboard
  }
}
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }
</style>
