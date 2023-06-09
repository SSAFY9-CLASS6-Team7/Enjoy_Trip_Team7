<template>
  <div class="create-container">
    <div class="plan-modal" v-if="isModalOpen">
      <attraction-search-modal @addAttraction="addAttraction"></attraction-search-modal>
    </div>
    <div class="create-area">
      <div class="tab-title">글쓰기</div>
      <div class="category-anonymous-container">
        <div class="category-container">
          <div class="inner-title">말머리</div>
          <select class="condition-box" v-model="selectedCode">
            <option value="100" selected>자유</option>
            <option value="101">후기</option>
            <option value="102">추천</option>
            <option value="103">질문</option>
          </select>
        </div>
        <div class="anonymous-container">
          <div class="inner-title">익명</div>
          <input type="checkbox" id="anonymousFlag" v-model="anonymous" />
          <label for="anonymousFlag" class="is-anonymous"></label>
        </div>
      </div>

      <div class="title-input-container">
        <div class="inner-title">제 목</div>
        <input
          type="text"
          class="board-title-input"
          placeholder="제목을 입력하세요"
          v-model="title"
        />
      </div>

      <div class="content-input-container">
        <div class="inner-title">내 용</div>
        <div class="editor-container">
          <quill-editor
            class="editor"
            id="post-content"
            :content="content"
            @change="onEditorChange"
            required
          >
          </quill-editor>
        </div>
      </div>
      <div class="attraction-container">
        <div class="inner-title">관광지 선택</div>
        <div class="select-attraction">
          <div v-if="!selectedAttractionId" class="empty-attraction" @click="selectAttraction">
            선택된 관광지가 없습니다.
          </div>
          <board-attraction
            v-if="selectedAttractionId"
            :attractionId="this.selectedAttractionId"
            @click="selectAttraction"
          ></board-attraction>
          <div v-if="selectedAttractionId" class="attraction-buttons">
            <button class="delete-attraction" @click="deleteAttraction">관광지 삭제</button>
            <button class="reroll" @click="selectAttraction">다시 고르기</button>
          </div>
        </div>
      </div>

      <div class="upload-container">
        <div class="inner-title">사진 업로드</div>
        <div class="filebox">
          <label class="upload-search-button" for="file">찾아보기...</label>
          <div v-if="this.files.length == 0" class="upload-name">선택된 파일 없음</div>
          <div v-if="this.files.length > 0" class="upload-name">
            <span v-for="file in this.files" :key="file"> {{ file.name }} </span>
          </div>
          <input type="file" id="file" ref="files" @change="handleFileChange" multiple />
        </div>
      </div>

      <div class="buttons">
        <button class="cancel-button" @click="cancel">취 소</button>
        <button class="board-submit" @click="boardModify">확 인</button>
      </div>
    </div>
    <div class="left-aside"></div>
    <dif class="right-aside"></dif>
  </div>
</template>

<script>
import AttractionSearchModal from '../AttractionSearchModal.vue';
import BoardAttraction from '@/components/board/board_components/BoardAttraction.vue';
import { quillEditor } from 'vue-quill-editor';
import 'quill/dist/quill.core.css';
import 'quill/dist/quill.snow.css';
import 'quill/dist/quill.bubble.css';
import axios from 'axios';
import { mapGetters } from 'vuex';

