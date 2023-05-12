<template>
  <div class="header">
    <div>
      <router-link to="/user/login">로그인</router-link> |
      <router-link to="/board">게시판</router-link> | <router-link to="/plan">계획</router-link> |
      <router-link to="/history">기록</router-link> |
      <router-link to="/attraction">관광지</router-link> |
    </div>
  </div>
</template>

<script>
export default {
  name: 'TheHeader',
};
</script>

<style scoped>
img {
  width: 150px;
}

.header {
  padding: 30px;
  text-align: center;
  box-shadow: 0px 1px 10px rgba(159, 157, 157, 0.3);
}

a {
  font-weight: bold;
  color: #2c3e50;
  text-decoration: none;
}

a:hover {
  color: #42b983;
}
</style>
