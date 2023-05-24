<template>
    <div class="container">
        <div class="left-aside"></div>
        <div class="main">
            <div class="header">
                <img src="@/assets/common/back.svg" @click="back" style="margin:0 20px 0 0;">
                <div class="inner-title" @click="back">목록으로</div>
            </div>

            <div class="devider"></div>

            <div class="attraction-detail">
                <div class="title-container">
                    <div class="attraction-title">{{  this.attraction.title  }}</div>
                    <img :src="getCategoryIcon(attraction.code)" class ="category">    
                </div>
                <div class="overview-container">
                    <div class="image-container">
                        <img v-if="this.attraction.thumbnail != ''" :src="this.attraction.thumbnail" class="thumbnail">
                    </div>
                    <div class="overview">
                        <div class="inner-title" style="color: #E93F78; font-weight: 900; margin:0 0 10px 0">소개</div>
                        <div class="overview-content"> {{ this.attraction.overView }}</div>
                    </div>
                </div>
            </div>
            <div class="devider"></div>

            <div class="map-container">
                <div class="inner-title" style="color: #E93F78; font-weight: 900; margin:0 0 10px 0"> <img src="@/assets/attraction_icon/address.svg" class="marker"> 위치</div>
                <div class="address"> {{ this.attraction.address }}</div>
                <attraction-kakao-map :location="this.location" :code="this.attraction.code"></attraction-kakao-map>
            </div>

        </div>
        <div class="right-aside"></div>
    </div>    
</template>
  
<script>
import AttractionKakaoMap from '@/components/attraction/attraction_components/AttractionKakaoMap.vue'
import axios from 'axios'

export default {
    name: "AttractionView",
    components: { AttractionKakaoMap },
    data() {
        return {
            attraction: '',
            location: '',
        }
    },
    methods: {
        back(){
            this.$router.go(-1);
        },
        getCategoryIcon(code) {
            if (code === 32) {
                return require("@/assets/attraction_icon/house.svg");
            } else if (code === 39) {
                return require("@/assets/attraction_icon/food.svg");
            } else if (code === 15) {
                return require("@/assets/attraction_icon/festival.svg");
            } else if (code === 12) {
                return require("@/assets/attraction_icon/tour.svg");
            } else {
                return require("@/assets/attraction_icon/etc.svg");
            }
        },
    },
    created() {
        axios.get(process.env.VUE_APP_MY_BASE_URL+'/attraction/'+this.$route.params.attractionId)
        .then(response => {
            this.attraction = response.data
            this.location = {
                lat: this.attraction.lat,
                lng: this.attraction.lng
                }
            }
        );
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

.container {
    display: grid;
    grid-template-columns: 2fr 5fr 2fr;
    grid-template-areas: 'left main right';
}

.left-aside {
    grid-area: left;
}
.right-aside {
    grid-area: right;
}

.devider {
    height: 1px;
    width: 98%;
    background-color: #e7e7e7;
    margin: 20px 0 20px 0;
}
.main {
    grid-area: main;
    min-height: 81.5vh;
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 0px 0px 30px 0;
}

.header {
    display: flex;
    width: 100%;
    min-width: 1200px;
    align-items: center;
    justify-content: start;
    margin: 25px 0 10px 0;
}

.header img:hover,
.header div:hover {
    cursor: pointer;
}

.inner-title {
    font-family: 'S-CoreDream-3Light';
    font-size: 26px;
    font-weight: 800;   
}

.attraction-detail {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
}

.title-container {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: center;
    margin: 10px 0 20px 0;
}

.attraction-title {
    font-family: 'S-CoreDream-3Light';
    font-size: 32px;
    font-weight: 800;
    margin:0 20px 0 0;
}

.category {
    width: 80px;
}

.overview-container {
    display: flex;
    height: 300px;
}

.overview-container .image-container {
    width: 40%;
    margin: 0 45px 0 0;
    border-radius: 10px;
    overflow: hidden;
}

.thumbnail {
    width: 100%;
    max-height: 300px;
}

.overview-container .overview {
    width: 60%;
    display: flex;
    flex-direction: column;
    align-items: start;
}

.overview-content {
    font-family: 'S-CoreDream-3Light';
    text-align: start;
    height: 100%;
    line-height: 2;
    overflow-y: scroll; 
    scrollbar-width: thin; 
    scrollbar-color: #888 #f1f1f1; 
}

.overview-content::-webkit-scrollbar {
    width: 8px;
}

.overview-content::-webkit-scrollbar-track {
    /* background-color: #a1a1a1; */
    border-radius: 8px;
}

.overview-content::-webkit-scrollbar-thumb {
    background-color: #E93F78;
    border-radius: 4px;
}

.map-container {
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: start;

}
.marker {
    width: 20px;
}

.address {
    font-family: 'S-CoreDream-3Light';
    font-size: 20px;
    font-weight: 800;   
    margin: 0 0 10px 0;
}


</style>
  