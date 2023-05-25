<template>
  <div class="main-setting-container">
    <div class="blackbg">
      <button class="save-btn" @click="saveCustomInfo">수정완료</button>
      <div class="text-msg">각 항목들을 드래그해 순서를 변경할 수 있습니다.</div>
    </div>
    <div class="main-img">
      <swiper class="image-swiper" :options="swiperOption2">
        <swiper-slide class="image-swiper-item" v-for="image in images" :key="image">
          <img :src="image.path" height="450px" width="100%" />
        </swiper-slide>
      </swiper>

      <!-- 공지사항 영역 -->
      <div class="announcement">
        <div class="announcement-title">
          <div class="announcement-title-value">공지사항</div>
        </div>
        <div class="page-navigation">
          <div class="swiper-button-prev announcement-prev" slot="button-prev">←</div>
          <div class="swiper-button-next announcement-next" slot="button-next">→</div>
        </div>
        <div class="announcement-content" style="display: inline-block">
          <swiper class="announcement-swiper" :options="swiperOption1">
            <swiper-slide
              class="swiper-item"
              v-for="announcement in announcements"
              :key="announcement"
            >
              <div class="announcement-value">{{ announcement.title }}</div>
              <div class="announcement-time">
                <img src="../assets/clock.svg" style="margin-right: 10px" />
                {{ announcement.time }}
              </div>
            </swiper-slide>
          </swiper>
        </div>
      </div>
    </div>
    <div class="custom-main-container">
      <draggable v-model="mainCustom">
        <div
          class="custom-main"
          v-for="(item, index) in mainCustom"
          :key="index"
          v-bind:class="[
            {
              'main-history': isMainHistory(mainCustom[index]),
              'main-plan': isMainPlan(mainCustom[index]),
              'last-content': isLastCotent(index),
            },
          ]"
        >
          <component class="custom-main-items" :is="mainCustom[index]"></component>
        </div>
      </draggable>
    </div>
  </div>
</template>

<script>
import { Swiper, SwiperSlide } from 'vue-awesome-swiper';
import 'swiper/css/swiper.css'; // swiper CSS 파일 import
import MainCommunity from '@/components/main/MainCommunity';
import MainCards from '@/components/main/MainCards';
import MainHistory from '@/components/main/MainHistory.vue';
import MainPlan from '@/components/main/MainPlan.vue';
import { mapActions, mapGetters } from 'vuex';
import draggable from 'vuedraggable';
import axios from 'axios';

export default {
  name: 'AppUser',
  components: {
    draggable,
    Swiper,
    SwiperSlide,
    MainCommunity,
    MainCards,
    MainHistory,
    MainPlan,
  },
  data() {
    return {
      userid: '',
      //100: 게시판 200:기록 300:관광지 400:계획
      mainCustom: [],
      images: [
        {
          path: require('../assets/main1.png'),
        },
        {
          path: require('../assets/main2.png'),
        },
        {
          path: require('../assets/main3.png'),
        },
      ],
      announcements: [
        {
          title: '최종 관통 일정 공개',
          time: '2023.05.10',
        },
        {
          title: '전환 되나용?',
          time: '2023.05.11',
        },
        {
          title: '전환 잘 되네용 ^_^',
          time: '2023.05.12',
        },
      ],
      swiperOption1: {
        slidesPerView: 1,
        spaceBetween: 10,
        direction: 'vertical',
        autoplay: {
          delay: 3000,
          disableOnInteraction: false,
        },
        pagination: {
          el: '.swiper-pagination',
          clickable: true,
        },
        navigation: {
          nextEl: '.announcement-next',
          prevEl: '.announcement-prev',
        },
      },
      swiperOption2: {
        slidesPerView: 1,
        spaceBetween: 10,
        direction: 'horizontal',
        autoplay: {
          delay: 4000,
          disableOnInteraction: false,
        },
        pagination: {
          el: '.swiper-pagination',
          clickable: false,
        },
      },
    };
  },
  computed: {
    ...mapGetters('userStore', ['checkToken', 'checkUserInfo']),
  },
  methods: {
    ...mapActions('userStore', ['userConfirm']),
    async saveCustomInfo() {
      let tempCustomOrder = [];

      for (var component of this.mainCustom) {
        if (component === MainCommunity) tempCustomOrder.push(100);
        else if (component === MainHistory) tempCustomOrder.push(200);
        else if (component === MainCards) tempCustomOrder.push(300);
        else if (component === MainPlan) tempCustomOrder.push(400);
      }
      let f = new FormData();
      let newMainpageCustom = tempCustomOrder.join('-');

      f.append('mainpageCustom', newMainpageCustom);
      await axios.put(process.env.VUE_APP_MY_BASE_URL + '/user/' + this.checkUserInfo.userId, f);

      let user = {
        userId: this.checkUserInfo.userId,
        password: this.checkUserInfo.password,
        mainpageCustom: newMainpageCustom,
      };
      //vuex에 반영하는 부분
      await this.userConfirm(user);
      //메인화면으로 이동
      this.$router.push('/');
    },
    isMainHistory(component) {
      return component === MainHistory ? true : false;
    },
    isMainPlan(component) {
      return component === MainPlan ? true : false;
    },
    isLastCotent(index) {
      return index === this.mainCustom.length - 1 ? true : false;
    },
  },
  created() {
    this.userId = this.checkUserInfo.userId;
    //100: 게시판 200:기록 300:관광지 400:계획(default: 300-100-200-400)
    var tempArr = this.checkUserInfo.mainpageCustom.split('-');
    for (var tempNumber of tempArr) {
      if (tempNumber === '100') this.mainCustom.push(MainCommunity);
      else if (tempNumber === '200') this.mainCustom.push(MainHistory);
      else if (tempNumber === '300') this.mainCustom.push(MainCards);
      else if (tempNumber === '400') this.mainCustom.push(MainPlan);
    }
  },
};
</script>

