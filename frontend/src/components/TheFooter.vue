<template>
  <div class="footer">
    <div class="menu-set">
      <router-link to="/user/login">About</router-link>
      <router-link to="/board">Features</router-link>
      <router-link to="/plan">Works</router-link>
      <router-link to="/history">Blog</router-link>
      <router-link to="/attraction">Help</router-link>
      <router-link to="/attraction">Contacts</router-link>
    </div>
    <div>&copy; All rights Reserved. SSAFY Inc.</div>
    <div class="bottom-bar"></div>
  </div>
</template>

<script>
export default {
  name: "TheFooter",
};
</script>

<style scoped>
.footer {
  width: 100%;
  text-align: center;
  background-color: #e8e8e8;
  font-size: 13px;
  font-weight: 400;
  color: #333333;
  min-width: 1920px;
}

.menu-set {
  padding: 10px;
}

.bottom-bar {
  bottom: 0px;
  background: linear-gradient(90deg, #e1306c 0%, #ff699a 38.02%, rgba(252, 175, 69, 0.78) 99.48%);
  height: 10px;
  margin: 5px 0px 0px 0px; /*위쪽에 마진을 줘서 메뉴들과 떨어지도록*/
}

a {
  font-weight: 600;
  font-size: 13px;
  padding: 20px 13px;
}

/* a:hover {
  color: #42b983;
} */
</style>
