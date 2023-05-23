<template>
    <div class='card' @click="attractionDetail">
        <div class="image-area">
            <img v-if="attraction.thumbnail != ''" :src="attraction.thumbnail" class="thumbnail">
            <img v-else src="@/assets/attraction_icon/no-image.png" class="thumbnail">

            <img :src="getCategoryIcon(attraction.code)" class ="category">
        </div>
        <div class="detail">
            <div class="attraction-title"> {{ attraction.title }}</div>
            <div class="devider"></div>
            <div class="address-container">
                <img src="@/assets/attraction_icon/address.svg" class="marker">
                <div class="address"> {{ attraction.address }}</div>
            </div>
        </div>
        
    </div>
</template>

<script>
export default {
    name: 'AttractionListItem',
    components: {},
    props: ['attraction'],
    data() {
        return {
            
        };
    },
    created() {},
    methods: {
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
        attractionDetail() {
            this.$router.push("/attraction/view/"+this.attraction.attractionId);
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

.card {
    margin: 10px 10px 10px 10px;
    border-radius: 5px;
    /* border: 2px solid red; */
    height: 300px;
    width: 230px;
    border-radius: 15px;
    overflow: hidden;
    box-shadow: -1px 5px 16px rgba(0, 0, 0, 0.13);
}

.card:hover {
    cursor: pointer;
}

.image-area {
    position: relative;
    height: 70%;
    width: 100%;
}

.thumbnail {
    width: 100%;
    height: 100%;
}

.category {
    position: absolute;
    bottom: 5%;
    right: 5%;
    z-index: 1;
}

.detail {
    width: 100%;
    height: 30%;
    padding: 5px 5px 5px 10px;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.attraction-title {
    padding: 0 0 4px 15px;
    width: 100%;
    font-family: 'S-CoreDream-3Light';
    font-weight: 800;
    text-align: start;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.devider {
    height: 2px;
    width: 95%;
    background-color: #e7e7e7;
    margin: 0 0 5px 0;
}


.address-container {
    display: flex;
    align-items: start;
}

.marker {
    padding: 2px 0 0 0;
}

.address {
    padding: 0 0 0 0;
    text-align: start;
    font-family: 'S-CoreDream-3Light';
    font-size: 14px;
    font-weight: 500;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
}


</style>