<template>
  <div class="container">
    <div class="left-aside"></div>
    <div class="main">
      <div class="header">
        <div class="dropdown">
          <button class="dropdown-toggle" @click="toggleDropdown">
            시·도 선택
          <img src="@/assets/board_icons/dropdown.svg" />
          </button>
            <ul class="dropdown-menu" v-show="sidoDropdownOpen">
              <div class="dropdown-li" v-for="sido, i in this.getSidoCode" :key="i">
                <li>
                  <label
                    ><input
                      type="checkbox"
                      name="sido"
                      :value="sido.code"
                      v-model="selectedSido"
                    />
                    {{ sido.text }}
                  </label>
                </li>
              </div>
            </ul>
          </div>
        <div class="category-container">
          <input type="checkbox" class="search-category" id="category-house" v-model="category" value="32" hidden>
          <label for="category-house" class="category-button" :class="{ 'selected': category.includes('32') }">숙박</label>
          <input type="checkbox" class="search-category" id="category-food" v-model="category" value="39" hidden>
          <label for="category-food" class="category-button" :class="{ 'selected': category.includes('39') }">식당</label>
          <input type="checkbox" class="search-category" id="category-tour" v-model="category" value="12" hidden>
          <label for="category-tour" class="category-button" :class="{ 'selected': category.includes('12') }">관광</label>
          <input type="checkbox" class="search-category" id="category-festival" v-model="category" value="15" hidden>
          <label for="category-festival" class="category-button" :class="{ 'selected': category.includes('15') }">행사</label>
        </div>

         <div class="keyword-container">
            <input type="text" class="keyword" placeholder="검색어를 입력하세요" v-model="keyword" @keyup.enter="goSearch">
            <img src="@/assets/board_icons/search.svg" @click="goSearch">
          </div>

      </div>
      <div class="content">
        <attraction-list-item v-for="attraction in attractions" :key='attraction' :attraction="attraction"></attraction-list-item>
      </div>
      <attraction-pagination class="pagination" @pageChange="pageChanged" :pageResult="pageResult"></attraction-pagination>
    </div>
    <div class="right-aside"></div>
  </div>
</template>

<script>
import AttractionPagination from '@/components/attraction/attraction_components/AttractionPagination.vue'
import AttractionListItem from '@/components/attraction/attraction_components/AttractionListItem.vue'
import { mapActions, mapGetters } from 'vuex'
import qs from "qs"
import axios from 'axios'

export default {
  name : 'AttractionList',
  components: { AttractionPagination, AttractionListItem },
  data(){
    return {
      attractions: [],
      pageResult: '',
      sido: [],
      selectedSido: [],
      category: [],
      keyword: '',
      sidoDropdownOpen: false,
      pageNo: 1,
    }
  },
  computed: {
      ...mapGetters(['getSidoCode', 'getPage']),
    },
  methods: {
    ...mapActions(['pageNoChange']),
    toggleDropdown() {
      this.sidoDropdownOpen = !this.sidoDropdownOpen;
    },
    pageChanged(clickedPage){
      this.pageNo = clickedPage;
      this.goSearch();
    },
    async goSearch(){
      this.pageNoChange(1);
      await axios({
        url: `http://192.168.212.72/attraction`,
        method: 'GET',
        params: {
          pageNo: this.pageNo,
          code: this.category,
          sido: this.selectedSido,
          keyword: this.keyword,
        },
        paramsSerializer: (params) => {
          const code = Array.isArray(params.code) ? params.code : [params.code];
          const sido = Array.isArray(params.sido) ? params.sido : [params.sido];

          return qs.stringify(
            {
              ...params,
              code: code.join(','), 
              sido: sido.join(','), 
            },
            { arrayFormat: 'comma' }
          );
        },
      }).then((response) => {
        this.attractions = response.data.attractions;
        this.pageResult = response.data.pageResult;
        this.pageNoChange(this.pageNo);
      });
    },
  },
  async created() {
    this.pageNo = this.getPage;
    await this.goSearch();
  }
}

</script>

<style scoped>
@font-face {
     font-family: 'S-CoreDream-3Light';
     src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-3Light.woff') format('woff');
     font-weight: normal;
     font-style: normal;
}

.container {
  display: grid;
  grid-template-columns: 1fr 5fr 1fr;
  grid-template-areas: 'left main right';
  height: fit-content;
  min-height: 763.64px;
}

.left-aside {
  grid-area: left;
}

.right-aside {
  grid-area: right;
}

.main {
  grid-area: main;
  min-width: 1300px;
  margin: 0 0 20px 0;
}

.header {
  display: flex;
  min-height: 100px;
  align-items: center;
  justify-content: center;
}

.dropdown-toggle {
  border: 2px solid #5C5C5C;
  border-radius: 4px;
  background-color: white;
  font-weight: 400;
  color: rgb(37, 37, 37);
  padding: 5px 10px 5px 10px;
  width: 151px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-family: 'S-CoreDream-3Light';
  font-weight: 600;
  font-size: 12px;
  margin: 0 20px;
}

.dropdown-menu {
  position: absolute;
  border-radius: 4px;
  z-index: 3; 
  font-weight: 400;
  background-color: white;
  min-width: 150px;
  text-align: left;
  margin: 0 20px;
  max-height: 300px;
  overflow-y: auto;
}
.dropdown-li li {
  margin-bottom: 5px;
  width: 130px;
}
.dropdown-li li:hover {
  margin-bottom: 5px;
  background-color: #ececec;
}
.dropdown-li li label {
  display: block;
  padding: 12px 20px;
}

.category-container {
  margin: 0 20px 0 20px;
  width: 300px;
  display: flex;
  justify-content: space-between;
  margin: 0 100px 0 50px;
}

.category-button {
  border: 2px solid #5C5C5C;
  padding: 3px 15px 3px 15px;
  border-radius: 4px;
  font-family: 'S-CoreDream-3Light';
  font-weight: 600;
  font-size: 13px;
  width: 59px;
  height: 28.5px;
}

.category-button:hover {
  cursor: pointer;
}

.selected {
  width: 59px;
  height: 28.5px;
  border: none;
  padding: 5px 17px 5px 17px;
  color: #ffffff;
  font-weight: 800;
  background: linear-gradient(93.52deg, #E1306C -1.5%, #FF699A 40.19%, rgba(252, 175, 69, 0.78) 110.32%);
}


.keyword-container {
  position: relative;
  display: flex;
  height: 30px;
  border: 2px solid #5C5C5C;
  border-radius: 4px; 
  font-family: 'S-CoreDream-3Light';
  font-weight: 600;
  font-size: 14px;
  color: #9b9b9b;
  margin-right: 20px;
  width: 300px;
}

.keyword-container .keyword {
  outline: none;
  border: none;
  color: #9b9b9b;
  font-family: 'S-CoreDream-3Light';
  font-weight: 600;
  font-size: 12px;
  padding: 0 10px 0 10px;
  width: 95%;
}

.keyword-container .keyword:not(:placeholder-shown) {
  color: black;
}

.keyword-container img {
  padding: 3px 2px 3px 2px;
  position: absolute;
  right: 0;
}

.keyword-container img:hover {
  cursor: pointer;
}

.content {
  display: flex;
  flex-wrap: wrap;
  justify-content: start;
  padding:10px 20px 10px 20px;
}

.pagination {
  margin: 10px 0 10px 0;
}

</style>
