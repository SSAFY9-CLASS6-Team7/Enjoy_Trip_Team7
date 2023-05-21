<template>
<!-- 계획 관광지 검색 후 클릭하면 나오는 상세 정보 -->
  <div class="plan-search-item">
    <div class="title-and-btn">
      <div class="plan-attraction-title">{{ attraction.title }}</div>
      <button class="plus-btn" @click="emitAddAttraction">
        <img src="@/assets/plan_icon/circle_add.svg" />
      </button>
    </div>
    <div class="plan-attraction-type" v-if="contentTypeSrc">
      <img :src="contentTypeSrc" />
    </div>
    <div class="plan-attraction-image" v-if="attraction.thumbnail">
      <img :src="attraction.thumbnail" />
    </div>
    <div class="info-title"><img src="@/assets/plan_icon/search_location.svg" />소개</div>
    <div class="plan-attraction-info">{{ attraction.overView }}</div>
    <div class="address-title"><img src="@/assets/plan_icon/pin.svg" />위치</div>
    <div class="plan-attraction-address">{{ attraction.address }}</div>
    <div class="map-area">Map</div>
  </div>
</template>
<script>
import axios from 'axios';
export default {
  name: 'PlanAttractionItem',
  data() {
    return {
      attraction: Object,
      contentTypeSrc: '',
    };
  },
  props: ['attractionId'],
  watch: {
    attractionId: function () {
      this.loadData(this.attractionId);
    },
  },
  methods: {
    async loadData(attractionId) {
      await axios
        .get(`http://localhost/attraction/${attractionId}`)
        .then((response) => (this.attraction = response.data));
      this.setContentTypeSrc();
    },
    //관광지 타입 설정하기
    setContentTypeSrc() {
      if (this.attraction.code === 12)
        this.contentTypeSrc = require('@/assets/content_type_icon/tour.svg');
      else if (this.attraction.code === 15)
        this.contentTypeSrc = require('@/assets/content_type_icon/event.svg');
      else if (this.attraction.code === 32)
        this.contentTypeSrc = require('@/assets/content_type_icon/stay.svg');
      else if (this.attraction.code === 39)
        this.contentTypeSrc = require('@/assets/content_type_icon/restaurant.svg');
      else this.contentTypeSrc = require('@/assets/content_type_icon/etc.svg');
    },
    //선택한 관광지 정보를 부모 객체로 전달
    emitAddAttraction() {
      this.$emit('emitAddAttraction', this.attraction);
    },
  },
  mounted() {
    this.loadData(this.attractionId);
  },
};
</script>
<style scoped>
.plan-search-item {
  width: 97%;
  height: 100%;
}

.plan-search-item > * {
  margin-bottom: 10px;
}

.title-and-btn {
  display: flex;
  align-items: center;
  justify-content: space-between;
  text-align: left;
}

.plus-btn {
  min-width: 45px;
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
  border-radius: 10px;
  border: none;
}

.plan-attraction-title {
  font-weight: 700;
  font-size: 25px;
}

.plan-attraction-type {
  text-align: left;
}

.plan-attraction-image img {
  width: 100%;
  height: auto;
  border-radius: 10px;
  margin-bottom: 20px;
}

.info-title,
.address-title {
  display: flex;
  align-items: center;
  text-align: left;
  font-weight: 700;
  font-size: 20px;
  color: #e1306c;
}

.info-title img,
.address-title img {
  margin: 5px;
}
.plan-attraction-info {
  font-size: 15px;
  text-align: left;
  padding-bottom: 30px;
  border-bottom: 2px solid #e6e6e6;
}

.address-title {
  margin-top: 20px;
}

.plan-attraction-address {
  text-align: left;
}
</style>
