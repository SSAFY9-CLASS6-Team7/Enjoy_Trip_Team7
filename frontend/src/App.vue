<template>
  <div id="app">
    <the-header @openRightMenu="openRightMenu"></the-header>
    <router-view></router-view>
    <right-menu v-if="isRight" @openRightMenu="openRightMenu"></right-menu>
    <the-footer></the-footer>
  </div>
</template>

<script>
import TheHeader from '@/components/TheHeader';
import TheFooter from '@/components/TheFooter';
import RightMenu from '@/components/RightMenu.vue';
// import AppMain from "@/views/AppMain";
// import AppBoard from "@/views/AppBoard";

export default {
  components: {
    TheHeader,
    TheFooter,
    RightMenu,
    // AppMain,
    // AppBoard,
  },
  data() {
    return {
      isRight: false,
    };
  },
  methods: {
    openRightMenu() {
      this.isRight = !this.isRight;
    },
  },
};
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700&display=swap');
#app {
  font-family: 'Noto Sans KR', sans-serif;
  font-weight: 400;
  /* font-family: Avenir, Helvetica, Arial, sans-serif; */
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: black;
}

:root {
  --main-grad1-color: #e1306c;
  --main-grad2-color: #ff699a;
  --main-grad3-color: rgba(252, 175, 69, 0.7);
}

/* nav {
  padding: 30px;
} */

/* nav a {
  font-weight: bold;
  color: #2c3e50;
} */

/* nav a.router-link-exact-active {
  color: #42b983;
} */

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  list-style: none;
}

a {
  text-decoration: none;
  color: black;
}

a:hover {
  cursor: pointer;
}

button:hover {
  cursor: pointer;
}
</style>