<style scoped>
.main-img {
  height: 450px;
}

.blackbg {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 550px;
  background-color: #000000c1;
  z-index: 2;
}

.save-btn {
  position: relative;
  top: 40%;
  color: white;
  font-size: 30px;
  font-weight: 700;
  background: rgba(255, 255, 255, 0.111);
  border: 3px white solid;
  padding: 30px 40px;
  border-radius: 30px;
}
.save-btn:hover {
  background: rgba(255, 255, 255, 0.274);
}

.text-msg {
  position: relative;
  top: 45%;
  color: white;
  font-size: 20px;
  font-weight: 400;
}

/* Swiper 추가 */
.announcement-swiper .swiper-item {
  display: flex;
  align-items: center;

  color: #e2e2e2;
  font-size: 22px;
  font-weight: 400;
}

.announcement-swiper {
  height: 60px;
}

.announcement-content {
  flex-grow: 10;
}

.announcement-content .announcement-value {
  text-align: left;
  flex-grow: 8;
}

.announcement-content .announcement-time {
  display: flex;
  align-items: center;
  font-size: 20px;
  flex-grow: 5;
}

/* 공지 영역 */
.announcement {
  overflow: hidden;
  position: relative;
  height: 60px;
  top: -63px;
  width: 100%;
  background: #1d1d1d7a;
  z-index: 1;
  display: flex;
  min-width: 1920px;
}

.announcement-title {
  display: flex;
  justify-content: end;
  align-items: center;
  flex-grow: 3;
}

.announcement-title > .announcement-title-value {
  font-size: 24px;
  font-weight: 300;
  color: #e2e2e2;
}

.page-navigation {
  flex-grow: 1;
  color: #e2e2e2;
  display: flex;
  justify-content: center;
  align-items: center;
}

.image-swiper .swiper-wrapper {
  display: flex;
  flex-direction: row;
}

.announcement .swiper-button-prev {
  position: absolute;
  left: 20px;
  font-size: 20px;
  color: #e2e2e2;
}
.announcement .swiper-button-next {
  position: absolute;
  left: 60px;
  font-size: 20px;
  color: #e2e2e2;
}

.announcement .page-navigation {
  position: relative;
}

.swiper-button-next::after,
.swiper-button-prev::after {
  display: none;
}

.normal-main-container > *,
.custom-main-container > * {
  padding: 50px 0;
}

.main-history >>> *,
.main-plan >>> * {
  margin-bottom: 0px !important;
  min-height: 0px;
}

.last-content {
  margin-bottom: 100px;
}

.main-history >>> h1,
.main-plan >>> h1 {
  margin: 30px 0;
}

.custom-main-items {
  pointer-events: none;
}

.custom-main:hover {
  cursor: pointer;
}
</style>
