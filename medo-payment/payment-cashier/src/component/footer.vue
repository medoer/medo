<template lang="html">
  <div class="footer" :class="[white]" >
    <img :src="logoSrc">
    <span></span>
    <img :src="cdnBaseUrl +'static/cashier/' + channelName + '@2x.png'">
  </div>
</template>

<script>
export default {
  props: {
    white: {
      type: String,
      default: ''
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
</script>

<style scoped>
.footer {
  height: 8.2rem;
  line-height: 8.2rem;
  background-color: #ededed;
  text-align: center;
}

.white.footer {
  background: #fff;
}

img {
  height: 2.6rem;
  position: relative;
  top: 1.4rem;
}

span {
  display: inline-block;
  width: 1px;
  height: 3.6rem;
  background-color: #cfcfcf;
  margin: 0 2rem;
  position: relative;
  top: 2rem;
}
</style>
