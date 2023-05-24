<template>
  <div class="plan-update-container">
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
        <button class="update-btn" @click="updatePlanAttractions">
          <img class="update-btn-vector" src="@/assets/common/check_icon_white.svg" />
          수정완료
        </button>
      </div>
      <div class="line"></div>
      <div class="main">
        <div class="map-area">Map</div>
        <div class="sequence-area">
          <div class="date-slide">
            <div class="date-swiper" v-if="this.planDates.length !== 0">
              <swiper :options="swiperOption" @transitionEnd="updateFocusInfo">
                <div class="swiper-button-prev img-prev" slot="button-prev">
                  <img src="@/assets/left.svg" width="40px" height="40px" />
                </div>
                <swiper-slide
                  class="date-swiper-item"
                  v-for="(date, i) in planDates"
                  :class="{ 'swiper-slide-active': focused === i - 1 }"
                  :key="i"
                >
                  <div class="date">day{{ i + 1 }}</div>
                </swiper-slide>
                <div class="swiper-button-next img-next" slot="button-next">
                  <img src="@/assets/right.svg" width="40px" height="40px" style="right: 30px" />
                </div>
              </swiper>
            </div>
          </div>
          <div class="attraction-slide-area">
            <div class="attraction-slide" v-for="(date, i) in planDates" :key="i">
              <div class="plan-attractions-by-date-container" ref="attractionsPerDate">
                <div class="day-info">
                  <h4>day{{ i + 1 }} &nbsp;</h4>
                  | &nbsp; {{ formatDate(date) }}
                </div>
                <div class="attractions-area" v-if="groupedAttractions[date]">
                  <draggable
                    v-model="groupedAttractionsArray[i]"
                    :options="{ group: 'ATTRACTIONS' }"
                  >
                    <div
                      class="attractions"
                      v-for="(attraction, index) in groupedAttractionsArray[i]"
                      :key="attraction.planAttractionId"
                    >
                      <plan-attraction
                        :planAttraction="attraction"
                        :date="date"
                        :index="index"
                        :type="'update'"
                        @deletePlanAttraction="deletePlanAttraction"
                      ></plan-attraction>
                    </div>
                  </draggable>
                </div>
              </div>
            </div>
          </div>
          <button class="add-attraction-btn" @click="setModal(true)">
            <img src="@/assets/plan_icon/circle_add.svg" />
          </button>
        </div>
      </div>
    </div>
    <div class="right-aside"></div>
  </div>
</template>
<script>
import AttractionSearchModal from '../AttractionSearchModal.vue';
import PlanAttraction from './plan_components/PlanAttraction.vue';
import draggable from 'vuedraggable';
import axios from 'axios';
import { Swiper, SwiperSlide } from 'vue-awesome-swiper';
import 'swiper/css/swiper.css';

