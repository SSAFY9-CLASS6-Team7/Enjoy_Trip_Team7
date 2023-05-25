<template>
  <div class="container" @click="closeRightMenu">
    <div class="right-menu" :class="{ open: isOpen, close: !isOpen }" @click.stop>
      <div class="top">
        <div v-if="!checkToken" class="user-info">
          <img src="@/assets/header_icon/profile.svg" class="profile" />
          <div class="go-to-login" @click="goToLogin">로그인 하세요.</div>
        </div>
        <div v-if="checkToken" class="user-info">
          <button class="logout" @click="logout">
            로그아웃
            <img src="@/assets/user_icons/logout.svg" />
          </button>
          <img
            v-if="checkUserInfo.profilePicPath != null && checkUserInfo.profilePicPath != ''"
            :src="`${baseUrl}/profilePath/` + checkUserInfo.profilePicPath"
            class="profile"
          />
          <img
            v-if="checkUserInfo.profilePicPath == null || checkUserInfo.profilePicPath == ''"
            src="@/assets/header_icon/profile.svg"
            class="profile"
          />
          <div class="id-email-container">
            <div class="id">{{ checkUserInfo.nickname }}</div>
            <div class="email">{{ checkUserInfo.email }}</div>
          </div>
          <button
            class="custom-main-btn"
            @click="
              gotoSetting();
              closeRightMenu();
            "
          >
            <img class="setting-btn-vector" src="@/assets/user_icons/setting.svg" />
            메인화면 커스텀
          </button>
          <button class="open-gradation-input-btn">
            <input type="color" id="gradation-select" v-model="mainColor1" />
            <input type="color" id="gradation-select" v-model="mainColor2" />
            <input type="color" id="gradation-select" v-model="mainColor3" />
          </button>
        </div>
      </div>
      <div class="devider"></div>

      <div v-if="!checkToken" class="main">
        <div class="announcement" @click="moveAnnouncement">
          <img src="@/assets/announcement.svg" style="margin: 0 10px 0 0" />
          <span>공지사항</span>
        </div>
        <div class="inner-devider"></div>
        <div class="signup" @click="moveSignUp">
          <img src="@/assets/signup.svg" style="margin: 0 10px 0 0" />
          <span>회원가입</span>
        </div>
        <div class="inner-devider"></div>
        <div class="history" @click="moveHistory">
          <img src="@/assets/header_icon/history.svg" style="margin: 0 10px 0 0" />
          <span>기록</span>
        </div>
        <div class="inner-devider"></div>
        <div class="plan" @click="movePlan">
          <img src="@/assets/header_icon/plan.svg" style="margin: 0 10px 0 0" />
          <span>계획</span>
        </div>
      </div>
      <div v-if="checkToken" class="main">
        <div class="announcement" @click="moveAnnouncement">
          <img src="@/assets/announcement.svg" style="margin: 0 10px 0 0" />
          <span>공지사항</span>
        </div>
        <div class="inner-devider"></div>
        <div class="modify-user" @click="moveModifyUser">
          <img src="@/assets/modify_user.svg" style="margin: 0 10px 0 0" />
          <span>회원정보 수정</span>
        </div>
        <div class="inner-devider"></div>
        <div class="history" @click="moveHistory">
          <img src="@/assets/header_icon/history.svg" style="margin: 0 10px 0 0" />
          <span>기록</span>
        </div>
        <div class="inner-devider"></div>
        <div class="plan" @click="movePlan">
          <img src="@/assets/header_icon/plan.svg" style="margin: 0 10px 0 0" />
          <span>계획</span>
        </div>
        <div class="inner-devider"></div>
        <div class="quit-user" @click="userExit">
          <img src="@/assets/quit_user.svg" style="margin: 0 10px 0 0" />
          <span>탈퇴</span>
        </div>
      </div>

      <div class="devider"></div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
