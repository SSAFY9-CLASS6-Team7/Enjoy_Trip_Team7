<template>
  <div class="history-item">
    <div class="card">
      <img src="@/assets/sample/sample_history.jpg" class="history-thumnail" />
      <div class="history-info">
        <div class="history-title">기록이름도열자이내로</div>
        <div class="history-img">
          <img src="@/assets/common/image.svg" class="img-icon-vector" />
          <div class="img-num">5</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "HistoryImage",
};
</script>

<style scoped>
.history-item {
  /*반응형 정사각형 크기를 유지하기 위함*/
  position: relative;
  width: 100%;
  min-width: 150px;
  height: 0;
  overflow: hidden;
  padding-bottom: 100%;
}

.card {
  position: absolute;
  overflow: hidden;
  width: 100%;
  height: 100%;
}

.card:hover .history-info {
  opacity: 1;
}

.history-info {
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  width: 100%;
  background: rgba(0, 0, 0, 0.3);
  color: white;
  display: grid;
  grid-template-columns: 1fr 2fr 1fr;
  grid-template-rows: 1fr 2fr 1fr;
  opacity: 0;
  transition: opacity 0.5s;
  z-index: 1;
}

.history-title {
  grid-column: 1 / span 2;
  grid-row: 1 / span 2;
  margin: 2% 0 0 5%;
  font-size: 30px;
  font-weight: 600;
  text-align: left;
}

.history-img {
  grid-column: 3 / span 1;
  grid-row: 3 / span 1;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  font-weight: 400;
}

.img-icon-vector {
  width: 30%;
  max-width: 20px;
  height: 30%;
  margin: 3px 3px 0 0;
}

.history-thumnail {
  width: 100%;
  height: 100%;
  /*블러처리를 했을 때 밖으로 튀어나오지 않게
  크기를 1.1로 키운 뒤 overflow로 잘라줌*/
  transform: scale(1.1);
  filter: blur(0px);
  transition: filter 0.3s ease-in;
}

.card:hover .history-thumnail {
  filter: blur(2px);
}
</style>
