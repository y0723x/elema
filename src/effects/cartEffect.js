import { useStore } from 'vuex'
import { computed } from 'vue'

export const useCartEffect = (shopId) => {
    const store = useStore()
    // 获取shopName
    const shopName = store.state.cart[shopId]?.shopName
    // 获取购物车中数量大于0的产品
    const productList = computed(() => {
        const products = store.state.cart[shopId]?.goodsList || []
        // 获取cart中count>0的产品
        const notEmptyProducts = {}
        for (const i in products) {
            const product = products[i]
            if (product.count > 0) {
                notEmptyProducts[i] = product
            }
        } 
        // console.log(notEmptyProducts)
        return notEmptyProducts
    })

    // 购物车产品总价格、购物车中产品数量>0的总数
    const total = computed(() => {
        // let total = 0
        const result = {
          totalCount: 0,
          totalPrice: 0
        }
        const productList = store.state.cart[shopId]?.goodsList || []
        for (const i in productList) {
            const product = productList[i]
            if (product.count > 0) {
                result.totalCount += 1
                result.totalPrice += (product.count * product.goodsPrice)
            }
        }
        return result
    })

    // 清空购物车
    const clearCart = (shopId) => {
        store.commit('clearCart', {
            shopId
        })
    }

    // 改变产品在购物车中的数量
    const changeCartItemInfo = (shopId, shopName, goods, num) => {
        store.commit('changeCartItemInfo', {
            shopId, shopName, goods, num
        })
    }

    return { productList, total, clearCart, changeCartItemInfo, shopName }
}