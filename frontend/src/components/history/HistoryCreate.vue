<template>
  <div class="modal">
    <div class="overlay" @click="emitCreateModalOff"></div>
    <div class="modal-card">
      <div class="img-area">
        <label for="file">
          <div class="btn-upload">파일 업로드하기</div>
          <input
            type="file"
            class="file"
            id="file"
            accept="image/*"
            @change="fileChange"
            ref="files"
            multiple
          />
        </label>
        <p v-for="file in files" :key="file.name">{{ file.name }}</p>
      </div>
      <div class="detail-area">
        <input type="text" class="title-input" v-model.lazy="title" />
        <div class="date-area">
          <input
            type="date"
            class="date-input"
            v-model="startDay"
            required
            aria-required="true"
            @change="formatDate(this.startDay)"
          />
          -
          <input
            type="date"
            class="date-input"
            v-model="endDay"
            @change="formatDate(this.endDay)"
          />
        </div>
        <div class="line"></div>
        <input type="text" class="content-input" v-model.lazy="content" />
        <div class="btn-area">
          <button class="createBtn" @clisk="checkValue">
            <img class="create-btn-vector" src="@/assets/common/check_icon_black.svg" />
          </button>
          <button class="cancelBtn" @click="emitCreateModalOff">
            <img class="cancel-btn-vector" src="@/assets/common/x_icon.svg" />
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import axios from 'axios';

export default {
  name: 'HistoryCreate',
  data() {
    return {
      title: '제목은최대10자입니다',
      startDay: '날짜선택',
      endDay: '',
      content: '내용은 최대 얼마나?',
      files: [],
      // history: Object,
    };
  },
  props: {},
  methods: {
    emitCreateModalOff() {
      this.$emit('setCreateModal', false);
    },
    fileChange: function (e) {
      console.log('파일 잘 들어옴');
      const files = e.target.files;
      let validation = true;
      let message = '';

      if (files.length > 5) {
        validation = false;
        message = `파일은 다섯 개만 등록 가능합니다.`;
      }

      for (let i = 0; i < files.length; i++) {
        if (files[i].size > 1024 * 1024 * 25) {
          message = `${message} 파일은 용량은 25MB 이하만 가능합니다.`;
          validation = false;
        }

        if (files[i].type.indexOf('image') < 0) {
          message = `${message} 이미지 파일만 업로드 가능합니다.`;
          validation = false;
        }
      }

      if (validation) {
        this.files = e.target.files;
      } else {
        this.files = '';
        alert(message);
      }
      console.dir(this.files);
    },
    // 인풋 적절한지 체크
    checkValue() {
      console.log('1.인풋 적절한지 체크!!');
      this.createHistory();
    },
    //기록 등록
    createHistory() {
      console.log('2.인풋을 post로 보냄!!!');
    },
  },
};
</script>

<style scoped>
input {
  font-family: 'Noto Sans KR', sans-serif;
}

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
  background-color: rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.btn-upload {
  width: 150px;
  height: 40px;
  background: #fff;
  border: 1px solid rgb(77, 77, 77);
  border-radius: 15px;
  font-weight: 500;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}

.btn-upload:hover {
  background: rgb(77, 77, 77);
  color: #fff;
}

.img-area input {
  display: none;
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

.title-input {
  width: 85%;
  font-size: 20px;
  margin: 5% 3% 0 5%;
  padding: 3%;
  border: none;
  border-radius: 10px;
  background-color: #f5f5f5;
}

.date-area {
  /* width: 80%; */
  display: flex;
  justify-content: space-evenly;
  margin: 5% 3% 0 5%;
}

.date-input {
  border: none;
  margin-bottom: 3%;
  background-color: #f5f5f5; /* 배경색 설정 */
  border-radius: 5px; /* 테두리 모서리 둥글게 설정 */
  padding: 5px;
}
/* .date-input::-webkit-datetime-edit-fields-wrapper {
} */

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

.content-input {
  width: 80%;
  height: 60%;
  margin: 5% 3% 0 5%;
  padding: 3%;
  border: none;
  border-radius: 10px;
  background-color: #f5f5f5;
  text-align: left;
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
