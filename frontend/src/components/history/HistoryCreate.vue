<template>
  <div class="modal">
    <div class="overlay" @click="modalOpen = false"></div>
    <div class="modal-card">
      <h2>기록 추가 페이지</h2>
    </div>
  </div>
</template>

<script>
export default {
  name: "HistoryCreate",
  data() {
    return {
      modalOpen: false,
    };
  },
};
</script>

<style scoped>
.modal,
.overlay {
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

.overlay {
  opacity: 0.5;
  background-color: black;
}

.modal-card {
  position: relative;
  min-width: 70%;
  min-height: 70%;
  border-radius: 30px;
  background-color: white;
  z-index: 11;
}
</style>
