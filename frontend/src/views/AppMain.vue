<template>

<div class="main-container">

  <div class="main-img">
    <swiper class="image-swiper" :options="swiperOption2">
      <swiper-slide class="image-swiper-item" v-for="image in images" :key="image">
        <img :src="image.path" height="450px" width="100%">
      </swiper-slide>
    </swiper>

    <!-- 공지사항 영역 -->
    <div class="announcement">    
      <div class="announcement-title">
        <div class="announcement-title-value">공지사항</div>
      </div>
      <div class="page-navigation">
        <div class="swiper-button-prev announcement-prev" slot="button-prev"> ← </div>
        <div class="swiper-button-next announcement-next" slot="button-next"> → </div>
      </div>
      <div class="announcement-content" style="display: inline-block;">
        <swiper class="announcement-swiper" :options="swiperOption1">
          <swiper-slide class="swiper-item" v-for="announcement in announcements" :key="announcement">
            <div class="announcement-value" > {{ announcement.title }} </div>
            <div class="announcement-time">
              <img src="../assets/clock.svg" style="margin-right:10px">
              {{ announcement.time }}
            </div>
          </swiper-slide>
        </swiper>
      </div>
    </div>
  </div>
  
  <main-cards></main-cards>
  <main-community></main-community>

</div>

</template>

<script>
import { Swiper, SwiperSlide } from "vue-awesome-swiper";
import "swiper/css/swiper.css"; // swiper CSS 파일 import
import MainCommunity from '@/components/main/MainCommunity';
import MainCards from '@/components/main/MainCards';

export default {
  name: "AppUser",
  components:	{
       Swiper,
       SwiperSlide,
       MainCommunity,
       MainCards
	},
  data() {
    return {
      images: [
        {
          path: require("../assets/main1.png")
        },
        {
          path: require("../assets/main2.png")
        },
        {
          path: require("../assets/main3.png")
        }
      ],
      announcements: [
        {
          title : "최종 관통 일정 공개",
          time: "2023.05.10"
        },
        {
          title : "전환 되나용?",
          time: "2023.05.11"
        },
        {
          title : "전환 잘 되네용 ^_^",
          time: "2023.05.12"
        },
      ],
      swiperOption1: {
        slidesPerView: 1,
        spaceBetween: 10,
        direction: 'vertical',
        autoplay: {
          delay: 3000, 
          disableOnInteraction: false 
        },
        pagination: {
          el: ".swiper-pagination",
          clickable: true
        },
        navigation: { 
          nextEl: '.announcement-next', 
          prevEl: '.announcement-prev' 
        } 
      },
      swiperOption2: {
        slidesPerView: 1,
        spaceBetween: 10,
        direction: 'horizontal',
        autoplay: {
          delay: 4000, 
          disableOnInteraction: false 
        },
        pagination: {
          el: ".swiper-pagination",
          clickable: false
        },
      },
    };
  }, 
  methods: {},
};

</script>

<style scoped>
.main-img {
  height: 450px;
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

.announcement-title > .announcement-title-value{
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
.announcement .swiper-button-next{
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
