<style scoped>
.calc {
  height: 52rem;
  width: 100%;
  position: relative;
}

.num {
  width: 75%;
  height: 52rem;
  background-color: #fff;
  display: flex;
  flex-wrap: wrap;
}

.num > div {
  height: 13rem;
  line-height: 13rem;
  min-width: 15rem;
  font-size: 5rem;
  color: #4a4a4a;
  width: 33.3%;
  display: inline-block;
}

.tool {
  width: 25%;
  height: 52rem;
  background-color: #ededed;
  position: absolute;
  top: 0;
  right: 0;
}

.tool > div {
  height: 13rem;
  line-height: 13rem;
  min-width: 18rem;
}

.tool > div:nth-child(2) {
  font-size: 2.8rem;
  color: #4a4a4a;
}

.tool > div:last-child,
.alipay .tool > div:last-child {
  height: 26rem;
  line-height: 26rem;
  background-color: #0076ff;
  color: #fff;
  font-size: 4rem;
}

.alipay .tool > div:last-child:active {
  background-color: #0077ee;
}

.wechat .tool > div:last-child {
  background-color: #44973c;
}

.wechat .tool > div:last-child:active {
  background-color: #449933;
}

.btn-disable {
  background-color: #ededed !important;
  color: #4a4a4a !important;
}

.calc .icon {
  height: 6rem;
  position: relative;
  top: 3rem;
  fill: #030303;
  width: 100%;
}

.num-item:active,
.num-item:focus {
  -webkit-tap-highlight-color: #f3f3f3;
  background-color: #f3f3f3;
}

.num > div,
.tool > div {
  position: relative;
  display: inline-block;
}

.num > div:after,
.tool > div:after {
  content: '';
  position: absolute;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 100%;
  border: 1px solid #d8d8d8;
  box-sizing: border-box;
  -webkit-transform-origin: left top;
  transform-origin: left top;
}

.tool > div:active,
.tool > div:focus {
  -webkit-tap-highlight-color: #f3f3f3;
  background-color: #f3f3f3;
}



.icon {
  width: 1em;
  height: 1em;
  fill: #00cc00;
  position: relative;
  top: 0.2rem;
  left: 0;
}
</style>

<template >
  <section>
    <div class="calc">
      <div class="num">
        <div v-for="item in nums" @touchstart="onTap(item)" class="num-item" :key="item">{{item}}</div>
      </div>
      <div class="tool">
        <div v-for="(item,index) in tools" @touchstart="handleClick(index)" class="num-item" :class="{'btn-disable': disabled && index === 2}" :key="item">
          <template v-if="item === `Delete`">
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#icon-delete"></use>
            </svg>
          </template>
          <template v-else>
            <span> {{item}}</span>
          </template>
        </div>
      </div>
    </div>
  </section>

</template>

