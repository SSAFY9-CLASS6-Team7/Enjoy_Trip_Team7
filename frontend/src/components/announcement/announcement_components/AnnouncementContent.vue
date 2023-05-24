<template>
    <div class="board-list"> 
        <announcement-content-item v-for="announcement, i in announcements" :key='i' :announcement="announcement" class="board-list-item"></announcement-content-item>
    </div>
    
</template>

<script>
import AnnouncementContentItem from "./AnnouncementContentItem.vue";

export default {
    name: 'BoardContent',
    components: {AnnouncementContentItem},
    props: ['announcements'],
    data() {
        return {
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