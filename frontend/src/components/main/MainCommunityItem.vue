<template>
    <div>
        
        <div class="board-content-item"> 
            <div class="board-category">
                <img :src="formatCategoryIcon(item.code)" />
            </div>
            <div class="board-title" @click="boardView">
                {{ item.title }}
            </div>
            <div v-if="!item.anonymous" class="writer"> {{ this.nickname }} </div>
            <div v-if="item.anonymous" class="writer"> 익 명 </div>
            <div class="create-time">{{ formatCreateTime(item.createTime) }}</div>
            <div class="heart">{{ item.heart }}</div>
        </div>
        <div class="devider"/>
    </div>
</template>

<script>
import axios from "axios";
export default {
    name: 'MainCommunityItem',
    components: {},
    props: ['item'],
    data() {
        return {
            message: '',
            nickname: '',
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
        boardView() {
            this.$router.push("board/view/"+this.item.boardId);
        },
        fetchUserInfo() {
            axios.get(process.env.VUE_APP_MY_BASE_URL+`/user/${this.item.userId}`)
            .then(response => {
                this.nickname = response.data.userInfo.nickname;
            });
        },
    },
    created() {
        this.fetchUserInfo();  
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
.board-content-item {
    display: flex;
    height: 100%;
    align-items: center;
    padding: 10px 0 10px 0;
}

.board-content-item > *:not(.board-title) {
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}

.devider {
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

.board-title:hover {
    cursor: pointer;
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

.heart {
    color: #b1b1b1;
    font-size: 14px;
    flex-basis: 8%;
    font-family: 'S-CoreDream-3Light';
    padding: 0 10px 0 0;
    font-weight: 200;
}

.comment-length {
    color: #E1306C;
    font-weight: 600;
    margin-left: 10px;
}


</style>