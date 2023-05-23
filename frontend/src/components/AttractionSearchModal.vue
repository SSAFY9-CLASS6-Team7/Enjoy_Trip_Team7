<template>
  <!-- 사용 시 부모 쪽에서 emitAddAttraction 이벤트와 emitModalOff 이벤트를 @으로 받으면 됨 -->
  <div class="att-search-modal">
    <div class="search-blackbg" @click="emitModalOff"></div>
    <div class="search-whitebg">
      <div class="top-bar"></div>
      <div class="search-modal-area">
        <div class="content-area">
          <div class="dropdown-area">
            <div class="dropdown">
              <button class="dropdown-toggle" @click="toggleDropdown('sido')">
                시·도 선택
                <img src="@/assets/board_icons/dropdown.svg" />
              </button>
              <ul class="dropdown-menu" v-show="sidoDropdownOpen">
                <div class="dropdown-li" v-for="sido in sidoCode" :key="sido.code">
                  <li>
                    <label
                      ><input
                        type="checkbox"
                        name="sido"
                        :value="sido.code"
                        @change="sidoUpdate()"
                      />
                      {{ sido.text }}
                    </label>
                  </li>
                </div>
              </ul>
            </div>
            <div class="dropdown">
              <button class="dropdown-toggle" @click="toggleDropdown('category')">
                카테고리 선택
                <img src="@/assets/board_icons/dropdown.svg" />
              </button>
              <ul class="dropdown-menu" v-show="categoryDropdownOpen">
                <div
                  class="dropdown-li"
                  v-for="category in contentTypeId"
                  :key="category.id"
                  @change="cateUpdate()"
                >
                  <li>
                    <label
                      ><input type="checkbox" name="category" :value="category.id" />
                      {{ category.text }}
                    </label>
                  </li>
                </div>
              </ul>
            </div>
          </div>
          <div class="keyword-search-area">
            <div class="search-box">
              <input type="text" placeholder="검색어 입력" v-model.lazy="keyword" />
              <img src="@/assets/plan_icon/search.svg" @click="keywordSearch" />
            </div>
          </div>
          <div class="att-list-area">
            <div class="att-list">
              <div
                class="search-results"
                v-for="result in searchResults"
                :key="result.attractionId"
              >
                <plan-search-item
                  :attraction="result"
                  @setFocusedAttId="setFocusedAttId"
                ></plan-search-item>
              </div>
              <infinite-loading
                v-if="infiniteScrollFeature"
                @infinite="infiniteHandler"
              ></infinite-loading>
            </div>
          </div>
          <div class="att-detail-area">
            <div class="att-detail">
              <div class="att-detail-view" v-if="focusedAttId !== 0">
                <plan-search-detail
                  :attractionId="focusedAttId"
                  @emitAddAttraction="emitAddAttraction"
                ></plan-search-detail>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import PlanSearchItem from './plan/plan_components/PlanSearchItem.vue';
import PlanSearchDetail from './plan/plan_components/PlanSearchDetail.vue';
import InfiniteLoading from 'vue-infinite-loading';
import axios from 'axios';
import qs from 'qs';

