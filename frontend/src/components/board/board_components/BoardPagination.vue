<template>
  <div>
    <!-- 페이징된 데이터 표시 -->
    <ul>
      <li v-for="item in data" :key="item.id">{{ item }}</li>
    </ul>

    <!-- 페이징 컨트롤 -->
    <paginate
    :page-count="10"
    :click-handler="functionName"
    :prev-text="'Prev'"
    :next-text="'Next'"
    :container-class="'className'">
    </paginate>
  </div>
</template>

<script>
export default {
  data() {
    return {
      data: [], // 전체 데이터 배열
      currentPage: 1, // 현재 페이지 번호
      itemsPerPage: 10, // 페이지 당 항목 수
    };
  },
  computed: {
    pageCount() {
      return Math.ceil(this.data.length / this.itemsPerPage);
    },
    displayedData() {
      const startIndex = (this.currentPage - 1) * this.itemsPerPage;
      const endIndex = startIndex + this.itemsPerPage;
      return this.data.slice(startIndex, endIndex);
    },
  },
  methods: {
    changePage(pageNumber) {
      this.currentPage = pageNumber;
    },
  },
};
</script>

<style scoped>

</style>