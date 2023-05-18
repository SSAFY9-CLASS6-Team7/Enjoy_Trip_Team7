<template>
    <div>
        <div class="board-content-item"> 
            <div class="board-id">{{ board.boardId }}</div>
            <div class="board-category">
                <img :src="formatCategoryIcon(board.code)" />
                <!-- <img src='@/assets/board_icons/board_category/자유.svg'/> -->
            </div>
            <div class="is-image">
                <img v-if="images > 0" src="@/assets/board_icons/isImage.svg"/>
            </div>
            <div class="board-title" @click="boardView">
                {{ board.title }}
                <div v-if="comments > 0" class="comment-length">
                   [{{ comments }}]
                </div>
            </div>
            <div class="writer">{{ board.userId }} </div>
            <div class="create-time">{{ formatCreateTime(board.createTime) }}</div>
            <div class="hits">{{ board.hits }}</div>
            <div class="heart">{{ board.heart }}</div>
        </div>
        <div class="divider"/>
    </div>
</template>

<script>
import axios from "axios";
export default {
    name: 'BoardContentItem',
    components: {},
    props: ['board'],
    data() {
        return {
            message: '',
            comments: '',
            images: '',
        };
    },
    methods: {
        formatCreateTime(createTime) {
            const currentDate = new Date();
            const targetDate = new Date(createTime);
            if (
                targetDate.getFullYear() === currentDate.getFullYear() &&
                targetDate.getMonth() === currentDate.getMonth() &&
                targetDate.getDate() === currentDate.getDate()
            ) {
                // 오늘인 경우 HH:MM 형식으로 표시
                const hours = targetDate.getHours().toString().padStart(2, '0');
                const minutes = targetDate.getMinutes().toString().padStart(2, '0');
                return `${hours}:${minutes}`;
            } else {
                // 오늘이 아닌 경우 yy-mm-dd 형식으로 표시
                const year = targetDate.getFullYear().toString().slice(2);
                const month = (targetDate.getMonth() + 1).toString().padStart(2, '0');
                const date = targetDate.getDate().toString().padStart(2, '0');
                return `${year}.${month}.${date}`;
            }
        },
        formatCategoryIcon(code){
            let path = '';
            if (code === 100) {
                path = require('@/assets/board_icons/board_category/free.svg');
            }else if (code === 101){
                path = require('@/assets/board_icons/board_category/review.svg');
            }else if (code === 102){
                path = require('@/assets/board_icons/board_category/recommend.svg');
            }else if (code === 103) {
                path = require('@/assets/board_icons/board_category/question.svg');
            }
            return path;
        },
        fetchComments() {
            axios.get(`http://43.201.218.74/board/${this.board.boardId}/comment`)
            .then(response => this.comments = response.data.length);
        },
        fetchImages() {
            axios.get(`http://43.201.218.74/board/${this.board.boardId}`)
            .then(response => this.images = response.data.images.length);
        },
        boardView(){
            console.log(this.board.boardId);
            this.$router.push('/board/view/'+this.board.boardId);
        },
    },
    created() {
        this.fetchComments();
        this.fetchImages();
    },
    watch: {
        board(){
            this.fetchComments();
            this.fetchImages();
        }
    }
};
</script>

<style scoped>
@font-face {
     font-family: 'S-CoreDream-3Light';
     src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-3Light.woff') format('woff');
     font-weight: normal;
     font-style: normal;
}
.board-content-item {
    display: flex;
    height: 100%;
    align-items: center;
}

.board-content-item > *:not(.board-title) {
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}

.divider {
    height: 1px;
    background-color: #e7e7e7;
    
    margin-bottom: 5px;
}

.board-id {
    flex-basis: 8%;
    color: #1B1C37;
    font-family: 'S-CoreDream-3Light';
    font-weight: 300;
}

.board-category {
    flex-basis: 8%;
    color: #1B1C37;
}

.is-image {
    flex-basis: 8%;
    color: #1B1C37;
}

.board-title {
    display: flex;
    align-items: center;
    height: 100%;
    flex-basis: 59%;
    text-align: left;
    padding-left: 35px;
    color: #1B1C37;
    font-family: 'S-CoreDream-3Light';
    font-weight: 600;
}

.writer {
    font-family: 'S-CoreDream-3Light';
    font-weight: 300;
    flex-basis: 15%;
    color: #000000;
}

.create-time {
    color: #b1b1b1;
    font-size: 14px;
    flex-basis: 15%;
    font-family: 'S-CoreDream-3Light';
    font-weight: 200;
}

.hits {
    color: #b1b1b1;
    font-size: 14px;
    flex-basis: 8%;
    font-family: 'S-CoreDream-3Light';
    font-weight: 200;
}

.heart {
    color: #b1b1b1;
    font-size: 14px;
    flex-basis: 8%;
    font-family: 'Poppins', sans-serif;
    font-weight: 200;
}

.comment-length {
    color: #E1306C;
    font-weight: 600;
    margin-left: 10px;
}


</style>