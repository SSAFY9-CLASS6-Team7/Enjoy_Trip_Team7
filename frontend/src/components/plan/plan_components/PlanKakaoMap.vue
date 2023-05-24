<template>
  <div>
    <div id="map"></div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'PlanKakaoMap',
  components: {},
  data() {
    return {
      map: null,
      positions: [],
      latlngs: [],
      markers: [],
      linePathArr: [],
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
      const sido = this.sidoCode.find((item) => item.code === String(this.plan.travelArea));
      const lat = sido ? sido.lat : 36.35536430944077;
      const lng = sido ? sido.lng : 127.29826735340974;
      return [lat, lng];
    },
  },
  watch: {
    focused: function () {
      this.loadData();
    },

    groupedAttractionsArray: function () {
      this.loadData();
    },
  },
  created() {},
  async mounted() {
    // api 스크립트 소스 불러오기 및 지도 출력
    if (window.kakao && window.kakao.maps) {
      this.loadMap();
    } else {
      this.loadScript();
    }
  },
  methods: {
    refresh() {
      this.positions = [];
      this.latlngs = [];
    },
    sortPosArray() {
      this.positions.sort((a, b) => a.sequence - b.sequence);
      this.latlngs = this.positions.map((item) => item.latlng);
      console.log('watch ing');
      console.dir(this.positions);
    },
    setMapPinSrc(attraction) {
      var imgSrc = '';
      if (attraction.code === 12) imgSrc = require('@/assets/map_assets/blue_tour_pin.svg');
      else if (attraction.code === 15) imgSrc = require('@/assets/map_assets/orange_event_pin.svg');
      else if (attraction.code === 32) imgSrc = require('@/assets/map_assets/pink_stay_pin.svg');
      else if (attraction.code === 39)
        imgSrc = require('@/assets/map_assets/green_restaurant_pin.svg');
      else imgSrc = require('@/assets/map_assets/black_etc_pin.svg');
      const imgSize = new kakao.maps.Size(50, 70);
      const imageOption = { offset: new kakao.maps.Point(23, 65) };
      const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize, imageOption);
      return markerImage;
    },
    async loadData() {
      this.refresh();
      await Promise.all(
        this.groupedAttractionsArray[this.focused - 1].map(async (attraction) => {
          var targetAttraction = {};
          const res = await axios.get('http://localhost/attraction/' + attraction.attractionId);
          targetAttraction = res.data;

          let obj = {};
          obj.title = targetAttraction.title;
          obj.latlng = new kakao.maps.LatLng(targetAttraction.lat, targetAttraction.lng);
          obj.image = this.setMapPinSrc(targetAttraction);
          obj.sequence = attraction.sequence;
          this.positions.push(obj);
        })
      );
      this.sortPosArray();
      this.loadMaker();
      this.drawLine();
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
    async loadMap() {
      const container = document.getElementById('map');
      const options = {
        center: new window.kakao.maps.LatLng(this.SidoLatLng[0], this.SidoLatLng[1]),
        level: 10,
      };

      this.map = new window.kakao.maps.Map(container, options);
      await this.loadData();
    },
    // 지정한 위치에 마커 불러오기
    loadMaker() {
      // 현재 표시되어있는 marker들이 있다면 marker에 등록된 map을 없애준다.
      this.deleteMarker();
      // 마커를 생성합니다
      this.markers = [];
      this.positions.forEach((position) => {
        const marker = new kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          position: position.latlng, // 마커를 표시할 위치
          title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          image: position.image,
        });
        this.markers.push(marker);
      });

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
      if (this.markers.length > 0) {
        this.markers.forEach((item) => {
          item.setMap(null);
        });
      }
    },
    deletePoly() {
      if (this.linePathArr.length > 0) {
        this.linePathArr.forEach((item) => {
          item.setMap(null);
        });
      }
    },
    drawLine() {
      this.deletePoly();
      this.linePathArr = [];
      var linePath = new kakao.maps.Polyline({
        path: this.latlngs, // 선을 구성하는 좌표배열
        strokeWeight: 5,
        strokeColor: '#E1306C',
        strokeOpacity: 0.9,
        strokeStyle: 'solid',
      });
      linePath.setMap(this.map);
      this.linePathArr.push(linePath);
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
