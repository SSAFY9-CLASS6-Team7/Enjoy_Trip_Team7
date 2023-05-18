<template>
  <div class="history-item-container">
    <h2>계획</h2>
    <div>{{ planId }}</div>
  </div>
</template>
<script>
export default {
  name: 'PlanListItem',
  props: {
    planId: Number,
  },
};
</script>
<style scoped></style>
