<template>
  <div class="header">
    <div class="top-bar"></div>
    <div class="header-content">
      <img class="logo-img" src="@/assets/logo.gif" />
      <div class="menu-set">
        <router-link to="/attraction">여행지</router-link>
        <router-link to="/plan">계 획</router-link>
        <router-link to="/history">기 록</router-link>
        <router-link to="/board">게시판</router-link>
      </div>
      <div class="user-area">
        <img class="profile-img" src="@/assets/logo.png" />
        <router-link to="/user/login">로그인</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "TheHeader",
};
</script>

<style scoped>
.header {
  width: 100%;
  text-align: center;
  box-shadow: 0px 5px 5px rgba(0, 0, 0, 0.15);
  font-size: 15px;
  font-weight: 700;
}

.header-content {
  display: flex; /* 10:65:15 비율 */
  justify-content: space-between;
  align-items: center;
}

.logo-img {
  flex: 1 1 10%;
}

.menu-set {
  display: flex;
  justify-content: space-around;
  align-items: center;
  flex: 2 1 65%;
  padding: 5px 15% 5px 10%;
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
  padding: 13px;
  flex-shrink: 1;
}
/* a:hover {
  color: #42b983;
} */
img {
  height: 40px;
}
</style>
