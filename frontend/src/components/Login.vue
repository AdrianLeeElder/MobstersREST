<template>
    <v-layout flex align-center justify-center>
        <v-flex xs12 sm4 elevation-6>
            <v-toolbar class="pt-2 blue darken-3">
                <v-toolbar-title class="white--text">
                    <h4>Login</h4>
                </v-toolbar-title>
            </v-toolbar>
            <v-card>
                <v-card-text class="pt-4">
                    <div>
                        <v-form @submit.prevent>
                            <v-text-field label="Username" v-model="username" required></v-text-field>
                            <v-text-field label="Password" v-model="password" :append-icon="e1 ? 'visibility' : 'visibility_off'" :append-icon-cb="() => (e1 = !e1)" :type="e1 ? 'password' : 'text'" :rules="passwordRules" required></v-text-field>
                            <v-layout justify-space-between>
                                <v-btn @click="login" :class=" { 'blue darken-4 white--text' : valid, disabled: !valid }">Login</v-btn>
                            </v-layout>
                        </v-form>
                    </div>
                </v-card-text>
            </v-card>
        </v-flex>
    </v-layout>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "Login",
  data() {
    return {
      valid: false,
      username: "",
      password: "",
      error: false,
      e1: false,
      passwordRules: [v => !!v || "Password is required"]
    };
  },
  computed: {
    ...mapGetters({ currentUser: "currentUser" })
  },
  created() {
    this.checkCurrentLogin();
  },
  updated() {
    this.checkCurrentLogin();
  },
  methods: {
    checkCurrentLogin() {
      if (this.currentUser) {
        this.$router.replace(this.$route.query.redirect || "/index");
      }
    },
    login() {
      this.$http
        .post("/auth", { username: this.username, password: this.password })
        .then(request => this.loginSuccessful(request))
        .catch(() => this.loginFailed());
    },
    loginSuccessful(req) {
      if (!req.data.token) {
        this.loginFailed();
        return;
      }
      this.error = false;
      localStorage.token = req.data.token;
      console.log('Adding token to local storage: ' + req.data.token)
      this.$store.dispatch("login");
      this.$router.replace(this.$route.query.redirect || "/index");
      this.$router.go(0)
    },
    loginFailed() {
      this.error = "Login failed!";
      this.$store.dispatch("logout");
      delete localStorage.token;
    }
  }
};
</script>
