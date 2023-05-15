<template>
<div class="main_img">
  <img src="@/assets/main1.png" width="100%" height="600px">
  <div class="announcement">
    <h2>공지사항</h2>
  </div>
</div>

</template>

<script>
export default {
  name: "AppUser",
};
</script>

<style>
/* Swiper 추가 */


/* 공지 영역 */
.announcement {
  position: relative;
  height: 60px;
  top: -65px;
  width: 100%;
  background: #1d1d1d7a;
  z-index: 1;
}

/* 임시 테스트용 */ 
.announcement > h2{
  font-weight: 500;
  font-size: 26px;
  color : #f1f1f1;
}

</style>
