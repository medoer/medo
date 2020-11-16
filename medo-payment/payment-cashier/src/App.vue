<template>
<div id="app" :class="[channelName]">
  <router-view></router-view>
</div>
</template>

<script>
export default {
  name: 'app'
}
</script>
