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
      { value: null, text: '선택하세요' },
      { value: '1', text: '서울' },
      { value: '2', text: '인천' },
      { value: '4', text: '대구' },
      { value: '5', text: '광주' },
      { value: '6', text: '부산' },
      { value: '8', text: '세종특별자치시' },
      { value: '31', text: '경기도' },
      { value: '32', text: '강원도' },
      { value: '33', text: '충청북도' },
      { value: '34', text: '충청남도' },
      { value: '35', text: '경상북도' },
      { value: '36', text: '경상남도' },
      { value: '37', text: '전라북도' },
      { value: '38', text: '전라남도' },
      { value: '39', text: '제주도' },
      { value: '1013900', text: '대전' },
      { value: '1013911', text: '울산' },
    ],
  },
  getters: {
    getSidoCode: (state) => {
      return state.sidoCode;
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
