<template>
  <div class="wrapper">
    <div class="register_container">
      <div class="register_container_pic">
        <i class="user_pic iconfont">&#xe99e;</i>
      </div>
      <div class="register_container_input">
        <div class="username">
          <i class="username_icon iconfont">&#xe68e;</i>
          <input
            type="text"
            placeholder="请输入用户名"
            v-model="userinfo.username"
          />
        </div>
        <div class="password">
          <i class="password_icon iconfont">&#xe6ae;</i>
          <input
            type="password"
            placeholder="请输入密码"
            v-model="userinfo.password"
          />
        </div>
      </div>
      <div class="register_container_button" @click="handleRegister()">
        注册
      </div>
      <div class="register_container_link" @click="handleLogin()">登录</div>
    </div>
    <Toast v-if="show" :message="toastMessage" />
  </div>
</template>

<script>
import { reactive, toRefs, ref } from "vue";
import { useRouter } from "vue-router";
import { post } from "../../utils/request";
import Toast, { useToastEffect } from "../../components/Toast.vue";
export default {
  name: "Register",
  setup() {
    const router = useRouter();
    const { show, showToast, toastMessage } = useToastEffect();
    // 用户信息
    const userinfo = reactive({
      username: "",
      password: "",
    });

    const handleRegister = async () => {
      // 获得结果请求
      const result = await post("/api/user/register", {
        name: userinfo.username,
        password: userinfo.password,
      });

      // 没有后端所以假判断一下
      if (result?.error === 0) {
        router.push('/login')
      } else {
        // 请求状态为500
        showToast("数据库错误，请联系管理员！");
      }
    };

    const handleLogin = () => {
      router.push("/login");
    };

    //   const user = toRefs(userinfo)

    return { handleLogin, handleRegister, userinfo, show, toastMessage };
  },
};
</script>

<style lang="scss" scoped>
.register_container {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -70%);
  background: #fff;
  &_pic {
    line-height: 1rem;
    width: 1rem;
    text-align: center;
    color: #7fbeea;
    background: #a59f9f;
    border: 1px solid #fff;
    border-radius: 50%;
    margin: 0 auto 0.1rem auto;
    .user_pic {
      font-size: 0.5rem;
    }
  }
  &_input {
    height: 0.96rem;
    padding: 0.1rem;
    .username {
      display: flex;
      margin: 0.1rem 0;
      background: #f9f9f9;
      //   border: 1px solid rgba(0, 0, 0, 0.1);
      .username_icon {
        font-size: 0.24rem;
        margin-right: 0.1rem;
      }
      .username input {
        border: none;
        background: none;
        outline: none;
        height: 0.56rem;
        padding: 0.1rem;
      }
    }
    .password {
      display: flex;
      //   border: 1px solid rgba(0, 0, 0, 0.1);
      background: #f9f9f9;
      .password_icon {
        font-size: 0.24rem;
        margin-right: 0.1rem;
      }
      .password input {
        border: none;
        background: none;
      }
    }
  }
  &_button {
    line-height: 0.48rem;
    box-shadow: 0 0.04rem 0.08rem 0 rgba(0, 145, 255, 0.32);
    background: #0091ff;
    text-align: center;
    border-radius: 0.04rem;
    color: #fff;
    margin-bottom: 0.1rem;
  }
  &_link {
    text-align: center;
    font-size: 0.14rem;
  }
}
</style>