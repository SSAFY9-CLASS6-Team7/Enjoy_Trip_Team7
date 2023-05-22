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

  <div class="cards">
    <div class="cards-title">
      여 행 지 
    </div>
    <div class="attraction-swiper">  
      <swiper :options="swiperOption3">
      <div class="swiper-button-prev card-prev" slot="button-prev"> 
        <img src="../assets/left.svg" width="40px" height="40px">
      </div>
        <swiper-slide class="attraction-swiper-item" v-for="city in cities" :key="city">
          <div class="card">
            <div class="card-title"> {{city.title }} </div>
            <img :src="city.path" style="border-radius: 10px;" />
          </div>
        </swiper-slide>
      <div class="swiper-button-next card-next" slot="button-next">
        <img src="../assets/right.svg" width="40px" height="40px" style="right:30px">
      </div>
      </swiper>
    </div>
  </div>


  <div class="plan-recommend">
    <div class="plan-recommend-title">
      추천 여행 경로
    </div>
    <div class="plan-recommend-contents">

    </div>
  </div>

</div>


</template>

<script>
import { Swiper, SwiperSlide } from "vue-awesome-swiper";
import "swiper/css/swiper.css"; // swiper CSS 파일 import

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
      cities: [
        {
          title: "서울",
          path: require("../assets/main_attractions/seoul.png")
        },
        {
          title: "대전",
          path: require("../assets/main_attractions/daejeon.png")
        },
        {
          title: "대구",
          path: require("../assets/main_attractions/daegu.png")
        },
        {
          title: "인천",
          path: require("../assets/main_attractions/incheon.png")
        },
        {
          title: "포항",
          path: require("../assets/main_attractions/pohang.png")
        },
        {
          title: "부산",
          path: require("../assets/main_attractions/busan.png")
        },
        {
          title: "부산",
          path: require("../assets/main_attractions/busan.png")
        },
        {
          title: "부산",
          path: require("../assets/main_attractions/busan.png")
        },
        {
          title: "부산",
          path: require("../assets/main_attractions/busan.png")
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
      swiperOption3: {
        slidesPerView: 6,
        spaceBetween: 10,
        direction: 'horizontal',
        pagination: {
          el: ".swiper-pagination",
          clickable: true
        },
        navigation: { 
        nextEl: '.card-next', 
        prevEl: '.card-prev' 
        } 
      }
    };
  }, 
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

.cards {
  padding: 20px 10px 0 50px;
  margin: 0 100px 0px 100px;
}

.cards-title {
  padding: 50px 0 30px 20px;
  text-align: left;
  font-size: 28px;
  font-weight: 600;
}

.card {
  position: relative;
  width: 220px;
  height: 250px;
  filter: drop-shadow(4px 4px 10px rgba(0, 0, 0, 0.53));
  border-radius: 10px;
  display: inline-block;
  margin: 0 10px 0 0;
  overflow: visible; 
}

.attraction-swiper .swiper-wrapper{
  margin: 20px 0 20px 0;
}

.card-title {
  position: absolute;
  left: 28px;
  top: 17px;
  color: #e2e2e2;
  font-size: 22px;
  font-weight: 600;
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


.plan-recommend {
  padding: 20px 50px 0 50px;
  margin: 0 100px 50px 100px;
}

.plan-recommend .plan-recommend-title {
  padding: 20px 0 30px 20px;
  text-align: left;
  font-size: 28px;
  font-weight: 600;
}

.plan-recommend-contents {
  margin: 0 20px 0 20px;
  border: 1px solid #333333;
  width: 100%;
  height: 450px;
  border-radius: 10px;
  background: #d4d4d4;
}

</style>
