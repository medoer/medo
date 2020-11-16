<template lang="html">
  <div></div>
</template>

<script>
export default {
  data() {
    return {
      params: Object.assign({}, this.$route.query)
    }
  },
  created() {
    this.runWeixinJS(this.wechatpay)
  },
  methods: {
    convertCParams() {
      // 与后端约定，如果cParams=== '{}',不返回
      if (this.params.cParams === '{}') {
        return ''
      }
      const _params = JSON.parse(this.params.cParams)
      let _str = ''
      for (let key in _params) {
        _str += '&' + key + '=' + _params[key]
      }
      return _str.replace('&', '?')
    },
    wechatpay() {
      const vm = this
      const data = this.params
      window.WeixinJSBridge.invoke(
        'getBrandWCPayRequest',
        {
          appId: data.appId,
          timeStamp: data.timestamp,
          nonceStr: data.nonceStr,
          package: data.package,
          signType: data.signType,
          paySign: data.paySign
        },
        function(res) {
          if (res.err_msg === 'get_brand_wcpay_request:ok') {
            if (!data.callback) {
              vm.$router.push({
                name: 'Success',
                query: {
                  trxId: vm.$route.query.transactionId,
                  channelEn: vm.$route.query.channelEn
                }
              })
            } else {
              window.location.href = data.callback + vm.convertCParams()
            }
          } else if (res.err_msg === 'get_brand_wcpay_request:cancel') {
            WeixinJSBridge.invoke('closeWindow', {}, function(res) {
              console.log('get_brand_wcpay_request:cancel')
            })
          } else {
            WeixinJSBridge.invoke('closeWindow', {}, function(res) {
              console.log('get_brand_wcpay_request:fail')
            })
          }
        }
      )
    },
    runWeixinJS(cb) {
      if (typeof window.WeixinJSBridge === 'undefined') {
        if (document.addEventListener) {
          document.addEventListener('WeixinJSBridgeReady', cb, false)
        } else if (document.attachEvent) {
          document.attachEvent('WeixinJSBridgeReady', cb)
          document.attachEvent('onWeixinJSBridgeReady', cb)
        }
      } else {
        cb()
      }
    }
  }
}
</script>
