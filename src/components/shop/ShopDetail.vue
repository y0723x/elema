<template>
<div
    class="mask"
    v-show="showMask"
    @click="handleMask()"
></div>
  <div class="wrapper">
    <!-- shop_header -->
    <div class="shop_header">
      <img
        src="https://yxrepository.oss-cn-hangzhou.aliyuncs.com/img/spideman/626164.jpg"
        class="shop_header_img"
      />
      <div class="shop_header_body">
        <div class="shop_header_body_back iconfont" @click="handleBack()">
          &#xe602;
        </div>
        <div class="shop_header_body_search iconfont">&#xe752;</div>
        <div class="shop_header_body_concern iconfont">
          &#xe603;
          <div class="shop_header_body_concern_text">关注</div>
        </div>
        <div class="shop_header_body_other iconfont">&#xe708;</div>
      </div>
      <div class="shop_header_info">
        <div class="shop_header_info_body">
          <div class="shop_header_info_body_title">
            <span class="shop_name">{{ shop.shopName }}</span>
            <span class="shop_address">({{ shop.shopAddress }})</span>
          </div>
          <div class="shop_header_info_body_situation">
            <span class="shop_score">{{ shop.shopScore }}分</span>
            <span class="shop_sale">月售{{ shop.shopSale }}</span>
          </div>
          <div class="shop_header_info_body_announcement">
            公告：新老贵客大家好，感谢您对小店的厚爱。
          </div>
          <div class="shop_header_info_body_coupons">
            注意：下俩行涉及优惠券，数据库暂时没写这类，所以先暂时不写
          </div>
        </div>
        <img
          src="https://yxrepository.oss-cn-hangzhou.aliyuncs.com/img/spideman/247850.png"
          class="shop_header_info_img"
        />
      </div>
    </div>
    <!-- shop_container -->
    <div class="shop">
      <div class="shop_choice">
        <div class="shop_choice_left">
          <div class="shop_choice_left_order">点餐</div>
          <div class="shop_choice_left_appraise">评价</div>
          <div class="shop_choice_left_self">商家</div>
        </div>
        <div class="shop_choice_right">
          <span class="shop_choice_right_together">好友拼单</span>
        </div>
      </div>
      <div class="shop_order">
        <div class="shop_order_cate">
          <div
            :class="{
              shop_order_cate_item: true,
              'shop_order_cate_item-active': activeIndex === index,
            }"
            v-for="(cate, index) in shopCateList.values"
            :key="index"
            @click="handleCate(index)"
          >
            {{ cate.shopCateName }}
          </div>
        </div>
        <div class="shop_order_content">
          <div class="shop_order_content_title">{{ shopCateName }}</div>
          <div
            class="shop_order_content_item"
            v-for="(goods, index) in goodsList.values"
            :key="index"
          >
            <img :src="goods.goodsUrl" class="shop_order_content_item_img" />
            <div class="shop_order_content_item_body">
              <div class="shop_order_content_item_body_name">
                {{ goods.goodsName }}
              </div>
              <div class="shop_order_content_item_body_intro">
                {{ goods.goodsIntro }}
              </div>
              <div class="shop_order_content_item_body_sale">
                月售{{ goods.goodsSale }}
              </div>
              <div class="shop_order_content_item_body_price">
                <span class="pre_price">￥</span>
                <span class="goods_price">{{ goods.goodsPrice }}</span>
              </div>
              <div
                class="shop_order_content_item_body_sub"
                @click="() => changeCartItemInfo(shopId, shopName, goods, -1)"
              >-</div>
              <div class="goods_count">{{ productList[goods.id]?.count || 0 }}</div>
              <div 
                class="shop_order_content_item_body_add"
                @click="() => changeCartItemInfo(shopId, shopName, goods, 1)"
              >+</div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- cart -->
    <div
        class="cart"
        v-show="showMask"
    >
      <div class="cart_header">
        <div class="cart_header_title">已选商品</div>
        <div class="cart_header_clear">
          <div class="cart_header_clear_icon iconfont">&#xe682;</div>
          <div 
            class="cart_header_clear_text"
            @click="clearCart(shopId)"
          >清空</div>
        </div>
      </div>
      <div
        class="cart_item"
        v-for="(product, index) in productList"
        :key="index"
      >
        <img 
            :src="product.goodsUrl" 
            class="cart_item_img" 
        />
        <div class="cart_item_body">
          <div class="cart_item_body_name">{{ product.goodsName }}</div>
          <div class="cart_item_body_price">
              <span class="cart_item_body_price_pre">￥</span>
              <span class="cart_item_body_price_count">{{ product.goodsPrice }}</span>
          </div>
          <div
            class="cart_item_body_sub"
            @click="() => changeCartItemInfo(shopId, shopName, product, -1)"
          >-</div>
          <div class="cart_item_body_goods_count">{{ product.count }}</div>
          <div 
            class="cart_item_body_add"
            @click="() => changeCartItemInfo(shopId, shopName, product, 1)"
          >+</div>
        </div>
      </div>
    </div>
    <!-- shop_cart -->
    <div class="shop_cart">
      <div 
        class="shop_cart_icon iconfont"
        @click="handleMask()"
      >&#xe619;</div>
      <div class="shop_cart_price">￥{{ total.totalPrice }}</div>
      <div
        class="shop_cart_confirm"
        v-show="total.totalCount > 0"
        @click="checkOut(shopId)"
      >去结算</div>
    </div>
  </div>
