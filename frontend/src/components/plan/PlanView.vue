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
          <div class="date-slide">날짜슬라이드</div>
          <div class="attraction-slide-area">
            <!-- <draggable :attractions="attractions" :group="{ name: 'attractions', pull: 'clone', put: false }">
              <div v-for="(date, i) in planDates" :key="i">
                <plan-attractions-by-date
                  :dateInfo="groupedAttractions[date]"
                  :date="date"
                  :orderOfDate="i"
                ></plan-attractions-by-date>
              </div>
            </draggable> -->
            <!-- <div class="attraction-slide" v-for="(date, i) in planDates" :key="i">
              <plan-attractions-by-date
                :dateInfo="groupedAttractions[date]"
                :date="date"
                :orderOfDate="i"
                @attractionDragged="handleAttractionDragged"
              ></plan-attractions-by-date>
            </div> -->
            <div class="attraction-slide" v-for="(date, i) in planDates" :key="i">
              <div class="plan-attractions-by-date-container">
                <div class="day-info">
                  <h4>day{{ i + 1 }}</h4>
                  | {{ formatDate(date) }}
                </div>
                <div class="attractions-area" v-if="groupedAttractions[date].length > 0">
                  <draggable
                    v-model="attractions"
                    @end="handleAttractionDragged"
                    :group="{ name: 'attractions', pull: 'clone', put: false }"
                  >
                    <div
                      class="attractions"
                      v-for="(attraction, index) in groupedAttractions[date]"
                      :key="index"
                    >
                      <plan-attraction
                        :planAttraction="attraction"
                        :sequence="attraction.sequence"
                      ></plan-attraction>
                    </div>
                  </draggable>
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
import draggable from 'vuedraggable';
// import PlanAttractionsByDate from './plan_components/PlanAttractionsByDate.vue';
import axios from 'axios';

export default {
  name: 'PlanView',
  components: {
    AttractionSearchModal,
    PlanAttraction,
    draggable,
    // PlanAttractionsByDate,
  },
  data() {
    return {
      plan: Object,
      attractions: [],
      groupedAttractions: [],
      groupedAttractionsArray: {},
      isModalOpen: false,
    };
  },
  computed: {
    planDates() {
      return Object.keys(this.groupedAttractions); // groupedAttractions 객체의 키들을 반환
    },
  },
  methods: {
    gotoUpdate() {
      this.$router.push('/plan/update/' + this.planId);
    },
    //날짜 출력 형식을 변경하기
    formatDate(selectDate) {
      const dateParts = selectDate.split('-');
      const year = dateParts[0].substr(2);
      const month = dateParts[1];
      const day = dateParts[2];
      return `${year}.${month}.${day}`;
    },
    handleAttractionDragged(evt) {
      // 드래그 앤 드롭 이벤트 핸들러
      console.log(evt);
      this.setGroupedAttractions();
    },
    //TODO: 드래그 시 시퀀스 변경(자동으로 해주면 업데이트 시 그대로 db에 저장하면 될듯)
    //TODO: 드래그를 두 그룹 사이를 넘나들도록(예제 찾아보기)
    setGroupedAttractions() {
      //   const sortedAttractions = [...this.attractions].sort((a, b) => {
      //     return a.sequence - b.sequence;
      //   });
      //   const groupedAttractions = sortedAttractions.reduce((groups, attraction) => {
      const groupedAttractions = this.attractions.reduce((groups, attraction) => {
        const { planDate } = attraction;

        if (!groups[planDate]) {
          groups[planDate] = [];
        }

        groups[planDate].push(attraction);
        return groups;
      }, {});
      console.dir(groupedAttractions);
      this.groupedAttractions = groupedAttractions;
      this.groupedAttractionsArray = Object.values(this.groupedAttractions);
    },
  },
  async created() {
    this.plan.planId = this.$route.params.planId;
    await axios.get('http://localhost/plan/' + this.plan.planId).then((response) => {
      this.plan = response.data;
    });
    console.dir(this.attractions);
    this.attractions = this.plan.planAttractions;
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
  height: 630px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  align-items: stretch;
  justify-items: stretch;
}

.map-area {
  padding: 0 10px;
  max-height: 630px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: aquamarine;
}

.sequence-area {
  padding: 0 10px;
  max-height: 630px;
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
</style>
