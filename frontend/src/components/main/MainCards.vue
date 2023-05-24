<template>
    <div class="cards">
        <div class="cards-title">
        여 행 지 
        </div>
        <div class="attraction-swiper">  
            <swiper :options="swiperOption3">
                <div class="swiper-button-prev card-prev" slot="button-prev"> 
                    <img src="@/assets/left.svg" width="40px" height="40px">
                </div>
                <swiper-slide class="attraction-swiper-item" v-for="city in cities" :key="city">
                    <div class="card" @click="attractionSearch(city.code)">
                        <div class="card-overlay"></div> 
                        <div class="card-title"> {{city.title }} </div>
                        <img :src="city.path" style="border-radius: 10px; width: 100%;  height: 100%;" />
                    </div>
                </swiper-slide>
                <div class="swiper-button-next card-next" slot="button-next">
                    <img src="@/assets/right.svg" width="40px" height="40px" style="right:30px">
                </div>
            </swiper>
        </div>
    </div>
</template>

<script>
import { Swiper, SwiperSlide } from "vue-awesome-swiper";
import "swiper/css/swiper.css"; // swiper CSS 파일 import
import { mapActions } from 'vuex';

export default {
    name: 'MainCards',
    components: {Swiper, SwiperSlide},
    data() {
        return {
            message: '',
            cities: [
                {
                title: "서울",
                path: require("@/assets/main_attractions/seoul.png"),
                code: '1'
                },
                {
                title: "대전",
                path: require("@/assets/main_attractions/daejeon.png"),
                code: '3'
                },
                {
                title: "대구",
                path: require("@/assets/main_attractions/daegu.png"),
                code: '4'
                },
                {
                title: "부산",
                path: require("@/assets/main_attractions/busan.png"),
                code: '6'
                },
                {
                title: "인천",
                path: require("@/assets/main_attractions/incheon.png"),
                code: '2'
                },
                {
                title: "울산",
                path: require("@/assets/main_attractions/ulsan.png"),
                code: '7'
                },
                {
                title: "광주",
                path: require("@/assets/main_attractions/gwangju.jpg"),
                code: '5'
                },
                {
                title: "강원도",
                path: require("@/assets/main_attractions/gangwondo.jpg"),
                code: '32'
                },
                {
                title: "경기도",
                path: require("@/assets/main_attractions/gyeonggido.jpg"),
                code: '31'
                },
                {
                title: "충청남도",
                path: require("@/assets/main_attractions/chungnam.jpg"),
                code: '34'
                },
                {
                title: "충청북도",
                path: require("@/assets/main_attractions/chungbuk.jpg"),
                code: '33'
                },
                {
                title: "전라남도",
                path: require("@/assets/main_attractions/jeonnam.jpg"),
                code: '38'
                },
                {
                title: "전라북도",
                path: require("@/assets/main_attractions/jeonbuk.jpg"),
                code: '37'
                },
                {
                title: "경상남도",
                path: require("@/assets/main_attractions/gyeongnam.jpg"),
                code: '36'
                },
                {
                title: "경상북도",
                path: require("@/assets/main_attractions/gyeongbuk.jpg"),
                code: '35'
                },
                {
                title: "제주도",
                path: require("@/assets/main_attractions/jeju.jpg"),
                code: '39'
                },
            ],
            swiperOption3: {
                slidesPerView: 6,
                spaceBetween: 10,
                direction: 'horizontal',
                pagination: {
                el: ".swiper-pagination",
                clickable: true
                },
                navigation: { 
                nextEl: '.card-next', 
                prevEl: '.card-prev' 
                } 
            }
        };
    },
    created() {},
    methods: {
        ...mapActions(['attractionSidoChange', 'activeMenuChange']),
        attractionSearch(code){
        this.attractionSidoChange([code]);
        this.activeMenuChange('attraction');
        this.$router.push("/attraction");
        }
    },
};
</script>

<style scoped>
.page-navigation {
  flex-grow: 1;
  color: #e2e2e2;
  display: flex;
  justify-content: center;
  align-items: center;
}

.image-swiper .swiper-wrapper {
  display: flex;
  flex-direction: row;
}

.cards {
  padding: 20px 10px 0 50px;
  margin: 0 100px 0px 100px;
}

.cards-title {
  padding: 50px 0 30px 20px;
  text-align: left;
  font-size: 28px;
  font-weight: 600;
}

.card {
  position: relative;
  width: 220px;
  height: 250px;
  filter: drop-shadow(4px 4px 10px rgba(0, 0, 0, 0.53));
  border-radius: 10px;
  display: inline-block;
  margin: 0 10px 0 0;
  overflow: visible; 
}

.card:hover {
  cursor: pointer;
}

.card-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  border-radius: 10px;
  background: linear-gradient(151.23deg, rgba(0, 0, 0, 0.6) 1.83%, rgba(44, 44, 44, 0.497032) 17.59%, rgba(255, 255, 255, 0) 53.07%, rgba(235, 235, 235, 0.0471495) 69.07%)
}

.attraction-swiper .swiper-wrapper{
  margin: 20px 0 20px 0;
}

.attraction-swiper >>> .swiper-wrapper {
  padding: 15px 0 30px 0 ;
}

.card-title {
  position: absolute;
  left: 28px;
  top: 17px;
  color: #e2e2e2;
  font-size: 22px;
  font-weight: 600;
}

.swiper-button-next::after,
.swiper-button-prev::after {
  display: none;
}

</style>