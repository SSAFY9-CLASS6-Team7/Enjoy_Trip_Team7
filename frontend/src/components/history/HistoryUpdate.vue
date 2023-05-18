<template>
  <div class="viewModal">
    <div class="modal-card" v-if="history.history">
      <div class="img-area">
        <!-- 기존 이미지 불러오기(현재는 썸네일만) -->
        <img :src="imageSrc()" class="history-thumnail" @click="bigImage()" />
        <!-- TODO: 이미지 스와이프 후 마지막에 추가하게? -->
        <div class="file-area">
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
          <p v-for="file in files" :key="file.name" style="color: white">{{ file.name }}</p>
        </div>
      </div>
      <div class="detail-area">
        <input
          type="text"
          class="title-input"
          v-model.lazy="title"
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
          :style="{ backgroundColor: content ? 'white' : '' }"
        ></textarea>
        <div class="btn-area">
          <button class="doneBtn" @click="checkValue">
            <img class="done-btn-vector" src="@/assets/common/check_icon_black.svg" />
          </button>
          <button class="cancelBtn" @click="emitGotoView">
            <img class="cancel-btn-vector" src="@/assets/common/x_icon.svg" />
          </button>
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
      //기존 데이터
      history: Object,
      imageList: [],
      //새로운 데이터
      title: '',
      startDay: '',
      endDay: '',
      content: '',
      files: [],
    };
  },
  props: {
    historyId: Number,
  },
  methods: {
    //이미지 소스를 세팅하기
    imageSrc() {
      if (this.imageList.length > 0) {
        return require(this.imageList[0].imagePath);
      } else {
        let sampleSrc = (this.historyId % 5) + 1;
        return require('@/assets/sample/sample' + sampleSrc + '.jpg');
      }
    },
    //날짜 출력 형식을 변경하기
    formatDate(selectDate) {
      const dateParts = selectDate.split('-');
      const year = dateParts[0].substr(2);
      const month = dateParts[1];
      const day = dateParts[2];
      return `${year}.${month}.${day}`;
    },
    //update 모달로 변경하기
    emitGotoView() {
      this.$emit('emitSetType', 'view');
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
        this.updateHistory();
      } else {
        alert('입력을 확인해주세요!');
      }
    },
    //수정 진행하기
    async updateHistory() {
      let f = new FormData();
      f.append('title', this.title);
      f.append('content', this.content);
      f.append('startDay', this.startDay);
      f.append('endDay', this.endDay);

      let tempFiles = this.files;

      for (let u = 0; u < tempFiles.length; u++) {
        f.append('files', tempFiles[u]);
      }
      await axios.put(`http://43.201.218.74/history/` + this.historyId, f);
      this.$emit('emitNeedToUpdate');
      this.$emit('emitModalOff');
    },
  },
  //TODO: db에서 이미지 로딩으로 문제 발생시 나머지 데이터들도 로딩안됨
  async mounted() {
    await axios
      .get('http://43.201.218.74/history/' + this.historyId)
      .then((response) => (this.history = response.data));
    //미리 정보 로딩
    this.imageList = this.history.images;
    this.title = this.history.history.title;
    this.startDay = this.history.history.startDay;
    this.endDay = this.history.history.endDay;
    this.content = this.history.history.content;
  },
};
</script>

<style scoped>
.modal-card {
  position: relative;
  border-radius: 30px;
  background-color: white;
  display: flex;
}

.img-area {
  width: 500px;
  height: 500px;
  overflow: hidden;
  border-radius: 30px;
  background-color: rgba(0, 0, 0, 0.9);
  background-image: none;
  /*TODO: 어색하면 배경에 같은 이미지 블러해서 깔기*/
}

.img-area img {
  width: 100%;
  height: 100%;
  object-fit: scale-down;
}

.file-area {
  position: absolute;
  top: 0%;
  left: 0%;
  background-color: rgba(0, 0, 0, 0.4);
  width: 500px;
  height: 500px;
  border-radius: 30px;
  z-index: 10;
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

.file {
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

input,
textarea {
  font-family: 'Noto Sans KR', sans-serif;
  font-weight: 400;
}
</style>
