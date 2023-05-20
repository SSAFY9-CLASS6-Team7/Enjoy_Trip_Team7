<template>
    <div class="background-blur">
        <div class="modal-container">
            <img src="@/assets/user_icons/signupComplete.svg" style="padding-bottom:  10px">
            가입이 완료되었습니다.
            <button class="login" @click="moveLogin()">로그인</button>
        </div>
    </div>
</template>
<script>
export default {
  name: 'SignUpModal',
  methods: {
    moveLogin(){
        this.$emit('modalOff');
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
    top: 50%;
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

.login {
    width: 50%;
    height: 35px;
    border: none;
    background: linear-gradient(90deg, #E1306C 0%, #FF699A 38.02%, rgba(252, 175, 69, 0.78) 99.48%);
    border-radius: 4px;
    margin: 20px 0 0 0;
    color: #ffffff;
    font-family: 'S-CoreDream-3Light';
    font-size: 14px;
    font-weight: 700;
    letter-spacing: 3px;
}

</style>
