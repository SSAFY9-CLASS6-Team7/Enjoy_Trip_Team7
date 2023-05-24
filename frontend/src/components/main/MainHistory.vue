<template>
  <div class="main-history-continer">
    <div class="history-container-title">
      나의 기록
      <div class="more" @click="toHistories">...더보기</div>
    </div>
    <div class="history-mid-line"></div>
    <div class="empty-main" v-if="histories.length === 0">
      <history-empty @setCreateModal="toHistories"></history-empty>
    </div>
    <div class="history-main" v-if="histories.length !== 0">
      <history-image
        v-for="history in showHistories"
        :key="history.historyId"
        :historyId="history.historyId"
        @setViewModal="gotoViewModal"
      ></history-image>
    </div>
  </div>
</template>
<script>
import HistoryImage from '../history/history_components/HistoryImage.vue';
import HistoryEmpty from '../history/HistoryEmpty.vue';
import axios from 'axios';
import { mapGetters } from 'vuex';

export default {
  name: 'MainHistory',
  components: {
    HistoryImage,
    HistoryEmpty,
  },
  data() {
    return {
      histories: [],
      showHistories: [],
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
    async loadHistories() {
      await axios
        .get(
          process.env.VUE_APP_MY_BASE_URL + `/history?pageNo=${this.pageNo}&userId=${this.userId}`
        )
        .then((response) => {
          this.histories = response.data.histories;
          this.pageResult = response.data.pageResult;
          if (this.histories.length > 5) {
            this.showHistories = this.histories.slice(0, 5);
          } else {
            this.showHistories = this.histories.slice(0, this.histories.length);
          }
        });
    },
    gotoViewModal(historyId) {
      this.$router.push('/history/list/' + historyId);
    },
    toHistories() {
      this.$router.push('/history/list/');
    },
  },
  created() {
    this.userId = this.checkUserInfo.userId;
    this.loadHistories();
  },
};
</script>
<style scoped>
.main-history-continer {
  padding: 20px 50px 0 50px;
  margin: 0 100px 50px 100px;
}

.history-main {
  display: grid;
  justify-content: space-between;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
  margin-top: 20px;
}

.main-history-continer .history-container-title {
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

.history-mid-line {
  width: 100%;
  height: 3px;
  background-color: #ebe8e8;
}
</style>
