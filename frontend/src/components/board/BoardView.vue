<template>
    <div class="container">
        <div class="left-aside"></div>
        
        

        <div class="content-container">
            <div class="header">
                <h2>헤더인데용 ??</h2>
            </div>
            
            <div class="board-content">
                <h2>글 보기인데용??</h2>
            </div>
            <div class="comment-input">
                <h2>댓글 입력칸인데용??</h2>
            </div>
            <div class="comments">
                <board-comment v-for="comment in comments" :key='comment' :comment="comment"></board-comment>
            </div>
        </div>

        <div class="right-aside"></div>
    </div>
    
</template>
  
<script>
import BoardComment from '@/components/board/board_components/BoardComment.vue'
export default {
  name : 'BoardView',
  components: { BoardComment },
  data(){
    return {
      comments: [],
      board: {},
    }
  },
  created(){

  }
}
</script>
  
<style scoped>
.container {
    width: 100%;
    display: grid;
    grid-template-columns: 1fr 5fr 1fr;
    grid-template-areas: 'left content right';
    justify-items: stretch;
    min-width: 1900px;

}
.left-aside {
    border: 1px solid #333333;
    grid-area: left;
}

.right-aside {
    border: 1px solid #333333;
    grid-area: right;
}

.header {
    min-height: 10vh;
    border: 1px solid #333333;
}

.content-container {
    grid-area: content;
    border: 1px solid #333333;
    height: 71.5vh;
    min-height: 800px;
}

.board-content {
    border: 1px solid #333333;
}

.comments {
    border: 1px solid #333333;
}



</style>
  