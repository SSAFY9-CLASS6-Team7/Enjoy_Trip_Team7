<template>
  <div class="history-item">
    <div class="card" @click="emitViewModalOn">
      <!-- <div class="card" @click="$router.push('view/' + history.history.historyId)"> -->
      <!-- <div class="card"> -->
      <img :src="imageSrc()" class="history-thumnail" />
      <!-- <img src="@/assets/sample/sample1.jpg" class="history-thumnail" /> -->
      <div class="history-info">
        <div class="history-title" v-if="history.history">
          {{ history.history.title }}
        </div>
        <div class="history-img">
          <img src="@/assets/common/image_icon.svg" class="img-icon-vector" />
          <div class="img-num" v-if="history.history">
            {{ history.images.length }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'HistoryImage',
  data() {
    return {
      history: Object,
      imageList: [],
    };
  },
  props: {
    historyId: Number,
  },
  methods: {
    emitViewModalOn() {
      this.$emit('setViewModal', true, this.historyId);
    },
    imageSrc() {
      if (this.imageList.length > 0) {
        return require(this.imageList[0].imagePath);
      } else {
        let sampleSrc = (this.historyId % 5) + 1;
        return require('@/assets/sample/sample' + sampleSrc + '.jpg');
      }
    },
  },
  async created() {
    await axios
      .get('http://43.201.218.74/history/' + this.historyId)
      .then((response) => (this.history = response.data));
    this.imageList = this.history.images;
  },
};
</script>

<style scoped>
.history-item {
  position: relative;
  width: 100%;
  min-width: 340px;
  height: 0;
  overflow: hidden;
  padding-bottom: 100%;
}

.card {
  position: absolute;
  overflow: hidden;
  width: 100%;
  height: 100%;
  /* width: 340px;
  height: 340px; */
  /* min-width: 340px;
  min-height: 340px; */
}

.card:hover .history-info {
  opacity: 1;
}

.history-info {
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  width: 100%;
  background: rgba(0, 0, 0, 0.3);
  color: white;
  display: grid;
  grid-template-columns: 1fr 2fr 1fr;
  grid-template-rows: 1fr 2fr 1fr;
  opacity: 0;
  transition: opacity 0.5s;
  z-index: 1;
}

.history-title {
  grid-column: 1 / span 2;
  grid-row: 1 / span 2;
  margin: 2% 0 0 5%;
  font-size: 30px;
  font-weight: 600;
  text-align: left;
}

.history-img {
  grid-column: 3 / span 1;
  grid-row: 3 / span 1;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  font-weight: 400;
}

.img-icon-vector {
  width: 30%;
  max-width: 20px;
  height: 30%;
  margin: 3px 3px 0 0;
}

.history-thumnail {
  width: 100%;
  height: 100%;
  /*블러처리를 했을 때 밖으로 튀어나오지 않게
    크기를 1.1로 키운 뒤 overflow로 잘라줌*/
  transform: scale(1.1);
  filter: blur(0px);
  transition: filter 0.3s ease-in;
}

.card:hover .history-thumnail {
  filter: blur(2px);
}
</style>
