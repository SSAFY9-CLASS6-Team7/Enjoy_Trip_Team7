<template>
  <!-- plan list에 나타나는 컴포넌트 -->
  <div class="plan-item-container">
    <div class="modal-card" v-if="plan">
      <div class="goto-view-area" @click="gotoView">
        <!-- 해당 지역에 대한 대표이미지 1개 -->
        <div class="img-area">
          <h2 class="title">{{ plan.title }}</h2>
          <img :src="travelAreaImgSrc" class="plan-img" />
        </div>
        <div class="detail-area">
          <div class="address">
            <img src="@/assets/plan_icon/pin.svg" alt="" class="map-pin" />
            {{ getSidoText(plan.travelArea) }}
          </div>
          <div class="date" v-if="plan.planAttractions">
            {{ formatDate(plan.startDay) }} - {{ formatDate(plan.endDay) }}
          </div>
          <div class="line"></div>
          <div class="content" v-html="formatBoardContent(plan.content)" v-if="plan.content"></div>
        </div>
      </div>
      <div class="btn-area">
        <button class="updateBtn" @click="emitUpdateModalOn(planId)">
          <img class="update-btn-vector" src="@/assets/common/modify_icon.svg" />
        </button>
        <button class="deleteBtn" @click="checkAlert()">
          <img class="delete-btn-vector" src="@/assets/common/delete_icon.svg" />
        </button>
      </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios';
export default {
  name: 'PlanListItem',
  props: ['planId', 'needToUpdate'],
  data() {
    return {
      plan: Object,
      travelAreaImgSrc: '',
    };
  },
  computed: {
    sidoCode() {
      return this.$store.getters.getSidoCode;
    },
    getSidoText() {
      return (code) => {
        const sido = this.sidoCode.find((item) => item.code === String(code));
        return sido ? sido.text : '';
      };
    },
  },
  watch: {
    // 목록 내용이 변경 시 다시 로딩
    needToUpdate: function () {
      if (this.needToUpdate === true) {
        this.loadPlan();
      }
    },
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
    emitUpdateModalOn(planId) {
      this.$emit('updateModalOpen', planId);
    },
    //삭제 확인용 알림 띄우기
    checkAlert() {
      if (confirm('정말 삭제하시겠습니까?') === true) {
        this.deletePlan();
      } else return false;
    },
    //삭제 진행하기
    async deletePlan() {
      await axios.delete(process.env.VUE_APP_MY_BASE_URL + `/plan/` + this.planId);
      this.$emit('setNeedToUpdate', true);
      // this.$router.push('/plan/');
    },
    async loadPlan() {
      await axios
        .get(process.env.VUE_APP_MY_BASE_URL + `/plan/` + this.planId)
        .then((response) => (this.plan = response.data));
      this.setTravelAreaImgSrc();
    },
    setTravelAreaImgSrc() {
      if (this.plan.travelArea === 1) {
        //서울
        this.travelAreaImgSrc = require('@/assets/main_attractions/seoul.png');
      } else if (this.plan.travelArea === 2) {
        //인천
        this.travelAreaImgSrc = require('@/assets/main_attractions/incheon.png');
      } else if (this.plan.travelArea === 3) {
        //대전
        this.travelAreaImgSrc = require('@/assets/main_attractions/daejeon.png');
      } else if (this.plan.travelArea === 4) {
        //대구
        this.travelAreaImgSrc = require('@/assets/main_attractions/daegu.png');
      } else if (this.plan.travelArea === 5) {
        //광주
        this.travelAreaImgSrc = require('@/assets/main_attractions/gwangju.jpg');
      } else if (this.plan.travelArea === 6) {
        //부산
        this.travelAreaImgSrc = require('@/assets/main_attractions/busan.png');
      } else if (this.plan.travelArea === 7) {
        //울산
        this.travelAreaImgSrc = require('@/assets/main_attractions/ulsan.png');
      } else if (this.plan.travelArea === 8) {
        //세종
        this.travelAreaImgSrc = require('@/assets/main_attractions/daejeon.png');
      } else if (this.plan.travelArea === 31) {
        //경기도
        this.travelAreaImgSrc = require('@/assets/main_attractions/gyeonggido.jpg');
      } else if (this.plan.travelArea === 32) {
        //강원도
        this.travelAreaImgSrc = require('@/assets/main_attractions/gangwondo.jpg');
      } else if (this.plan.travelArea === 33) {
        //충북
        this.travelAreaImgSrc = require('@/assets/main_attractions/chungbuk.jpg');
      } else if (this.plan.travelArea === 34) {
        //충남
        this.travelAreaImgSrc = require('@/assets/main_attractions/chungnam.jpg');
      } else if (this.plan.travelArea === 35) {
        //경북
        this.travelAreaImgSrc = require('@/assets/main_attractions/gyeongbuk.jpg');
      } else if (this.plan.travelArea === 36) {
        //경남
        this.travelAreaImgSrc = require('@/assets/main_attractions/gyeongnam.jpg');
      } else if (this.plan.travelArea === 37) {
        //전북
        this.travelAreaImgSrc = require('@/assets/main_attractions/jeonbuk.jpg');
      } else if (this.plan.travelArea === 38) {
        //전남
        this.travelAreaImgSrc = require('@/assets/main_attractions/jeonnam.jpg');
      } else if (this.plan.travelArea === 39) {
        //제주
        this.travelAreaImgSrc = require('@/assets/main_attractions/jeju.jpg');
      }
    },
  },
  mounted() {
    this.loadPlan();
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
}

.goto-view-area {
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
.date {
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
  max-height: 160px;
  text-align: left;
  margin: 5% 10% 0 10%;
  overflow: hidden;
  white-space: normal;
  display: -webkit-box;
  -webkit-line-clamp: 7;
  -webkit-box-orient: vertical;
}

.btn-area {
  position: absolute;
  top: 80%;
  left: 80%;
  display: flex;
  z-index: 1;
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
