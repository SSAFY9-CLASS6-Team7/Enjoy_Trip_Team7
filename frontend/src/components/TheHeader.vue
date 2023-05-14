<template>
  <div class="header">
    <div class="top-bar"></div>
    <div class="header-content">
      <img class="logo-img" src="@/assets/logo.gif" />
      <div class="menu-set">
        <div :class="{ 'nav-item': true, active: activeMenuItem === 'attraction' }" @click="activeMenuItem = 'attraction'">
          <img class="nav-item-vector" src="../assets/header_icon/attraction.svg"/>
          <router-link to="/attraction">여행지</router-link>
        </div>

        <div :class="{ 'nav-item': true, active: activeMenuItem === 'plan' }" @click="activeMenuItem = 'plan'">
          <img class="nav-item-vector" src="../assets/header_icon/plan.svg"/>
          <router-link to="/plan">계 획</router-link>
        </div>

        <div :class="{ 'nav-item': true, active: activeMenuItem === 'history' }" @click="activeMenuItem = 'history'">
          <img class="nav-item-vector" src="../assets/header_icon/history.svg"/>
          <router-link to="/history">기 록</router-link>
        </div>

        <div :class="{ 'nav-item': true, active: activeMenuItem === 'board' }" @click="activeMenuItem = 'board'">
          <img class="nav-item-vector" src="../assets/header_icon/community.svg"/>
          <router-link to="/board">게시판</router-link>
        </div>
        
      </div>
      <div class="user-area">
        <img class="profile-img" src="../assets/header_icon/profile.svg" />
        <router-link to="/user/login">로그인</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "TheHeader",
  data() {
    return {
      activeMenuItem: '-1',
    }
  }
};

</script>

<style scoped>
  .nav-item {
  display: flex;
  flex-direction: row;
  align-items: center;
  border-bottom : 6px solid #e1306b00;
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
  height: 60px;
}

.menu-set {
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

.profile-img {
  background-color: #e8e8e8;
  border-radius: 22px;
}

a {
  font-weight: bold;
  padding: 13px 10px 13px 8px;
  flex-shrink: 1;

}
/* a:hover {
  color: #42b983;
} */
img {
  height: 40px;
}
</style>
