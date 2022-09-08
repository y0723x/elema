<template>
  <div class="wrapper">
    <!-- header -->
    <div class="order_header">
      <div
        class="order_header_item"
        v-for="(os, index) in orderStatus"
        :key="index"
        @click="handleHeader(index)"
      >
        <div :class="{ order_header_item_text: activeIndex === index }">
          {{ os.text }}
        </div>
        <div :class="{ order_header_underline: activeIndex === index }"></div>
      </div>
      <div class="order_header_search iconfont">&#xe752;</div>
    </div>
    <!-- container -->
    <div class="order_container">
      <div
        class="order_container_item"
        v-for="(order, index) in orders.values"
        :key="index"
      >
        <div class="order_container_item_title">
          <div
            class="order_container_item_title_body"
            @click="toShop(order.shop.id)"
          >
            <img
              :src="order.shop.shopUrl"
              class="order_container_item_title_body_img"
            />
            <div class="order_container_item_title_body_name">
              {{ order.shop.shopName }}
            </div>
            <div class="order_container_item_title_body_icon iconfont">
              &#xe602;
            </div>
          </div>
          <div
            class="order_container_item_title_status"
            v-html="
              order.status === 0
                ? '未付款'
                : order.status === 1
                ? '已付款'
                : order.status === 2
                ? '已取消'
                : '已退款'
            "
            @click="toOrderDetail(order.id)"
          ></div>
        </div>
        <div
          class="order_container_item_goods"
          @click="toOrderDetail(order.id)"
        >
          <div class="order_container_item_goods_images">
            <template v-if="order.goodsList.length > 1">
              <img
                :src="goods.goodsUrl"
                class="order_container_item_goods_images_item"
                v-for="(goods, innerIndex) in order.goodsList"
                :key="innerIndex"
              />
            </template>
            <template v-else>
              <img
                :src="goods.goodsUrl"
                class="order_container_item_goods_images_item"
                v-for="(goods, innerIndex) in order.goodsList"
                :key="innerIndex"
              />
              <div class="goods_name">{{ order.goodsList[0].goodsName }}</div>
            </template>
          </div>
          <div class="order_container_item_goods_total">
            <div class="order_container_item_goods_total_price">
              ￥{{ order.totalPrice }}
            </div>
            <div class="order_container_item_goods_total_count">
              共{{ order.totalCount }}件
            </div>
          </div>
        </div>
        <div class="order_container_item_other">
          <div class="order_container_item_other_similar">相似商家</div>
          <div class="order_container_item_other_again">再来一单</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, reactive, onMounted } from "vue";
import { getRequest } from "@/utils/request";
import { useRouter } from "vue-router";

// 获取orderList
const useOrderListEffect = () => {
  const status = ref(-1);
  // 下标逻辑
  const activeIndex = ref(0);
  const orderStatus = reactive([
    {
      status: -1,
      text: "全部",
    },
    {
      status: 0,
      text: "待消费",
    },
    {
      status: 1,
      text: "待评价",
    },
    {
      status: 3,
      text: "退款",
    },
  ]);

  // orderList数据
  const orderList = reactive([]);
  const orders = reactive([]);

  const handleHeader = (index) => {
    activeIndex.value = index;
    status.value = orderStatus[activeIndex.value]?.status;

    if (status.value != -1) {
      orders.values = [];
      orderList.values.forEach((order) => {
        if (order.status === status.value) {
          orders.values.push(order);
        }
      });
    } else {
      orders.values = orderList.values;
    }
  };

  onMounted(() => {
    getAllOrders();
  });

  // 获取所有历史订单记录
  const getAllOrders = async () => {
    const result = await getRequest("/orders");
    // console.log(result)
    if (result?.code && result?.code === 200) {
      orderList.values = result?.object;
      orders.values = result?.object;
    }
  };

  return { orderList, orders, handleHeader, orderStatus, activeIndex };
};

// 跳转逻辑
const toEffect = () => {
  const router = useRouter();
  const toOrderDetail = (orderId) => {
    router.push(`/orders/${orderId}`);
  };
  const toShop = (shopId) => {
    router.push(`/shop/${shopId}`);
  };

  return { toOrderDetail, toShop };
};

export default {
  name: "Orders",
  setup() {
    // 获取orderList
    const { orderList, orders, handleHeader, orderStatus, activeIndex } =
      useOrderListEffect();
    // 跳转逻辑
    const { toOrderDetail, toShop } = toEffect();

    return {
      handleHeader,
      orderStatus,
      activeIndex,
      orderList,
      orders,
      toOrderDetail,
      toShop,
    };
  },
};
</script>

<style lang="scss" scoped>
.wrapper {
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
  bottom: 0;
  background: #f2ecec;
}

.order_header {
  display: flex;
  position: relative;
  font-size: 0.18rem;
  margin: 0.2rem 0 0.1rem 0;
  &_underline {
    height: 0.02rem;
    width: 0.15rem;
    border: 0.01rem solid #0091ff;
    border-radius: 0.1rem;
    background: #0091ff;
    margin: 0.05rem auto;
  }
  &_item {
    text-align: center;
    width: 0.6rem;
    margin-right: 0.1rem;
    &_text {
      font-weight: bolder;
      font-size: 0.2rem;
    }
  }
  &_search {
    position: absolute;
    top: 0.02rem;
    right: 0.1rem;
    font-size: 0.2rem;
  }
}

.order_container {
  // position:relative;
  // height:100%;
  // width:100%;
  position: absolute;
  top: 0.8rem;
  left: 0;
  right: 0;
  bottom: 0;
  overflow-y: scroll;
  &_item {
    position: relative;
    width: 89%;
    margin: 0 0.1rem 0.1rem 0.1rem;
    background: #fff;
    height: 1.5rem;
    border-radius: 0.1rem;
    padding: 0.1rem;
    &_title {
      margin-bottom: 0.1rem;
      &_body {
        display: flex;
        position: relative;
        &_img {
          height: 0.2rem;
          width: 0.2rem;
          border-radius: 0.1rem;
          margin-right: 0.05rem;
        }
        &_name {
          font-size: 0.18rem;
          line-height: 0.2rem;
          margin-right: 0.1rem;
        }
        &_icon {
          transform: rotate(180deg);
          color: #f2ecec;
          font-size: 0.1rem;
          line-height: 0.2rem;
        }
      }
      &_status {
        position: absolute;
        color: #737373;
        top: 0.16rem;
        right: 0.1rem;
        font-size: 0.1rem;
      }
    }
    &_goods {
      display: flex;
      position: relative;
      margin-bottom: 0.2rem;
      &_images {
        display: flex;
        width: 2.9rem;
        overflow-x: scroll;
        &::-webkit-scrollbar {
          display: none;
        }
        &_item {
          height: 0.6rem;
          width: 0.6rem;
          border-radius: 0.1rem;
          margin-right: 0.05rem;
        }
      }
      &_total {
        position: absolute;
        top: 0.1rem;
        right: 0;
        text-align: center;
        &_price {
          margin-bottom: 0.06rem;
        }
        &_count {
          color: #737373;
        }
      }
    }
    &_other {
      position: relative;
      &_similar {
        position: absolute;
        right: 1rem;
        border: 0.01rem solid #f2ecec;
        width: 0.9rem;
        border-radius: 0.2rem;
        text-align: center;
        line-height: 0.3rem;
      }
      &_again {
        position: absolute;
        right: 0;
        border: 0.01rem solid #0091ff;
        color: #0091ff;
        width: 0.9rem;
        border-radius: 0.2rem;
        text-align: center;
        line-height: 0.3rem;
      }
    }
  }
}
</style>