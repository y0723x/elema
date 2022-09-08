import { nextTick } from 'vue'
import { createRouter, createWebHashHistory, createWebHistory } from 'vue-router'

const routes = [
  // {
  //   path: '/',
  //   redirect: '/dashboard'
  // },
  {
    path: '/orders/:orderId',
    name: 'OrderDetail',
    component: () => import(/* webpackname: 'orders' */ '../views/orders/OrderDetail/OrderDetail')
  },
  {
    path: '/orderPay/:shopId',
    name: 'OrderPay',
    component: () => import(/* webpackname: 'orderpay' */ '../views/orders/OrderPay/OrderPay')
  },
  {
    path: '/orderConfirm/:shopId',
    name: 'OrderConfirm',
    component: () => import(/* webpackname: 'orderconfirm' */ '@/views/orders/OrderConfirm/OrderConfirm')
  },
  {
    path: '/shop/:shopId',
    name: 'ShopDetail',
    component: () => import(/* webpackname: 'shopdetail' */ '@/components/shop/ShopDetail')
  },
  {
    path: '/index/:menuId',
    name: 'menudetail',
    component: () => import(/* webpackname: 'menudetail' */ '../views/dashboard/firstmenu/menudetail')
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import(/* webpackname: 'home' */ '../views/home/Home'),
    children: [
      {
        path: '/index',
        name: 'DashBoard',
        component: () => import(/* webpackname: 'dashboard' */ '../views/dashboard/DashBoard')
      },
      {
        path: '/dynamic',
        name: 'Dynamic',
        component: () => import(/* webpackname: 'dynamic' */ '../views/dynamic/Dynamic')
      },
      {
        path: '/orders',
        name: 'Orders',
        component: () => import(/* webpackname: 'orders' */ '../views/orders/Orders')
      },
      {
        path: '/my',
        name: 'My',
        component: () => import(/* webpackname: 'my' */ '../views/my/My')
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import(/* webpackname: 'login' */ '../views/login/Login'),
    /**
     * 判断用户是否为登录状态
     * @param {*} to 
     * @param {*} from 
     * @param {*} next 
     */
    beforeEnter(to, from, next){
      // const { isLogin } = localStorage;
      // const { token } = localStorage;
      const token = sessionStorage.getItem('token')
      token ? next('/index') : next()
    }
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import(/* webpackname: 'register' */ '../views/register/Register'),
    /**
     * 判断用户是否为登录状态
     * @param {*} to 
     * @param {*} from 
     * @param {*} next 
     */
    beforeEnter(to, from, next){
      // const { isLogin } = localStorage;
      // const { token } = localStorage;
      const token = sessionStorage.getItem('token')
      token ? next('/index') : next()
    }
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  /**
   * 1.判断用户是否登录
   * 2.判断页面是否为登录或注册
   */
  // const { isLogin }  = localStorage
  // const { token }  = localStorage
  const token = sessionStorage.getItem('token')
  const { name } = to
  const isLoginOrRegister = (name === 'Login' || name === 'Register');
  (token || isLoginOrRegister) ? next() : next('/login')
})

export default router
