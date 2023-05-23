<template>
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
      <input
        type="text"
        class="title-input"
        v-model.lazy="title"
        placeholder="제목은10글자이내로"
        :style="{ backgroundColor: title ? 'white' : '' }"
      />
      <div class="date-area">
        <input
          type="date"
          class="date-input"
          v-model="startDay"
          :style="{ backgroundColor: startDay ? 'white' : '' }"
        />
        -
        <input
          type="date"
          class="date-input"
          v-model="endDay"
          :style="{ backgroundColor: endDay ? 'white' : '' }"
        />
      </div>
      <div class="line"></div>
      <textarea
        class="content-input"
        v-model.lazy="content"
        ref="contentInput"
        placeholder="내용을 입력해주세요."
        :style="{ backgroundColor: content ? 'white' : '' }"
      ></textarea>
      <div class="btn-area">
        <button class="createBtn" @click="checkValue">
          <img class="create-btn-vector" src="@/assets/common/check_icon_black.svg" />
        </button>
        <button class="cancelBtn" @click="emitModalOff">
          <img class="cancel-btn-vector" src="@/assets/common/x_icon.svg" />
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { mapGetters } from 'vuex';

export default {
  name: 'HistoryCreate',
  data() {
    return {
      title: '',
      startDay: '',
      endDay: '',
      content: '',
      files: [],
      userId: '',
    };
  },
  props: {},
  computed: {
    ...mapGetters('userStore', ['checkUserInfo']),
  },
  methods: {
    //모달창을 닫기
    emitModalOff() {
      this.$emit('emitModalOff');
    },
    //파일 입력 관련한 체크 진행하기
    fileChange: function (e) {
      const files = e.target.files;
      let validation = true;
      let message = '';

      if (files.length > 5) {
        validation = false;
        message = `파일은 다섯 개만 등록 가능합니다. `;
      }

      for (let i = 0; i < files.length; i++) {
        if (files[i].size > 1024 * 1024 * 25) {
          message = `${message} 파일은 용량은 25MB 이하만 가능합니다. `;
          validation = false;
        }

        if (files[i].type.indexOf('image') < 0) {
          message = `${message} 이미지 파일만 업로드 가능합니다. `;
          validation = false;
        }
      }

      if (validation) {
        this.files = e.target.files;
      } else {
        this.files = '';
        alert(message);
      }
    },
    // 파일 외의 인풋이 적절한지 체크
    checkValue() {
      let isAllValid = false;

      if (this.title && this.startDay && this.endDay && this.content) isAllValid = true;
      if (this.title.length > 10) isAllValid = false;
      if (this.endDay < this.startDay) isAllValid = false;

      if (isAllValid) {
        this.createHistory();
      } else {
        alert('입력을 확인해주세요!');
      }
    },
    //기록 등록 진행하기
    async createHistory() {
      let f = new FormData();
      f.append('title', this.title);
      f.append('content', this.content);
      f.append('startDay', this.startDay);
      f.append('endDay', this.endDay);
      f.append('userId', this.userId);
      let tempFiles = this.files;

      for (let c = 0; c < tempFiles.length; c++) {
        f.append('files', tempFiles[c]);
      }

      await axios.post(process.env.VUE_APP_MY_BASE_URL+`/history`, f);
      this.$emit('emitNeedToUpdate');
      this.emitModalOff();
    },
  },
  created() {
    this.userId = this.checkUserInfo.userId;
  },
};
</script>

<style scoped>
input,
textarea {
  font-family: 'Noto Sans KR', sans-serif;
  font-weight: 400;
}

.modal-card {
  position: relative;
  border-radius: 30px;
  background-color: white;
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

.title-input {
  width: 85%;
  font-size: 20px;
  margin: 5% 3% 0 5%;
  padding: 3%;
  border: none;
  border-radius: 10px;
  background-color: #f5f5f5;
  font-size: 1.5em;
  font-weight: bold;
}

.date-area {
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
  margin: 5% 3% 3% 5%;
  padding: 3%;
  border: none;
  border-radius: 10px;
  background-color: #f5f5f5;
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
