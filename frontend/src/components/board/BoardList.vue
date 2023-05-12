<template>
  <div>
    <h2>게시판 리스트 ^_^</h2>
    <button @click="writePage"> 글 쓰기 </button>
  </div>
</template>

<script>
export default {
  name : 'BoardList',
  data(){
    return {

    }
  },
  methods: {
    writePage() {
      this.$router.push("create");
    }
  },
}
</script>

<style></style>
