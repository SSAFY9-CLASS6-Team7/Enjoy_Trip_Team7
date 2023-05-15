<template>

<div class="main_img" :options="swiperOption2" style="min-width: 1920px;">
  <swiper class="image-swiper">
    <swiper-slide class="swiper-item" v-for="image in images" :key="image">
      <div> <img :src="image.path" width="100%" height="450px"> </div>
    </swiper-slide>
  </swiper>
  

  <!-- 공지사항 영역 -->
  <div class="announcement">    
    <div class="announcement-title">
      <div class="announcement-title-value">공지사항</div>
    </div>
    <div class="page-navigation"> 《  》 </div>
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

</template>

<script>
import { Swiper, SwiperSlide } from "vue-awesome-swiper";

export default {
  name: "AppUser",
  components:	{
       Swiper,
       SwiperSlide
	},
  data() {
    return {
      images: [
        {
          path: require("../assets/main1.png")
        },
        {
          path: require("../assets/main2.png")
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
      },
      swiperOption2: {
        slidesPerView: 1,
        spaceBetween: 10,
        direction: 'horizontal',
        autoplay: {
          delay: 3000, 
          disableOnInteraction: false 
        },
      }
    };
  }, 
};

</script>

<style>
/* Swiper 추가 */

.swiper-item {
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
  top: -65px;
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

</style>
