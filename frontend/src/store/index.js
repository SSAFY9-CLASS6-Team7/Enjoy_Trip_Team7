import Vue from 'vue';
import Vuex from 'vuex';
import createPersistedState from 'vuex-persistedstate';

import userStore from '@/store/modules/userStore';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    activeMenuItem: sessionStorage.getItem('activeMenuItem') || '',
    pageNo: sessionStorage.getItem('pageNo') || 1,
    searchKeyword: sessionStorage.getItem('searchKeyword') || '',
    condition: sessionStorage.getItem('condition') || '',
    boardTab: sessionStorage.getItem('boardTab') || '',
    sidoCode: [
      { code: '1', text: '서울', lat: 37.540705, lng: 126.956764 },
      { code: '2', text: '인천', lat: 37.469221, lng: 126.573234 },
      { code: '3', text: '대전', lat: 36.321655, lng: 127.378953 },
      { code: '4', text: '대구', lat: 35.798838, lng: 128.583052 },
      { code: '5', text: '광주', lat: 35.126033, lng: 126.831302 },
      { code: '6', text: '부산', lat: 35.198362, lng: 129.053922 },
      { code: '7', text: '울산', lat: 35.519301, lng: 129.239078 },
      { code: '8', text: '세종특별자치시', lat: 36.5040736, lng: 127.2494855 },
      { code: '31', text: '경기도', lat: 37.567167, lng: 127.190292 },
      { code: '32', text: '강원도', lat: 37.555837, lng: 128.209315 },
      { code: '33', text: '충청북도', lat: 36.628503, lng: 127.929344 },
      { code: '34', text: '충청남도', lat: 36.557229, lng: 126.779757 },
      { code: '35', text: '경상북도', lat: 36.248647, lng: 128.664734 },
      { code: '36', text: '경상남도', lat: 35.259787, lng: 128.664734 },
      { code: '37', text: '전라북도', lat: 35.716705, lng: 127.144185 },
      { code: '38', text: '전라남도', lat: 34.8194, lng: 126.893113 },
      { code: '39', text: '제주도', lat: 33.364805, lng: 126.542671 },
    ],
    contentTypeId: [
      { id: '12', text: '관광지' },
      { id: '15', text: '행사' },
      { id: '32', text: '숙박' },
      { id: '39', text: '음식점' },
      { id: '0', text: '기타' }, //위 4개에 해당하지 않는 모든 타입
    ],
    selectedSido: sessionStorage.getItem("sido") || [],
    selctedCategory: sessionStorage.getItem("category") || [],
    selectedKeyword: sessionStorage.getItem("keyword") || ''
  },
  getters: {
    getSidoCode: (state) => {
      return state.sidoCode;
    },
    getContentTypeId: (state) => {
      return state.contentTypeId;
    },
    getActiveMenu: (state) => {
      return state.activeMenuItem;
    },
    getPage: (state) => {
      return state.pageNo;
    },
    getSearchKeyword: (state) => {
      return state.searchKeyword;
    },
    getCondition: (state) => {
      return state.condition;
    },
    getSelectedSido: (state) => {
      return state.selectedSido;
    },
    getBoardTab: (state) => {
      return state.boardTab;
    }
  },
  mutations: {
    activeMenuChange(state, data) {
      state.activeMenuItem = data;
      sessionStorage.setItem('activeMenuItem', data);
      sessionStorage.setItem('pageNo', 1);
    },
    pageNoChange(state, page) {
      state.pageNo = page;
      sessionStorage.setItem('pageNo', page);
    },
    boardTabChange(state, tab) {
      state.boardTab = tab;
      sessionStorage.setItem('boardTab', tab);
    },
    conditionChange(state, condition) {
      state.condition = condition;
      sessionStorage.setItem('condition', '');
    },
    searchKeywordChange(state, searchKeyword) {
      state.searchKeyword = searchKeyword;
      sessionStorage.setItem('searchKeyword', searchKeyword);
    },
    resetBoardState(state) {
      state.boardTab = '',
      state.pageNo = 1,
      state.condition = '',
      state.searchKeyword = '',
      sessionStorage.setItem('boardTab', '');
      sessionStorage.setItem('pageNo', 1);
      sessionStorage.setItem('searchKeyword', ''),
      sessionStorage.setItem('condition', '')
    }
  },
  actions: {
    pageNoChange({ commit }, pageNo) {
      commit('pageNoChange', pageNo);
    },
    boardTabChange({ commit }, code) {
      commit('boardTabChange', code);
    },
    conditionChange({ commit }, condition) {
      commit('conditionChange', condition);
    },
    searchKeywordChange({ commit }, searchKeyword) {
      commit('searchKeywordChange', searchKeyword);
    },
    

  },
  modules: {
    userStore,
  },
  plugins: [
    createPersistedState({
      storage: sessionStorage,
    }),
  ],
});
