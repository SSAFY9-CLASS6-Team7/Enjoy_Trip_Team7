<template>
  <div class="page-outer">
    <button class="start-button" @click="moveStart"><img src="@/assets/board_icons/start.svg"></button>
    <button class="tab-button" @click="moveTabLeft()" > <img src="@/assets/board_icons/leftpage.svg"></button>
    <span class="page-no" v-if="pageResult.pageNo - 2 >= 1" @click="pageChange(pageResult.pageNo - 2)"> {{ pageResult.pageNo - 2 }} </span>
    <span class="devider" v-if="pageResult.pageNo - 2 >= 1 && pageResult.pageNo - 1 >= 1"></span>
    <span class="page-no" v-if="pageResult.pageNo - 1 >= 1" @click="pageChange(pageResult.pageNo - 1)"> {{ pageResult.pageNo - 1 }} </span>
    <span class="devider" v-if="pageResult.pageNo - 1 >= 1"></span>
    <span class="page-no selected" @click="pageChange(pageResult.pageNo)"> {{ pageResult.pageNo }} </span>
    <span class="devider" v-if="pageResult.pageNo + 1 <= pageResult.lastPage"></span>
    <span class="page-no" v-if="pageResult.pageNo + 1 <= pageResult.lastPage" @click="pageChange(pageResult.pageNo + 1)"> {{ pageResult.pageNo + 1 }} </span>
    <span class="devider" v-if="pageResult.pageNo + 1 <= pageResult.lastPage && pageResult.pageNo + 2 <= pageResult.lastPage"></span>
    <span class="page-no" v-if="pageResult.pageNo + 2 <= pageResult.lastPage" @click="pageChange(pageResult.pageNo + 2)"> {{ pageResult.pageNo + 2 }} </span>
    <button class="tab-button" @click="moveTabRight()" > <img src="@/assets/board_icons/rightpage.svg"></button>
    <button class="end-button" @click="moveEnd"><img src="@/assets/board_icons/end.svg"></button>
  </div>
</template>

<script>
export default {
    props: ['pageResult'],
    data() {
        return {
            
        };
    },
    methods: {
        pageChange(clickedPage){
            this.$emit('pageChange', clickedPage);
        },
        moveStart(){
            if (this.pageResult.pageNo > 1) {
                this.$emit('pageChange', 1);
            }
        },
        moveEnd() {
            if (this.pageResult.pageNo < this.pageResult.lastPage) {
                this.$emit('pageChange', this.pageResult.lastPage);
            }
        },
        moveTabLeft() {
            let nextPage = this.pageResult.pageNo - 5 > 1 ? this.pageResult.pageNo - 5 : 1;
            this.$emit('pageChange', nextPage);
        },
        moveTabRight(){
            let nextPage = this.pageResult.pageNo + 5 < this.pageResult.lastPage ? this.pageResult.pageNo + 5 : this.pageResult.lastPage;
            this.$emit('pageChange', nextPage);
        }
    },  
};  
</script>

<style scoped>

.page-outer {
    display: flex;
    justify-content: center;
    align-items: center;
}

.page-no {
    margin: 0 10px 0 10px;
    color: #1B1C37;
    font-family: 'S-CoreDream-3Light';
    font-weight: 300;
}

.page-no:hover {
    cursor: pointer;
}

.start-button  {
    border: 1px solid #0c0c0c;
    padding: 2px 3px 0 2px;
    background: #0c0c0c;
    border-radius: 4px;
    margin-right: 10px;
}

.end-button  {
    border: 1px solid #0c0c0c;
    padding: 2px 2px 0 3px;
    background: #0c0c0c;
    border-radius: 4px;
    margin-left: 10px;
}

.tab-button  {
    border: 1px solid #0c0c0c;
    padding: 4px 5px 2px 5px;
    background: #0c0c0c;
    border-radius: 4px;
}

.devider {
    height: 15px;
    border: 1.5px solid #cccccc;
}

.selected {
    /* background: #0c0c0c;
    color: #cccccc; */
    font-weight: 800;
    color: #e1306c;
    border-radius: 4px;
}

</style>