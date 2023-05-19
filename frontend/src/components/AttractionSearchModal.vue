<template>
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
              <ul ref="sidoDropdownMenu" class="dropdown-menu">
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
              <ul ref="categoryDropdownMenu" class="dropdown-menu">
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
                <plan-attraction-item :attraction="result"></plan-attraction-item>
              </div>
            </div>
          </div>
          <div class="att-detail-area">
            <div class="att-detail"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import PlanAttractionItem from './plan/plan_components/PlanAttractionItem.vue';
import axios from 'axios';

export default {
  name: 'AttractionSearchModal',
  components: {
    PlanAttractionItem,
  },
  data() {
    return {
      sido: [],
      category: [],
      keyword: '',
      searchResults: [],
      pageNo: 1,
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
      console.log(dropdownStyle, 1);

      var dropdownMenu = null;
      if (dropdownStyle === 'sido') dropdownMenu = this.$refs.sidoDropdownMenu;
      if (dropdownStyle === 'category') dropdownMenu = this.$refs.categoryDropdownMenu;
      console.log(dropdownMenu.style.display); //TODO: 처음 버튼 클릭 시 여기가 null도 아니고 아무것도 안찍히는데 해결 필요
      dropdownMenu.style.display = dropdownMenu.style.display === 'none' ? 'block' : 'none';
      console.log(dropdownStyle, 2);
      console.log(dropdownMenu.style.display);
    },
    //인풋이 변경되면 sido 배열을 업데이트
    sidoUpdate() {
      const checkedInputs = Array.from(document.querySelectorAll('input[name="sido"]:checked'));
      this.sido = checkedInputs.map((input) => input.value);
    },
    cateUpdate() {
      const checkedInputs = Array.from(document.querySelectorAll('input[name="category"]:checked'));
      this.category = checkedInputs.map((input) => input.value);
    },
    //키워드로 검색하기
    async keywordSearch() {
      console.log('lets search');
      await axios
        .get(
          `http://localhost/attraction?pageNo=${this.pageNo}&code=${this.category}&sido=${this.sido}&keyword=${this.keyword}`
        )
        .then((response) => (this.searchResults = response.data));
      //검색하면 드롭다운 닫기
      this.$refs.sidoDropdownMenu.style.display = 'none';
      this.$refs.categoryDropdownMenu.style.display = 'none';
    },
    //모달 창을 닫기
    emitModalOff() {
      this.$emit('setModal', false);
    },
  },
};
</script>
<style scoped>
.plan-search-modal,
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
  font-size: 12px;
  margin: 0 20px;
}

.dropdown-menu {
  display: none;
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
}

.att-detail {
  width: 80%;
  height: 95%;
  background-color: aquamarine;
}
</style>
