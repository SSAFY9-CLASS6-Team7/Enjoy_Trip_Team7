<template>
<div class="container">
    <delete-modal v-if="modal" @no="no" @modalOff="modalOff" @yes="yes" :link='this.deleteLink'></delete-modal>
    <image-modal v-if="imageDetail != ''" @imageOff="imageOff" :imagePath="this.imageDetail"></image-modal>
    <div class="left-aside"></div>
        
    <div class="content-container">
        <div class="board-title">
            <img src="@/assets/board_icons/board_category/announcement.svg" class="category"/>    
            
            <div class="board-title-value">
                {{ board.title }}
            </div>
        </div>
        <div class="header">
            <div class="header-left">
                <img src="@/assets/header_icon/profile.svg" class="profile-image">
                <div class="user-name">{{ board.userId }}</div>
            </div>
            <div class="header-right">
                <div class="buttons">
                    <button class="list-button" @click="moveList">목록</button>
                    <button v-if="this.checkToken && this.checkUserInfo.userId === this.board.userId" class="modify-button" @click='boardModify'><img src="@/assets/common/modify_icon.svg" style="height: 20px"></button>
                    <button v-if="this.checkToken && this.checkUserInfo.userId === this.board.userId" class="delete-button" @click='boardDelete'><img src="@/assets/common/x_icon.svg" style="width:14px"></button>
                </div>
                <div class="board-createtime">
                    {{ formattedCreateTime }}
                </div>
            </div>
        </div>
        <div class="devider"/>

        <div class="board-content">
            <div class="board-content-value" v-html="formatBoardContent(board.boardContent)"></div>
            <div v-if="images.length > 0" class="board-images">
                <swiper :options="swiperOption3">
                    <swiper-slide class="image-swiper-item" v-for="image in images" :key="image">
                        <div class="card">
                            <img :src="`${baseUrl}/imagePath/${image.imagePath}`" style="border-radius: 10px; height: 150px;" @click="detail(image)"/>
                        </div>
                    </swiper-slide>
                </swiper>
            </div>
        </div>
        
        <div class="devider"/>
    </div>

    <div class="right-aside"></div>
</div>

</template>

<script>
import DeleteModal from '@/components/board/board_components/DeleteModal.vue';
import ImageModal from '@/components/board/board_components/ImageModal.vue';
import { mapGetters } from 'vuex';
import axios from "axios";
import { Swiper, SwiperSlide } from "vue-awesome-swiper";
import "swiper/css/swiper.css"; // swiper CSS 파일 import