export default {
  name: 'RightMenu',
  components: {},
  data() {
    return {
      isOpen: false,
      baseUrl: process.env.VUE_APP_MY_BASE_URL,
      mainColor1: '',
      mainColor2: '',
      mainColor3: '',
    };
  },
  computed: {
    ...mapGetters('userStore', ['checkToken', 'checkUserInfo']),
    ...mapGetters(['getMainColor1', 'getMainColor2', 'getMainColor3']),
  },
  watch: {
    mainColor1: function () {
      this.mainColor1Change(this.mainColor1);
    },
    mainColor2: function () {
      this.mainColor2Change(this.mainColor2);
    },
    mainColor3: function () {
      this.mainColor3Change(this.mainColor3);
    },
  },
  methods: {
    ...mapActions('userStore', ['userConfirm', 'userLogout', 'nextPathChange']),
    ...mapActions(['mainColor1Change', 'mainColor2Change', 'mainColor3Change']),
    menuClose() {
      this.$emit('openRightMenu');
    },
    closeRightMenu() {
      this.isOpen = false;
      setTimeout(() => {
        this.$emit('openRightMenu');
      }, 300);
    },
    goToLogin() {
      this.movePage('/user/login');
    },
    logout() {
      this.mainColor1Change('#e1306c');
      this.mainColor2Change('#ff699a');
      this.mainColor3Change('#fcaf45');
      this.userLogout();
      this.movePage('/');
    },
    moveAnnouncement() {
      this.movePage('/announcement');
    },
    moveSignUp() {
      this.movePage('/user/signup');
    },
    moveHistory() {
      this.movePage('/history/list');
    },
    movePlan() {
      this.movePage('/plan/list');
    },
    moveModifyUser() {
      this.nextPathChange('/user/modify');
      this.movePage('/user/check');
    },
    userExit() {
      this.nextPathChange('delete');
      this.movePage('/user/check');
    },
    movePage(link) {
      this.closeRightMenu();
      if (this.$route.path !== link) {
        this.$store.commit('activeMenuChange', -1);
        this.$router.push(link);
      }
    },
    gotoSetting() {
      this.$router.push('/setting');
    },
  },
  mounted() {
    setTimeout(() => {
      this.isOpen = true;
    }, 100);
    // Get the computed color values
    this.mainColor1 = this.getMainColor1;
    this.mainColor2 = this.getMainColor2;
    this.mainColor3 = this.getMainColor3;
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

.container {
  background: #51515167;
  position: fixed;
  right: 0%;
  top: 0%;
  width: 100vw;
  height: 100vh;
  z-index: 10;
}

.devider {
  height: 1px;
  background-color: #bbbaba;
  margin: 10px 20px 10px 20px;
}

.inner-devider {
  height: 1px;
  width: 30%;
  background-color: #bbbaba;
  margin: 5px 40px 5px 40px;
}

.right-menu {
  position: fixed;
  height: 100vh;
  width: 20vw;
  min-width: 300px;
  border-radius: 40px 0 0 40px;
  background: #f2f2f2;
  right: -20vw;
  transition: right 0.5s;
  z-index: 20;
}

.right-menu.open {
  right: 0;
}

.right-menu.close {
  right: -20vw;
}

.top {
  height: 20vh;
  min-height: 250px;
  background: #f2f2f2;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
}

.profile {
  width: 45px;
  height: 45px;
  margin: 0 20px 0 0;
  border-radius: 45%;
}

.go-to-login {
  /* font-family: 'S-CoreDream-3Light'; */
  font-weight: 600;
  font-size: 18px;
}

.go-to-login:hover {
  cursor: pointer;
}

.logout {
  position: absolute;
  right: 10%;
  top: 5%;
  border: 1px solid #b7b7b7;
  border-radius: 4px;
  padding: 5px 10px 5px 10px;
  font-family: 'S-CoreDream-3Light';
  font-weight: 800;
  font-size: 12px;
  display: flex;
  align-items: center;
}

.logout img {
  margin: 0 0 0 5px;
}

.id-email-container {
  display: flex;
  flex-direction: column;
  align-items: start;
}

.id {
  font-family: 'S-CoreDream-3Light';
  font-size: 18px;
  font-weight: 800;
}

.email {
  font-family: 'S-CoreDream-3Light';
  color: #b7b7b7;
  font-weight: 500;
}

.main {
  height: 80vh;
  min-height: 830px;
  background: #f2f2f2;
  display: flex;
  flex-direction: column;
  align-items: start;
  padding: 50px 40px 0 40px;
}

.main div {
  font-size: 25px;
  font-weight: 600;
  margin: 10px 0 10px 0;
  display: flex;
}

.main div:not(.inner-devider) {
  padding: 10px 0 10px 20px;
}

.main div:hover {
  cursor: pointer;
  /* background: linear-gradient(
    92.4deg,
    #e1306c 0.11%,
    #ff699a 53.13%,
    rgba(252, 175, 69, 0.87) 95.51%
  ); */
  background: linear-gradient(
    92.4deg,
    var(--main-grad1-color) 0.11%,
    var(--main-grad2-color) 53.13%,
    var(--main-grad3-color) 95.51%
  );
  border-radius: 5px;
  width: 100%;
}

.main div:hover img,
.main div:hover span {
  filter: invert(100%);
}

.custom-main-btn {
  position: absolute;
  top: 15%;
  left: 35%;
  padding: 7px 10px;
  margin-top: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  /* background: linear-gradient(95.36deg,
   #e1306c 2.32%,
    #ff699a 68.42%,
     #fcaf45 104.98%); */
  background: linear-gradient(
    95.36deg,
    var(--main-grad1-color) 2.32%,
    var(--main-grad2-color) 68.42%,
    var(--main-grad3-color) 104.98%
  );
  background-blend-mode: darken;
  border-radius: 5px;
  font-size: 12px;
  font-weight: 600;
  border: none;
  color: white;
}

.setting-btn-vector {
  width: 15px;
  height: auto;
  margin-right: 5px;
}

html,
body {
  height: 100%;
  width: 100%;
}

.open-gradation-input-btn {
  position: absolute;
  top: 21%;
  left: 30%;
  border: none;
}

#gradation-select {
  margin: 3px;
}
</style>
