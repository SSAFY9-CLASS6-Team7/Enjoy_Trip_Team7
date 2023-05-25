<template>
  <div class="main-container">
    <div class="main-img">
      <swiper class="image-swiper" :options="swiperOption2">
        <swiper-slide class="image-swiper-item" v-for="image in images" :key="image">
          <img :src="image.path" height="450px" width="100%" />
        </swiper-slide>
      </swiper>

      <!-- 공지사항 영역 -->
      <div class="announcement">
        <div class="announcement-title">
          <div class="announcement-title-value" @click="moveAnnouncementList">공지사항</div>
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
              <div class="announcement-value" @click="moveAnnouncementView(announcement.boardId)">
                {{ announcement.title }}
              </div>
              <div class="announcement-time" @click="moveAnnouncementView(announcement.boardId)">
                <img src="../assets/clock.svg" style="margin-right: 10px" />
                {{ formatDate(announcement.createTime) }}
              </div>
            </swiper-slide>
          </swiper>
        </div>
      </div>
    </div>
    <div class="custom-main-container" v-if="this.checkToken">
      <div
        class="custom-main"
        v-for="(item, index) in mainCustom"
        :key="index"
        v-bind:class="[
          {
            'main-history': isMainHistory(mainCustom[index]),
            'main-plan': isMainPlan(mainCustom[index]),
          },
        ]"
      >
        <component class="custom-main-items" :is="mainCustom[index]"></component>
      </div>
    </div>
    <div class="normal-main-container" v-if="!this.checkToken">
      <div class="main-cards">
        <main-cards></main-cards>
      </div>
      <div class="main-community">
        <main-community></main-community>
      </div>
      <div class="main-history">
        <main-history></main-history>
      </div>
      <div class="main-plan">
        <main-plan></main-plan>
      </div>
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
import axios from 'axios';

export default {
  name: 'AppUser',
  components: {
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
      searchKeyword: '',
      announcements: [],
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
    formatDate(date) {
      return date.split(' ')[0];
    },
    moveAnnouncementView(announcementId) {
      this.$router.push('/announcement/view/' + announcementId);
    },
    moveAnnouncementList() {
      this.$router.push('/announcement');
    },
    isMainHistory(component) {
      return component === MainHistory ? true : false;
    },
    isMainPlan(component) {
      return component === MainPlan ? true : false;
    },
  },
  async created() {
    if (this.checkUserInfo !== null) {
      this.userId = this.checkUserInfo.userId;
    }
    await axios
      .get(process.env.VUE_APP_MY_BASE_URL + `/announcement?pageNo=1&keyword=${this.searchKeyword}`)
      .then((response) => {
        console.dir(response.data.boards);
        this.announcements = response.data.boards;
      });
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

.announcement-title:hover {
  cursor: pointer;
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

.swiper-item:hover {
  cursor: pointer;
}
.normal-main-container > * {
  padding: 50px 0;
}

.main-history >>> *,
.main-plan >>> * {
  margin-bottom: 0px !important;
  min-height: 0px;
}

.main-plan {
  margin-bottom: 100px;
}

.main-history >>> h1,
.main-plan >>> h1 {
  margin: 30px 0;
}
</style>
