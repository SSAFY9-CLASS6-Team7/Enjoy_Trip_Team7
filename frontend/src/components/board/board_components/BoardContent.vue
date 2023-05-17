<template>
    <div class="board-list"> 
        <board-content-item v-for="board, i in boards" :key='i' :board="board" class="board-list-item"></board-content-item>
    </div>
    
</template>

<script>
import BoardContentItem from "./BoardContentItem.vue";

export default {
    name: 'BoardContent',
    components: {BoardContentItem},
    props: ['boards'],
    data() {
        return {
            message: '',
        };
    },
    created() {
    },
    methods: {},
};
</script>

<style scoped>
.board-list {
    display: flex;
    flex-direction: column;
}

.board-list-item {
    flex-basis: 10%;
}
</style>