export default {
  name: 'PlanUpdate',
  components: {
    AttractionSearchModal,
    PlanAttraction,
    draggable,
    Swiper,
    SwiperSlide,
  },
  data() {
    return {
      plan: Object,
      groupedAttractions: Object, //key로 접근 가능
      groupedAttractionsArray: [], //interable하게 배열로 변환, 변경사항은 여기에 바로 반영
      focused: 1,
      isModalOpen: false,
      //스와이프 관련 설정
      swiperOption: {
        slidesPerView: 8,
        spaceBetween: 30,
        direction: 'horizontal',
        pagination: {
          el: '.swiper-pagination',
          clickable: true,
        },
        navigation: {
          nextEl: '.img-next',
          prevEl: '.img-prev',
        },
        slideToClickedSlide: true,
        slidesOffsetBefore: 10,
        slidesOffsetAfter: 700,
      },
    };
  },
  computed: {
    //여행 기간 안의 날짜들 배열
    planDates() {
      const dateArray = [];
      const start = new Date(this.plan.startDay);
      const end = new Date(this.plan.endDay);

      // 날짜를 하루씩 증가시키면서 추가
      for (let date = start; date <= end; date.setDate(date.getDate() + 1)) {
        const dateString = date.toISOString().split('T')[0];
        dateArray.push(dateString);
      }
      return dateArray;
    },
  },
  watch: {
    focused() {
      this.$nextTick(() => {
        this.scrollToFocused();
      });
    },
  },
  methods: {
    setModal(value) {
      this.isModalOpen = value;
    },
    //날짜 출력 형식을 변경하기
    formatDate(selectDate) {
      const dateParts = selectDate.split('-');
      const year = dateParts[0].substr(2);
      const month = dateParts[1];
      const day = dateParts[2];
      return `${year}.${month}.${day}`;
    },
    setGroupedAttractionsArray() {
      this.groupedAttractions = this.plan.planAttractions.reduce((groups, attraction) => {
        const { planDate } = attraction;

        if (!groups[planDate]) {
          groups[planDate] = [];
        }

        groups[planDate].push(attraction);
        return groups;
      }, {});
      for (var index in this.planDates) {
        if (!this.groupedAttractions[this.planDates[index]]) {
          this.groupedAttractions[this.planDates[index]] = [];
        }
      }

      this.groupedAttractionsArray = Object.values(this.groupedAttractions);
    },
    updateFocusInfo() {
      this.$nextTick(() => {
        const activeElement = document.querySelector('.swiper-slide-active');
        const date = parseInt(activeElement.childNodes[0].innerText.replace('day', ''));
        this.focused = date;
      });
    },
    scrollToFocused() {
      this.$refs.attractionsPerDate[this.focused - 1].scrollIntoView({ behavior: 'smooth' });
    },
    addAttraction(attraction) {
      this.groupedAttractionsArray[this.focused - 1].push(attraction);
    },
    async updatePlanAttractions() {
      let newAttractionList = [];
      var dayOrder = 0;
      var sequence = 1;

      for (var dateAttractions of this.groupedAttractionsArray) {
        var date = this.planDates[dayOrder];
        for (var attraction of dateAttractions) {
          var newAttraction = {};
          newAttraction.planAttractionId = attraction.planAttractionId;
          newAttraction.attractionId = attraction.attractionId;
          newAttraction.sequence = sequence;
          newAttraction.planDate = date;
          newAttraction.planId = this.plan.planId;
          newAttractionList.push(newAttraction);
          sequence += 1;
        }
        dayOrder += 1;
      }

      await axios.post(
        process.env.VUE_APP_MY_BASE_URL+'/plan/' + this.plan.planId + '/attraction',
        newAttractionList
      );
      this.$router.push('/plan/view/' + this.plan.planId);
    },
    //날짜와 순서를 받아서 요소 삭제
    deletePlanAttraction(date, index) {
      var dateIndex = this.planDates.findIndex((element) => element === date);
      this.groupedAttractionsArray[dateIndex].splice(index);
    },
  },
  async created() {
    this.plan.planId = this.$route.params.planId;
    await axios.get(process.env.VUE_APP_MY_BASE_URL+'/plan/' + this.plan.planId).then((response) => {
      this.plan = response.data;
    });
    this.setGroupedAttractionsArray();
  },
};
</script>
<style scoped>
.plan-update-container {
  position: relative;
  width: 100%;
  /* height: 81.5vh; */
  padding-bottom: 30px;
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
  height: 35px;
  display: flex;
  justify-content: center;
  margin: 15px 0;
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
  max-width: 640px;
}

.date-swiper .swiper-wrapper {
  width: 100%;
  height: 0% !important;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  flex-direction: row;
  margin: 20px 0 20px 0;
  overflow: hidden;
  white-space: nowrap;
}

.swiper-button-next::after,
.swiper-button-prev::after {
  display: none;
}

.swiper-button-prev {
  left: 0;
}

.swiper-button-next {
  right: 0;
}

.date-swiper-item {
  width: 30px !important;
  height: auto;
  padding: 0 33px;
  margin: 0;
  color: #b7b7b7;
  font-size: 18px;
}

.swiper-slide-active {
  font-weight: 700;
  color: black;
}

.date {
  cursor: pointer;
}

/* button */
.add-attraction-btn {
  position: absolute;
  left: 66%;
  top: 93%;
  min-width: 100px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(
    109.72deg,
    #e1306c 10.05%,
    #ff699a 52.97%,
    rgba(252, 175, 69, 0.7) 95%
  );
  margin: 5px 0 0 5px;
  border-radius: 40px;
  border: none;
}
</style>
