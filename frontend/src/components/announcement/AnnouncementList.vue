<template>
    <div class='container'>
      <div class="left-aside"></div>
      <div class="inner-header">
        <div class="header-left">
          <div class="title"> 공 지 사 항</div>
        </div>
        <div class="header-right">
          <div class="keyword-container">
            <input type="text" class="keyword" placeholder="검색어를 입력하세요" @keyup.enter="goSearch" v-model="searchKeyword">
            <img src="@/assets/board_icons/search.svg" @click="goSearch">
          </div>
          <button v-if="isAdmin" class="write-button" @click="createBoard"><img src="@/assets/board_icons/write.svg"></button>
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
      </div>

      <announcement-content :announcements='announcements'></announcement-content>

      <announcement-pagination class="pagination" @pageChange="pageChanged" :pageResult="pageResult"></announcement-pagination>
      <div class="right-aside"></div>

    </div>
</template>

<script >
import { mapActions, mapGetters } from 'vuex';
import AnnouncementContent from '@/components/announcement/announcement_components/AnnouncementContent.vue'
import AnnouncementPagination from '@/components/announcement/announcement_components/AnnouncementPagination.vue'
import axios from 'axios'

export default {
  name : 'AnnouncementList',
  components: { AnnouncementContent, AnnouncementPagination },
  data(){
    return {
      activeBoardTab: '',
      searchKeyword: '',
      selectedCondition: '',
      pageNo: 1,
      announcements: [],
      pageResult: {},
      isAdmin: false,
    }
  },
  computed: {
      ...mapGetters('userStore', ['checkToken', 'checkUserInfo']),
      ...mapGetters(['getPage','getSearchKeyword']),
  },
  methods: {
    ...mapActions(['pageNoChange', 'searchKeywordChange']),
    goSearch(){
      axios.get(process.env.VUE_APP_MY_BASE_URL+`/announcement?pageNo=1&keyword=${this.searchKeyword}`)
      .then(response => {
        this.announcements = response.data.boards
        this.pageResult = response.data.pageResult;
        this.pageResult.pageNo = 1;
        this.pageNo = 1;
        this.pageNoChange(1);
        this.searchKeywordChange(this.searchKeyword);
        })
    },
    
    pageChanged(clickedPage){
      axios.get(process.env.VUE_APP_MY_BASE_URL+`/announcement/?pageNo=${clickedPage}&keyword=${this.searchKeyword}`)
      .then( response => {
        this.boards = response.data.boards;
        this.pageNo = clickedPage;
        this.pageResult = response.data.pageResult;
        this.pageNoChange(clickedPage);
      })
    },
    createBoard(){
      if (this.checkToken) {
        this.$router.push("/announcement/create");
      }else {
        alert("관리자만 공지를 작성할 수 있습니다!");
        this.$router.push("/user/login");
      }
    }

  },
  async created(){
    this.pageNo = this.getPage;
    this.searchKeyword = this.getSearchKeyword;
    if(this.checkUserInfo !== null) {
      if (this.checkUserInfo.userId === 'TestUser1' || this.checkUserInfo.userId === 'TestUser2') {
        this.isAdmin = true;
      }
    }
    await axios.get(process.env.VUE_APP_MY_BASE_URL+`/announcement?pageNo=${this.pageNo}&keyword=${this.searchKeyword}`)
    .then(response => {
      this.announcements = response.data.boards;
      this.pageResult = response.data.pageResult;
      if (this.pageResult.pageNo > this.pageResult.lastPage) {
        this.pageResult.pageNo = this.pageResult.lastPage;
        this.pageNoChange(this.pageResult.pageNo);
        this.pageChanged(this.pageResult.lastPage);
        }
      }
    );
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
  height: 81.5vh;
  display: grid;
  grid-template-columns: 1fr 5fr 1fr;
  grid-template-rows: 2fr 1fr 11fr 1fr;
  grid-template-areas: 'left header right' 'left table-title right' 'left main right' 'left pagination right';
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
  min-height: 107px;
  grid-area: header;
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-areas: 'header-left header-right';
}

.header-left {
  display: flex;
  flex-direction: column;
  justify-content: center;
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
  min-height: 51px;
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

.pagination {
  grid-area: pagination;
}

</style>
