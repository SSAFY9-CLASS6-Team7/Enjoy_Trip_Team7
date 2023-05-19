<template>
  <div class="plan-attraction-item-container">
    <div class="title-and-category">
      <div class="attraction-title">{{ attraction.title }}</div>
      <div class="content-type-area">
        <img src="@/assets/content_type_icon/etc.svg" class="content-type" />
      </div>
    </div>
    <div class="attraction-address">
      <img src="@/assets/plan_icon/pin.svg" alt="" class="pin" />
      <div class="address-text">{{ attraction.address }}</div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'PlanAttractionItem',
  props: ['attraction'],
};
</script>
<style scoped>
.plan-attraction-item-container {
  margin: 30px;
  padding: 10px;
  border-radius: 15px;
  background-color: white;
  box-shadow: 4px 4px 10px rgba(0, 0, 0, 0.1);
}
.title-and-category {
  display: flex;
  justify-content: space-between;
  padding: 8px 15px;
}
.attraction-title {
  position: relative;

  text-align: left;
  font-weight: 700;
}
.content-type {
  position: relative;
}

.attraction-address {
  display: flex;
  align-items: center;
  padding: 0 30px 5px 30px;
}

.address-text{
    font-size: 12px;
    margin: 0 0 2px 5px;
}
</style>
