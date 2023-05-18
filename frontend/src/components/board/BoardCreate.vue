<template>
  <div class="create-container">
  <div class="create-area">
    <div class="tab-title">글 쓰기</div>
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
        <input type="checkbox" class="is-anonymous"/>
      </div>

    </div>

    <div class="title-input-container">
      <div class="inner-title">제 목</div>
      <input type="text" class="board-title-input" placeholder="제목을 입력하세요">
    </div>

    <div class="content-input-container">
      <div class="inner-title">내 용</div>
       <div class="editor-container">
          <quill-editor class="editor"
            id="post-content"
            :content='content'
            @change="onEditorChange"
            required>
          </quill-editor>
      </div>
    </div>
    <div class="attraction-container">
      <div class="inner-title">관광지 선택</div>
    </div>

    <button @click="boardSubmit">전송</button>
  </div>
  <div class="left-aside"></div>
  <dif class="right-aside"></dif>

  </div>
</template>

<script>
import { quillEditor } from "vue-quill-editor";
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import "quill/dist/quill.bubble.css";
import axios from 'axios';

export default {
  name: 'BoardCreate',
  components: {
    quillEditor,
  },
  data() {
    return {
      selectedCode: '100',
      content: '',
    };
  },
  created() {},
  methods: {
    onEditorChange(value) {
      console.log(value);
      this.content = value.html;
    },
    boardSubmit(){
      let f = new FormData();
      f.append('boardContent', this.content);
      axios.post('http://43.201.218.74/board', f).then(
        response => (
          console.log(response.data)
        )
      );
    },
  },
};
</script>

<style scoped>
@font-face {
     font-family: 'S-CoreDream-3Light';
     src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-3Light.woff') format('woff');
     font-weight: normal;
     font-style: normal;
}

.create-container {
  display: grid;
  grid-template-columns: 1fr 5fr 1fr;
  grid-template-areas: 'left main right';
}

.create-area {
  border: 1px solid #333333;
  grid-area: main;
  min-height: 81.2vh;
  min-width: 1200px;
}

.left-aside{
  border: 1px solid #333333;
  grid-area: left;
}

.right-aside {
  border: 1px solid #333333;
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
  border: 1px solid #333333;
}

.condition-box {
  -moz-appearance: none;
  -webkit-appearance: none;
  appearance: none;
  font-family: 'S-CoreDream-3Light';
  font-weight: 600;
  font-size: 14px;
  /* color: #9b9b9b; */
  text-align: left;
  height: 30px;
  border: 2px solid #5C5C5C;
  border-radius: 4px; 
  padding: 0 40px 1px 15px;
  margin-right : 20px;

  background-image: url('@/assets/board_icons/dropdown.svg');
  background-position: calc(100% - 10px) center;
  background-repeat: no-repeat;
}

.anonymous-container {
  display: flex;
  flex-direction: column;
  align-items: start;
  border: 1px solid #333333;
}

.title-input-container {
  display: flex;
  flex-direction: column;
  border: 1px solid #333333;
}

.board-title-input {
  outline: none;
  height: 40px;
  border: 2px solid #5C5C5C;
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
  border: 2px solid #5C5C5C;
  border-radius: 4px; 
}

.editor >>> .ql-editor {
  min-height: 30vh;
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



.inner-title {
  font-size: 25px;
  font-weight: 600;
  text-align: left;
}

</style>