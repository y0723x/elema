<template>
  <div
    class="mask"
    v-show="mask.showMask"
    @click="handleMask(mask.maskMessage)"
  >
    <div class="mask_body">
      <div class="mask_body_title">{{ mask.maskMessage }}</div>
      <div class="mask_body_choice">
        <div class="mask_body_choice_cancel" @click="handleChoice('取消')">
          取消
        </div>
        <div class="mask_body_choice_confirm" @click="handleChoice('确认')">
          确认
        </div>
      </div>
    </div>
  </div>
  <div class="wrapper">
    <!-- header -->
    <div class="order_pay_header">
      <div class="order_pay_header_body">
        <div
          class="order_pay_header_body_back iconfont"
          @click="handleBack(shopId)"
        >
          &#xe602;
        </div>
        <div class="order_pay_header_body_title">订单未付款</div>
      </div>
      <!-- arrive+distribution+operation -->
      <div class="order_pay_header_info">
        <div class="order_pay_header_info_arrive">预计今天11:30送到</div>
        <div class="order_pay_header_info_distribution">商家配送</div>
        <div class="order_pay_header_info_operation">
          <div class="order_pay_header_info_operation_pay">
            <div class="order_pay_header_info_operation_pay_icon iconfont">
              &#xe7ac;
            </div>
            <div
              class="order_pay_header_info_operation_pay_text"
              @click="handleMask('确认支付吗？')"
            >
              去支付
            </div>
          </div>
          <div class="order_pay_header_info_operation_update">
            <div class="order_pay_header_info_operation_update_icon iconfont">
              &#xe8cf;
            </div>
            <div class="order_pay_header_info_operation_update_text">
              改订单信息
            </div>
          </div>
          <div class="order_pay_header_info_operation_talk">
            <div class="order_pay_header_info_operation_talk_icon iconfont">
              &#xec3b;
            </div>
            <div class="order_pay_header_info_operation_talk_text">
              联系商家
            </div>
          </div>
          <div class="order_pay_header_info_operation_cancel">
            <div class="order_pay_header_info_operation_cancel_icon iconfont">
              &#xe8ae;
            </div>
            <div
              class="order_pay_header_info_operation_cancel_text"
              @click="handleMask('确认取消订单吗？')"
            >
              取消订单
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- orderList -->
    <div class="order_pay_container">
      <div class="order_pay_container_productList">
        <div class="order_pay_container_productList_title">{{ shopName }}</div>
        <div
          class="order_pay_container_productList_item"
          v-for="(product, index) in productList"
          :key="index"
        >
          <img
            :src="product.goodsUrl"
            class="order_pay_container_productList_item_img"
          />
          <div class="order_pay_container_productList_item_body">
            <div class="order_pay_container_productList_item_body_name">
              {{ product.goodsName }}
            </div>
            <div class="order_pay_container_productList_item_body_count">
              ×{{ product.count }}
            </div>
            <div class="order_pay_container_productList_item_body_price">
              ￥{{ product.goodsPrice }}
            </div>
          </div>
        </div>
        <div class="order_pay_container_productList_total">
          总计￥{{ total.totalPrice }}
        </div>
        <div class="order_pay_container_productList_other">
          <div class="order_pay_container_productList_other_address">
            <div class="order_pay_container_productList_other_address_pre">
              收货地址
            </div>
            <div class="order_pay_container_productList_other_address_show">
              杭电信工
            </div>
          </div>
          <div class="order_pay_container_productList_other_orderNo">
            <div class="order_pay_container_productList_other_orderNo_pre">
              订单号
            </div>
            <div class="order_pay_container_productList_other_orderNo_show">
              10086
            </div>
          </div>
          <div class="order_pay_container_productList_other_arrive">
            <div class="order_pay_container_productList_other_arrive_pre">
              送达时间
            </div>
            <div class="order_pay_container_productList_other_arrive_show">
              尽快送达[11:30]
            </div>
          </div>
          <div class="order_pay_container_productList_other_distribution">
            <div class="order_pay_container_productList_other_distribution_pre">
              配送方式
            </div>
            <div
              class="order_pay_container_productList_other_distribution_show"
            >
              商家配送
            </div>
          </div>
        </div>
      </div>
      <div class="order_pay_container_activity"></div>
      <div class="order_pay_container_command"></div>
      <div class="order_pay_container_other"></div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useCartEffect } from "@/effects/cartEffect";
import { getRequest, putRequest } from '@/utils/request';

// 返回逻辑
const useBackEffect = () => {
  const router = useRouter();
  const handleBack = (shopId) => {
    router.push(`/shop/${shopId}`);
  };
  return { handleBack };
};

// 面罩逻辑
const useMask = () => {
  const mask = ref({
    showMask: false,
    maskMessage: "",
  });

  const handleMask = (message) => {
    mask.value.showMask = !mask.value.showMask;
    mask.value.maskMessage = message;
  };

  return { mask, handleMask };
};

// 支付和取消订单逻辑
const usePayOrCancelEffect = (mask) => {
  const route = useRoute()
  const router = useRouter();
  // 获取orderId
  const orderId = route.query.orderId

  const handleChoice = (message) => {
    if (mask.value.maskMessage === "确认支付吗？") {
      if (message === "取消") {
      } else {
        getOrderPay(orderId)
        // console.log("成功支付");
      }
    } else {
      if (message === "取消") {
      } else {
        getOrderCancel(orderId)
        // router.push(`/orders/${orderId}`)
        // console.log("成功取消订单");
      }
    }
  };

  // 去支付
  const getOrderPay = async(orderId) => {
    const result = await getRequest(`/orders/pay`, {
      orderId: orderId
    })
    // console.log(result)
    if (result?.code && result?.code === 200) {
      router.push(`/orders/${orderId}`)
    }
  }

  // 取消订单
  const getOrderCancel = async(orderId) => {
    const result = await getRequest(`/orders/cancel`, {
      orderId: orderId
    })
    // console.log(result)
    if (result?.code && result?.code === 200) {
      router.push(`/orders/${orderId}`)
    }
  }


  return { handleChoice };
};

