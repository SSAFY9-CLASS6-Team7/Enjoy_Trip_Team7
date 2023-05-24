<template>
  <div class="main-plan-continer">
    <div class="plan-container-title">
      나의 계획
      <div class="more" @click="toPlans">...더보기</div>
    </div>
    <div class="plan-mid-line"></div>
    <div class="empty-main" v-if="plans.length === 0">
      <plan-empty @setCreateModal="toPlans"></plan-empty>
    </div>
    <div class="plan-main" v-if="plans.length !== 0">
      <plan-list-item
        v-for="plan in showplans"
        :key="plan.planId"
        :planId="plan.planId"
        @updateModalOpen="gotoView"
      ></plan-list-item>
    </div>
  </div>
</template>
<script>
import PlanEmpty from '../plan/PlanEmpty.vue';
import PlanListItem from '../plan/plan_components/PlanListItem.vue';
import axios from 'axios';
import { mapGetters } from 'vuex';

export default {
  name: 'MainPlan',
  components: {
    PlanEmpty,
    PlanListItem,
  },
  data() {
    return {
      plans: [],
      showplans: [],
      userId: '',
      pageNo: 1,
      pageResult: {},
    };
  },
  computed: {
    ...mapGetters('userStore', ['checkToken', 'checkUserInfo']),
  },
  methods: {
    // 기록 리스트 로딩
    async loadPlans() {
      console.log('load');
      await axios
        .get(process.env.VUE_APP_MY_BASE_URL + `/plan?pageNo=${this.pageNo}&userId=${this.userId}`)
        .then((response) => {
          this.plans = response.data.plans;
          this.pageResult = response.data.pageResult;
          if (this.plans.length > 3) {
            this.showplans = this.plans.slice(0, 3);
          } else {
            this.showplans = this.plans.slice(0, this.plans.length);
          }
        });
      console.log('load end');
    },
    gotoView(planId) {
      this.$router.push('/plan/view/' + planId);
    },
    toPlans() {
      this.$router.push('/plan/list/');
    },
  },
  created() {
    console.log('created');
    this.userId = this.checkUserInfo.userId;
    this.loadPlans();
  },
};
</script>
<style scoped>
.main-plan-continer {
  padding: 20px 50px 0 50px;
  margin: 0 100px 50px 100px;
}

.plan-main {
  display: grid;
  justify-content: space-between;
  justify-items: center;
  grid-template-columns: 1fr 1fr 1fr;
  margin-top: 20px;
}

.main-plan-continer .plan-container-title {
  padding: 20px 0 0 0;
  text-align: left;
  font-size: 28px;
  font-weight: 600;
  display: flex;
  justify-content: flex-start;
  align-items: baseline;
}

.more {
  text-align: left;
  padding: 0 20px 10px 20px;
  font-size: 18px;
  font-weight: 600;
}

.more:hover {
  cursor: pointer;
}

.plan-mid-line {
  width: 100%;
  height: 3px;
  background-color: #ebe8e8;
}
</style>
