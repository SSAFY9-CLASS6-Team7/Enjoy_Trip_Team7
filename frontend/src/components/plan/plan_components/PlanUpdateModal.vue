<template>
  <div class="plan-create-modal">
    <div class="search-blackbg" @click="emitModalOff"></div>
    <div class="search-whitebg">
      <div class="top-bar"></div>
      <div class="search-modal-area">
        <div class="content-area">
          <div class="sub-title">계획 이름</div>
          <div class="title-input-area">
            <input
              type="text"
              class="title-input"
              v-model.lazy="title"
              placeholder="제목은10글자이내로"
            />
          </div>
          <div class="sub-info-area">
            <div class="date-info-area">
              <div class="sub-title">시작 날짜</div>
              <input type="date" class="date-input" v-model="startDay" />
            </div>
            <div class="date-info-area">
              <div class="sub-title">종료 날짜</div>
              <input type="date" class="date-input" v-model="endDay" />
            </div>
            <div class="area-info-area">
              <div class="sub-title">여행 지역</div>
              <select class="travel-area-input" v-model="travelArea">
                <option v-for="sido in sidoCode" :key="sido.code" :value="sido.code">
                  {{ sido.text }}
                </option>
              </select>
            </div>
          </div>
          <div class="sub-title">메모</div>
          <div class="memo-input-area">
            <textarea
              class="memo-input"
              placeholder="내용은 최대 100자 입니다."
              v-model.lazy="memo"
            ></textarea>
          </div>
          <button class="done-btn" @click="checkValue">
            <img src="@/assets/common/check_icon_white.svg" alt="" class="submit-icon" />
            수정 완료
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios';

export default {
  name: 'PlanCreateModal',
  props: ['planId'],
  data() {
    return {
      plan: Object,
      title: '',
      startDay: '',
      endDay: '',
      travelArea: '',
      memo: '',
    };
  },
  computed: {
    sidoCode() {
      return this.$store.getters.getSidoCode;
    },
  },
  methods: {
    emitModalOff() {
      this.$emit('setModal', false);
    },
    checkValue() {
      let isAllValid = false;

      if (this.title && this.startDay && this.endDay && this.memo) isAllValid = true;
      if (this.title.length > 10) isAllValid = false;
      if (this.endDay < this.startDay) isAllValid = false;
      if (this.memo.length > 100) isAllValid = false;

      if (isAllValid) {
        this.updatePlan();
      } else {
        alert('입력을 확인해주세요!');
      }
    },
    async updatePlan() {
      let newPlan = {};
      newPlan.title = this.title;
      newPlan.startDay = this.startDay;
      newPlan.endDay = this.endDay;
      newPlan.travelArea = this.travelArea;
      newPlan.content = this.memo;

      await axios.post(process.env.VUE_APP_MY_BASE_URL + '/plan/' + this.planId, newPlan);
      this.$emit('setNeedToUpdate', true);
      console.log('업데이트해!!');
      this.emitModalOff();
    },
  },
  async created() {
    await axios
      .get(process.env.VUE_APP_MY_BASE_URL + '/plan/' + this.planId)
      .then((res) => (this.plan = res.data));
    this.title = this.plan.title;
    this.startDay = this.plan.startDay;
    this.endDay = this.plan.endDay;
    this.travelArea = this.plan.travelArea;
    this.memo = this.plan.content;
  },
};
</script>
<style scoped>
input,
textarea,
select,
option {
  font-family: 'Noto Sans KR', sans-serif;
  font-weight: 400;
}

select {
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  background: url('@/assets/board_icons/dropdown.svg') no-repeat 95% 50%;
}

.plan-create-modal,
.search-blackbg {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 10;
}

.search-blackbg {
  opacity: 0.5;
  background-color: black;
}

.search-whitebg {
  width: 1300px;
  height: 700px;
  min-width: 1000px;
  position: relative;
  border-radius: 30px;
  background-color: white;
  z-index: 11;
  overflow: hidden;
}

.top-bar {
  /* background: linear-gradient(90deg, #e1306c 0%, #ff699a 38.02%, rgba(252, 175, 69, 0.78) 99.48%); */
  background: linear-gradient(
    90deg,
    var(--main-grad1-color) 0%,
    var(--main-grad2-color) 38.02%,
    var(--main-grad3-color) 99.48%
  );
  height: 18px;
  width: 100%;
}

.search-modal-area {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.content-area {
  width: 90%;
  height: 90%;
  margin: 100px;
  padding: 30px 100px;
  text-align: left;
}

.sub-title {
  font-weight: 700;
  font-size: 24px;
}

.sub-info-area {
  display: flex;
  justify-content: space-around;
}

.sub-info-area > * {
  margin: 60px 0;
}

.title-input,
.travel-area-input,
.memo-input {
  width: 100%;
  font-size: 20px;
  padding: 3%;
  border: none;
  border-radius: 10px;
  background-color: #f5f5f5;
  font-size: 1.5em;
}

.memo-input {
  font-size: 1em;
}

.title-input {
  font-weight: bold;
}

.date-input {
  border: none;
  margin-bottom: 3%;
  background-color: #f5f5f5; /* 배경색 설정 */
  border-radius: 5px; /* 테두리 모서리 둥글게 설정 */
  padding: 12px;
  font-size: 1.5em;
}

.travel-area-input {
  padding: 12px;
  font-size: 1.5em;
}

.done-btn {
  color: white;
  padding: 10px 10px;
  margin-top: 20px;
  align-items: center;
  justify-content: center;
  /* background: linear-gradient(95.36deg, #e1306c 2.32%, #ff699a 68.42%, #fcaf45 104.98%); */
  background: linear-gradient(
    95.36deg,
    var(--main-grad1-color) 2.32%,
    var(--main-grad2-color) 68.42%,
    var(--main-grad3-color) 104.98%
  );
  background-blend-mode: darken;
  border-radius: 5px;
  border: none;
  font-size: 1.3em;
  font-weight: 600;
  position: relative;
  left: 40%;
}
</style>
