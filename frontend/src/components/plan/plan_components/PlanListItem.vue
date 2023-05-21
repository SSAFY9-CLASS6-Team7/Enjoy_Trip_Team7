<template>
  <!-- plan list에 나타나는 컴포넌트 -->
  <div class="plan-item-container">
    <div class="modal-card" v-if="plan" @click="gotoView">
      <!-- 해당 지역에 대한 대표이미지 1개 -->
      <div class="img-area">
        <h2 class="title">{{ plan.title }}</h2>
        <img src="@/assets/sample/sample5.jpg" class="plan-img" />
      </div>
      <div class="detail-area">
        <div class="address">
          <img src="@/assets/plan_icon/pin.svg" alt="" class="map-pin" />
          여행지역
        </div>
        <!-- TODO: 여행세부 계획을 쓰지 않으면 이부분이 공란 : plan에 날짜 컬럼을? -->
        <div class="date" v-if="plan.planAttractions">
          {{ formatDate(plan.planAttractions[0].planDate) }} - 종료날짜
        </div>
        <div class="line"></div>
        <div class="content" v-html="formatBoardContent(plan.content)" v-if="plan.content"></div>
        <div class="btn-area">
          <button class="updateBtn" @click="emitGotoUpdate()">
            <img class="update-btn-vector" src="@/assets/common/modify_icon.svg" />
          </button>
          <button class="deleteBtn" @click="checkAlert()">
            <img class="delete-btn-vector" src="@/assets/common/delete_icon.svg" />
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios';
export default {
  name: 'PlanListItem',
  props: ['planId'],
  data() {
    return {
      plan: Object,
    };
  },
  methods: {
    formatDate(selectDate) {
      const dateParts = selectDate.split('-');
      const year = dateParts[0].substr(2);
      const month = dateParts[1];
      const day = dateParts[2];
      return `${year}.${month}.${day}`;
    },
    //엔터를 <br>로
    formatBoardContent(content) {
      return content.replace(/\n/g, '<br>');
    },
    gotoView() {
      this.$router.push('/plan/view/' + this.planId);
    },
    //삭제 확인용 알림 띄우기
    checkAlert() {
      if (confirm('정말 삭제하시겠습니까?') === true) {
        this.deleteHistory();
      } else return false;
    },
    //삭제 진행하기
    async deleteHistory() {
      await axios.delete(`http://localhost/plan/` + this.planId);
    },
  },
  async mounted() {
    await axios
      .get(`http://localhost/plan/` + this.planId)
      .then((response) => (this.plan = response.data));
  },
};
</script>
<style scoped>
.plan-item-container {
  position: relative;
  max-width: 550px;
  max-height: 300px;
  border-radius: 30px;
  filter: drop-shadow(-1px 5px 16px rgba(0, 0, 0, 0.13));
}

.modal-card {
  position: relative;
  border-radius: 30px;
  background-color: white;
  display: flex;
}

.img-area {
  width: 300px;
  height: 300px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 30px;
  overflow: hidden;
  background-color: rgba(0, 0, 0, 0.9);
  background-image: none;
  /*TODO: 어색하면 배경에 같은 이미지 블러해서 깔기*/
}

.title {
  position: absolute;
  max-width: 200px;
  text-align: left;
  top: 10px;
  left: 25px;
  color: white;
  z-index: 1;
  font-weight: 700;
  font-size: 40px;
}

.img-area img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  opacity: 0.7;
  background: #454545;
}

.detail-area {
  width: 300px;
}

.address,
.date,
.content {
  text-align: left;
  margin: 0 3% 0 10%;
}

.address {
  margin-top: 5%;
  font-weight: 700;
}

.date {
  margin-bottom: 3%;
}

.line {
  width: 80%;
  height: 2px;
  background-color: #ebe8e8;
  border: 10px;
  margin: 0% 10%;
}

.content {
  margin-top: 5%;
  overflow: auto;
}

.btn-area {
  position: absolute;
  top: 80%;
  left: 80%;
  display: flex;
}

.btn-area button {
  width: 30px;
  height: 30px;
  margin: 5px;
  padding: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 10px;
  border: 0;
}
</style>
