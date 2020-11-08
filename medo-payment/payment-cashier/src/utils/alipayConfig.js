function Alipayready(callback) {
  if (window.AlipayJSBridge) {
    callback && callback();
  } else {
    document.addEventListener('AlipayJSBridgeReady', callback, false);
  }
}

function alipayFunc() {
  return Alipayready(function () {
    window.AlipayJSBridge.call('hideOptionMenu')
  })
}

export default alipayFunc