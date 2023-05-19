import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    activeMenuItem: localStorage.getItem('activeMenuItem') || '',
    loginUser: {
      // 임시 값
      userId: 'TestUser1',
    },
    sidoCode: [
      { code: '1', text: '서울' },
      { code: '2', text: '인천' },
      { code: '4', text: '대구' },
      { code: '5', text: '광주' },
      { code: '6', text: '부산' },
      { code: '8', text: '세종특별자치시' },
      { code: '31', text: '경기도' },
      { code: '32', text: '강원도' },
      { code: '33', text: '충청북도' },
      { code: '34', text: '충청남도' },
      { code: '35', text: '경상북도' },
      { code: '36', text: '경상남도' },
      { code: '37', text: '전라북도' },
      { code: '38', text: '전라남도' },
      { code: '39', text: '제주도' },
      { code: '1013900', text: '대전' },
      { code: '1013911', text: '울산' },
    ],
    contentTypeId: [
      { id: '12', text: '관광지' },
      { id: '15', text: '행사' },
      { id: '32', text: '숙박' },
      { id: '39', text: '음식점' },
      { id: '0', text: '기타' }, //위 4개에 해당하지 않는 모든 타입
    ],
  },
  getters: {
    getSidoCode: (state) => {
      return state.sidoCode;
    },
    getContentTypeId: (state) => {
      return state.contentTypeId;
    },
  },
  mutations: {
    activeMenuChange(state, data) {
      state.activeMenuItem = data;
      localStorage.setItem('activeMenuItem', data); // 로컬 스토리지에 값 저장
    },
  },
  actions: {},
  modules: {},
});
