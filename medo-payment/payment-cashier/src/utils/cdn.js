import Vue from 'vue'

const defaultUrl = '/h5/'

const cdns = {
  'c.medo-payment.tech': 'http://s3.ap-northeast-2.amazonaws.com/medo-payment-admin-dev/',
  'cashier.medo-payment.tech': defaultUrl,
  'wipaygroup.com': defaultUrl,
  'tradeserver.kldd.com.au': 'http://res.supaytechnology.com/',
  'superpay.kldd.com.au': 'http://res.supaytechnology.com/'
}

let cdnBaseUrl = cdns[location.host] ? cdns[location.host] : defaultUrl

Vue.prototype.cdnBaseUrl = cdnBaseUrl
Vue.prototype.programName = programName
