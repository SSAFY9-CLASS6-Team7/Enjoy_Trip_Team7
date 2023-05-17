<template>
  <div class="history-container">
    <div class="test" v-if="this.isModalOpen === true">
      <history-modal
        :modaltype="this.modaltype"
        :historyId="this.focusedHistoryId"
        @setType="setType"
        @setModal="setModal"
        @setNeedToUpdate="setNeedToUpdate"
      ></history-modal>
    </div>
    <div class="left-aside"></div>
    <div>
      <div class="inner-header">
        <h2>기록</h2>
        <button
          class="create-btn"
          @click="
            setType('create');
            setModal(true);
          "
        >
          <img class="create-btn-vector" src="../../assets/common/plus_icon_white.svg" />
          기록 추가
        </button>
      </div>
      <div class="line"></div>
      <div class="main">
        <history-image
          v-for="history in histories"
          :key="history.historyId"
          :historyId="history.historyId"
          :needToUpdate="needToUpate"
          @setViewModal="setViewModal"
        ></history-image>
      </div>
      <div class="pagination">pagination</div>
    </div>
    <div class="right-aside"></div>
  </div>
</template>

<script>
import HistoryImage from '../history/HistoryImage.vue';
import HistoryModal from './HistoryModal.vue';
import axios from 'axios';

export default {
  name: 'HistoryList',
  components: {
    HistoryImage,
    HistoryModal,
  },
  data() {
    return {
      histories: [],
      focusedHistoryId: 0,
      modaltype: '',
      isModalOpen: false,
      needToUpate: false,
    };
  },
  methods: {
    //열리는 모달 창의 타입(생성/조회/수정) 변경
    setType(type) {
      this.modaltype = type;
    },
    //모달 창 오픈 여부 변경
    setModal(value) {
      //모달 닫을 때면 초기화
      if (value === false) {
        this.setType('');
        this.focusedHistoryId = 0;
      }
      this.isModalOpen = value;
    },
    setNeedToUpdate(value) {
      this.needToUpate = value;
    },
    //기록 조회 모달 열기
    setViewModal(historyId) {
      this.focusedHistoryId = historyId;
      this.setType('view');
      this.setModal(true);
    },
    // 기록 리스트 로딩
    async loadHistories() {
      await axios
        .get(`http://43.201.218.74/history?pageNo=`)
        .then((response) => (this.histories = response.data));
    },
  },
  watch: {
    // 히스토리 목록 내용이 변경 시 다시 로딩
    needToUpate: async function () {
      if (this.needToUpate === true) {
        await this.loadHistories();
        this.setNeedToUpdate(false);
      }
    },
  },
  created() {
    this.loadHistories();
  },
};
</script>

<style scoped>
.history-container {
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
  margin: 20px 0;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;
  grid-template-rows: 1fr 1fr;
  background-color: antiquewhite;
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
  background-color: #fcaf45;
}

button {
  border: none;
  color: white;
  font-family: inherit;
}
</style>