export default {
  name: "OrderPay",
  setup() {
    // 获取shopId
    const route = useRoute();
    const shopId = route.params.shopId;
    // 返回逻辑
    const { handleBack } = useBackEffect();
    // 获取productList、total、shopName
    const { productList, total, shopName } = useCartEffect(shopId);
    // 面罩逻辑
    const { mask, handleMask } = useMask();
    // 支付和取消订单逻辑
    const { handleChoice } = usePayOrCancelEffect(mask);

    return {
      productList,
      total,
      shopName,
      handleBack,
      shopId,
      mask,
      handleMask,
      handleChoice,
    };
  },
};
</script>

<style lang="scss" scoped>
.mask {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background: rgb(0, 0, 0, 0.5);
  z-index: 1;
  text-align: center;
  &_body {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 2;
    height: 0.8rem;
    width: 2rem;
    background: #fff;
    border-radius: 0.1rem;
    padding: 0.1rem;
    &_title {
      font-size: 0.2rem;
      font-weight: bolder;
      margin-bottom: 0.1rem;
    }
    &_choice {
      display: flex;
      margin-top: 0.2rem;
      position: relative;
      &_cancel {
        border: 0.01rem solid #000;
        border-radius: 0.1rem;
        width: 0.5rem;
        position: absolute;
        left: 0.1rem;
      }
      &_confirm {
        border: 0.01rem solid #0091ff;
        border-radius: 0.1rem;
        width: 0.5rem;
        position: absolute;
        right: 0.1rem;
      }
    }
  }
}

.wrapper {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}

.order_pay_header {
  position: relative;
  height: 2.5rem;
  background-image: linear-gradient(0deg, rgba(0, 145, 255, 0) 4%, #0091ff 50%);
  &_body {
    display: flex;
    position: absolute;
    top: 0.4rem;
    left: 0;
    right: 0;
    color: #fff;
    &_back {
      font-size: 0.26rem;
      margin-left: 0.1rem;
    }
    &_title {
      flex: 2;
      font-size: 0.18rem;
      text-align: center;
      margin-right: 0.3rem;
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
    border: 0.01rem solid rgb(231, 227, 227);
    padding: 0.1rem;
    &_arrive {
      font-size: 0.2rem;
      margin-bottom: 0.1rem;
    }
    &_distribution {
      color: #737373;
      font-size: 0.14rem;
      height: 0.4rem;
      border-bottom: 0.01rem solid #f2ecec;
    }
    &_operation {
      display: flex;
      position: absolute;
      bottom: 0.1rem;
      left: 0;
      right: 0;
      &_pay {
        flex: 1;
        text-align: center;
        color: #0091ff;
      }
      &_update {
        flex: 1;
        text-align: center;
        color: #0091ff;
      }
      &_talk {
        flex: 1;
        text-align: center;
      }
      &_cancel {
        flex: 1;
        text-align: center;
      }
    }
  }
}

.order_pay_container {
  overflow-y: scroll;
  position: absolute;
  bottom: 0;
  top: 2.55rem;
  left: 0;
  right: 0;
  background: #f2ecec;
  padding: 0 0.1rem;
  border-radius: 0.1rem 0.1rem;
  &_productList {
    position: relative;
    background: #fff;
    border-radius: 0.1rem;
    margin-top: 0.2rem;
    padding: 0.1rem;
    &_title {
      font-weight: bolder;
      margin-left: 0.02rem;
    }
    &_item {
      display: flex;
      margin: 0.1rem 0;
      &_img {
        height: 0.5rem;
        width: 0.5rem;
        border-radius: 0.1rem;
        margin-right: 0.05rem;
      }
      &_body {
        flex: 1;
        position: relative;
        &_count {
          color: #737373;
          position: absolute;
          bottom: 0;
          left: 0;
        }
        &_price {
          position: absolute;
          right: 0;
          top: 0;
        }
      }
    }
    &_total {
      text-align: right;
      font-size: 0.15rem;
      font-weight: bolder;
      margin: 0.1rem 0;
    }
    &_other {
      border-top: 0.01rem solid #f2ecec;
      padding-top: 0.1rem;
      &_address {
        position: relative;
        display: flex;
        margin-bottom: 0.3rem;
        &_pre {
          position: absolute;
          top: 0;
          left: 0;
        }
        &_show {
          position: absolute;
          top: 0;
          right: 0;
        }
      }
      &_orderNo {
        position: relative;
        display: flex;
        margin-bottom: 0.3rem;
        &_pre {
          position: absolute;
          top: 0;
          left: 0;
        }
        &_show {
          position: absolute;
          top: 0;
          right: 0;
        }
      }
      &_arrive {
        position: relative;
        display: flex;
        margin-bottom: 0.3rem;
        &_pre {
          position: absolute;
          top: 0;
          left: 0;
        }
        &_show {
          position: absolute;
          top: 0;
          right: 0;
        }
      }
      &_distribution {
        position: relative;
        display: flex;
        margin-bottom: 0.3rem;
        &_pre {
          position: absolute;
          top: 0;
          left: 0;
        }
        &_show {
          position: absolute;
          top: 0;
          right: 0;
        }
      }
    }
  }
}
</style>