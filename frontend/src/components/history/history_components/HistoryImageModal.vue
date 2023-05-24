<template>
  <div class="background-blur" @click.self="no">
    <img :src="`${baseUrl}/imagePath/${this.imagePath}`" class="image" />
  </div>
</template>
<script>
export default {
  name: 'HistoryImageModal',
  props: ['imagePath'],
  data() {
    return {
      baseUrl: process.env.VUE_APP_MY_BASE_URL,
    };
  },
  methods: {
    no() {
      this.$emit('imageOff');
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

.background-blur {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 100vw;
  height: 100vh;
  align-self: center;
  background-color: #2e2e2e67;
  border-radius: 20px;
  z-index: 50;
}

.image {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border-radius: 5px;
  z-index: 25;
}
</style>
