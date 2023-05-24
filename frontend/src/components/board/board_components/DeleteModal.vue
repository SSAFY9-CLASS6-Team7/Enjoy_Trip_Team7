<template>
    <div class="background-blur" @click="no">
        <div class="modal-container">            
            정말 삭제하시겠습니까? 
            <div class="buttons">
                <button class="no" @click="no">취소</button>
                <button class="yes" @click="yes">삭제</button>
            </div>
        </div>
    </div>
</template>
<script>
export default {
  name: 'DeleteModal',
  props: ['link'],
  methods: {
    no(){
        this.$emit('modalOff');
    },
    yes() {
        this.$emit('yes', this.link)
    }
  }
};

</script>
<style scoped>
@font-face {
     font-family: 'S-CoreDream-3Light';
     src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-3Light.woff') format('woff');
     font-weight: normal;
     font-style: normal;
}

.modal-container {
    position: absolute;
    top: 30%;
    left: 50%;
    transform: translate(-50%, -50%);
    border: 1px solid #DBDBDB;
    border-radius: 5px;
    width: 300px;
    height: 200px;
    z-index: 10;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background: #ffffff;
    font-family: 'S-CoreDream-3Light';
    font-size: 14px;
    font-weight: 700;
}

.background-blur {
    position: absolute;
    width: 100vw;
    height: 100vh;
    background-color: #2e2e2e67;
    z-index: 9;
}

.buttons {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    align-items: center;
}

.buttons button {
    margin: 20px 10px 0 10px;
}
.yes {
    color: #ffffff;
    font-family: 'S-CoreDream-3Light';
    font-size: 14px;
    font-weight: 700;
    letter-spacing: 3px;
    
    height: 30px;
    width: 80px;

    border: none;
    background: linear-gradient(90deg, #E1306C 0%, #FF699A 38.02%, rgba(252, 175, 69, 0.78) 99.48%);
    border-radius: 4px;
}

.no {
    color: #333333;
        font-family: 'S-CoreDream-3Light';
    font-size: 14px;
    font-weight: 700;
    letter-spacing: 3px;
    
    height: 30px;
    width: 80px;
    
    border: none;
    background: #9b9b9b;
    border-radius: 4px;
}

</style>
