<template>
    <div class="background-blur" @click="no">
        <img :src="`${baseUrl}/imagePath/${this.imagePath}`" class="image">
    </div>
</template>
<script>
export default {
  name: 'ImageModal',
  props: ['imagePath'],
  data() {
    return {
        baseUrl: process.env.VUE_APP_MY_BASE_URL,
    }
  },
  methods: {
    no(){
        this.$emit('imageOff');
    },
  }
};

</script>
<style scoped>
@font-face {
     font-family: 'S-CoreDream-3Light';
     src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-3Light.woff') format('woff');
     font-weight: normal;
     font-style: normal;
}

.background-blur {
    position: absolute;
    width: 100vw;
    height: 89.3vh;
    background-color: #2e2e2e67;
    z-index: 9;
}

.image {
    position: absolute;
    top: 20%;
    left: 50%;
    transform: translate(-50%, -20%);
    width: 400px;
    height: 600px;
    border-radius: 5px;
}

</style>
