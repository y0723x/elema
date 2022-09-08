<template>
    <div class="wrapper">
        <!-- 定位 -->
        <div class="tag">
            <div class="tag_address">
                <span class="tag_address_detail">具体地址</span>
                <span class="tag_address_icon iconfont">&#xeb6d;</span>
            </div>
            <div class="tag_other">
                <span class="tag_other_cart iconfont">&#xe600;</span>
                <span class="tag_other_notify iconfont">&#xec3b;</span>
            </div>
        </div>
        <!-- 搜索 -->
        <div class="search">
            <span class="search_scan iconfont">&#xe601;</span>
            <span class="search_divide">|</span>
            <input 
                type="text" 
                placeholder="解忧咖啡馆" 
                class="search_input"
            />
            <span class="search_self">搜索</span>
        </div>
        <!-- 轮播图 -->
        <div class="swiper" @mouseenter="enter" @mouseleave="leave">
            <div class="swiper_body">
                <span 
                    :class="{'swiper_item': true, 'swiper_item-fade': idx === index}"
                    v-for="(item, index) in sliders"
                    :key="index"
                >
                    <img :src="item.imgurl">
                </span>
            </div>
            <span
                class="swiper_btn_prev iconfont"
                @click="toggle(-1)"
            >&#xeb6d;</span>
            <span 
                class="swiper_btn_next iconfont"
                @click="toggle(1)"
            >&#xeb6d;</span>
            <div class="swiper_indicator">
                <span
                v-for="(i, ix) in sliders.length"
                :key="ix"
                :class="{'swiper_indicator_item':true, 'swiper_indicator_item-active': idx === ix}"
                @click="idx = ix"
                ></span>
            </div>
        </div>
        <!-- 分类 -->
        <div class="category">
            <div
                class="category_item"
                v-for="(cate, index) in cateList.values"
                :key="index"
                @click="handleCateList(cate.id)"
            >
                <div
                    class="category_item_icon iconfont"
                    v-html="cate.icon"
                ></div>
                <span class="category_item_title">{{ cate.title }}</span>
            </div>
        </div>
        <!-- 附近推荐 -->
        <div class="nearby">
            <h3 class="nearby_title">附近店铺</h3>
            <div class="nearby_item"></div>
        </div>
        <Toast v-if="show" :message="toastMessage" />
    </div>
</template>

<script>
import { onMounted, onUnmounted, reactive, ref, watch, onBeforeMount } from 'vue'
import { useRouter } from 'vue-router'
import { getRequest } from '@/utils/request'
import Toast, { useToastEffect } from '@/components/Toast.vue'

// swiper轮播图配置
const swiperEffect = () => {
    // 下标
        const idx = ref(0)
        // 时间管理器
        let timer = null
        // 是否自动循环
        const autoplay = ref(true)
        // sliders信息
        const sliders = reactive([
            {
                imgurl: 'https://yxrepository.oss-cn-hangzhou.aliyuncs.com/img/spideman/247850.png',
                title: 'spiderman1'
            },
            {
                imgurl: 'https://yxrepository.oss-cn-hangzhou.aliyuncs.com/img/spideman/374464.jpg',
                title: 'spiderman2'
            },
            {
                imgurl: 'https://yxrepository.oss-cn-hangzhou.aliyuncs.com/img/spideman/626164.jpg',
                title: 'spiderman3'
            },
        ])

        // 自动做循环
        const doAutoplay = () => {
            timer = setInterval(() => {
                idx.value++
                if (idx.value >= sliders.length) {
                    idx.value = 0
                }
            },2000)
        }

        // 监听sliders的数据变化，同时判断是否自动循环
        watch(() => {sliders}, (newValue, oldValue) => {
            // console(newValue)
            if (newValue && autoplay) {
                idx.value = 0
                doAutoplay()
            }
        }, { immediate: true })

        // 未渲染页面时清除时间器，即跳转至其他页面
        onUnmounted(() => {
            clearInterval(timer)
        })

        // 鼠标进入计时器后，时间器销毁
        const enter = () => {
            clearInterval(timer)
        }

        // 鼠标离开计时器后，时间器启动
        const leave = () => {
            doAutoplay()
        }

        // 手动点击prev和next切换页面
        const toggle = (i) => {
            idx.value += i
            // 判断下标是否超过总数据长度
            if (idx.value >= sliders.length) {
                idx.value = 0
            }
            // 判断下标是否<0
            if (idx.value < 0) {
                idx.value = sliders.length-1
            }
        }

    return { sliders, enter, leave, idx, toggle }
}

