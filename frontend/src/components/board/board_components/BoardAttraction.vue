<template>
    <div class="board-attraction-container" @click.stop="moveToAttraction">
        
        <img v-if="!this.attraction.thumbnail" src="@/assets/attraction_icon/no-image.png" class="attraction-thumbnail">
        <img v-if="this.attraction.thumbnail" :src="this.attraction.thumbnail" class="attraction-thumbnail">

        <div class="text-content-container">
            <div class="text-content-header">
                <div class="attraction-title"> {{ this.attraction.title }}</div>
                <img v-if="this.attraction" src="@/assets/attraction_icon/address.svg" style="height: 18px; margin-right:5px;" >
                <div class="attraction-address">{{ this.attraction.address }}</div>
            </div>
            <div class="text-content-value">{{ this.attraction.overView }}</div>
        </div>
    </div>
</template>

<script>
import { mapActions } from 'vuex';
import axios from 'axios';

export default {
    name: "BoardAttraction",
    props: ['attractionId'],
    data() {
        return {
            attraction: '',
        }
    }, 
    methods: {
        ...mapActions(['activeMenuChange']),
        moveToAttraction() {
            this.activeMenuChange('attraction');
            this.$router.push("/attraction/view/"+this.attraction.attractionId);
        },
    },
    async created() {
        await axios.get(process.env.VUE_APP_MY_BASE_URL+'/attraction/'+this.attractionId)
        .then(response => {
            this.attraction = response.data;
        })
    },
    watch: {
        attractionId(newAttractionId) {
            axios.get(process.env.VUE_APP_MY_BASE_URL+'/attraction/'+newAttractionId)
            .then(response => {
                this.attraction = response.data;
            })
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
.board-attraction-container {
    width: 80%;
    border-radius: 8px;
    height: 150px;
    display: flex;
    align-items: center;
    padding: 10px 10px 10px 10px;
    margin: 20px 0 20px 10%;
    background: #FFF4E4;
    background-blend-mode: darken;
    box-shadow: 4px 4px 10px rgba(0, 0, 0, 0.25);
}

.board-attraction-container:hover {
    cursor: pointer;
}

.attraction-thumbnail {
    width: 20%;
    height: 90%;
    border-radius: 8px;
    margin:0 10px 0 0;
}

.image-container {
    width: 100%;
    height: 80%;
}

.text-content-container {
    height: 100%;
    width: 100%;
}

.text-content-header {
    display: flex;
    align-items: center;
    margin:0 0 10px 0;
}

.attraction-title {
    font-size: 22px;
    font-weight: 600;
    margin: 0 50px 0 0;
}

.attraction-address {
    font-size: 20px;
    font-weight: 400;
}

.text-content-value {
    height: 65%;
    line-height: 1.6;
    overflow: hidden;
    display: -webkit-box;
    -webkit-line-clamp: 3; 
    -webkit-box-orient: vertical;
    text-overflow: ellipsis;
}

</style>  