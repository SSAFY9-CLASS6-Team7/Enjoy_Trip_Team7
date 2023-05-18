import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    activeMenuItem: localStorage.getItem("activeMenuItem") || "",
    loginUser: {
      // 임시 값
      userId: 'TestUser1',
    },
  },
  getters: {
  },
  mutations: {
    activeMenuChange(state, data) {
      state.activeMenuItem = data;
      localStorage.setItem("activeMenuItem", data); // 로컬 스토리지에 값 저장
    }
  },
  actions: {
  },
  modules: {
  }
})
