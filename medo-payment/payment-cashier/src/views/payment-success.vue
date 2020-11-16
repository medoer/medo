
<style scoped>
.ps-message {
  height: 6rem;
  line-height: 6rem;
  width: 100%;
  background: #0076ff;
  font-size: 2.4rem;
  color: #fff;
  overflow: hidden;
}

.ps-message .ps-message-in {
  width: 8000%;
}

.ps-message .ps-message-in > div {
  float: left;
}

.ps-message .ps-message-in > div span {
  margin-right: 5rem;
}

.ps-status {
  height: 14.6rem;
  line-height: 14.6rem;
  font-size: 4.8rem;
  text-align: center;
  background: #f6f6f6;
}

.ps-image {
  height: 7rem;
  width: 7rem;
  border-radius: 50%;
  background: #0076ff;
  position: relative;
  top: 1.6rem;
}

.ps-icon {
  height: 4rem;
  width: 4rem;
  fill: #fff;
  position: absolute;
  top: 50%;
  left: 50%;
  margin-left: -1.7rem;
  margin-top: -1.7rem;
}

.ps-image,
.ps-success {
  display: inline-block;
}

.ps-success {
  color: #0076ff;
}

.wechat .ps-success {
  color: #44973c;
}

.wechat .ps-image,
.wechat .ps-message {
  background: #44973c;
}

.ps-info {
  background: #fff;
  color: #4a4a4a;
  text-align: center;
}

.ps-info > p,
.ps-info > div {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.ps-title {
  width: 90%;
  font-size: 3.6rem;
  margin: 0 auto;
  margin-top: 3rem;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.ps-local-amount {
  height: 7.2rem;
  width: 90%;
  margin: 2rem auto 0;
  font-size: 6.4rem;
  color: #000;
}

.ps-exchange {
  width: 90%;
  margin: 2rem auto 0;
  font-size: 2.4rem;
  color: #9b9b9b;
}

.ps-exchange img {
  height: 1.6rem;
  width: 2.2rem;
}

.ps-input-amount {
  color: #000;
  font-size: 3.6rem;
  width: 90%;
  margin: 2rem auto 0;
}

.ps-line {
  width: 90%;
  margin: 4rem auto;
  height: 1px;
  background: #e0e0e0;
}

.ps-detail {
  width: 90%;
  margin: 0 auto;
}

.ps-detail > li {
  margin-bottom: 4rem;
  position: relative;
  height: 3rem;
}

.ps-detail-name {
  color: #848484;
  font-size: 2.4rem;
  display: inline-block;
  position: absolute;
  left: 0;
}

.ps-detail-value {
  color: #000;
  font-size: 2.4rem;
  display: inline-block;
  position: absolute;
  right: 0;
}

.ps-ad-footer {
  background: #f6f6f6;
  padding-top: 8rem;
}

.ps-ad-footer img {
  width: 100%;
}

.ps-footer {
  position: absolute;
  bottom: 0;
  width: 100%;
}
</style>

<template lang="html">
<div class="payment-success" v-show="hide">
  <div class="ps-message" ref="wrap">
    <div class="ps-message-in">
      <div ref="text1">
        <span>Payment Successful</span>
        <span>{{info.createTime}}</span>
        <span>Payment Successful</span>
        <span>{{info.createTime}}</span>
      </div>
      <div ref="text2">
      </div>
    </div>
  </div>
  <div class="ps-status">
    <div class="ps-image">
      <svg class="ps-icon" aria-hidden="true">
        <use xlink:href="#icon-gou"></use>
      </svg>
    </div>

    <p class="ps-success">Payment Successful</p>
  </div>
  <div class="ps-info">
    <p class="ps-title">{{info.merchantName}}</p>
    <p class="ps-local-amount">CNY {{info.cnyAmount}}</p>
    <p class="ps-exchange">
      <img :src="cdnBaseUrl + 'static/cashier/exchange@2x.png'">
      {{info.currency}} 1.00 = ¥ {{info.exchangeRate}}
    </p>
    <p class="ps-input-amount">{{info.currency}} {{info.amount }}</p>

    <div class="ps-line"></div>

    <ul class="ps-detail">
      <li>
        <div class="ps-detail-name">
          Remark
        </div>
        <div class="ps-detail-value">
          {{info.remark}}
        </div>
      </li>
      <li>
        <div class="ps-detail-name">
          Create Time
        </div>
        <div class="ps-detail-value">
          {{info.createTime}}
        </div>
      </li>
      <li>
        <div class="ps-detail-name">
          Order ID
        </div>
        <div class="ps-detail-value">
          {{info.transactionId}}
        </div>
      </li>
      <li>
        <div class="ps-detail-name">
          Transaction ID
        </div>
        <div class="ps-detail-value">
          {{info.paymentTransactionId}}
        </div>
      </li>
    </ul>
  </div>
  <div class="ps-ad-footer" v-if="hasAd === 'Y' && params.ads.length !== 0">
    <a :href="ad.activeUrl" v-for="ad in params.ads"><img :src="ad.imgUrl" alt=""></a>
  </div>
  <div class="ps-footer" v-else>
    <app-footer white="white"></app-footer>
  </div>
</div>
</template>
<script>
import AppFooter from '../component/footer'

export default {
  components: {
    AppFooter
  },
  data() {
    return {
      hasAd: this.$route.query.hasAd,
      info: {},
      hide: false
    }
  },
  mounted() {
    document.title = 'Payment Successful'
    var wrap = this.$refs.wrap
    var text1 = this.$refs.text1
    var text2 = this.$refs.text2
    text2.innerHTML = text1.innerHTML
    setInterval(function() {
      if (wrap.scrollLeft - text2.offsetWidth >= 0) {
        wrap.scrollLeft -= text1.offsetWidth
      } else {
        wrap.scrollLeft++
      }
    }, 20)
  },
  created() {
    if (!sessionStorage.getItem('info')) {
      this.fetchInfo()
    } else {
      this.fetchInfoFromLocal()
    }
  },
  methods: {
    fetchInfoFromLocal() {
      this.info = JSON.parse(sessionStorage.getItem('info'))
    },
    fetchInfo() {
      const vm = this
      this.axios
        .get('/pay/detail', {
          params: {
            transactionId: this.$route.query.trxId
          }
        })
        .then(res => {
          vm.info = res.data.data
          sessionStorage.setItem('info', JSON.stringify(vm.info))
          vm.hide = true
        })
        .catch(error => {
          alert('Error Msg: ' + error)
        })
    }
  }
}
</script>
