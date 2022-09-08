import { createStore } from 'vuex'

export default createStore({
  state: {
    /**
     * 1.购物车里面有不同的店铺（shopId），店铺下有goodsList
     * cart:[
     *    shopId:{
     *        goodsList:[
     *            goods
     *        ]
     *    } 
     * ]
     */
    cart: []
  },
  getters: {
  },
  mutations: {
    changeCartItemInfo(state, payload){
      const { shopId, shopName, goods, num } = payload

      // 获取店铺信息
      const shop = state.cart[shopId] || {
        shopName: shopName,
        goodsList:[]
      }
      
      // 判断店铺中是否有该产品信息
      let product = shop.goodsList[goods.id]

      if (!product) {
        product = goods
        product.count = 0
      }

      // console.log(product)

      // 更新购物车中店铺产品信息
      product.count += num
      if (product.count < 0) {
        product.count = 0
      }
      shop.goodsList[goods.id] = product

      // 更新购物车中店铺信息
      state.cart[shopId] = shop
      // console.log(state.cart)
    },
    clearCart(state, payload){
      const { shopId } = payload
      state.cart[shopId].goodsList = []
    }
  },
  actions: {
  },
  modules: {
  }
})
