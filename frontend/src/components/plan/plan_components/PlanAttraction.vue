<template>
  <!-- 계획 세부 조회 각각의 관광지들 -->
  <div class="plan-attraction-container" v-if="attractionId">
    <div class="number" v-bind:style="{ backgroundColor: categoryColorSet }">
      {{ index + 1 }}
    </div>
    <div class="att-info-area">
      <div class="attraction-title">{{ attraction.title }}</div>
      <Div class="type-and-btn" v-if="contentTypeSrc">
        <img :src="contentTypeSrc" />
        <button class="delete-btn" v-if="type === 'update'" @click="emitDeletePlanAttraction">
          <img src="@/assets/plan_icon/cross_gray.svg" alt="" class="cross-vector" />
        </button>
      </Div>
    </div>
  </div>
</template>
<script>
import axios from 'axios';

export default {
  name: 'PlanAttraction',
  props: ['planAttraction', 'date', 'index', 'type'],
  data() {
    return {
      attraction: Object,
      contentTypeSrc: '',
      categoryColorSet: '',
    };
  },
  computed: {
    attractionId() {
      return this.planAttraction.attractionId;
    },
  },
  watch: {
    attractionId: async function () {
      await this.loadData();
      this.setContentTypeSrc();
    },
  },
  methods: {
    //관광지 타입 설정하기
    setContentTypeSrc() {
      if (this.attraction.code === 12) {
        this.categoryColorSet = '#6ECBEC';
        this.contentTypeSrc = require('@/assets/content_type_icon/tour.svg');
      } else if (this.attraction.code === 15) {
        this.contentTypeSrc = require('@/assets/content_type_icon/event.svg');
        this.categoryColorSet = '#EAAB83';
      } else if (this.attraction.code === 32) {
        this.contentTypeSrc = require('@/assets/content_type_icon/stay.svg');
        this.categoryColorSet = '#F17E8F';
      } else if (this.attraction.code === 39) {
        this.contentTypeSrc = require('@/assets/content_type_icon/restaurant.svg');
        this.categoryColorSet = '#7CC68F';
      } else {
        this.categoryColorSet = '#999999';
        this.contentTypeSrc = require('@/assets/content_type_icon/etc.svg');
      }
    },
    emitDeletePlanAttraction() {
      this.$emit('deletePlanAttraction', this.date, this.index);
    },
    async loadData() {
      await axios
        .get('http://localhost/attraction/' + this.planAttraction.attractionId)
        .then((res) => (this.attraction = res.data));
    },
  },
  async created() {
    if (typeof this.planAttraction.attractionId !== 'undefined') {
      await this.loadData();
    }
    this.setContentTypeSrc();
  },
};
</script>
<style scoped>
.plan-attraction-container {
  display: flex;
  align-items: center;
  margin: 10px 0;
}

.number {
  min-width: 45px;
  height: 45px;
  line-height: 45px;
  text-align: center;
  text-justify: center;
  border-radius: 30px;
  color: white;
  font-weight: 700;
}

.att-info-area {
  width: 90%;
  min-width: 280px;
  height: 60px;
  margin: 0 10px;
  padding: 10px;
  border-radius: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: white;
  font-weight: 700;
  box-shadow: 4px 4px 10px rgba(0, 0, 0, 0.1);
}

.type-and-btn > * {
  margin-left: 3px;
}

.delete-btn {
  background: none;
  border: none;
  position: relative;
  bottom: 20px;
}
</style>
