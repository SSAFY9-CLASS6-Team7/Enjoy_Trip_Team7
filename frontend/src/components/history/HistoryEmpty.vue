<template>
  <div class="history-empty-container">
    <h1>아직 여행을 다녀온 기록이 없어요!</h1>
    <button class="create-big-btn" @click="emitCreateModalOn()"></button>
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

.create-big-btn {
  width: 500px;
  height: 100px;
  background-image: url('@/assets/history_assets/emptyPage_btn.svg');
  background-repeat: no-repeat;
  background-position: center;
  border: none;
  background-color: white;
  margin-bottom: 200px;
}

h1 {
  color: rgba(183, 183, 183, 1);
  font-size: 70px;
  margin-bottom: 100px;
}
</style>