</template>

<script>
import { computed, onMounted, reactive, ref } from "vue";
import { getRequest } from "@/utils/request";
import { useRoute, useRouter } from "vue-router";
import { useStore } from 'vuex'
import { useCartEffect } from '@/effects/cartEffect'

const usePlaceOrderEffect = () => {
    const route = useRoute();
    // 获取shopId
    const shopId = route.params.shopId;
    // 被选中显示
    const activeIndex = ref(0);
    // shopName
    const shopName = ref()
    // shopOrderContentTitle
    const shopCateName = ref();
    // shop
    const shop = ref({});
    // shopCateList
    const shopCateList = reactive([]);
    // goodsList
    const goodsList = reactive([]);

    // 切换content
    const handleCate = (index) => {
      activeIndex.value = index;
      // 获取shopCate
      const shopCate = shopCateList.values[index];
      // 更新数据
      shopCateName.value = shopCate?.shopCateName;
      // console.log(shopCate)
      goodsList.values = shopCate?.goodsList;
    };

    onMounted(() => {
      getShopCatesWithGoods(shopId);
    });

    // 获取店铺详情
    const getShopCatesWithGoods = async (shopId) => {
      const result = await getRequest(`/shop/${shopId}`);
      // console.log(result);
      if (result?.code && result?.code === 200) {
        shop.value = result?.object;
        shopCateList.values = result?.object?.shopCateList;
        goodsList.values = result?.object?.shopCateList[0]?.goodsList;
        shopCateName.value = result?.object?.shopCateList[0]?.shopCateName;
        shopName.value = result?.object?.shopName
      }
    };

    return { shopCateList, goodsList, handleCate, activeIndex, shop, shopCateName,
    shopName
    };
}

export default {
  name: "ShopDetail",
  setup() {
    const route = useRoute()
    const router = useRouter();
    // 获取shopId
    const shopId = route.params.shopId
    // 返回
    const handleBack = () => {
      router.push('/index');
    };
    // 获取数据
    const { shopCateList, goodsList, handleCate, activeIndex, shop, shopCateName,
    shopName } = usePlaceOrderEffect()
    // cart方法
    const { productList, total, clearCart, changeCartItemInfo } = useCartEffect(shopId)

    // showMask
    const showMask = ref(false)
    // mask的显示切换
    const handleMask = () => {
        showMask.value = !showMask.value
    }

    // 去结算
    const checkOut = (shopId) => {
      router.push(`/orderConfirm/${shopId}`)
    }

    return {
      shopId,
      shopName,
      shopCateList,
      goodsList,
      handleCate,
      activeIndex,
      handleBack,
      shop,
      shopCateName,
      showMask,
      handleMask,
      changeCartItemInfo,
      productList,
      total,
      clearCart,
      checkOut
    };
  },
};
</script>

<style lang="scss" scoped>
.mask{
    position:fixed;
    top:0;
    left:0;
    right:0;
    bottom:0;
    background: rgb(0,0,0,.5);
    z-index: 1;
}

