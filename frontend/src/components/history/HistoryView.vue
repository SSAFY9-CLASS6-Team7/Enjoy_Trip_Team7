<template>
  <div class="viewModal">
    <history-image-modal
      v-if="imageDetail != ''"
      @imageOff="imageOff"
      :imagePath="this.imageDetail"
    ></history-image-modal>
    <div class="modal-card" v-if="history.history">
      <div class="img-area">
        <!-- 이미지 없으면 샘플이미지 1개 -->
        <img
          :src="setSampleImageSrc()"
          class="history-img"
          v-if="this.imageList.length === 0"
        />
        <!-- 이미지 하나라도 있으면 스와이프 -->
        <div class="img-swiper" v-if="this.imageList.length !== 0">
          <swiper :options="swiperOption">
            <div class="swiper-button-prev img-prev" slot="button-prev">
              <img src="@/assets/left.svg" width="100px" height="100px" />
            </div>
            <swiper-slide
              class="img-swiper-item"
              v-for="image in imageList"
              :key="image.imageId"
              style="100%"
            >
              <img :src="setImageSrc(image)" class="history-img" @click="bigImage(image)" />
            </swiper-slide>
            <div class="swiper-button-next img-next" slot="button-next">
              <img src="@/assets/right.svg" width="40px" height="40px" style="right: 30px" />
            </div>
            <div class="swiper-pagination" slot="pagination"></div>
          </swiper>
        </div>
      </div>
      <div class="detail-area">
        <h2 class="title">{{ history.history.title }}</h2>
        <div class="date">
          {{ formatDate(history.history.startDay) }} - {{ formatDate(history.history.endDay) }}
        </div>
        <div class="line"></div>
        <div class="content" v-html="formatBoardContent(history.history.content)"></div>
        <div class="btn-area">
          <button class="updateBtn" @click="emitGotoUpdate()">
            <img class="update-btn-vector" src="@/assets/common/modify_icon.svg" />
          </button>
          <button class="deleteBtn" @click="checkAlert()">
            <img class="delete-btn-vector" src="@/assets/common/delete_icon.svg" />
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import HistoryImageModal from './history_components/HistoryImageModal.vue';
import axios from 'axios';
import { Swiper, SwiperSlide } from 'vue-awesome-swiper';
import 'swiper/css/swiper.css'; // swiper CSS 파일 import

export default {
  name: 'HistoryView',
  components: {
    Swiper,
    SwiperSlide,
    HistoryImageModal
  },
  data() {
    return {
      history: Object,
      imageList: [],
      imageUrl: [],
      imageDetail: '',
      //스와이프 관련 설정
      swiperOption: {
        slidesPerView: 1,
        spaceBetween: 10,
        direction: 'horizontal',
        pagination: {
          el: '.swiper-pagination',
          clickable: true,
        },
        navigation: {
          nextEl: '.img-next',
          prevEl: '.img-prev',
        },
      },
    };
  },
  props: {
    historyId: Number,
  },
  methods: {
    //날짜 출력 형식을 변경하기
    formatDate(selectDate) {
      const dateParts = selectDate.split('-');
      const year = dateParts[0].substr(2);
      const month = dateParts[1];
      const day = dateParts[2];
      return `${year}.${month}.${day}`;
    },
    // TODO: 이미지 크게 띄우기
    bigImage(image) {
      this.imageDetail = image.imagePath;
    },
    imageOff() {
      this.imageDetail = '';
    },
    //update 모달로 변경하기
    emitGotoUpdate() {
      this.$emit('emitSetType', 'update');
    },
    //엔터를 <br>로
    formatBoardContent(content) {
      return content.replace(/\n/g, '<br>');
    },
    //삭제 확인용 알림 띄우기
    checkAlert() {
      if (confirm('정말 삭제하시겠습니까?') === true) {
        this.deleteHistory();
      } else return false;
    },
    //삭제 진행하기
    async deleteHistory() {
      await axios.delete(process.env.VUE_APP_MY_BASE_URL+`/history/` + this.historyId);
      this.$emit('emitNeedToUpdate');
      this.$emit('emitModalOff');
    },
    //이미지 소스를 세팅하기
    setImageSrc(image) {
      return process.env.VUE_APP_MY_BASE_URL+'/imagePath/' + image.imagePath;
    },
    //샘플 이미지 소스를 세팅하기
    setSampleImageSrc() {
      let sampleSrc = (this.historyId % 5) + 1;
      return require(`@/assets/sample/sample${sampleSrc}.jpg`);
    },
  },
  async mounted() {
    await axios
      .get(process.env.VUE_APP_MY_BASE_URL+'/history/' + this.historyId)
      .then((response) => (this.history = response.data));
    this.imageList = this.history.images;
  },
};
</script>

<style scoped>
.modal-card {
  position: relative;
  border-radius: 30px;
  background-color: white;
  display: flex;
}

.img-area {
  width: 500px;
  height: 500px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 30px;
  background-color: rgba(0, 0, 0, 0.9);
  background-image: none;
  /*TODO: 어색하면 배경에 같은 이미지 블러해서 깔기*/
}

.img-area img {
  width: 100%;
  height: 100%;
  object-fit: scale-down;
}

.img-swiper,
.swiper-container {
  width: 100%;
  height: 100%;
}

.img-swiper .swiper-wrapper {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: row;
  margin: 20px 0 20px 0;
}

.swiper-button-next::after,
.swiper-button-prev::after {
  display: none;
}

.swiper-pagination >>> .swiper-pagination-bullet {
  opacity: 1;
  border: white solid 1px;
  background-color: white;
  opacity: 0.2;
}
.swiper-pagination >>> .swiper-pagination-bullet-active {
  background-color: white;
  opacity: 1;
}

.detail-area {
  width: 300px;
}

.title,
.date,
.content {
  text-align: left;
  margin: 0 3% 0 10%;
}

.title {
  margin-top: 5%;
}

.date {
  margin-bottom: 3%;
}

.line {
  width: 80%;
  height: 2px;
  background-color: #ebe8e8;
  border: 10px;
  margin: 0% 10%;
}

.content {
  margin-top: 5%;
  height: 330px;
  overflow: auto;
}

.btn-area {
  position: absolute;
  top: 88%;
  left: 85%;
  display: flex;
}

.btn-area button {
  width: 30px;
  height: 30px;
  margin: 5px;
  padding: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 10px;
  border: 0;
}
</style>
