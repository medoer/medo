<template lang="html">
  <div class="mask-remark" v-show="showRemark">
    <div class="mr">
      <p class="mr-title">Remark</p>
      <textarea name="textarea" maxlength="100" v-model="remarkValueSelf" ref="remark"></textarea>
      <div class="mr-btm">
        <span>{{remarkNum}}</span>
        <div class="mr-btn btn-cancel" @touchstart="handleCancel">Cancel</div>
        <div class="mr-btn" @touchstart="handleConfirm">Confirm</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    showRemark: {
      default: false
    },
    remarkValue: {
      default: ''
    }
  },
  data() {
    return {
      remarkNum: 100,
      remarkValueSelf: ''
    }
  },
  watch: {
    remarkValueSelf(item) {
      this.remarkNum = 100 - item.length
    }
  },
  created() {
    this.remarkValueSelf = this.remarkValue
  },
  methods: {
    handleCancel() {
      event.preventDefault()
      this.$ga.event('Button', 'Cancel Remark', 'Payment to')
      this.$emit('update:showRemark', false)
      this.remarkValueSelf = this.remarkValue
      this.$refs.remark.blur()
    },
    handleConfirm() {
      event.preventDefault()
      this.$ga.event('Button', 'Confirm Remark', 'Payment to')
      this.$emit('update:showRemark', false)
      this.$emit('update:remarkValue', this.remarkValueSelf)
      this.$refs.remark.blur()
    }
  }
}
</script>

<style scoped>
.mask-remark {
  position: absolute;
  top: 0;
  background-color: rgba(0, 0, 0, 0.7);
  height: 100%;
  width: 100%;
  font-family: PingFangSC-Regular;
}

.mr {
  background: #ffffff;
  border-radius: 2px;
  width: 61rem;
  height: 38rem;
  position: absolute;
  left: 50%;
  margin-left: -30.5rem;
  top: 50%;
  margin-top: -19rem;
}

.mr textarea {
  background: #f2f2f2;
  border: 1px solid #979797;
  border-radius: 2px;
  width: 55rem;
  height: 12rem;
  margin-top: 4.4rem;
  font-size: 2.6rem;
}

.mr-title {
  font-size: 3.2rem;
  color: #000000;
  margin-top: 3.7rem;
}

.mr-btm {
  position: relative;
  margin-top: 4.9rem;
}

.mr-btn {
  font-size: 3.2rem;
  color: #0076ff;
  line-height: 3.2rem;
  width: auto;
  background-color: #fff;
  position: absolute;
  right: 5rem;
  height: auto;
  margin: 0;
}

.wechat .mr-btn {
  color: #44973c;
}

.mr-btn.mr-btn-remark {
  margin-top: 2.2rem;
}

.mr-btn.btn-cancel {
  right: 20rem;
  color: #c9c9c9;
}

.mr span {
  font-family: Avenir-Light;
  font-size: 2.6rem;
  color: #96a0a9;
  position: absolute;
  left: 5rem;
}
</style>