export default {
  name: 'AttractionSearchModal',
  components: {
    PlanSearchItem,
    PlanSearchDetail,
    InfiniteLoading,
  },
  data() {
    return {
      sido: [],
      category: [],
      keyword: '',
      searchResults: [],
      focusedAttId: 0,
      pageNo: 1,
      pageResult: {},
      sidoDropdownOpen: 0,
      categoryDropdownOpen: 0,
      infiniteScrollFeature: false,
    };
  },
  computed: {
    sidoCode() {
      return this.$store.getters.getSidoCode;
    },
    contentTypeId() {
      return this.$store.getters.getContentTypeId;
    },
  },
  methods: {
    //버튼 클릭시 해당하는 드롭다운을 토글
    toggleDropdown(dropdownStyle) {
      if (dropdownStyle === 'sido') {
        let tmp = (this.sidoDropdownOpen + 1) % 2;
        this.sidoDropdownOpen = tmp;
      } else if (dropdownStyle === 'category') {
        let tmp = (this.categoryDropdownOpen + 1) % 2;
        this.categoryDropdownOpen = tmp;
      }
    },
    //인풋이 변경되면 sido 배열을 업데이트
    sidoUpdate() {
      const checkedInputs = Array.from(document.querySelectorAll('input[name="sido"]:checked'));
      this.sido = checkedInputs.map((input) => input.value);
    },
    //인풋이 변경되면 category 배열을 업데이트
    cateUpdate() {
      const checkedInputs = Array.from(document.querySelectorAll('input[name="category"]:checked'));
      this.category = checkedInputs.map((input) => input.value);
      //기타 처리
      if (this.category.includes('0')) this.category.push(...['14', '25', '28', '38']);
    },
    //클릭한 서치 결과의 관광지 아이디 세팅
    setFocusedAttId(id) {
      this.focusedAttId = id;
    },
    //키워드로 검색하기
    async keywordSearch() {
      this.infiniteScrollFeature = false; //무한 스크롤 기능 껐다가
      await this.refresh();
      this.infiniteScrollFeature = true; //무한 스크롤 기능 다시 켜주기
    },
    //초기화 작업(비동기처리를 위해 분리)
    refresh() {
      this.sidoDropdownOpen = 0;
      this.categoryDropdownOpen = 0;
      this.pageNo = 1;
      this.searchResults = [];
      this.pageResult = {};
    },
    //모달 창을 닫기
    emitModalOff() {
      this.$emit('setModal', false);
    },
    //관광지 선택 완료, 관광지 정보를 부모 객체로 전달
    emitAddAttraction(attraction) {
      this.$emit('addAttraction', attraction);
      this.emitModalOff(); //전달하고 모달 창 닫기
    },
    //무한 스크롤
    async infiniteHandler($state) {
      this.infiniteState = $state;
      await axios({
        url: `http://192.168.212.72/attraction`,
        method: 'GET',
        params: {
          pageNo: this.pageNo,
          code: this.category,
          sido: this.sido,
          keyword: this.keyword,
        },
        paramsSerializer: (params) => {
          // code와 sido 값이 배열 형태인지 확인하여 배열이 아니면 단일 값으로 전달
          const code = Array.isArray(params.code) ? params.code : [params.code];
          const sido = Array.isArray(params.sido) ? params.sido : [params.sido];

          return qs.stringify(
            {
              ...params,
              code: code.join(','), // 배열 형태의 값을 쉼표로 구분하여 문자열로 변환
              sido: sido.join(','), // 배열 형태의 값을 쉼표로 구분하여 문자열로 변환
            },
            { arrayFormat: 'comma' }
          );
        },
      }).then((response) => {
        if (response.data.attractions.length) {
          this.pageNo += 1;
          this.searchResults.push(...response.data.attractions);
          this.pageResult = response.data.pageResult;
          $state.loaded();
        } else {
          $state.complete();
        }
      });
    },
  },
};
</script>
<style scoped>
.att-search-modal,
.search-blackbg {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 10;
}

.search-blackbg {
  opacity: 0.5;
  background-color: black;
}

.search-whitebg {
  width: 1300px;
  height: 700px;
  min-width: 1000px;
  position: relative;
  border-radius: 30px;
  background-color: white;
  z-index: 11;
  overflow: hidden;
}

.top-bar {
  background: linear-gradient(90deg, #e1306c 0%, #ff699a 38.02%, rgba(252, 175, 69, 0.78) 99.48%);
  height: 18px;
  width: 100%;
}

.search-modal-area {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.content-area {
  width: 90%;
  height: 90%;
  margin-bottom: 50px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 1fr 5fr;
}

.dropdown-area {
  display: flex;
  align-items: center;
  justify-content: center;
}

.dropdown-toggle {
  border: 1px solid rgb(37, 37, 37);
  border-radius: 4px;
  background-color: white;
  font-weight: 400;
  color: rgb(37, 37, 37);
  padding: 12px;
  width: 200px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 12px;
  margin: 0 20px;
}

.dropdown-menu {
  /* display: none; */
  position: absolute;
  border-radius: 4px;
  z-index: 1; /*다른 요소들보다 앞에 배치*/
  font-weight: 400;
  background-color: white;
  min-width: 200px;
  text-align: left;
  margin: 0 20px;
  max-height: 300px;
  overflow-y: auto;
}
.dropdown-li li {
  margin-bottom: 5px;
}
.dropdown-li li:hover {
  margin-bottom: 5px;
  background-color: #ececec;
}
.dropdown-li li label {
  display: block;
  padding: 12px 20px;
}

.keyword-search-area {
  display: flex;
  justify-content: center;
  align-items: center;
}

.search-box {
  display: flex;
  justify-content: space-evenly;
  border: 1px solid rgb(37, 37, 37);
  border-radius: 4px;
  background-color: white;
  font-weight: 400;
  color: rgb(37, 37, 37);
  padding: 0 12px;
  width: 400px;
}

.keyword-search-area input {
  border: none;
  width: 300px;
  height: 40px;
}

.keyword-search-area input:focus {
  outline: none;
}

.keyword-search-area img {
  position: relative;
  left: 5%;
  padding: 0 10px;
  cursor: pointer;
}

.att-list-area {
  display: flex;
  align-items: center;
  justify-content: center;
  border-right: 0.5px solid #cacaca;
  overflow: hidden;
}

.att-list {
  width: 80%;
  height: 95%;
  max-height: 95%;
  overflow: auto;
  background-color: #f6f6f6;
  border-radius: 20px;
}

.att-detail-area {
  display: flex;
  align-items: center;
  justify-content: center;
  border-left: 0.5px solid #cacaca;
  overflow: hidden;
}

.att-detail {
  width: 90%;
  height: 95%;
  padding: 0 0 0 5%;
  overflow: auto;
}
</style>
