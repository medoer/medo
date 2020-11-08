<template>
  <div class="error-view">
    <img :src="imgUrl" alt="">
    <div class="error-msg">{{$route.query.errorMsg || 'invalid'}}</div>
    <app-footer class="fixed-btm"></app-footer>
  </div>
</template>

<script>
import AppFooter from '../component/footer'

export default {
  name: 'error-view',
  components: {
    AppFooter
  },
  data() {
    return {
      imgUrl: require('@/assets/error@3x.png')
    }
  }
}
</script>


<style scoped>
.error-view {
  text-align: center;
  background: #ededed;
  height: 100vh;
}
.error-view img {
  width: 14rem;
  height: 14rem;
  margin-top: 30vh;
}

.error-view .error-msg {
  color: #d40101;
  font-size: 3.2rem;
  margin-top: 6rem;
  font-weight: 500;
}

.fixed-btm {
  position: fixed;
  bottom: 0;
  width: 100%;
}
</style>
