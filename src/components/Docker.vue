<template>
    <div class="docker">
      <div
        :class="{'docker_item': true, 'docker_item-active': activeIndex === index}"
        v-for="(item, index) in tabList"
        :key="index"
        @click="handleTab(index, item.path)"
      >
        <div class="docker_item_icon iconfont" v-html="item.icon"></div>
        <div :class="{'docker_item_title': true, 'docker_item_title-active': activeIndex === index}">{{ item.title }}</div>
      </div>
    </div>
</template>

<script>
import { reactive, ref } from "vue";
import { useRouter } from 'vue-router'
export default {
    name: 'Docker',
    setup() {
        const router = useRouter()
    // 是否高亮
    const activeIndex = ref(0)

    // tabList信息
    const tabList = reactive([
      {
        path: "/index",
        icon: "&#xe6b8;",
        title: "首页",
      },
      {
        path: "/dynamic",
        icon: "&#xe604;",
        title: "真香",
      },
      {
        path: "/orders",
        icon: "&#xe8ae;",
        title: "订单",
      },
      {
        path: "/my",
        icon: "&#xe70b;",
        title: "我的",
      },
    ]);

    // 切换tab
    const handleTab = (index, path) => {
        activeIndex.value = index
        router.push(path)
    }

    return { tabList, activeIndex, handleTab };
    },
}
</script>

<style lang="scss" scoped>
.docker {
  position: fixed;
  bottom: 0;
  height: 0.7rem;
  right: 0;
  left: 0;
  background: #efefef;
  border-top: 0.01rem solid rgb(138, 128, 128);
  display: flex;
  &_item{
      height:.7rem;
      width:.9rem;
      margin: .1rem .2rem 0 .2rem;
      text-align: center;
      &-active{
          color:rgba(0, 195, 255, 0.795);
      }
      &_icon{
          font-size:.26rem;
          margin-bottom:.05rem;
      }
      &_title{
          font-size:.1rem;
          color:rgb(163, 160, 160);
          &-active{
          color:rgba(0, 195, 255, 0.795);
      }
      }
  }
}
</style>