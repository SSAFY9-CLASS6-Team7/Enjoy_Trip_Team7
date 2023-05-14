<template>
  <div class="history-container">
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
      <div class="main">main area</div>
    </div>
    <div class="right-aside"></div>
  </div>
</template>

<script>
export default {
  name: "HistoryList",
};
</script>

<style scoped>
.history-container {
  width: 100%;
  display: grid;
  grid-template-columns: 1fr 5fr 1fr;
  grid-template-areas:
    "left header right"
    "left  main  right";
  align-items: stretch;
  justify-items: stretch;
}

.left-aside {
  grid-area: left;
  /* background-color: aquamarine; */
}

.right-aside {
  grid-area: right;
  /* background-color: aquamarine; */
}

.inner-header {
  grid-area: header;
  align-self: start;
  width: 100%;
  height: 70px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  /* background-color: aqua; */
}

.line {
  width: 100%;
  height: 3px;
  background-color: #ebe8e8;
}

.main {
  grid-area: main;
  background-color: #ff699a;
}

.create-btn {
  padding: 10px 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(95.36deg, #e1306c 2.32%, #ff699a 68.42%, #fcaf45 104.98%);
  background-blend-mode: darken;
  border-radius: 10px;
  font-size: 12px;
}

.create-btn-vector {
  width: 15px;
  height: auto;
}

button {
  border: none;
  color: white;
  font-family: inherit;
}
</style>
