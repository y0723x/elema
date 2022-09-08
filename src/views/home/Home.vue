<template>
  <div class="wrapper">
    <div class="content_box">
        <router-view v-slot="{ Component }">
            <transition name="move">
                <keep-alive>
                    <component :is="Component"></component>
                </keep-alive>
            </transition>
        </router-view>
    </div>
    <Docker />
  </div>
</template>

<script>
import Docker from '../../components/Docker.vue'
export default {
  name: "Home",
  components: {Docker},
  setup() {
  },
};
</script>

<style lang="scss" scoped>
.wrapper {
  position: absolute;
  top: 0;
  bottom: 0;
  right: 0;
  left: 0;
  background: #fff;
}

.content_box{
    position: absolute;
    top:0;
    right:0;
    left:0;
    bottom:.7rem;
}
</style>