<script>
const defaultTools = ['Delete', 'Remark', 'Pay']
const activeTools = ['Delete', 'Remark', 'Paying']
// 关于遮罩层使用：
// 微信使用项目中自定义组件，支付宝使用alipay原生组件。原因在于老版本UC浏览器对遮罩层支持有误。
export default {
  created() {
    this.val = this.inputValue
  },
  data() {
    return {
      nums: ['7', '8', '9', '4', '5', '6', '1', '2', '3', '0', '00', '.'],
      tools: defaultTools,
      val: '0',
      disabled: true,
      params: {}
    }
  },
  props: {
    showRemark: {
      default: false
    },
    inputValue: {
      default: '0'
    },
    page: {
      default: {}
    },
    remarkValue: {
      type: String
    },
    paying: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    handlePromoBtn() {
      this.promoInput = !this.promoInput
    },
    gotoSuccessPage(vm) {
      vm.$router.push({
        name: 'Success',
        query: {
          trxId: vm.params.transactionId,
          channelEn: vm.page.channelEn
        }
      })
    },
    updateInputValue() {
      this.$emit('update:inputValue', this.val)
      this.disabled = +this.val > 0 ? !true : true
    },
    activePayStatus() {
      if (this.channelName === 'alipay') {
        this.runAlipayJS(function() {
          AlipayJSBridge.call('showLoading', {
            text: 'Loading',
            cancelable: false
          })
        })
      } else {
        this.$emit('update:paying', true)
      }
      this.tools = activeTools
    },
    inactivePayStatus() {
      // if (this.channelName === 'alipay') {
        this.runAlipayJS(function() {
          AlipayJSBridge.call('hideLoading')
        })
      // } else {
      //   this.$emit('update:paying', false)
      // }
      this.tools = defaultTools
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
    },
    wechatpay() {
      const data = this.params
      const vm = this
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
            vm.gotoSuccessPage(vm)
            vm.inactivePayStatus()
          } else if (res.err_msg === 'get_brand_wcpay_request:cancel') {
            vm.inactivePayStatus()
          } else if (res.err_msg === 'get_brand_wcpay_request:fail') {
            vm.inactivePayStatus()
          } else {
            vm.inactivePayStatus()
            alert(res.err_msg)
          }
        }
      )
    },
    runAlipayJS(callback) {
      if (window.AlipayJSBridge) {
        callback && callback()
      } else {
        document.addEventListener('AlipayJSBridgeReady', callback, false)
      }
    },
    alipay() {
      const vm = this
      AlipayJSBridge.call(
        'tradePay',
        {
          tradeNO: this.page.tradeNO
        },
        function(result) {
          vm.gotoSuccessPage(vm)
          vm.inactivePayStatus()
        }
      )
    },
    handleClick(index) {
      if (index === 0) {
        this.handleDelete()
      } else if (index === 1) {
        this.handleRemark()
      } else if (index === 2) {
        this.handlePay()
      }
    },
    handleRemark() {
      this.$emit('update:showRemark', true)
    },
    handleDelete() {
      // this.val = this.val.length === 1 ? '0' : this.val.substr(0, this.val.length - 1)
      let _val = ''
      var len = this.val.length
      for (var i = 0; i < len; i++) {
        if (i < len - 1) {
          _val += this.val[i]
        }
      }
      if (_val === '') {
        _val = '0'
      }
      this.val = _val
      this.updateInputValue()
    },
    onTap(i) {
      event.preventDefault()
      // 输入位数的最大值
      const hasSpot = this.val.indexOf('.') !== -1
      if (this.val.length > 9) {
        return false
      }
      // 控制小数点
      if (hasSpot && i === '.') {
        return false
      }
      // 整数位最大六位
      if (hasSpot) {
        if (this.val.split('.')[1].length > 1) {
          return false
        } else if (this.val.split('.')[1].length === 1 && i === '00') {
          this.val += '0'
        } else {
          this.val += i
        }
      } else {
        if (this.val.length > 6 && i !== '.') {
          return false
        } else if (this.val.length === 6 && i === '00') {
          this.val += '0'
        } else if (this.val === '0') {
          if (i === '.') {
            this.val = '0.'
          } else if (i === '00' || i === '0') {
            return false
          } else {
            this.val = i
          }
        } else {
          this.val += i
        }
      }
      this.updateInputValue()
    },
    handlePay() {
      if (this.disabled) {
        return false
      }
      this.$ga.event('Button', 'Pay', 'Payment to')
      const vm = this
      this.activePayStatus()
      // params: {
      //       transactionId: this.$route.query.trxId
      //     }
      this.axios
        .post('/submit', {
          amount: this.inputValue,
          currency: "CNY",
          token: this.page.token,
          remark: this.remarkValue,
          openid: this.page.openid

        })
        .then(res => {
          alert(JSON.stringify(res))
          // if (res.data.code === '10000') {
            vm.params = res.data

            // if (vm.page.channelEn === 'wechat') {
            //   vm.runWeixinJS(vm.wechatpay)
            // } else if (vm.page.channelEn === 'alipay') {
            //   if (!vm.page.tradeNO) {
                window.location.href =
                  res.data.qrCode
                  //  +
                  // '?trxId=' +
                  // vm.params.trxId +
                  // '&hasAd=' +
                  // vm.params.hasAd
                vm.inactivePayStatus()
            //   } else {
            //     vm.runAlipayJS(vm.alipay)
            //   }
            // }
          // } else {
          //   vm.inactivePayStatus()
          //   vm.$ga.event('API return !200', JSON.stringify(res.data), '!200')
          // }
        })
        .catch(error => {
          alert(JSON.stringify(error))
          vm.$ga.exception('postQrcode error msg: ' + JSON.stringify(error))
          vm.$ga.event('API Error', error + '', 'Exception')
          alert('Error Msg: ' + error)
          vm.inactivePayStatus()
        })
    }
  }
}
</script>
