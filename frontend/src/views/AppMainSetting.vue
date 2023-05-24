<template>
  <div class="main-setting-container">
    <div class="blackbg">
      <button class="save-btn" @click="saveCustomInfo">수정완료</button>
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
        <div class="custom-main" v-for="(item, index) in mainCustom" :key="index">
          <div class="component-container">
            <component class="custom-main-items" :is="mainCustom[index]"></component>
          </div>
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
import { mapGetters } from 'vuex';
import draggable from 'vuedraggable';

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
      mainCustom: [MainPlan, MainHistory, MainCards, MainCommunity],
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
    saveCustomInfo() {
      //TODO: db저장
      this.$router.push('/');
    },
  },
  created() {
    this.userId = this.checkUserInfo.userId;
    //TODO: db에 컬럼 추가(100-200-300-400 형식으로)
    //가져와서 파싱하는 건 프론트에서, 저장할 때도 위 형식으로 변환해서
    // this.mainCustom = this.checkUserInfo.mainOrder;
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
</style>
