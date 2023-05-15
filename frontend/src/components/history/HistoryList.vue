<template>
  <div class="history-container">
    <!-- <div v-if="modalOpen === true">
      <history-create></history-create>
    </div> -->
    <div class="left-aside"></div>
    <div>
      <div class="inner-header">
        <h2>기록</h2>
        <router-link to="create">
          <button class="create-btn">
            <img class="create-btn-vector" src="../../assets/common/create_btn.svg" />
            기록 추가
          </button>
        </router-link>
      </div>
      <div class="line"></div>
      <div class="main">
        <history-image
          v-for="history in histories"
          :key="history.historyId"
          :historyId="history.historyId"
        ></history-image>
      </div>
      <div class="pagination">pagination</div>
    </div>
    <div class="right-aside"></div>
  </div>
</template>

<script>
import HistoryImage from '../history/HistoryImage.vue';
// import HistoryCreate from '../history/HistoryCreate.vue';
import axios from 'axios';

export default {
  name: 'HistoryList',
  components: {
    HistoryImage,
    // HistoryCreate,
  },
  data() {
    return {
      histories: [],
    };
  },
  async created() {
    await axios
      .get('http://localhost/history?pageNo=')
      .then((response) => (this.histories = response.data));
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
