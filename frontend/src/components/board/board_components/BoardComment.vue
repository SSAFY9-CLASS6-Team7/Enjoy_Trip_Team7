<template>
    <div>
        <div class="devider"></div>
        <div class="comment-item">
            <div class="profile-image-container">
                <img v-if="this.profile == ''" src="@/assets/header_icon/profile.svg" class="profile-image">
                <img v-if="this.profile != ''" :src="this.profile" class="profile-image">
            </div>
            <div class="comment-content"> 
                <div class="comment-user">
                    <div v-if="this.anonymous" class="comment-user-id">익 명</div>
                    <div v-if="!this.anonymous" class="comment-user-id">{{ comment.userId }}</div>
                    <div class="comment-createtime">{{ formattedCreateTime(comment.createTime) }}</div>
                </div>
                <div class="comment-value" v-html="formatCommentContent(comment.commentContent)"> </div>
            </div>
            <div class="right">
                <button class="report">
                    <img src="@/assets/board_icons/report.svg" class="report-image">
                    신고
                </button>
                <button v-if="checkUser(comment.userId)" class="comment-delete" @click="commentDelete"><img src="@/assets/common/x_icon.svg" style="width: 8px"></button>
            </div>
        </div>
    </div>
    
</template>

<script>
import { mapGetters } from 'vuex';

export default {
    name: 'BoardComment',
    props: ['comment', 'anonymous', 'boardId'],
    components: {},
    computed: {
        ...mapGetters('userStore', ['checkToken', 'checkUserInfo']),
    },
    data() {
        return {
            message: '',
            profile: '',
        };
    },
    created() {},
    methods: {
        formattedCreateTime(time) {
            const date = new Date(time);
            const year = date.getFullYear().toString().slice(-2);
            const month = ('0' + (date.getMonth() + 1)).slice(-2);
            const day = ('0' + date.getDate()).slice(-2);
            const hours = ('0' + date.getHours()).slice(-2);
            const minutes = ('0' + date.getMinutes()).slice(-2);

            return `${year}/${month}/${day} ${hours}:${minutes}`;
        },
        formatCommentContent(content) {
            return content.replace(/\n/g, '<br>');
        },
        checkUser(userId) {
            if (this.checkToken){
                return this.checkUserInfo.userId === userId;
            }
            return false;
        },
        commentDelete(){
            this.$emit('commentDelete', process.env.VUE_APP_MY_BASE_URL+"/board/"+ this.boardId +"/comment/" + this.comment.commentId);    
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
.comment-item {
    display: grid;
    grid-template-columns: 1fr 18fr 1fr;
    grid-template-areas: 'left content right';
    margin-bottom: 15px;
}

.devider {
    height: 1px;
    background: #e7e7e7;
    margin-bottom: 10px;
}

.profile-image-container {
    display: flex;
    justify-content: center;
    grid-area: left;
}

.profile-image {
    width: 30px;
    height: 30px;
    margin-top: 10px;
}

.comment-content {
    grid-area: content;
}

.comment-user {
    display: flex;
    justify-content: start;
    align-items: end;
    margin-bottom: 8px;
}

.comment-user-id {
    margin-right: 10px;
    font-size: 15px;
    font-weight: 800;
}

.comment-createtime {
    font-family: 'S-CoreDream-3Light';
    font-size: 11px;
    font-weight: 200;
    color: #b1b1b1;
}

.comment-value {
    font-size: 14px;
    text-align: start;
}

.right {
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    grid-area: right;
}

.report {
    position: absolute !important;
    top: 0px;
    border: none;
    background: none;
    display: flex;
    font-size: 12px;
    font-weight: 600;
    align-items: center;
}

.comment-delete {
    border: none;
    background: none;
    margin-top: 10px;
}

</style>