export default {
  name: 'BoardUpdate',
  components: {
    quillEditor,
    BoardAttraction,
    AttractionSearchModal,
  },
  data() {
    return {
      selectedCode: '100',
      boardId: '',
      title: '',
      content: '',
      selectedAttractionId: '',
      userId: '',
      files: '',
      anonymous: false,
      isModalOpen: false,
    };
  },
  created() {
    this.files = [];
    this.userId = this.checkUserInfo.userId;

    this.boardId = this.$route.params.boardId;
    console.log('board Id : ' + this.boardId);
    axios.get(process.env.VUE_APP_MY_BASE_URL + '/board/' + this.boardId).then((response) => {
      console.log(response.data);
      this.selectedCode = response.data.board.code;
      this.title = response.data.board.title;
      this.content = response.data.board.boardContent;
      this.selectedAttractionId = response.data.board.attractionId;
      this.anonymous = response.data.board.anonymous;
    });
  },
  computed: {
    ...mapGetters('userStore', ['checkUserInfo']),
  },
  methods: {
    onEditorChange(value) {
      this.content = value.html;
    },
    async boardModify() {
      let f = new FormData();
      f.append('code', this.selectedCode);
      f.append('title', this.title);
      f.append('boardContent', this.content);
      f.append('anonymous', this.anonymous);
      console.log('관광지 번호 : ' + this.selectedAttractionId);
      if (this.selectedAttractionId != '') {
        f.append('attractionId', this.selectedAttractionId);
      } else {
        f.append('attractionId', '');
      }

      let uploadFiles = this.$refs.files.files;
      for (let i = 0; i < uploadFiles.length; i++) {
        f.append('files', uploadFiles[i]);
      }

      await axios
        .put(process.env.VUE_APP_MY_BASE_URL + '/board/' + this.boardId, f)
        .then((response) => {
          console.log(response);
          this.$router.push('/board');
        });
    },
    handleFileChange(event) {
      this.files = Array.from(event.target.files);
    },
    selectAttraction() {
      this.isModalOpen = true;
    },
    cancel() {
      this.$router.push('/board');
      this.$router.go(0);
    },
    addAttraction(attraction) {
      this.selectedAttractionId = attraction.attractionId;
      this.isModalOpen = false;
    },
    deleteAttraction() {
      this.selectedAttractionId = '';
    },
  },
};
</script>

<style scoped>
@font-face {
  font-family: 'S-CoreDream-3Light';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-3Light.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}

.create-container {
  display: grid;
  grid-template-columns: 1fr 5fr 1fr;
  grid-template-areas: 'left main right';
}

.create-area {
  grid-area: main;
  min-height: 81.2vh;
  min-width: 1200px;
}

.left-aside {
  grid-area: left;
}

.right-aside {
  grid-area: right;
}

.tab-title {
  font-size: 30px;
  font-weight: 600;
  text-align: left;
}

.category-anonymous-container {
  display: flex;
}

.category-container {
  display: flex;
  flex-direction: column;
  align-items: start;
}

.condition-box {
  -moz-appearance: none;
  -webkit-appearance: none;
  appearance: none;
  font-family: 'S-CoreDream-3Light';
  font-weight: 600;
  font-size: 14px;
  text-align: left;
  height: 30px;
  width: 120px;
  border: 2px solid #5c5c5c;
  border-radius: 4px;
  padding: 0 40px 1px 15px;
  margin-right: 20px;

  background-image: url('@/assets/board_icons/dropdown.svg');
  background-position: calc(100% - 10px) center;
  background-repeat: no-repeat;
}

.anonymous-container input {
  display: none;
}

.is-anonymous {
  border: 2px solid #5c5c5c;
  border-radius: 4px;
  width: 30px;
  height: 30px;
  position: relative;
}

input[id='anonymousFlag']:checked + label::after {
  content: '⭕';
  width: 27px;
  height: 30px;
  position: absolute;
  text-align: center;
  padding: 0 5px 0 0;
  left: 0;
  top: 0;
}

.anonymous-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.title-input-container {
  display: flex;
  flex-direction: column;
}

.board-title-input {
  outline: none;
  height: 40px;
  border: 2px solid #5c5c5c;
  border-radius: 4px;
  font-family: 'S-CoreDream-3Light';
  font-weight: 600;
  font-size: 16px;
  color: #9b9b9b;
  padding: 10px 5px 10px 5px;
}

.content-input-container {
  display: flex;
  flex-direction: column;
}

.editor-container {
  border: 2px solid #5c5c5c;
  border-radius: 4px;
}

