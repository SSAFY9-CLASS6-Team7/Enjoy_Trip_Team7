<template>
  <div class="plan-view-container">
    <div class="plan-modal" v-if="isModalOpen">
      <attraction-search-modal
        @setModal="setModal"
        @addAttraction="addAttraction"
      ></attraction-search-modal>
    </div>
    <div class="left-aside"></div>
    <div class="main-area">
      <div class="inner-header">
        <h2>계획</h2>
        <button class="update-btn" @click="gotoUpdate">
          <img class="update-btn-vector" src="@/assets/plan_icon/modify_icon_white.svg" />
          수정하기
        </button>
      </div>
      <div class="line"></div>
      <div class="main">
        <div class="map-area">Map</div>
        <div class="sequence-area">
          <div class="date-slide">
            <div class="date-swiper" v-if="this.planDates.length !== 0">
              <swiper :options="swiperOption">
                <div class="swiper-button-prev img-prev" slot="button-prev">
                  <img src="@/assets/left.svg" width="40px" height="40px" />
                </div>
                <swiper-slide class="date-swiper-item" v-for="(date, i) in planDates" :key="i">
                  day{{ i + 1 }}
                </swiper-slide>
                <div class="swiper-button-next img-next" slot="button-next">
                  <img src="@/assets/right.svg" width="40px" height="40px" style="right: 30px" />
                </div>
              </swiper>
            </div>
          </div>
          <div class="attraction-slide-area">
            <div class="attraction-slide" v-for="(date, i) in planDates" :key="i">
              <div class="plan-attractions-by-date-container">
                <div class="day-info">
                  <h4>day{{ i + 1 }} &nbsp;</h4>
                  | &nbsp; {{ formatDate(date) }}
                </div>
                <div class="attractions-area" v-if="groupedAttractions[date]">
                  <div
                    class="attractions"
                    v-for="(attraction, index) in groupedAttractions[date]"
                    :key="attraction.planAttractionId"
                  >
                    <plan-attraction :planAttraction="attraction" :index="index"></plan-attraction>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="right-aside"></div>
  </div>
</template>
<script>
import AttractionSearchModal from '../AttractionSearchModal.vue';
import PlanAttraction from './plan_components/PlanAttraction.vue';
import axios from 'axios';
import { Swiper, SwiperSlide } from 'vue-awesome-swiper';
import 'swiper/css/swiper.css';

export default {
  name: 'PlanView',
  components: {
    AttractionSearchModal,
    PlanAttraction,
    Swiper,
    SwiperSlide,
  },
  data() {
    return {
      plan: Object,
      groupedAttractions: Object, //key로 접근 가능
      isModalOpen: false,
      //스와이프 관련 설정
      swiperOption: {
        slidesPerView: 1,
        spaceBetween: 20,
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
  computed: {
    //여행 기간 안의 날짜들 배열
    planDates() {
      const dateArray = [];
      const start = new Date(this.plan.startDay);
      const end = new Date(this.plan.endDay);

      // 날짜를 하루씩 증가시면서 추가
      for (let date = start; date <= end; date.setDate(date.getDate() + 1)) {
        const dateString = date.toISOString().split('T')[0];
        dateArray.push(dateString);
      }
      console.log(dateArray);
      return dateArray;
    },
  },
  methods: {
    gotoUpdate() {
      this.$router.push('/plan/update/' + this.plan.planId);
    },
    //날짜 출력 형식을 변경하기
    formatDate(selectDate) {
      const dateParts = selectDate.split('-');
      const year = dateParts[0].substr(2);
      const month = dateParts[1];
      const day = dateParts[2];
      return `${year}.${month}.${day}`;
    },
    setGroupedAttractions() {
      this.groupedAttractions = this.plan.planAttractions.reduce((groups, attraction) => {
        const { planDate } = attraction;

        if (!groups[planDate]) {
          groups[planDate] = [];
        }

        groups[planDate].push(attraction);
        return groups;
      }, {});
    },
  },
  async created() {
    this.plan.planId = this.$route.params.planId;
    await axios.get('http://localhost/plan/' + this.plan.planId).then((response) => {
      this.plan = response.data;
    });
    this.setGroupedAttractions();
  },
};
</script>
<style scoped>
.plan-view-container {
  position: relative;
  width: 100%;
  height: 81.5vh;
  display: grid;
  grid-template-columns: 1fr 5fr 1fr;
  grid-template-areas: 'left  main  right';
  align-items: stretch;
  justify-items: stretch;
}

.left-aside {
  grid-area: left;
}

.right-aside {
  grid-area: right;
}

.main-area {
  grid-area: main;
}

.inner-header {
  align-self: start;
  width: 100%;
  height: 70px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.update-btn {
  padding: 10px 10px;
  margin-top: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(95.36deg, #e1306c 2.32%, #ff699a 68.42%, #fcaf45 104.98%);
  background-blend-mode: darken;
  border: none;
  color: white;
  border-radius: 5px;
  font-size: 12px;
  font-weight: 600;
}

.update-btn > * {
  margin: 0 3px;
}

.line {
  width: 100%;
  height: 3px;
  background-color: #ebe8e8;
}

.main {
  margin-top: 10px;
  height: 90%;
  display: grid;
  grid-template-columns: 1fr 1fr;
  align-items: stretch;
  justify-items: stretch;
}

.map-area {
  padding: 0 10px;
  max-height: 730px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: aquamarine;
}

.sequence-area {
  padding: 0 10px;
  max-height: 730px;
}

.date-slide {
  width: 100%;
  height: 5%;
  margin-bottom: 10px;
  background-color: #fcaf45;
}
.attraction-slide-area {
  height: 90%;
  background-color: #f6f6f6;
  border-radius: 10px;
  overflow: auto;
}

.attraction-slide {
  width: 90%;
  margin: 15px 5%;
  /* background-color: #e1306c; */
}

.plan-attractions-by-date-container {
  padding: 0 10px;
}
.day-info {
  display: flex;
}

/* swiper */
.date-swiper,
.swiper-container {
  width: 100%;
  height: 100%;
}

.date-swiper .swiper-wrapper {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: row;
  margin: 20px 0 20px 0;
  overflow: auto;
  white-space: nowrap;
}

.swiper-button-next::after,
.swiper-button-prev::after {
  display: none;
}

.date-swiper-item {
  width: 30px !important;
}
</style>
