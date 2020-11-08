<template lang="html">
  <div class="mask-tips" v-show="showDetail">
    <div class="payment-detail">
      <p class="m-title">支付详情</p>
      <p class="m-cnt">
        <span class="m-cnt-name">金额</span>
        <span class="m-cnt-value">
          <i>{{page.tradeCurrency}}</i>{{amount.val}}</span>
      </p>
      <p class="m-cnt">
        <span class="m-cnt-name">服务费</span>
        <span class="m-cnt-value">
          <i>{{page.tradeCurrency}}</i>{{amount.feeVal}}</span>
      </p>
      <p class="m-cnt">
        <span class="m-cnt-name">汇率</span>
        <span class="m-cnt-value"><i>≈</i>{{page.rate}}</span>
      </p>
      <p class="m-sum">
        <span class="m-sum-name">实付金额</span>
        <span class="m-sum-value"><i>≈{{page.rateCurrency}}</i>{{amount.aVal}}</span>
      </p>
      <div class="m-btn" @touchstart="$emit('update:showDetail', false)">OK</div>
    </div>      
  </div>
</template>

<script>
export default {
  props: {
    page: {
      default: {}
    },
    amount: {
      default: {
        val: '0'
      }
    },
    showDetail: {
      default: false
    }
  }
}
</script>

<style scoped>
.mask-tips {
  position: absolute;
  top: 0;
  background-color: rgba(0, 0, 0, 0.7);
  height: 100%;
  width: 100%;
  font-family: PingFangSC-Regular;
}

.payment-detail {
  background: #FFFFFF;
  border-radius: 2px;
  width: 61rem;
  position: absolute;
  left: 50%;
  margin-left: -30.5rem;
  top: 50%;
  margin-top: -35.3rem;
}

.m-title {
  font-size: 3.2rem;
  color: #000000;
  padding: 2.8rem 0;
}

.m-cnt,
.m-sum {
  width: 55rem;
  margin: 0 3rem;
  border-top: 1px solid #d8d8d8;
  position: relative;
  height: 6rem;
}

.m-sum {
  margin-top: 1rem;
}

.m-cnt-name,
.m-sum-name {
  font-size: 2rem;
  color: #9B9B9B;
  line-height: 6rem;
  position: absolute;
  left: 1rem;
}

.m-cnt-value,
.m-sum-value {
  font-size: 2.4rem;
  color: #000000;
  line-height: 6rem;
  position: absolute;
  right: 1rem;
}

.m-cnt-value i,
.m-sum-value i {
  padding-right: 1rem;
  font-size: 1.8rem;
  color: #9B9B9B;
  font-style: normal;
}

.m-btn {
  background: #fff;
  border: 0;
  font-size: 3.2rem;
  color: #0076FF;
  line-height: 8rem;
  width: auto;
  height: 8rem;
}

.wechat .m-btn {
  color: #44973C;
}

.mask-remark {
  position: absolute;
  top: 0;
  background-color: rgba(0, 0, 0, 0.7);
  height: 100%;
  width: 100%;
}
</style>
