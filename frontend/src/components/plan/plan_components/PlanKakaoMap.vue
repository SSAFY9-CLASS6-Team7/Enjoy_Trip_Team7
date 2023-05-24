<template>
  <div>
    <div id="map"></div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'KakaoMap',
  components: {},
  data() {
    return {
      map: null,
      positions: [],
      markers: [],
      travelSido: 0,
    };
  },
  props: {
    groupedAttractionsArray: [], //날짜별 관광지들
    focused: Number, //몇번째 day를 보고 있는지
    plan: Object, //여행에 대한 정보 -> 대표여행지get
  },
  computed: {
    sidoCode() {
      return this.$store.getters.getSidoCode;
    },
    SidoLatLng() {
      const sido = this.sidoCode.find((item) => item.code === String(this.travelSido));
      const lat = sido ? sido.lat : 33.450701;
      const lng = sido ? sido.lng : 126.570667;
      return [lat, lng];
    },
  },
  watch: {
    focused() {
      this.$nextTick(() => {
        this.loadData();
      });
    },
  },
  created() {},
  async mounted() {
    // api 스크립트 소스 불러오기 및 지도 출력
    if (window.kakao && window.kakao.maps) {
      await this.loadData();
      // this.loadMap();
    } else {
      this.loadScript();
    }
  },
  methods: {
    setMapPinSrc(attraction) {
      var imgSrc = '';
      if (attraction.code === 12) imgSrc = require('@/assets/map_assets/blue_tour_pin.svg');
      else if (attraction.code === 15) imgSrc = require('@/assets/map_assets/orange_event_pin.svg');
      else if (attraction.code === 32) imgSrc = require('@/assets/map_assets/pink_stay_pin.svg');
      else if (attraction.code === 39)
        imgSrc = require('@/assets/map_assets/green_restaurant_pin.svg');
      else imgSrc = require('@/assets/map_assets/black_etc_pin.svg');
      const imgSize = new kakao.maps.Size(50, 70);
      const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);
      return markerImage;
    },
    async loadData() {
      this.travelSido = this.plan.travelArea;
      this.positions = [];
      await Promise.all(
        this.groupedAttractionsArray[this.focused - 1].map(async (attraction) => {
          var targetAttraction = {};
          const res = await axios.get('http://localhost/attraction/' + attraction.attractionId);
          // console.dir(res.data);
          targetAttraction = res.data;

          let obj = {};
          obj.title = targetAttraction.title;
          obj.latlng = new kakao.maps.LatLng(targetAttraction.lat, targetAttraction.lng);
          obj.image = this.setMapPinSrc(targetAttraction);

          this.positions.push(obj);
        })
      );
      this.loadMap();
      this.loadMaker();
    },

    // api 불러오기
    loadScript() {
      const script = document.createElement('script');
      script.src =
        '//dapi.kakao.com/v2/maps/sdk.js?appkey=' +
        process.env.VUE_APP_KAKAO_MAP_API_KEY +
        '&autoload=false';
      /* global kakao */
      script.onload = () => window.kakao.maps.load(this.loadMap);

      document.head.appendChild(script);
    },
    // 맵 출력하기
    loadMap() {
      const container = document.getElementById('map');
      const options = {
        center: new window.kakao.maps.LatLng(this.SidoLatLng[0], this.SidoLatLng[1]),
        level: 10,
      };

      this.map = new window.kakao.maps.Map(container, options);
      //   this.loadMaker();
      // this.loadData();
    },
    // 지정한 위치에 마커 불러오기
    loadMaker() {
      // 현재 표시되어있는 marker들이 있다면 marker에 등록된 map을 없애준다.
      this.deleteMarker();
      // 마커 이미지를 생성합니다
      //TODO: 임시로 이미지 넣어둠
      // const imgSrc = require('@/assets/map_assets/black_etc_pin.svg');
      // const imgSrc = require('@/assets/map_assets/blue_tour_pin.svg');
      // const imgSrc = require('@/assets/map_assets/green_restaurant_pin.svg');
      // const imgSrc = require('@/assets/map_assets/orange_event_pin.svg');
      // const imgSrc = require('@/assets/map_assets/pink_stay_pin.svg');
      // 마커 이미지의 이미지 크기 입니다
      // const imgSize = new kakao.maps.Size(24, 35);
      // const imgSize = new kakao.maps.Size(50, 70);
      // const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

      // 마커를 생성합니다
      this.markers = [];
      this.positions.forEach((position) => {
        const marker = new kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          position: position.latlng, // 마커를 표시할 위치
          title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          // image: markerImage, // 마커의 이미지
          image: position.image,
        });
        this.markers.push(marker);
      });
      console.log('마커수 ::: ' + this.markers.length);

      // 4. 지도를 이동시켜주기
      // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
      if (this.markers.length > 0) {
        const bounds = this.positions.reduce(
          (bounds, position) => bounds.extend(position.latlng),
          new kakao.maps.LatLngBounds()
        );

        this.map.setBounds(bounds);
      }
    },
    deleteMarker() {
      console.log('마커 싹 지우자!!!', this.markers.length);
      if (this.markers.length > 0) {
        this.markers.forEach((item) => {
          // console.log(item);
          item.setMap(null);
        });
      }
    },
  },
};
</script>

<style scoped>
#map {
  width: 100%;
  height: 700px;
}
</style>
