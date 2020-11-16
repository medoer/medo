import Vue from 'vue'
import App from './App'
import router from './router'
import './assets/font/iconfont.js'
require('es6-promise').polyfill()
import axios from './http.js'
import VueAxios from 'vue-axios'
import './utils/cdn'
import './utils/ua'
import './utils/toFixed'

Vue.use(VueAxios, axios)

import VueAnalytics from 'vue-analytics'

Vue.use(VueAnalytics, {
  id: require('./ga-config').id,
  router,
  autoTracking: {
    exception: true,
    pageviewTemplate(route) {
      return {
        page: route.path,
        title: document.title,
        location: window.location.href
      }
    }
  }
})

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  axios,
  template: '<App/>',
  components: {
    App
  }
})
