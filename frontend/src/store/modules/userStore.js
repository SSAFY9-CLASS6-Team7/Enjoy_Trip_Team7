import jwtDecode from "jwt-decode";
import router from "@/router";
import { login, findById, tokenRegeneration } from "@/api/lib/user.js";

const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    userInfo: null,
    isValidToken: false,
    nextPath: sessionStorage.getItem("nextPath")|| '',
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo;
    },
    checkToken: function (state) {
      return state.isLogin;
    },
    nextPath: function (state) {
      return state.nextPath;
    }
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_VALID_TOKEN: (state, isValidToken) => {
      state.isValidToken = isValidToken;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.userInfo = userInfo;
    },
    SET_NEXT_PATH: (state, nextPath) => {
      state.nextPath = nextPath;
    }
  },
  actions: {
    async userConfirm({ commit }, user) {
      await login(
        user,
        ({ data }) => {
          if (data.message === "success") {
            let accessToken = data["access-token"];
            let refreshToken = data["refresh-token"];
            commit("SET_IS_LOGIN", true);
            commit("SET_USER_INFO", data.loginUser);
            commit("SET_IS_VALID_TOKEN", true);
            sessionStorage.setItem("access-token", accessToken);
            sessionStorage.setItem("refresh-token", refreshToken);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_VALID_TOKEN", false);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getUserInfo({ commit, dispatch }, token) {
      let decodeToken = jwtDecode(token);
      await findById(
        decodeToken.userId,
        ({ data }) => {
          if (data.message === "success") {
            commit("SET_USER_INFO", data.userInfo);
          } else {
            console.log("유저 정보 없음!!!!");
          }
        },
        async (error) => {
          console.log("getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ", error.response.status);
          commit("SET_IS_VALID_TOKEN", false);
          await dispatch("tokenRegeneration");
        }
      );
    },
    async tokenRegeneration({ commit }) {
      await tokenRegeneration(
        ({ data }) => {
          if (data.message === "success") {
            let accessToken = data["access-token"];
            sessionStorage.setItem("access-token", accessToken);
            commit("SET_IS_VALID_TOKEN", true);
          } else if (data.message === "fail") {
            commit("SET_IS_LOGIN", false);
            commit("SET_USER_INFO", null);
            commit("SET_IS_VALID_TOKEN", false);
            router.push({ name: "login" });
          }
        },
      );
    },
    userLogout({ commit }) {
      sessionStorage.removeItem("access-token");
      sessionStorage.removeItem("refresh-token");
      commit("SET_USER_INFO", null);
      commit("SET_IS_LOGIN", false);
      commit("SET_IS_VALID_TOKEN", false);
    },
    nextPathChange({ commit }, nextPath) {
      sessionStorage.setItem("nextPath", nextPath);
      commit("SET_NEXT_PATH", nextPath);
    }
  },
};

export default userStore;
