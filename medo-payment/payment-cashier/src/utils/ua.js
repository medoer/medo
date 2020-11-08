import Vue from 'vue'
import wechatFunc from './wechatConfig'
import alipayFunc from './alipayConfig'

const ua = window.navigator.userAgent.toLocaleLowerCase()
let channelName = 'alipay'

if (ua.indexOf('micromessenger') !== -1) {
  channelName = 'wechat'
  wechatFunc()
} else if (ua.indexOf('alipay') !== -1) {
  channelName = 'alipay'
  alipayFunc()
}

Vue.prototype.channelName = channelName
