<template>
  <div class="footer">
    <div>
      <router-link to="/user/login">About</router-link> |
      <router-link to="/board">Features</router-link> |
      <router-link to="/plan">Works</router-link> | <router-link to="/history">Blog</router-link> |
      <router-link to="/attraction">Help</router-link> |
      <router-link to="/attraction">Contacts</router-link>
    </div>
    <div>&copy; All rights Reserved. SSAFY Inc.</div>
  </div>
</template>

<script>
export default {
  name: 'TheFooter',
};
</script>

<style scoped>
.footer {
  padding: 30px;
  text-align: center;
  background-color: #e8e8e8;
}

a {
  font-weight: bold;
  color: #2c3e50;
  /* text-decoration: none; */
}

a:hover {
  color: #42b983;
}
</style>