.wrapper {
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
  bottom: 0;
}

.shop_header {
  position: relative;
  height: 2.5rem;
  background: #fdf8f8;
  &_img {
    height: 1.6rem;
    width: 100%;
  }
  &_body {
    display: flex;
    position: absolute;
    top: 0.4rem;
    left: 0;
    right: 0;
    color: #fff;
    &_back {
      flex: 6;
      font-size: 0.26rem;
      margin-left: 0.2rem;
    }
    &_search {
      flex: 1;
      font-size: 0.26rem;
    }
    &_concern {
      flex: 2;
      font-size: 0.26rem;
      line-height: 0.24rem;
      display: flex;
      &_text {
        font-size: 0.16rem;
        padding: 0 0.1rem 0.1rem 0.02rem;
      }
    }
    &_other {
      flex: 1;
      font-size: 0.26rem;
    }
  }
  &_info {
    background: #fff;
    position: absolute;
    top: 1rem;
    left: 0.1rem;
    right: 0.1rem;
    height: 1.3rem;
    border-radius: 0.1rem;
    padding: 0.1rem;
    display: flex;
    &_body {
      flex: 6;
      &_title {
        font-size: 0.26rem;
        font-weight: bolder;
      }
      &_situation {
        margin-bottom: 0.05rem;
        .shop_score {
          color: orange;
          margin-right: 0.1rem;
        }
      }
      &_announcement {
        color: #797373;
        font-size: 0.08rem;
      }
      &_coupons {
        color: #e13939;
        font-size: 0.16rem;
      }
    }
    &_img {
      flex: 1;
      height: 0.5rem;
      width: 0.5rem;
      border-radius: 0.1rem;
    }
  }
}

.shop {
  position: absolute;
  top: 2.5rem;
  bottom: 0;
  left: 0;
  right: 0;
  background: #eae4e4;
  &_choice {
    display: flex;
    position: relative;
    font-size: 0.16rem;
    text-align: center;
    background: #fdf8f8;
    height: 0.5rem;
    &_left {
      display: flex;
      padding-left: 0.1rem;
      line-height: 0.5rem;
      &_order {
        margin-right: 0.3rem;
      }
      &_appraise {
        margin-right: 0.3rem;
      }
      &_self {
        margin-right: 0.3rem;
      }
    }
    &_right {
      position: absolute;
      line-height: 0.5rem;
      right: 0.1rem;
      &_together {
        text-align: center;
        border: 0.01rem solid #0082d4;
        color: #0082d4;
        border-radius: 0.12rem;
        font-size: 0.12rem;
        padding: 0.05rem 0.1rem;
      }
    }
  }
  &_order {
    position: absolute;
    overflow: hidden;
    top: 0.5rem;
    bottom: 0.6rem;
    left: 0;
    right: 0;
    &_cate {
      overflow-x: hidden;
      overflow-y: scroll;
      position: absolute;
      top: 0;
      bottom: 0;
      left: 0;
      width: 0.75rem;
      &::-webkit-scrollbar {
        display: none;
      }
      &_item {
        white-space: normal;
        line-height: 0.3rem;
        width: 100%;
        padding: 0.1rem 0;
        text-align: center;
        &-active {
          background: #fff;
          font-weight: bolder;
        }
      }
    }
    &_content {
      overflow-y: scroll;
      position: absolute;
      top: 0;
      bottom: 0;
      right: 0;
      left: 0.75rem;
      background: #fff;
      &_title {
        font-weight: bolder;
        margin: 0.1rem 0 0.2rem 0.08rem;
      }
      &_item {
        display: flex;
        padding: 0.1rem;
        &_img {
          height: 1rem;
          width: 1rem;
          border: 0.01rem solid #eae4e4;
          border-radius: 0.1rem;
          margin-right: 0.05rem;
        }
        &_body {
          position: relative;
          flex: 1;
          color: #797373;
          &_name {
            font-weight: bolder;
            color: #000;
            margin-bottom: 0.03rem;
          }
          &_intro {
            font-size: 0.1rem;
            height: 0.3rem;
            text-overflow: -o-ellipsis-lastline;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            margin-bottom: 0.03rem;
          }
          &_sale {
            font-size: 0.1rem;
            margin-bottom: 0.1rem;
          }
          &_price {
            color: #e13939;
            font-size: 0.16rem;
            .pre_price {
              font-size: 0.08rem;
            }
          }
          &_sub {
            height: 0.2rem;
            width: 0.2rem;
            border: 0.01rem solid #0082d4;
            border-radius: 50%;
            color: #0082d4;
            background: #fff;
            position: absolute;
            right: 0.4rem;
            bottom: 0.01rem;
            line-height: 0.16rem;
            text-align: center;
            font-size: 0.2rem;
          }
          .goods_count {
            position: absolute;
            right: 0.26rem;
            bottom: 0.04rem;
            line-height: 0.16rem;
            text-align: center;
            font-size: 0.15rem;
          }
          &_add {
            height: 0.2rem;
            width: 0.2rem;
            border: 0.01rem solid #0082d4;
            border-radius: 50%;
            color: #fff;
            background: #0082d4;
            position: absolute;
            right: 0;
            bottom: 0.01rem;
            line-height: 0.16rem;
            text-align: center;
            font-size: 0.2rem;
          }
        }
      }
    }
  }
}

