<template>
    <div class='container'>
      <div class="left-aside"></div>
      <div class="inner-header">
        <div class="header-left">
          <div class="title"> 게 시 판</div>
          <div class="tab-container">
            <span :class="{ 'board-tab': true, 'bold-title': acitveBoardTab === '전체' }" @click="acitveBoardTab = '전체'"> 전체 </span>
            <span class="vertical-devider"></span>
            <span :class="{ 'board-tab': true, 'bold-title': acitveBoardTab === '자유' }" @click="acitveBoardTab = '자유'"> 자유 </span>
            <span class="vertical-devider"></span>
            <span :class="{ 'board-tab': true, 'bold-title': acitveBoardTab === '질문' }" @click="acitveBoardTab = '질문'"> 질문 </span>
            <span class="vertical-devider"></span>
            <span :class="{ 'board-tab': true, 'bold-title': acitveBoardTab === '후기' }" @click="acitveBoardTab = '후기'"> 후기 </span>
            <span class="vertical-devider"></span>
            <span :class="{ 'board-tab': true, 'bold-title': acitveBoardTab === '추천' }" @click="acitveBoardTab = '추천'"> 추천 </span>
          </div>
        </div>
        <div class="header-right">
          <select class="condition-box">
            <option value="none" selected>정렬 조건</option>
            <option value="heart">좋아요</option>
            <option value="hits">조회수</option>
          </select>
          <div class="keyword-container">
            <input type="text" class="keyword" placeholder="검색어를 입력하세요" @keyup.enter="goSearch">
            <img src="@/assets/board_icons/search.svg" @click="goSearch">
          </div>
          <button class="write-button"><img src="@/assets/board_icons/write.svg"></button>
        </div>
      </div>
      <div class="table-title"> 
        <div class="board-id">No.</div>
        <div class="board-category"> </div>
        <div class="is-image"> </div>
        <div class="board-title">제목</div>
        <div class="writer">글쓴이</div>
        <div class="create-time">작성일</div>
        <div class="hits">조회수</div>
        <div class="heart">좋아요</div>
      </div>

      <!-- child Component 영역 -->
      <board-content :boards='boards'></board-content>
      <div class="pagenation"> .. </div>
      <div class="right-aside"></div>
    </div>
</template>

<script >
import BoardContent from '@/components/board/board_components/BoardContent.vue'
import axios from 'axios'

export default {
  name : 'BoardList',
  components: { BoardContent },
  data(){
    return {
      acitveBoardTab: '전체',
      boards: [],
    }
  },
  methods: {
    writePage() {
      this.$router.push("create");
    },
    goSearch(){
      // TODO Search 
      console.log("search!")
    }
  },
  async created(){
    await axios.get(`http://43.201.218.74/board?pageNo=&code=&condition=&anonymous=`)
    .then(response => this.boards = response.data);
  }
}
</script>

<style scoped>
@font-face {
     font-family: 'S-CoreDream-3Light';
     src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-3Light.woff') format('woff');
     font-weight: normal;
     font-style: normal;
}
.container {
  width: 100%;
  /* height: 764px; */
  height:86.5vh;
  display: grid;
  grid-template-columns: 1fr 5fr 1fr;
  grid-template-rows: 2fr 1fr 11fr 1fr;
  grid-template-areas: 'left header right' 'left table-title right' 'left main right' 'left pagenation right';
  justify-items: stretch;
  min-width: 1900px;
  /* max-height: 86.5vh; */
}

.left-aside {
  grid-area: left;
}

.right-aside {
  grid-area: right;
}

.inner-header {
  grid-area: header;
    display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-areas: 'header-left header-right';
}

.header-left {
  display: flex;
  flex-direction: column;
  justify-content: center;
  grid-area: header-left;
  
}

.header-right {
  display: flex;
  justify-content: end;
  align-items: end;
  grid-area: header-right;
  padding-bottom: 15px;
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


.keyword-container {
  display: flex;
  height: 30px;
  border: 2px solid #5C5C5C;
  border-radius: 4px; 
  font-family: 'S-CoreDream-3Light';
  font-weight: 600;
  font-size: 14px;
  color: #9b9b9b;
  margin-right: 20px;
}

.keyword-container .keyword {
  outline: none;
  border: none;
  color: #9b9b9b;
  font-family: 'S-CoreDream-3Light';
  font-weight: 600;
  font-size: 14px;
  padding: 0 10px 0 10px;
}

.keyword-container .keyword:not(:placeholder-shown) {
  color: black;
}

.keyword-container img {
  padding: 3px 2px 3px 2px;
}

.keyword-container img:hover {
  cursor: pointer;
}

.write-button {
  border: 1px solid #0c0c0c;
  padding: 1px 2px 0 2px;
  background: #0c0c0c;
  border-radius: 4px;
}

.title {
  font-size: 30px;
  font-weight: 600;
  text-align: left;
}


.tab-container {
  display: flex;
  justify-content: flex-start; 
  margin-top: 5px;
  font-size: 20px;
  font-weight: 600;
}

.board-tab {
  color: #cccccc;
  margin-right: 10px;
}

.bold-title {
  color : #333333;
}

.board-tab:hover {
  cursor: pointer;
}

.vertical-devider {
  margin-top: 5px;
  height: 20px;
  border: 1.5px solid #cccccc;
  margin-right: 10px
}

.table-title {
  border-top: 2px solid #333333; /* 두꺼운 구분선 추가 */
  border-bottom: 1px solid #0c0c0c; /* 얇은 구분선 추가 */
  grid-area: table-title;
  display: flex;
  font-family: 'S-CoreDream-3Light';
  font-weight: 600;
}

.board-id {
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: 'S-CoreDream-3Light';
  font-weight: 600;
  flex-basis: 8%;
}

.board-category {
  flex-basis: 8%;
}

.is-image {
  flex-basis: 8%;

}

.board-title {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-basis: 60%;
}

.writer {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-basis: 15%;
}

.create-time {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-basis: 15%;
}

.hits {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-basis: 8%;
}

.heart {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-basis: 8%;
}

.pagenation {
  grid-area: pagenation;
  background: #55f52d;
}

</style>
