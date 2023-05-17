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
            <div class="board-title">
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
        }
    },
    async created() {
        await axios.get(`http://43.201.218.74/board/${this.board.boardId}/comment`)
        .then(response => {
                this.comments = response.data.length
            }
        );
        await axios.get(`http://43.201.218.74/board/${this.board.boardId}`)
        .then(response => {
            this.images = response.data.images.length;
        });
    },
};
</script>

<style scoped>
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
    color: #1B1C37;
}

.divider {
    height: 1px;
    background-color: #e7e7e7;
    
    margin-bottom: 5px;
}

.board-id {
    flex-basis: 8%;
    color: #1B1C37;
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
    height: 100%;
    flex-basis: 59%;
    text-align: left;
    padding-left: 35px;
}

.writer {
    flex-basis: 15%;
}

.create-time {
    flex-basis: 15%;
}

.hits {
    flex-basis: 8%;
}

.heart {
    flex-basis: 8%;
}

.comment-length {
    color: #E1306C;
    font-weight: 600;
    margin-left: 10px;
}


</style>