.cart {
  z-index: 2;
  position: absolute;
  bottom: 0.6rem;
  left: 0;
  right: 0;
  background: #fff;
  border-bottom: 0.01rem solid #797373;
  &_header {
    position: relative;
    display: flex;
    height: 0.3rem;
    &_title {
      font-weight: bolder;
      margin: 0.1rem 0.05rem 0 0.1rem;
    }
    &_clear {
      position: absolute;
      color: #eae4e4;
      display: flex;
      font-size: 0.1rem;
      right: 0.1rem;
      top: 0.1rem;
      &_text {
        margin-left: 0.05rem;
      }
    }
  }
  &_item {
    padding: 0.1rem;
    display: flex;
    &_img {
      height: 0.5rem;
      width: 0.5rem;
      border: 0.01rem solid #eae4e4;
      border-radius: 0.1rem;
      margin-right: 0.05rem;
    }
    &_body {
      position: relative;
      flex:1;
      &_name{
          overflow: hidden;
          white-space: nowrap;
          text-overflow: ellipsis;
          width:3rem;
      }
      &_price{
          color: #e13939;
          position:absolute;
          bottom:0;
          left:0;
          &_pre{
              font-size:.1rem;
          }
          &_price{
              font-size:.15rem;
          }
      }
      &_sub {
        height: 0.2rem;
        width: 0.2rem;
        border: 0.01rem solid #0082d4;
        border-radius: 50%;
        color: #0082d4;
        background: #fff;
        position: absolute;
        right: 0.4rem;
        bottom: 0.01rem;
        line-height: 0.16rem;
        text-align: center;
        font-size: 0.2rem;
      }
      &_goods_count {
        position: absolute;
        right: 0.26rem;
        bottom: 0.04rem;
        line-height: 0.16rem;
        text-align: center;
        font-size: 0.15rem;
      }
      &_add {
        height: 0.2rem;
        width: 0.2rem;
        border: 0.01rem solid #0082d4;
        border-radius: 50%;
        color: #fff;
        background: #0082d4;
        position: absolute;
        right: 0;
        bottom: 0.01rem;
        line-height: 0.16rem;
        text-align: center;
        font-size: 0.2rem;
      }
    }
  }
}

.shop_cart {
  z-index: 2;
  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  height: 0.6rem;
  background: #fff;
  border-top: 0.01rem solid #eae4e4;
  display: flex;
  &_icon {
    font-size: 0.6rem;
    margin: 0.02rem 0.1rem 0 0.14rem;
  }
  &_price {
    margin: 0.02rem 0;
    line-height: 0.6rem;
    font-size: 0.2rem;
  }
  &_confirm {
    position: absolute;
    top: 0.2rem;
    right: 0.1rem;
    height: 0.3rem;
    width: 0.8rem;
    border: 0.01rem solid #0082d4;
    border-radius: 0.2rem;
    color: #fff;
    background: #0082d4;
    text-align: center;
    line-height: 0.3rem;
  }
}
</style>