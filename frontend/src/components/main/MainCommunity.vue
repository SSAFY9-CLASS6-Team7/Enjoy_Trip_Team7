<template>
    <div class="community-container">
      
    <div class="hits-board-container">
      <div class="community-container-title">
        베스트 게시글
      </div>
      <div class="more" @click="toBest">...더보기</div>
      <div class="hits-board-contents">
        <div class="header">
          <div class="header-title">제목</div>
          <div class="header-writer">작성자</div>
          <div class="header-createTime">작성시간</div>
          <div class="header-heart">좋아요</div>
        </div>
        <main-community-item v-for="item in hits" :key="item" :item=item ></main-community-item>
      </div>
    </div>
    <div class="new-board-container">
      <div class="community-container-title">
        최신 게시글
      </div>
      <div class="more" @click="toNew">...더보기</div>
      <div class="new-board-contents">
        <div class="header">
          <div class="header-title">제목</div>
          <div class="header-writer">작성자</div>
          <div class="header-createTime">작성시간</div>
          <div class="header-heart">좋아요</div>
        </div>
        <main-community-item v-for="item in newBoard" :key="item" :item=item ></main-community-item>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { mapActions } from 'vuex';
import MainCommunityItem from "@/components/main/MainCommunityItem";
export default {
    name: 'MainCommunity',
    components: { MainCommunityItem },
    data() {
        return {
            boards: '',
            hits: [],
            newBoard: [],
        };
    },
    async created() {
      await axios.get(process.env.VUE_APP_MY_BASE_URL+`/board?pageNo=1&code=&condition=heart&anonymous=&keyword=`)
      .then(response => {
          this.boards = response.data.boards;
          if (this.boards.length > 5) {
            this.hits = this.boards.slice(0, 5);
          }else {
            this.hits = this.boards.slice(0, this.boards.length);
          }
        }
      );
      await axios.get(process.env.VUE_APP_MY_BASE_URL+`/board?pageNo=1&code=&condition=&anonymous=&keyword=`)
      .then(response => {
          this.boards = response.data.boards;
          if (this.boards.length > 5) {
            this.newBoard = this.boards.slice(0, 5);
          }else {
            this.newBoard = this.boards.slice(0, this.boards.length);
          }
        }
      );
    },
    methods: {
      ...mapActions(['conditionChange']),
      toBest() {
        this.conditionChange('heart');
        this.$router.push("/board");
      },
      toNew() {
        this.$router.push("/board");
      }
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

.community-container {
  display: flex;
  justify-content: space-between;
  padding: 20px 50px 0 50px;
  margin: 0 100px 50px 100px;
}

.community-container .community-container-title {
  padding: 20px 0 0 0;
  text-align: left;
  font-size: 28px;
  font-weight: 600;
}

.more {
  text-align: right;
  padding: 0 20px 10px 0;
  font-size: 18px;
  font-weight: 600
}

.more:hover {
  cursor: pointer;
}

.hits-board-container,
.new-board-container {
  width: 48%;
}

.hits-board-contents {
  width: 100%;
  height: 300px;
}

.new-board-contents {
  width: 100%;
  height: 300px;
}

.header {
  display: flex;
  border-top: 1px solid #333333;
  border-bottom: 2px solid #333333;
  padding: 5px 10px 5px 10px;
  width: 100%;
}

.header div {
  font-size : 18px;
  font-weight: 800;
  font-family: 'S-CoreDream-3Light';
}

.header-title {
  margin:0 0 0 300px;
}

.header-writer {
  margin:0 30px 0 250px;
}

.header-createTime {
  margin:0 10px 0 30px;
}

.header-heart {
  margin: 0 0 0 22px;
}

</style>