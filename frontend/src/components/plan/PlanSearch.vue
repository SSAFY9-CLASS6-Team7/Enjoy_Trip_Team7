<template>
  <div class="plan-search-modal">
    <div class="blackbg"></div>
    <div class="whitebg">
      <div class="top-bar"></div>
      <div class="modal-area">
        <div class="content-area">
          <div class="dropdown-area">
            <div class="dropdown">
              <button class="dropdown-toggle" @click="toggleDropdown('sido')">
                시·도 선택
                <img src="@/assets/board_icons/dropdown.svg" />
              </button>
              <ul ref="sidoDropdownMenu" class="dropdown-menu">
                <li>
                  <label><input type="checkbox" value="서울" />서울</label>
                </li>
                <li>
                  <label><input type="checkbox" value="대전" />대전</label>
                </li>
                <li>
                  <label><input type="checkbox" value="부산" />부산</label>
                </li>
              </ul>
            </div>
            <div class="dropdown">
              <button class="dropdown-toggle" @click="toggleDropdown('category')">
                카테고리 선택
                <img src="@/assets/board_icons/dropdown.svg" />
              </button>
              <ul ref="categoryDropdownMenu" class="dropdown-menu">
                <li>
                  <label><input type="checkbox" value="관광" />관광</label>
                </li>
                <li>
                  <label><input type="checkbox" value="식당" />식당</label>
                </li>
                <li>
                  <label><input type="checkbox" value="행사" />행사</label>
                </li>
                <li>
                  <label><input type="checkbox" value="숙박" />숙박</label>
                </li>
                <li>
                  <label><input type="checkbox" value="기타" />기타</label>
                </li>
              </ul>
            </div>
          </div>
          <div class="keyword-search-area">
            <div class="search-box">
              <input type="text" placeholder="검색어 입력" />
              <img src="@/assets/plan_icon/search.svg" @click="kewordSearch" />
            </div>
          </div>
          <div class="att-list-area">
            <div class="att-list"></div>
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
export default {
  name: 'PlanSearch',
  methods: {
    toggleDropdown(dropdownStyle) {
      var dropdownMenu = null;
      if (dropdownStyle === 'sido') dropdownMenu = this.$refs.sidoDropdownMenu;
      if (dropdownStyle === 'category') dropdownMenu = this.$refs.categoryDropdownMenu;
      dropdownMenu.style.display = dropdownMenu.style.display === 'none' ? 'block' : 'none';
    },
    kewordSearch() {
      console.log('lets search');
    },
  },
};
</script>
<style scoped>
.plan-search-modal,
.blackbg {
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

.blackbg {
  opacity: 0.5;
  background-color: black;
}

.whitebg {
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

.modal-area {
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
}
.dropdown-menu li {
  margin-bottom: 5px;
}
.dropdown-menu li:hover {
  margin-bottom: 5px;
  background-color: #ececec;
}
.dropdown-menu li label {
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
  justify-content: space-around;
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
}

.att-list {
  width: 80%;
  height: 95%;
  background-color: aquamarine;
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
