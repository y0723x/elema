<template>
    <div class="wrapper">
        <!-- header区域=search区域+cart区域+secondmenu区域 -->
        <div class="menu_header">
            <div class="menu_header_top">
                <div
                    class="back iconfont"
                    @click="handleBack"
                >&#xe602;</div>
                <div class="title">{{ parentTitle }}</div>
                <div class="search">
                    <span class="search_icon iconfont">&#xe752;</span>
                    <input type="text" class="search_input" placeholder="叫了个炸鸡">
                </div>
                <div class="cart iconfont">&#xe600;</div>
            </div>
            <div class="menu_header_cate">
                <div class="menu_cates">
                    <div
                        :class="{'menu_cates_item': true, 'menu_cates_item-active': activeIndex === index}"
                        v-for="(cate, index) in cateList.values"
                        :key="index"
                        @click="getShopByMenuId(index)"
                    >
                        <div class="menu_cates_item_title">{{ cate.title }}</div>
                        <div
                            :class="{'menu_cates_item_underline-active': activeIndex === index}"
                        ></div>
                    </div>
                </div>
                <div class="all_cate">
                    <div class="all_cate_icon iconfont">&#xe62f;</div>
                    <div class="all_cate_text">全部</div>
                </div>
            </div>
        </div>
        <!-- shop区域 -->
        <div class="shop">
            <div
                class="shop_item"
                v-for="(shop, index) in shopList.values"
                :key="index"
                @click="handleShop(shop.id)"
            >
                <div class="shop_item_image">
                    <img
                        :src="shop.shopUrl"
                        class="shop_item_img"
                    >
                </div>
                <div class="shop_item_body">
                    <div class="shop_item_body_name">{{ shop.shopName }}</div>
                    <div class="shop_item_body_situation">
                        <span class="shop_item_body_situation_score">{{ shop.shopScore }}分</span>
                        <span class="shop_item_body_situation_sale">月售{{ shop.shopSale }}</span>
                    </div>
                    <div class="shop_item_body_distribution">
                        <span class="min_price">起送￥{{ shop.distributionMinPrice }}</span>
                        <span class="fee">配送￥{{ shop.shopDistribution }}</span>
                    </div>
                </div>
                <div class="shop_item_address">{{ shop.shopAddress }}</div>
            </div>
        </div>
        <Toast v-if="show" :message="toastMessage" />
    </div>
</template>

<script>
import { onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getRequest } from '@/utils/request'
import Toast, { useToastEffect } from '@/components/Toast.vue'

const useCateListEffect = () => {
        // 获取menuId
        const route = useRoute()
        const menuId = route.params.menuId

        // shop
        const shopList = reactive([])

        const activeIndex = ref(0)
        const parentTitle = ref()
        const cateList = reactive([])

        onMounted(() => {
            getCateList(menuId)
        })

        // 获取店铺通过menuId
        const getShopByMenuId = async(index) => {
            activeIndex.value = index
            // 获取menuId
            const menuId = cateList.values[index].id
            const result = await getRequest('/shop', {
                menuId: menuId
            })
            if (result?.code && result?.code == 200) {
                shopList.values = result?.object
            }
        }

        // 获取二级菜单
        const getCateList = async (menuId) => {
            const result = await getRequest(`/index/${menuId}`)
            if (result?.code && result?.code == 200) {
                cateList.values = result?.object
                parentTitle.value = result?.object[0].parentTitle
            } else {
                showToast(result?.message)
            }
        }
        
        return { cateList, parentTitle, getShopByMenuId, activeIndex, shopList }
}

export default {
    name: 'menudetail',
    components: {Toast},
    setup() {
        const router = useRouter()
        // 返回首页
        const handleBack = () => {
            router.back()
        }
        // Toast
        const { show, showToast, toastMessage } = useToastEffect();
        // 获取cateList
        const { cateList, parentTitle, getShopByMenuId, activeIndex, shopList } = useCateListEffect()

        // 进入店铺
        const handleShop = (shopId) => {
            router.push(`/shop/${shopId}`)
        }

        return { cateList, show, showToast, toastMessage, parentTitle, handleBack,
        shopList, getShopByMenuId, activeIndex, handleShop }
    },
}
</script>

<style lang="scss" scoped>
.wrapper{
    position:absolute;
    top:0;
    left:0;
    right:0;
    bottom:0;
}

.menu_header{
    position:relative;
    height:.8rem;
    background:#0082d4;
    padding:.2rem 0 .1rem .1rem;
    &_top{
        display:flex;
        color:#fff;
        .back{
            font-size:.2rem;
            line-height:.32rem;
        }
        .title{
            font-size:.23rem;
            font-weight:bolder;
        }
        .search{
            border:.01rem solid #fff;
            border-radius:.14rem;
            background:#fff;
            margin:.02rem .1rem;
            height:.28rem;
            width:1.8rem;
            display: flex;
            &_icon{
                margin:.02rem .1rem;
                color:#0082d4;
                line-height:.2rem;
            }
            &_input{
                border:none;
                outline: none;
                background:none;
                &::placeholder{
                    color:#0082d4;
                    font-size:.14rem;
                    line-height:.2rem;
                }
            }
        }
        .cart{
            font-size:.26rem;
            margin-left:.04rem;
            line-height:.32rem;
        }
    }
    &_cate{
        display:flex;
        color:#fff;
        margin:.15rem .02rem .08rem .02rem;
        overflow:hidden;
        .menu_cates{
            display:flex;
            overflow-x: scroll;
            overflow-y: hidden;
            white-space: nowrap;
            width:3rem;
            &::-webkit-scrollbar{
                display:none;
            }
            .menu_cates_item{
                margin-right:.1rem;
                width:25%;
                .menu_cates_item-active{
                    font-weight: bolder;
                }
                .menu_cates_item_underline-active{
                    border:1px solid #fff;
                    width: .1rem;
                    margin:.05rem auto;
                }
            }
        }
        .all_cate{
            border-left:.01rem solid #5b5959;
            // box-shadow: -.08rem 0 0 0 rgba(41, 12, 127, 0.2);
            // border-radius:.01rem 0 0 .01rem;
            padding-left:.1rem;
            display:flex;
            &_icon{
                margin-right:.02rem;
                line-height:.21rem;
            }
        }
    }
}

.shop{
    position: absolute;
    top:1rem;
    bottom:0;
    left:0;
    right:0;
    background: #e2d8d8;
    &_item{
        margin:.05rem .1rem;
        display:flex;
        background: #fff;
        border-radius:.1rem;
        padding: .1rem;
        &_image{
            border-radius: .1rem;
            margin-right:.07rem;
        }
        &_img{
            height:1rem;
            width:1rem;
            border-radius: .1rem;
        }
        &_body{
            color:#797373;
            &_name{
                line-height:.2rem;
                font-weight:bolder;
                color:#000;
            }
            &_situation{
                line-height:.45rem;
                &_score{
                    font-size:.13rem;
                    color:orange;
                    margin-right: .05rem;
                }
                &_sale{
                    font-size:.1rem;
                }
            }
            &_distribution{
                line-height: .3rem;
                margin-top:.05rem;
                font-size:.1rem;
                .min_price{
                    margin-right:.05rem;
                }
            }
        }
        &_address{
            color:#797373;
            margin-left:.6rem;
            font-size:.1rem;
            line-height:.68rem;
        }
    }
}
</style>