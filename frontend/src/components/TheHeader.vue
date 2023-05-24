<template>
  <div class="header">
    <div class="top-bar"></div>
    <div class="header-content">
      <img class="logo-img" src="@/assets/logo.gif" @click="toMain"/>
      <div class="menu-set">
        <div
          :class="{ 'nav-item': true, active: activeMenuItem === 'attraction' }"
          @click="setActiveMenuItem('attraction')"
        >
          <img class="nav-item-vector" src="../assets/header_icon/attraction.svg" />
          <router-link to="/attraction">여행지</router-link>
        </div>

        <div
          :class="{ 'nav-item': true, active: activeMenuItem === 'plan' }"
          @click="setActiveMenuItem('plan')"
        >
          <img class="nav-item-vector" src="../assets/header_icon/plan.svg" />
          <router-link to="/plan">계 획</router-link>
        </div>

        <div
          :class="{ 'nav-item': true, active: activeMenuItem === 'history' }"
          @click="setActiveMenuItem('history')"
        >
          <img class="nav-item-vector" src="../assets/header_icon/history.svg" />
          <router-link to="/history">기 록</router-link>
        </div>

        <div
          :class="{ 'nav-item': true, active: activeMenuItem === 'board' }"
          @click="setActiveMenuItem('board')"
        >
          <img class="nav-item-vector" src="../assets/header_icon/community.svg" />
          <router-link to="/board">게시판</router-link>
        </div>
      </div>
      <div v-if="!checkToken" class="user-area" @click="setActiveMenuItem('-1')">
        <img class="profile-img" src="../assets/header_icon/profile.svg" />
        <router-link to="/user/login">로그인</router-link>
      </div>

      <div v-if="checkToken" class="user-area" @click="profileClick" >
        <img class="profile-img"  v-if="checkUserInfo.profilePicPath != null && checkUserInfo.profilePicPath != ''" :src="`${baseUrl}/profilePath/` + checkUserInfo.profilePicPath" />
        <img class="profile-img" v-if="checkUserInfo.profilePicPath == null || checkUserInfo.profilePicPath == ''" src="@/assets/header_icon/profile.svg">
        <div>{{ checkUserInfo.nickname }}</div>
      </div>

      <button class="menu-open" @click="openRightMenu">
        <img src="@/assets/common/more.svg" style="width: 30px;">
      </button>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
export default {
  name: "TheHeader",
  data() {
    return {
      baseUrl: process.env.VUE_APP_MY_BASE_URL,
      // activeMenuItem: '',
    }
  },
  computed: {  
    ...mapGetters('userStore', ['checkToken', 'checkUserInfo']),
    activeMenuItem : function() {
      return this.$store.state.activeMenuItem;
    },
  },
  methods: {
    toMain(){
      if(this.$route.path !== '/') {
        this.$router.push("/");
        this.setActiveMenuItem('-1');
      }
    },
    setActiveMenuItem(menuItem) {
      this.$store.commit('activeMenuChange', menuItem);
    },
    openRightMenu() {
      this.$emit('openRightMenu')
    },
    profileClick() {
      this.setActiveMenuItem('-1');
      this.openRightMenu();
  },
  },
};
</script>

<style scoped>
.nav-item {
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  align-items: center;
  border-bottom: 6px solid #e1306b00;
}

.nav-item-vector {
  width: 16px;
  height: 22px;
  margin-left: 5px;
}

.nav-item:hover {
  border-bottom: 6px solid #e1306c;
  transition: 0.3s;
}

.nav-item.active {
  border-bottom: 6px solid #e1306c;
  /* 하단 밑줄 그라데이션 */
  /* background-image: linear-gradient(#ffffff, #ffffff), linear-gradient(97.1deg, #E1306C 0%, #FF699A 48.96%, rgba(252, 175, 69, 0.78) 100%);
  background-origin: border-box;
  background-clip: content-box, border-box; */
}

.header {
  width: 100%;
  height: 100px;
  text-align: center;
  box-shadow: 0px 5px 5px rgba(0, 0, 0, 0.15);
  font-size: 15px;
  font-weight: 700;
  min-width: 1920px;
}

.header-content {
  display: flex; /* 10:65:15 비율 */
  justify-content: space-between;
  align-items: center;
  margin-top: 10px;
}

.logo-img {
  flex: 1 1 10%;
  margin-left: 20px;
  height: 50px;
}
.logo-img:hover {
  cursor: pointer;
}

.menu-set {
  align-self: flex-start;
  display: flex;
  justify-content: space-around;
  align-items: center;
  flex: 2 1 65%;
  padding: 3px 15% 5px 10%;
  font-size: 20px;
}

.top-bar {
  background: linear-gradient(90deg, #e1306c 0%, #ff699a 38.02%, rgba(252, 175, 69, 0.78) 99.48%);
  height: 18px;
  margin: 0px 0px 0px 0px; /*아래쪽에 마진을 줘서 메뉴들과 떨어지도록*/
}

.user-area {
  flex: 1 1 15%;
  display: flex;
  align-items: center;
}

.user-area:hover {
  cursor: pointer;
}

.profile-img {
  background-color: #e8e8e8;
  border-radius: 45px;
  width: 45px;
  height: 45px;
  margin:0 10px 0 0;
}

a {
  font-weight: bold;
  padding: 13px 10px 13px 8px;
  flex-shrink: 0;
}

img {
  height: 40px;
}

.menu-open {
  position:absolute;
  right:0%;
  border: none;   
  border-radius: 40px 0 0 40px;
  padding: 0 10px 0 10px;
  background: linear-gradient(105.82deg, #E1306C 9.07%, #FF699A 40.96%, rgba(252, 175, 69, 0.7) 71.54%);
  display: flex;
  align-items: center;
}
</style>
