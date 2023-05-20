<template>
  <div class="plan-search-item-container" @click="emitFocusedId">
    <div class="title-and-category">
      <div class="attraction-title">{{ attraction.title }}</div>
      <div class="content-type-area">
        <img :src="contentTypeSrc" class="content-type" />
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
  name: 'PlanSearchItem',
  data() {
    return {
      contentTypeSrc: 'require("@/assets/content_type_icon/etc.svg")',
    };
  },
  props: ['attraction'],
  methods: {
    emitFocusedId() {
      this.$emit('setFocusedAttId', this.attraction.attractionId);
    },
  },
  mounted() {
    if (this.attraction.code === 12)
      this.contentTypeSrc = require('@/assets/content_type_icon/tour.svg');
    else if (this.attraction.code === 15)
      this.contentTypeSrc = require('@/assets/content_type_icon/event.svg');
    else if (this.attraction.code === 32)
      this.contentTypeSrc = require('@/assets/content_type_icon/stay.svg');
    else if (this.attraction.code === 39)
      this.contentTypeSrc = require('@/assets/content_type_icon/restaurant.svg');
  },
};
</script>
<style scoped>
.plan-search-item-container {
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
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.content-type {
  position: relative;
}

.attraction-address {
  display: flex;
  align-items: center;
  padding: 0 30px 5px 30px;
}

.address-text {
  font-size: 12px;
  margin: 0 0 2px 5px;
  width: 100%;
  text-align: left;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
