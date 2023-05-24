<template>
  <div class="container" @click="closeRightMenu">
    <div class="right-menu" :class="{ 'open': isOpen, 'close': !isOpen}" @click.stop>
        <div class="top">
            <div v-if='!checkToken' class="user-info">
                <img src="@/assets/header_icon/profile.svg" class='profile' >
                <div class="go-to-login" @click="goToLogin" >로그인 하세요.</div>
            </div>
            <div v-if='checkToken' class="user-info">
                <button class="logout" @click="logout">
                    로그아웃
                    <img src="@/assets/user_icons/logout.svg" >
                </button>
                <img v-if="checkUserInfo.profilePicPath != null && checkUserInfo.profilePicPath != ''" :src="`${baseUrl}/profilePath/` + checkUserInfo.profilePicPath" class='profile' >
                <img v-if="checkUserInfo.profilePicPath == null || checkUserInfo.profilePicPath == ''" src="@/assets/header_icon/profile.svg" class='profile'>
                <div class="id-email-container">
                    <div class="id">{{ checkUserInfo.nickname }}</div>
                    <div class="email">{{ checkUserInfo.email }}</div>
                </div>
            </div>
        </div>
        <div class="devider"></div>

        <div v-if='!checkToken' class="main">
            <div class="announcement" @click="moveAnnouncement">
                <img src="@/assets/announcement.svg" style="margin:0 10px 0 0;" >
                <span>공지사항</span>
            </div>
            <div class="inner-devider"></div>
            <div class="signup" @click="moveSignUp">
                <img src="@/assets/signup.svg" style="margin:0 10px 0 0;">
                <span>회원가입</span>
            </div>
            <div class="inner-devider"></div>
            <div class="history" @click="moveHistory">
                <img src="@/assets/header_icon/history.svg" style="margin:0 10px 0 0;">
                <span>기록</span>
            </div>
            <div class="inner-devider"></div>
            <div class="plan" @click="movePlan">
                <img src="@/assets/header_icon/plan.svg" style="margin:0 10px 0 0;">
                <span>계획</span>
            </div>

        </div>
        <div v-if='checkToken' class="main">
            <div class="announcement" @click="moveAnnouncement">
                <img src="@/assets/announcement.svg" style="margin:0 10px 0 0;" >
                <span>공지사항</span>
            </div>
            <div class="inner-devider"></div>
            <div class="modify-user" @click="moveModifyUser">
                <img src="@/assets/modify_user.svg" style="margin:0 10px 0 0;">
                <span>회원정보 수정</span>
            </div>
            <div class="inner-devider"></div>
            <div class="history" @click="moveHistory">
                <img src="@/assets/header_icon/history.svg" style="margin:0 10px 0 0;">
                <span>기록</span>
            </div>
            <div class="inner-devider"></div>
            <div class="plan" @click="movePlan">
                <img src="@/assets/header_icon/plan.svg" style="margin:0 10px 0 0;">
                <span>계획</span>
            </div>
            <div class="inner-devider"></div>
            <div class="quit-user">
                <img src="@/assets/quit_user.svg" style="margin:0 10px 0 0;">
                <span>탈퇴</span>
            </div>
        </div>

        <div class="devider"></div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
export default {
    name: 'RightMenu',
    components: {
    
    },
    data() {
        return {
            isOpen: false,
            baseUrl: process.env.VUE_APP_MY_BASE_URL,
        }
    },
    computed: {
        ...mapGetters('userStore', ['checkToken', 'checkUserInfo']),
    },
    methods: {
        ...mapActions('userStore', ['userConfirm', 'userLogout']),
        menuClose(){
            this.$emit('openRightMenu');
        },
        closeRightMenu() {
            this.isOpen = false;
            setTimeout(() => {
                this.$emit('openRightMenu');
            }, 300);
            
        },
        goToLogin() {
            this.movePage('/user/login');
        },
        logout() {
            this.userLogout();  
            this.movePage('/');
        },
        moveAnnouncement() {
            this.movePage("/announcement");
        },
        moveSignUp() {
            this.movePage('/user/signup');
        },
        moveHistory() {
            this.movePage('/history/list');
        },
        movePlan() {
            this.movePage('/plan/list')

        },
        moveModifyUser() {
            this.movePage('/user/check');
        },
        movePage(link){
            this.closeRightMenu();
            if (this.$route.path !== link){
                this.$store.commit('activeMenuChange', -1);
                this.$router.push(link);
            }
        }
    },
    mounted() {
        setTimeout(() => {
            this.isOpen = true; 
        }, 100);
    },
}
</script>

<style scoped>
@font-face {
     font-family: 'S-CoreDream-3Light';
     src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-3Light.woff') format('woff');
     font-weight: normal;
     font-style: normal;
}

.container {
    background: #51515167;
    position: fixed;
    right: 0%;
    top: 0%;
    width: 100vw;
    height: 100vh;
    z-index: 10;
}

.devider {
    height: 1px;
    background-color: #bbbaba;
    margin: 10px 20px 10px 20px;
}

.inner-devider {
    height: 1px;
    width: 30%;
    background-color: #bbbaba;
    margin: 5px 40px 5px 40px;
}

.right-menu {
    position: fixed;
    height: 100vh;
    width: 20vw;
    min-width: 300px;
    border-radius: 40px 0 0 40px;
    background: #F2F2F2;
    right: -20vw; 
    transition: right 0.5s; 
    z-index: 20;
}

.right-menu.open {
  right: 0; 
}

.right-menu.close {
  right: -20vw;
}

.top {
    height: 20vh;
    min-height: 250px;
    background: #F2F2F2;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

.user-info {
    display: flex;
    align-items: center;
}

.profile {
    width: 45px;
    height: 45px;
    margin:0 20px 0 0 ;
    border-radius: 45%;
}

.go-to-login {
    /* font-family: 'S-CoreDream-3Light'; */
    font-weight: 600;
    font-size: 18px;
}

.go-to-login:hover {
    cursor: pointer;
}

.logout {
    position: absolute;
    right: 10%;
    top: 5%;
    border: 1px solid #b7b7b7;
    border-radius: 4px;
    padding: 5px 10px 5px 10px;
    font-family: 'S-CoreDream-3Light';
    font-weight: 800;
    font-size: 12px;
    display: flex;
    align-items: center;
}

.logout img{
    margin: 0 0 0 5px;
}

.id-email-container {
    display: flex;
    flex-direction: column;
    align-items: start;
}

.id {
    font-family: 'S-CoreDream-3Light';
    font-size: 18px;
    font-weight: 800;
}

.email {
    font-family: 'S-CoreDream-3Light';
    color: #b7b7b7;
    font-weight: 500;
}

.main {
    height: 80vh;
    min-height: 830px;
    background: #F2F2F2;
    display: flex;
    flex-direction: column;
    align-items: start;
    padding: 50px 40px 0 40px;
}

.main div {
    font-size: 25px;
    font-weight: 600;
    margin: 10px 0 10px 0;
    display: flex;
}

.main div:not(.inner-devider) {
    padding: 10px 0 10px 20px;
}

.main div:hover {
    cursor: pointer;
    background: linear-gradient(92.4deg, #E1306C 0.11%, #FF699A 53.13%, rgba(252, 175, 69, 0.87) 95.51%);
    border-radius: 5px;
    width: 100%;
}

.main div:hover img, 
.main div:hover span {
    filter: invert(100%);
}

</style>