export default {
    name : 'AnnouncementView',
    components: { DeleteModal, Swiper, SwiperSlide, ImageModal },
    data(){
        return {
            baseUrl: process.env.VUE_APP_MY_BASE_URL,
            comments: [],
            board: {},
            profile: '',
            images: [],
            boardId: '',
            modal: false,
            imageDetail: '',
            deleteLink: '',
            swiperOption3: {
                slidesPerView: 8,
                spaceBetween: 2,
                direction: 'horizontal',
                pagination: {
                    el: ".swiper-pagination",
                    clickable: true
                },
            },
        }
    },
    computed:{
        formattedCreateTime() {
            const date = new Date(this.board.createTime);
            const year = date.getFullYear().toString().slice(-2);
            const month = ('0' + (date.getMonth() + 1)).slice(-2);
            const day = ('0' + date.getDate()).slice(-2);
            const hours = ('0' + date.getHours()).slice(-2);
            const minutes = ('0' + date.getMinutes()).slice(-2);

            return `${year}/${month}/${day} ${hours}:${minutes}`;
        },
      ...mapGetters('userStore', ['checkToken', 'checkUserInfo']),
    },
    methods: {
        formatBoardContent(content) {
            return content.replace(/\n/g, '<br>');
        },
        async boardDelete(){
            this.deleteLink = process.env.VUE_APP_MY_BASE_URL+'/announcement/'+this.board.boardId;
            this.modal = true;
        },
        boardModify() {
            this.$router.push('/announcement/update/'+this.board.boardId);
        },
        moveList() {
            this.$router.push("/announcement");
        },
        modalOff() {
            this.modal = false;
        },
        async yes(myLink) {
            await axios.delete(myLink);
            this.$router.push("/announcement");
        },
        detail(image){
            this.imageDetail = image.imagePath;
        },
        imageOff(){
            this.imageDetail = '';
        },
    },
    async created(){
        await axios.get(process.env.VUE_APP_MY_BASE_URL+"/announcement/"+ this.$route.params.boardId)
        .then(response =>{
            this.board = response.data.board;
            this.images = response.data.images;
            this.boardId = response.data.board.boardId;
        } );
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
    display: grid;
    grid-template-columns: 1fr 5fr 1fr;
    grid-template-areas: 'left content right';
    justify-items: stretch;
    min-width: 1900px;
}

.left-aside {
    grid-area: left;
}

.right-aside {
    grid-area: right;
}

.header {
    min-height: 8vh;
    display: grid;
    grid-template-areas: 'header-left header-right';
    grid-template-columns: 1fr 1fr;
}

.board-title {
    display: flex;
    padding: 20px 0 10px 10px;
    align-items: start;
}

.board-title-value {
    padding: 0;
    text-align: left;
    font-size: 20px;
    font-weight: 800;
}

.category {
    margin : 7px 10px 0 0;
}

.header-left {
    grid-area: header-left;
    display: flex;
    align-items: center;
    justify-content: start;
}

.profile-image {
    width: 30px;
    height: 30px;
    margin: 0 10px 0 10px;   
}

.user-name {
    font-size: 17px;
    font-weight: 600;
    margin-bottom : 2px;
}

.header-right {
    grid-area: header-right;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: end;
}

.buttons {
    display: flex;
    align-items: center;
    margin: 5px 0 7px 0;    
}

.list-button {
    border: none;
    border-radius: 5px;
    margin: 0px 20px 0px 0;
    padding: 2px 7px 2px 7px;
    background: #d6d6d6;
    font-size: 16px;
    font-family: 'S-CoreDream-3Light';
    font-weight: 600;
}

.modify-button {
    border: none;
    border-radius: 5px;
    margin: 0px 20px 0px 0;
    padding: 4px 7px 2px 7px;
    background: #d6d6d6;
}

.delete-button {
    border: none;
    border-radius: 5px;
    margin: 0px 10px 0px 0px;
    padding: 7px 8px 5px 8px;
    background: #d6d6d6;
}

.board-createtime {
    margin: 5px 10px 0 0;
    color: #b1b1b1;
    font-size: 16px;
    font-family: 'S-CoreDream-3Light';
    font-weight: 200;
}

.devider {
    border: 1.5px solid #333333;
    margin: 10px 0 10px 0;
}

.content-container {
    grid-area: content;
    min-height: 81.5vh;
}

.board-content {
    text-align: start;
    padding: 0 10px 0 10px;
    font-size : 17px;
    font-weight: 400;
    min-height: 250px;
    display: flex;
    flex-direction: column;
    position: relative;
}

.board-images {
    /* position: absolute; */
    width: 80%;
    margin: 20px 10% 30px 10%;
    height: 150px;
    bottom: 0;
    display: flex;
    align-items: center;
}

.card {
  height: 150px;
  border-radius: 10px;
  display: inline-block;
  margin: 0 10px 0 0;
  overflow: visible; 
}

.card:hover {
    cursor: pointer;
}

.swiper-wrapper{
    
    width: 100%;
}

.swiper-container {
    margin: 0 0 0 0;
    padding: 10px 0 10px 0;
    width: 100%;
    height: 170px;
}

.image-swiper-item {
    width: 100%;
    display: flex;
    align-items: center;
    padding: 0 20px 0 20px;
}

.swiper-button-next::after,
.swiper-button-prev::after {
  display: none;
}

.heart-container {
    position: relative;
    margin: 10px 0 0 0;
    display: flex;
    justify-content: center;
    align-items: center;
}

.heart {
    border:none;
    background: #d6d6d6;
    border-radius: 4px;
    padding: 9px 10px 8px 10px;
    margin: 0 20px 0 0;
}

.heart img {
    width: 25px;
}

.heart-count {
    font-size: 20px;
    font-family: 'S-CoreDream-3Light';
    font-weight: 600;
}

.report-button {
    position: absolute;
    right: 10px;
    background: #FFF4E4;
    padding: 5px 10px 5px 10px;
    border: none;
    border-radius: 4px;
}

.comment-input-container {
    display: flex;
    justify-content: stretch;
    background: #F3F3F3;
    background-blend-mode: darken;
    box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
    border-radius: 4px;
    margin-bottom: 20px;
    align-items: center;
}

.comment-input {
    resize: none;
    outline: none;
    border: none;
    background: #00000000;
    flex: 1;
    height: 70px;
    padding: 12px 15px 10px 10px;
    line-height: 1.8;
    font-family: 'S-CoreDream-3Light';
    font-weight: 400;
    font-size: 12px;
    white-space: pre-line;
    word-wrap: break-word;
    overflow-wrap: break-word;
}

.comment-submit {
    border:none;
    background: #E6E6E6;
    background-blend-mode: darken;
    box-shadow: 4px 4px 4px rgba(0, 0, 0, 0.25);
    margin: 0 20px 0 20px;
    border-radius: 8px;
    padding: 0 10px 0 10px;
    height: 30px;
}

.comment-submit-button {
    margin: 1px 5px 0 5px;
}

.comments {
    position: relative;
}

</style>