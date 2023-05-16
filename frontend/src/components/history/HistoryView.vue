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
        <div class="date">{{ history.history.startDay }} - {{ history.history.endDay }}</div>
        <div class="line"></div>
        <div class="content">
          {{ history.history.content }}
        </div>
        <div class="btn-area">
          <router-link to="update">
            <button class="updateBtn">
              <img class="create-btn-vector" src="@/assets/common/modify_icon.svg" />
            </button>
          </router-link>
          <router-link to="delete">
            <button class="deleteBtn">
              <img class="create-btn-vector" src="@/assets/history/delete_icon.svg" />
            </button>
          </router-link>
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
    await axios
      .get('http://localhost/history/' + this.historyId)
      .then((response) => (this.history = response.data));
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

.title,
.date,
.content {
  text-align: left;
  margin: 0 3% 0 10%;
}

.title {
  margin-top: 5%;
}

.date {
  margin-bottom: 3%;
}

.line {
  width: 80%;
  height: 2px;
  background-color: #ebe8e8;
  border: 10px;
  margin: 0% 10%;
}

.content {
  margin-top: 5%;
}

.btn-area {
  position: absolute;
  top: 88%;
  left: 85%;
  display: flex;
}

.btn-area button {
  width: 30px;
  height: 30px;
  margin: 5px;
  padding: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 10px;
  border: 0;
}
</style>
