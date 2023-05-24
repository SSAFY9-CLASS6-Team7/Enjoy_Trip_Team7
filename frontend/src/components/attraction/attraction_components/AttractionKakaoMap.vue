<template>
    <div class="container">
        <div id="map"></div>
    </div>
</template>

<script>
export default {
    name: 'AttractionKakaoMap',
    components: {},
    props: ['location', 'code'],
    data() {
        return {
            map: null,
            localMarker: '',
            localLocation: '',
        };
    },
    watch: {
        location() {
            this.loadMap();
        }
    },
    methods: {
        loadScript() {
            const script = document.createElement("script");
            script.src =
            "//dapi.kakao.com/v2/maps/sdk.js?appkey=" + process.env.VUE_APP_KAKAO_MAP_API_KEY + "&autoload=false";
            script.onload = () => window.kakao.maps.load(this.loadMap);

            document.head.appendChild(script);
        },
        loadMap() {
            const container = document.getElementById("map");
            const options = {
                center: new window.kakao.maps.LatLng(this.location.lat, this.location.lng),
                level: 5,
            };

            this.map = new window.kakao.maps.Map(container, options);

            this.loadMarker();
        },
        loadMarker() {
            var imageSrc = this.getCategoryIcon(this.code),
                imageSize = new window.kakao.maps.Size(40, 60),
                imageOption = {offset: new window.kakao.maps.Point(20, 30)};


            var markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);
            const marker = new window.kakao.maps.Marker({
                map: this.map,
                position: new window.kakao.maps.LatLng(this.location.lat, this.location.lng),
                image: markerImage
            })   
            this.localMarker = marker;
        },
        getCategoryIcon(code) {
            if (code === 32) {
                return require("@/assets/map_assets/pink_stay_pin.svg");
            } else if (code === 39) {
                return require("@/assets/map_assets/green_restaurant_pin.svg");
            } else if (code === 15) {
                return require("@/assets/map_assets/orange_event_pin.svg");
            } else if (code === 12) {
                return require("@/assets/map_assets/blue_tour_pin.svg");
            } else {
                return require("@/assets/map_assets/black_etc_pin.svg");
            }
        },
    },
    created() {},
    async mounted() {
        if(window.kakao && window.kakao.maps) {
            await this.loadMap();
        }else {
            this.loadScript();
        }
    },
};
</script>

<style scoped>
.container {
    border-radius: 10px;
    overflow: hidden;
    width: 1200px;
    min-width: 1200px;
    height: 400px;
}

#map {
    width: 1200px;
    min-width: 1200px;
}

</style>