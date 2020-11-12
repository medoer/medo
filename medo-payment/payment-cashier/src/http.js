import Vue from 'vue'
import axios from 'axios'

axios.defaults.timeout = 500000

if (window.performance) {
  axios.interceptors.request.use(
    function(config) {
      config.apiStart = Math.round(performance.now())
      return config
    },
    function(error) {
      return Promise.reject(error)
    }
  )
}

axios.interceptors.response.use(
  response => {
    if (window.performance) {
      Vue.$ga.time(
        'API',
        response.config.url,
        Math.round(performance.now()) - response.config.apiStart,
        window.location.host
      )
    }
    return response
  },
  error => {
    return Promise.reject(error)
  }
)

axios.defaults.baseURL = '/payment'

export default axios
