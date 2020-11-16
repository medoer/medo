import Vue from 'vue'
import Router from 'vue-router'

import Pay from '@/views/payment'
import OrderPay from '@/views/payment-order'
import PaySuc from '@/views/payment-success'
import ErrorView from '@/views/error'

Vue.use(Router)

var baseRouteUrl = '/h5'
let router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      redirect: {
        name: 'Index'
      }
    },
    {
      path: `${baseRouteUrl}/fail`,
      name: 'ErrorView',
      component: ErrorView
    },
    {
      path: `${baseRouteUrl}/payment`,
      name: 'Index',
      component: Pay
    },
    {
      path: `${baseRouteUrl}/order-payment`,
      name: 'Order-Payment',
      component: OrderPay
    },
    {
      path: `${baseRouteUrl}/success`,
      name: 'Success',
      component: PaySuc,
      beforeEnter: (to, from, next) => {
        if (from.name === 'Order-Payment' || from.name === 'Index') {
          localStorage.clear()
        }
        next()
      }
    }
  ]
})

export default router