// 有关cateList的配置
const cateListEffect = () => {
        const router = useRouter()

        // cateList分类
        const cateList = reactive([])

        onMounted(() => {
            // 每次页面刷新时，获取cateList
            getCateList()
        })
        
        const getCateList = async () => {
            const result = await getRequest('/index')
            // console.log(result)
            if (result?.code && result?.code == 200) {
                cateList.values = result?.object
            } else {
                showToast(result?.message)
            }
        }

        const handleCateList = (menuId) => {
            // 跳转至二级菜单页面
            router.push(`/index/${menuId}`)
        }
        return { cateList, handleCateList }
}

export default {
    name: 'DashBoard',
    components: {Toast},
    setup() {
        // Toast
        const { show, showToast, toastMessage } = useToastEffect();
        // swiper轮播图
        const { sliders, enter, leave, idx, toggle } = swiperEffect()
        // 有关cateList的处理
        const { cateList, handleCateList } = cateListEffect()
        


        return { sliders, enter, leave, idx, toggle, cateList, handleCateList,
        show, showToast, toastMessage
         }
    },
}
</script>

<style lang="scss" scoped>
.wrapper{
    overflow-y: auto;
    position: absolute;
    top:.2rem;
    bottom:0;
    left:0;
    right:0;
}

.tag{
    position:relative;
    display:flex;
    height:.3rem;
    margin:.1rem;
    &_address{
        line-height: .3rem;
        &_detail{
            font-size:.18rem;
            margin-right:.06rem;
        }
        &_icon{
            font-size:.12rem;
        }
    }
    &_other{
        position:absolute;
        right:0;
        line-height: .3rem;
        &_cart{
            font-size:.23rem;
        }
        &_notify{
            margin:0 .1rem 0 .2rem;
            font-size:.23rem;
        }
    }
}

.search{
    position: relative;
    line-height:.35rem;
    border: .01rem solid rgba(0, 195, 255, 0.795);
    margin: 0 .1rem;
    border-radius:.2rem;
    &_divide{
        font-size:.2rem;
        color:rgb(219, 213, 213);
        margin-right:.05rem;
    }
    &_scan{
        margin:0 .05rem 0 .1rem;
        font-size:.2rem;
        color:rgba(0, 195, 255, 0.795);
    }
    &_input{
        border:none;
        outline: none;
        &::placeholder{
            color:rgb(163, 160, 160);
        }
    }
    &_self{
        position:absolute;
        right:0;
        color:#fff;
        width:.6rem;
        background: rgba(0, 195, 255, 0.795);
        text-align: center;
        border-radius:.15rem;
        line-height: .35rem;
        height:.38rem;
    }
}

.swiper{
    position:relative;
    height:1rem;
    margin:.1rem;
    border:.01rem solid #efefef;
    border-radius:.1rem;
    &_item{
        position:absolute;
        top:0;
        left:0;
        bottom:0;
        right:0;
        opacity:0;
        transition: opacity 0.5s linear;
        
        &-fade{
            opacity: 1;
        }
        img{
            border:.01rem solid #efefef;
            border-radius:.1rem;
            height:100%;
            width:100%;
        }
    }
    &_btn_prev{
        position:absolute;
        line-height:.44rem;
        width:.44rem;
        top:.3rem;
        left:.2rem;
        background: rgba(0,0,0,.2);
        border-radius:50%;
        text-align: center;
        transform: rotate(90deg);
        color:#a09b9b;
        transition:all 0.5s;
    }
    &_btn_next{
        position:absolute;
        line-height:.44rem;
        width:.44rem;
        top:.3rem;
        background: rgba(0,0,0,.2);
        right:.2rem;
        border-radius:50%;
        text-align: center;
        transform: rotate(-90deg);
        color:#a09b9b;
        transition:all 0.5s;
    }
    &_indicator{
        position:absolute;
        top:.8rem;
        left:0;
        width:100%;
        text-align: center;
        &_item{
            display: inline-block;
            height:.12rem;
            width:.12rem;
            background: #a09b9b;
            border-radius: 50%;
            cursor: pointer;
            margin-left:.1rem;
            transition: all 0.5s;
            &-active{
                background: #fff;
            }
        }
        
    }
}

.category{
    position:relative;
    height:2.4rem;
    width:100%;
    background: #fcf7f7;
    padding:.2rem .2rem 0 0;
    display:flex;
    flex-wrap: wrap;
    &_item{
        width:20%;
        text-align: center;
        &_icon{
            font-size:.26rem;
        }
        &_title{
            display:inline-block;
            margin-top:.08rem;
        }
    }
}

</style>