.editor >>> .ql-editor {
  min-height: 22vh;
  overflow-y: hidden;
  font-family: 'S-CoreDream-3Light';
  font-weight: 600;
  font-size: 16px;
  color: #9b9b9b;
}

.editor >>> .ql-editor:not(:placeholder-shown) {
  font-family: 'S-CoreDream-3Light';
  font-weight: 600;
  font-size: 16px;
  color: #333333;
}

.select-attraction {
  margin: 10px 0 10px 0;
}

.empty-attraction {
  height: 100px;
  display: flex;
  justify-content: start;
  align-items: center;

  border: none;
  border-radius: 8px;
  background: #c7c6c6;

  font-family: 'S-CoreDream-3Light';
  font-weight: 800;
  font-size: 16px;
  padding: 0 0 0 10px;
}

.empty-attraction:hover {
  cursor: pointer;
}

.filebox {
  display: flex;
  align-items: center;

  height: 100px;
  width: 100%;
  padding: 0 0 0 10px;
  border: none;
  border-radius: 8px;
  background: #c7c6c6;
  font-family: 'S-CoreDream-3Light';
  font-weight: 800;
  font-size: 16px;
}

.upload-search-button {
  border-radius: 8px;
  padding: 5px 7px 5px 10px;
  margin: 0 10px 0 0;
  border: none;
  background: #9b9b9b;
}

.upload-search-button:hover {
  cursor: pointer;
}

.upload-name {
  outline: none;
  border: none;
  width: 80%;
  text-align: start;
  background: #c7c6c6;
  font-family: 'S-CoreDream-3Light';
  font-weight: 800;
  font-size: 16px;
}

.filebox input[type='file'] {
  position: absolute;
  width: 0;
  height: 0;
  padding: 0;
  overflow: hidden;
  border: 0;
}

.buttons {
  margin: 20px 0 20px 0;
}

.cancel-button {
  border: none;
  color: #333333;
  font-family: 'S-CoreDream-3Light';
  font-weight: 800;
  font-size: 18px;
  background: #9b9b9b;
  border-radius: 8px;
  padding: 10px 23px 10px 23px;
  min-width: 100px;
  margin-right: 40px;
}

.board-submit {
  border: none;
  color: #ffffff;
  font-family: 'S-CoreDream-3Light';
  font-weight: 800;
  font-size: 18px;
  /* background: linear-gradient(95.36deg, #E1306C 2.32%, #FF699A 68.42%, #FCAF45 104.98%); */
  background: linear-gradient(
    95.36deg,
    var(--main-grad1-color) 2.32%,
    var(--main-grad2-color) 68.42%,
    var(--main-grad3-color) 104.98%
  );
  background-blend-mode: darken;
  border-radius: 8px;
  padding: 10px 20px 10px 20px;
  min-width: 100px;
  margin-left: 40px;
}

.inner-title {
  font-size: 25px;
  font-weight: 600;
  text-align: left;
  margin: 20px 0 10px 0;
}

.reroll {
  border: none;
  color: #ffffff;
  font-family: 'S-CoreDream-3Light';
  font-weight: 800;
  font-size: 18px;
  /* background: linear-gradient(95.36deg, #e1306c 2.32%, #ff699a 68.42%, #fcaf45 104.98%); */
  background: linear-gradient(
    95.36deg,
    var(--main-grad1-color) 2.32%,
    var(--main-grad2-color) 68.42%,
    var(--main-grad3-color) 104.98%
  );
  background-blend-mode: darken;
  border-radius: 8px;
  padding: 10px 20px 10px 20px;
  min-width: 100px;
  margin: 0 0 0 40px;
}

.attraction-buttons {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
}

.delete-attraction {
  border: none;
  color: #333333;
  font-family: 'S-CoreDream-3Light';
  font-weight: 800;
  font-size: 18px;
  background: #9b9b9b;
  border-radius: 8px;
  padding: 10px 20px 10px 20px;
  min-width: 100px;
  margin-right: 40px;
}
</style>
