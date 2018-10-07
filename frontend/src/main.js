// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Vuetify from 'vuetify'
import VueAxios from 'vue-axios'
import mobstersApi from './vue-axios/axios'
import store from './store'

import 'material-design-icons-iconfont/dist/material-design-icons.css' // Ensure you are using css-loader
import 'vuetify/src/stylus/main.styl'
import 'vuetify/src/stylus/settings/_colors.styl'

Vue.config.productionTip = false
Vue.use(Vuetify)
Vue.use(VueAxios, mobstersApi)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  mobstersApi,
  store,
  components: { App },
  template: '<App/>'
})
