function Weixinready(cb) {
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

function wechatFunc() {
  return Weixinready(function () {
    // 设置网页字体为默认大小
    WeixinJSBridge.invoke('setFontSizeCallback', {
      'fontSize': 0
    })
    // 重写设置网页字体大小的事件
    WeixinJSBridge.on('menu:setfont', function () {
      WeixinJSBridge.invoke('setFontSizeCallback', {
        'fontSize': 0
      })
    })
    // 去掉menu
    window.WeixinJSBridge.invoke('hideOptionMenu', {}, function (res) {})
  })
}

export default wechatFunc
