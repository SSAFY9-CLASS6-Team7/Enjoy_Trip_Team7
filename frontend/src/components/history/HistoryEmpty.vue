<template>
  <div class="history-empty-container">
    <h1>아직 여행을 다녀온 기록이 없어요!</h1>
    <button class="create-big-btn" @click="emitCreateModalOn()">새로운 기록 만들기 ></button>
  </div>
</template>
<script>
export default {
  name: 'HistoryEmpty',
  methods: {
    emitCreateModalOn() {
      this.$emit('setCreateModal');
    },
  },
};
</script>
<style scoped>
.history-empty-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 775px;
}

/* .create-big-btn {
  width: 500px;
  height: 100px;
  background-image: url('@/assets/history_assets/emptyPage_btn.svg');
  background-repeat: no-repeat;
  background-position: center;
  border: none;
  background-color: white;
  margin-bottom: 200px;
} */

.create-big-btn {
  font-weight: 900;
  font-size: 50px;
  width: 500px;
  height: 100px;
  border: none;
  margin-bottom: 200px;
  /* background: linear-gradient(95.36deg, #e1306c 2.32%, #ff699a 68.42%, #fcaf45 104.98%); */
  background: linear-gradient(
    95.36deg,
    var(--main-grad1-color) 2.32%,
    var(--main-grad2-color) 68.42%,
    var(--main-grad3-color) 104.98%
  );

  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  text-fill-color: transparent;
  background-blend-mode: darken;
}

h1 {
  color: rgba(183, 183, 183, 1);
  font-size: 70px;
  margin-bottom: 100px;
}
</style>
