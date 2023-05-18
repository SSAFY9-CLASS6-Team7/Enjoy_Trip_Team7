<template>
  <div class="plan-container">
    <div class="plan-modal"></div>
    <div class="left-aside"></div>
    <div>
      <div class="inner-header">
        <h2>계획</h2>
        <button class="create-btn">
          <img class="create-btn-vector" src="../../assets/common/plus_icon_white.svg" />
          계획 추가
        </button>
      </div>
      <div class="line"></div>
      <div class="main">
        <plan-list-item
          v-for="plan in plans"
          :key="plan.planId"
          :planId="plan.planId"
        ></plan-list-item>
      </div>
      <div class="pagination"></div>
    </div>
    <div class="right-aside"></div>
  </div>
</template>

<script>
import PlanListItem from './plan_components/PlanListItem.vue';
import axios from 'axios';
export default {
  name: 'PlanList',
  components: {
    PlanListItem,
  },
  data() {
    return {
      plans: [],
      pageNo: 1,
    };
  },
  async created() {
    await axios
      .get(`http://localhost/plan?pageNo=${this.pageNo}`)
      .then((response) => (this.plans = response.data));
  },
};
</script>

<style scoped>
.plan-container {
  width: 100%;
  display: grid;
  grid-template-columns: 1fr 5fr 1fr;
  grid-template-areas:
    'left header right'
    'left  main  right'
    'left  page  right';
  align-items: stretch;
  justify-items: stretch;
}

.left-aside {
  grid-area: left;
}

.right-aside {
  grid-area: right;
}

.inner-header {
  grid-area: header;
  align-self: start;
  width: 100%;
  height: 70px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.line {
  width: 100%;
  height: 3px;
  background-color: #ebe8e8;
}

.main {
  grid-area: main;
  margin-top: 20px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 1fr 1fr;
  background-color: white;
}

.create-btn {
  padding: 7px 10px;
  margin-top: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(95.36deg, #e1306c 2.32%, #ff699a 68.42%, #fcaf45 104.98%);
  background-blend-mode: darken;
  border-radius: 5px;
  font-size: 12px;
  font-weight: 600;
}

.create-btn-vector {
  width: 15px;
  height: auto;
}

.pagination {
  grid-area: page;
  min-height: 60px;
  border: #fcaf45 1px solid;
}

button {
  border: none;
  color: white;
  font-family: inherit;
}
</style>
