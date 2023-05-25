<template>
  <div>
    <div class="login-container">
      <div class="left-aside"></div>

      <div class="login-form-container">
        <div class="login-form">
          <img src="@/assets/logo.gif" class="logo-image" />

          <div class="id-input-container">
            <div class="inner-title">ID</div>
            <div class="id-input-area">
              <img src="@/assets/user_icons/login_input.svg" class="input-image" />
              <input type="text" class="id-input" v-model="user.userId" />
            </div>
          </div>

          <div class="password-input-container">
            <div class="inner-title">Password</div>
            <div class="password-input-area">
              <img src="@/assets/user_icons/password_input.svg" class="input-image" />
              <input
                :type="showPassword ? 'password' : 'text'"
                class="password-input"
                v-model="user.password"
              />
              <img
                src="@/assets/user_icons/eye.svg"
                class="password-type-change"
                @click="passwordTypeChange"
              />
            </div>
          </div>

          <button class="login" @click="login">Login</button>

          <div class="user-service">
            <div class="user-info">
              아이디나 비밀번호를 잊으셨나요?
              <span class="find-user-info" @click="findUserInfo">여기</span>를 클릭하세요.
            </div>
            <div class="sign-up" @click="signUp">회원 가입</div>
          </div>

          <div class="devider"></div>

          <div class="social-login-container">
            <img
              src="@/assets/user_icons/social/google.svg"
              style="box-shadow: -1px 5px 16px rgba(0, 0, 0, 0.13); border-radius: 50px"
            />
            <img @click="openInsta" src="@/assets/user_icons/social/instagram.svg" />
            <img @click="openNaver" src="@/assets/user_icons/social/naver.svg" />
            <img src="@/assets/user_icons/social/kakaotalk.svg" />
          </div>
        </div>
      </div>

      <div class="right-aside"></div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
export default {
  name: 'UserLogin',
  components: {},
  data() {
    return {
      message: '',
      showPassword: 'password',
      user: {
        userId: '',
        password: '',
      },
    };
  },
  computed: {
    ...mapGetters('userStore', ['checkToken', 'checkUserInfo']),
  },
  created() {},
  methods: {
    ...mapActions('userStore', ['userConfirm']),
    passwordTypeChange() {
      this.showPassword = !this.showPassword;
    },
    signUp() {
      this.$router.push('/user/signup');
    },
    findUserInfo() {},
    openNaver() {
      window.open('https://www.naver.com');
    },
    openInsta() {
      window.open('https://www.instagram.com');
    },
    async login() {
      await this.userConfirm(this.user);
      if (this.checkToken) {
        this.$router.push('/');
      } else {
        alert('ID나 비밀번호가 맞지 않습니다.');
      }
    },
  },
};
</script>

<style scoped>
@font-face {
  font-family: 'S-CoreDream-3Light';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-3Light.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}

.login-container {
  display: grid;
  grid-template-columns: 1fr 5fr 1fr;
  grid-template-areas: 'left content right';
}

.left-aside {
  grid-area: left;
}

.right-aside {
  grid-area: right;
}

.login-form-container {
  grid-area: content;
  min-height: 760px;
  height: 81.3vh;
  padding: 20px 20px 20px 20px;
  max-height: 81.3vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-form {
  min-height: 570px;
  min-width: 400px;
  height: 60vh;
  width: 20vw;
  padding: 0 30px 0 30px;
  background: #ffffff;
  box-shadow: -1px 5px 16px rgba(0, 0, 0, 0.13);
  border-radius: 14px;
}

.logo-image {
  margin: 60px 0 30px 0;
  width: 15vw;
  min-width: 300px;
}

.inner-title {
  margin: 10px 0 5px 0;
  font-weight: 600;
  font-size: 16px;
  color: #5f6073;
}

.id-input-container {
  display: flex;
  flex-direction: column;
  align-items: start;
}

.id-input-area {
  width: 100%;
  display: flex;
  align-items: center;
  height: 35px;
  border-radius: 4px;
  background: #ffe8eb;
}

input {
  border: none;
  outline: none;
  height: 35px;
  width: 80%;
  background: #ffe8eb;
  /* font-family: 'S-CoreDream-3Light'; */
  font-weight: 400;
  letter-spacing: 1px;
}

.input-image {
  margin: 0 10px 0 10px;
}

.password-input-container {
  display: flex;
  flex-direction: column;
  align-items: start;
}

.password-input-area {
  width: 100%;
  display: flex;
  align-items: center;
  height: 35px;
  border-radius: 4px;
  background: #ffe8eb;
}

.password-type-change:hover {
  cursor: pointer;
}

.login {
  width: 100%;
  height: 35px;
  border: none;
  /* background: linear-gradient(90deg, #E1306C 0%, #FF699A 38.02%, rgba(252, 175, 69, 0.78) 99.48%); */
  background: linear-gradient(
    90deg,
    var(--main-grad1-color) 0%,
    var(--main-grad2-color) 38.02%,
    var(--main-grad3-color) 99.48%
  );
  border-radius: 4px;
  margin: 20px 0 0 0;
  color: #ffffff;
  font-family: 'S-CoreDream-3Light';
  font-size: 12px;
  font-weight: 600;
  letter-spacing: 3px;
}

.user-service {
  display: flex;
  justify-content: space-between;
  margin: 10px 0 30px 0;
}

.user-service div {
  font-size: 11px;
}

.find-user-info {
  background: linear-gradient(90deg, #b224ef 0%, #7579ff 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.find-user-info:hover {
  cursor: pointer;
}

.sign-up {
  /* background: linear-gradient(90deg, #e1306c 0%, #ff699a 68.75%, rgba(252, 175, 69, 0.72) 99.48%); */
  background: linear-gradient(
    90deg,
    var(--main-grad1-color) 0%,
    var(--main-grad2-color) 68.75%,
    var(--main-grad3-color) 99.48%
  );

  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.sign-up:hover {
  cursor: pointer;
}

.devider {
  height: 1px;
  background-color: #e7e7e7;
  margin: 50px 0 40px 0;
}

.social-login-container {
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.social-login-container img:hover {
  cursor: pointer;
}
</style>
