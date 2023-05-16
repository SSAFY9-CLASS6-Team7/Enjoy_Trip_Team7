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
          <select>
            <option value="none" selected>정렬 조건</option>
            <option value="heart">좋아요</option>
            <option value="hits">조회수</option>
          </select>
          <input type="text" class="keyword">
          <button><img src="@/assets/board_icons/search.svg"></button>

          <button><img src="@/assets/board_icons/write.svg"></button>
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
    }
  },
  async created(){
    await axios.get(`http://43.201.218.74/board?pageNo=&code=&condition=&anonymous=`)
    .then(response => this.boards = response.data);
  }
}
</script>

<style scoped>
.container {
  width: 100%;
  height: 764px;
  display: grid;
  grid-template-columns: 1fr 5fr 1fr;
  grid-template-rows: 2fr 1fr 11fr 1fr;
  grid-template-areas: 'left header right' 'left table-title right' 'left main right' 'left pagenation right';
  justify-items: stretch;
  min-width: 1900px;
}

.left-aside {
  grid-area: left;
  background: #cef5f8;
}

.right-aside {
  grid-area: right;
  background: #cef5f8;
  
}

.inner-header {
  grid-area: header;
    display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-areas: 'header-left header-right';
}

.header-left {
  grid-area: header-left;
}

.title {
  font-size: 30px;
  font-weight: 600;
  text-align: left;
  margin-left: 30px;
}


.tab-container {
  display: flex;
  justify-content: flex-start; 
  margin-top: 5px;
  margin-left: 30px;
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
}

.board-id {
  display: flex;
  align-items: center;
  justify-content: center;
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
