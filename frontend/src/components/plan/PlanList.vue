<template>
  <div class="plan-container">
    <div class="plan-modal" v-if="isModalOpen">
      <plan-create-modal
        @setModal="setModal"
        @setNeedToUpdate="setNeedToUpdate"
        v-if="openedModal === 'create' && isModalOpen === true"
      ></plan-create-modal>
      <plan-update-modal
        @setModal="setModal"
        @setNeedToUpdate="setNeedToUpdate"
        :planId="focusedPlanId"
        v-if="openedModal === 'update' && isModalOpen === true"
      ></plan-update-modal>
    </div>
    <div class="left-aside"></div>
    <div>
      <div class="inner-header">
        <h2>계획</h2>
        <button class="create-btn" @click="createModalOpen">
          <img class="create-btn-vector" src="../../assets/common/plus_icon_white.svg" />
          계획 추가
        </button>
      </div>
      <div class="line"></div>
      <div class="empty-main" v-if="plans.length === 0">
        <plan-empty @setCreateModal="createModalOpen"></plan-empty>
      </div>
      <div class="plan-main" v-if="plans.length !== 0">
        <div class="main">
          <plan-list-item
            v-for="plan in plans"
            :key="plan.planId"
            :planId="plan.planId"
            :needToUpdate="needToUpdate"
            @updateModalOpen="updateModalOpen"
            @setNeedToUpdate="setNeedToUpdate"
          ></plan-list-item>
        </div>
        <plan-pagination
          class="pagination"
          :pageResult="pageResult"
          @pageChange="pageChange"
        ></plan-pagination>
      </div>
    </div>
    <div class="right-aside"></div>
  </div>
</template>

<script>
import PlanListItem from './plan_components/PlanListItem.vue';
import PlanCreateModal from './plan_components/PlanCreateModal.vue';
import PlanUpdateModal from './plan_components/PlanUpdateModal.vue';
import PlanPagination from './plan_components/PlanPagination.vue';
import PlanEmpty from './PlanEmpty.vue';
import axios from 'axios';
import { mapGetters } from 'vuex';

export default {
  name: 'PlanList',
  components: {
    PlanListItem,
    PlanCreateModal,
    PlanUpdateModal,
    PlanPagination,
    PlanEmpty,
  },
  data() {
    return {
      plans: [],
      isModalOpen: false,
      openedModal: '',
      focusedPlanId: '',
      pageNo: 1,
      pageResult: {},
      needToUpdate: false,
      userId: '',
    };
  },
  computed: {
    ...mapGetters('userStore', ['checkUserInfo']),
  },
  watch: {
    // 목록 내용이 변경 시 다시 로딩
    needToUpdate: async function () {
      if (this.needToUpdate === true) {
        await this.loadPlans();
        this.setNeedToUpdate(false);
      }
    },
  },
  methods: {
    //모달 창 오픈 여부 변경
    setModal(value) {
      this.isModalOpen = value;
      if (value === false) {
        this.openedModal = '';
        this.focusedPlanId = '';
      }
    },
    createModalOpen() {
      if (this.checkToken) {
        this.openedModal = 'create';
        this.setModal(true);
      } else {
        alert('로그인이 필요합니다!');
        this.$router.push('/user/login');
      }
    },
    updateModalOpen(planId) {
      this.focusedPlanId = planId;
      this.openedModal = 'update';
      this.setModal(true);
    },
    setNeedToUpdate(value) {
      this.needToUpdate = value;
    },
    addAttraction(attraction) {
      console.log('---관광지 정보 넘어옴 : ' + attraction.title);
    },
    async pageChange(clickedPage) {
      this.pageNo = clickedPage;
      await this.loadPlans();
    },
    loadPlans() {
      axios
        .get(process.env.VUE_APP_MY_BASE_URL + `/plan?pageNo=${this.pageNo}&userId=${this.userId}`)
        .then((response) => {
          this.plans = response.data.plans;
          this.pageResult = response.data.pageResult;
        });
    },
  },
  created() {
    this.userId = this.checkUserInfo.userId;
    this.loadPlans();
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

.main > * {
  margin: 2%;
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
}

button {
  border: none;
  color: white;
  font-family: inherit;
}
</style>
