<style scoped>
.payment {
  height: 100%;
  width: 100%;
  display: flex;
  font-family: 'PingFang SC';
}

.cnt {
  background-color: #fff;
  text-align: center;
  width: 100%;
  overflow: scroll;
}

.cnt img {
  height: 7rem;
  border: none;
  margin-top: 3.4rem;
  margin-bottom: 3rem;
}

.cnt .title {
  max-width: 66rem;
  margin: 0 auto;
  color: #4a4a4a;
  font-size: 3.6rem;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.cnt .title.subtitle {
  color: #878787;
  font-weight: lighter;
}

.show-amount {
  height: 12rem;
  width: 66rem;
  background: #d8d8d8;
  border: 1px solid #9b9b9b;
  margin: 0 auto;
  margin-top: 2rem;
  position: relative;
}

.show-amount > span {
  display: inline-block;
}

.show-amount span:first-child {
  line-height: 12rem;
  font-size: 2.4rem;
  color: #8b8f92;
  position: absolute;
  left: 1.5rem;
}

.show-amount span:last-child {
  line-height: 12rem;
  font-size: 7.2rem;
  color: #4a4a4a;
  position: absolute;
  right: 1.5rem;
  max-width: 55rem;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.info,
.remark {
  height: 9.4rem;
  line-height: 9.4rem;
  width: 66rem;
  position: relative;
  margin: 0 auto;
  color: #9b9b9b;
}

.info > span:first-child,
.remark > span:first-child {
  position: absolute;
  left: 0.2rem;
  font-size: 2.4rem;
}

.remark > span span:first-child {
  display: inline-block;
  text-align: left;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.remark > span span:last-child {
  color: #414141;
  padding-left: 0.5rem;
  display: inline-block;
  width: 45rem;
  text-align: left;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.info-amount {
  font-size: 2rem;
  position: absolute;
  right: 0.2rem;
}

.info-amount > span:nth-child(2) {
  color: #000;
  font-size: 2.8rem;
}

.icon {
  width: 3.3rem;
  height: 3.3rem;
  vertical-align: -0.5rem;
  fill: #cfcfcf;
  overflow: hidden;
}

.space-line {
  width: 66rem;
  height: 1px;
  background-color: #e0e0e0;
  margin: 0 auto;
}

.edit {
  color: #0076ff;
  font-size: 2.6rem;
  position: absolute;
  right: 0.2rem;
}

.payment-footer {
  position: fixed;
  bottom: 0;
  width: 100%;
}

@media only screen and (min-device-width: 768px) and (max-device-width: 1024px) and (orientation: portrait) and (-webkit-min-device-pixel-ratio: 1) {
  .payment-footer {
    position: relative;
  }
}

@media only screen and (min-device-width: 768px) and (max-device-width: 1024px) and (orientation: portrait) {
  .payment-footer {
    position: relative;
  }
}

.payment-loading {
  height: 100%;
  width: 100%;
  position: fixed;
  top: 0;
  left: 0;
  background-color: #fff;
  opacity: 0;
  position: 999;
}

.mask-loading {
  position: fixed;
  top: 0;
  background-color: rgba(0, 0, 0, 0.7);
  height: 100%;
  width: 100%;
  z-index: 99999;
}

/* loading style*/

.sk-three-bounce {
  width: 12rem;
  text-align: center;
  position: absolute;
  top: 50%;
  margin-top: -1.5rem;
  left: 50%;
  margin-left: -6rem;
}

.sk-three-bounce .sk-child {
  width: 3rem;
  height: 3rem;
  background-color: #fff;
  border-radius: 100%;
  display: inline-block;
  -webkit-animation: sk-three-bounce 1.4s ease-in-out 0s infinite both;
  animation: sk-three-bounce 1.4s ease-in-out 0s infinite both;
}

.sk-three-bounce .sk-bounce1 {
  -webkit-animation-delay: -0.32s;
  animation-delay: -0.32s;
}

.sk-three-bounce .sk-bounce2 {
  -webkit-animation-delay: -0.16s;
  animation-delay: -0.16s;
}

@-webkit-keyframes sk-three-bounce {
  0%,
  80%,
  100% {
    -webkit-transform: scale(0);
    transform: scale(0);
  }
  40% {
    -webkit-transform: scale(1);
    transform: scale(1);
  }
}

@keyframes sk-three-bounce {
  0%,
  80%,
  100% {
    -webkit-transform: scale(0);
    transform: scale(0);
  }
  40% {
    -webkit-transform: scale(1);
    transform: scale(1);
  }
}
</style>

<template lang="html">
  <section class="payment">
    <div class="cnt">
      <img :src="logoSrc">
      <p class="title">{{page.mchName}}</p>
      <p class="title subtitle">{{page.mchSubName}}</p>
      <div class="show-amount">
        <span>CNY</span>
        <span>{{inputValue}}</span>
      </div>

      <div class="space-line" v-if="remarkValue !== ''"></div>

      <div class="remark" v-if="remarkValue !== ''">
        <span>
          <span>Remark:</span>
          <span>{{remarkValue}}</span>
        </span>
        <div class="edit" @touchstart="showRemark = true">Edit</div>
      </div>

      <div class="payment-footer">
        <calc :showRemark.sync="showRemark" :inputValue.sync="inputValue" :page="page" :remarkValue="remarkValue" :paying.sync="paying"></calc>
        <app-footer></app-footer>
      </div>

      <detail :showDetail.sync="showDetail" :page="page" :amount="amount"></detail>
      <remark :showRemark.sync="showRemark" :remarkValue.sync="remarkValue"></remark>
    </div>

    <div class="mask-loading" v-if="paying">
      <div class="sk-three-bounce">
        <div class="sk-child sk-bounce1"></div>
        <div class="sk-child sk-bounce2"></div>
        <div class="sk-child sk-bounce3"></div>
      </div>
    </div>
  </section>
</template>

<script>
import Calc from '../component/calc'
import AppFooter from '../component/footer'
import Detail from '../component/detail'
import Remark from '../component/remark'

export default {
  components: {
    Calc,
    AppFooter,
    Detail,
    Remark
  },
  data() {
    return {
      page: Object.assign({}, this.$route.query),
      remarkValue: '',
      showRemark: false,
      showDetail: false,
      amount: {
        val: '0',
        aVal: '0', // 显示转换后货币金额
        eaVal: '0', // Alipay 独享
        feeVal: '0' // 手续费金额
      },
      inputValue: '0',
      paying: false
    }
  },
  computed: {
    logoSrc() {
      if (process.env.NODE_ENV === 'development') {
        return '/static/cashier/medo-payment.png'
      }

      return '/h5/static/cashier/medo-payment.png'
    }
  }
}
</script>
