<template>
  <div class="history-item">
    <!-- <img src="@/assets/sample/sample1.jpg"/>
    <img src="C:/enjoytrip_image/images/987bd1d4-79cc-4a9c-b378-259870b517dc.png"/> -->
    <div class="card" @click="emitViewModalOn">
      <!-- <img :src="imageSrc()" class="history-thumnail" /> -->
      <img :src="setThumbnailSrc()" class="history-thumnail" />

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
    needToUpdate: Boolean,
  },
  methods: {
    //기록 조회 모달창 열기
    emitViewModalOn() {
      this.$emit('setViewModal', this.historyId);
    },
    async loadHistory() {
      await axios
        .get(process.env.VUE_APP_MY_BASE_URL+'/history/' + this.historyId)
        .then((response) => (this.history = response.data));
      this.imageList = this.history.images;
      // this.setThumbnailSrc();
    },
    //이미지 소스 가져오기
    setThumbnailSrc() {
      if (this.imageList.length > 0) {
        return process.env.VUE_APP_MY_BASE_URL+'/imagePath/' + this.imageList[0].imagePath;
      } else {
        let sampleSrc = (this.historyId % 5) + 1;
        return require(`@/assets/sample/sample${sampleSrc}.jpg`);
      }
    },
  },
  watch: {
    needToUpdate: function () {
      if (this.needToUpdate === true) {
        this.loadHistory();
      }
    },
  },
  created() {
    this.loadHistory();
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
