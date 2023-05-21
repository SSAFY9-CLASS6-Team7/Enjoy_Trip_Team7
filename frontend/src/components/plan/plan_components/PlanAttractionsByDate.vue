<template>
  <!-- 계획 세부 조회의 날짜별 항목 -->
  <!-- !!!현재 안씀!!! -->
  <div class="plan-attractions-by-date-container">
    <div class="day-info">
      <h4>day{{ orderOfDate + 1 }}</h4>
      | {{ formatDate(date) }}
    </div>
    <div class="attractions-area" v-if="dateInfo.length > 0">
      <!-- <div class="attractions" v-for="attraction in dateInfo" :key="attraction.sequence">
        <plan-attraction :planAttraction="attraction"></plan-attraction>
      </div> -->
      <draggable v-model="localDateInfo" @end="handleAttractionDragged">
        <div class="attractions" v-for="attraction in localDateInfo" :key="attraction.sequence">
          <plan-attraction :planAttraction="attraction"></plan-attraction>
        </div>
      </draggable>
    </div>
  </div>
</template>
<script>
import PlanAttraction from './PlanAttraction.vue';
import draggable from 'vuedraggable';

export default {
  name: 'PlanAttractionsByDate',
  components: {
    PlanAttraction,
    draggable,
  },
  props: ['dateInfo', 'date', 'orderOfDate'],
  data() {
    return {
      localDateInfo: [],
    };
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
    handleAttractionDragged() {
      // 드래그 앤 드롭 이벤트 핸들러
      this.$emit('attractionDragged', this.localDateInfo);
    },
  },
  mounted() {
    this.localDateInfo = [...this.dateInfo]; // dateInfo prop을 로컬 데이터에 복사
  },
};
</script>
<style scoped>
.plan-attractions-by-date-container {
  padding: 0 10px;
}
.day-info {
  display: flex;
}
</style>
