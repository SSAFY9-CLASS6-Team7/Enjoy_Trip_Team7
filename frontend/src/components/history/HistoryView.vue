<template>
  <div class="modal">
    <div class="overlay" @click="emitViewModalOff"></div>
    <div class="modal-card">
      <div class="img-area">
        <img src="@/assets/sample/sample_history.jpg" />
        <!-- <img src="@/assets/sample/flower_sample.jpg" /> -->
      </div>
      <div class="detail-area">
        <h2 class="title">{{ history.history.title }}</h2>
        <div>{{ history.history.startDay }} - {{ history.history.endDay }}</div>
        <div class="line"></div>
        <div class="content">
          {{ history.history.content }}
        </div>
        <div class="btn-area">
          <button class="updateBtn">update</button>
          <button class="deleteBtn">delete</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'HistoryView',
  data() {
    return {
      history: Object,
    };
  },
  props: {
    historyId: Number,
  },
  methods: {
    emitViewModalOff() {
      this.$emit('setViewModal', false, 0);
    },
  },
  async mounted() {
    // console.log(this.historyId);
    await axios
      .get('http://localhost/history/' + this.historyId)
      .then((response) => (this.history = response.data));
    // console.log(this.history);
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
  border-radius: 30px;
  background-color: white;
  z-index: 11;
  display: flex;
}

.img-area {
  width: 500px;
  height: 500px;
  border-radius: 30px;
  background-color: rgba(0, 0, 0, 0.9);
  background-image: none; /*어색하면 배경에 같은 이미지 깔기*/
}

.img-area img {
  width: 100%;
  height: 100%;
  object-fit: scale-down;
}

.detail-area {
  width: 300px;
}

.title {
  margin: 5px 3px 0px 3px;
}

.line {
  width: 80%;
  height: 3px;
  background-color: #ebe8e8;
  border: 10px;
  margin: 0% 10%;
}

.btn-area {
  position: absolute;
  top: 90%;
  left: 85%;
}

.btn-area button {
  margin: 3px;
}
</style>
