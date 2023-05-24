<template>
  <div>
    <delete-modal v-if="isModal" @yes="goDelete" @modalOff="modalOff"></delete-modal>
    <exit-user-modal v-if="exitModal" @modalOff="modalOff"></exit-user-modal>
    <div class="login-container">
      <div class="left-aside"></div>

      <div class="check-container">
            <div class="check">
                <div class="check-message" v-html="message"></div>
                <div class="password-input-container">
                    <div class="inner-title">비밀번호 확인</div>
                    <input type="password" class="password-input" v-model="password" @keyup.enter="findUserInfo">
                </div>
                <button class="submit" @click="findUserInfo" >확인</button>
            </div>
      </div>
      <div class="right-aside"></div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { mapGetters } from 'vuex';
import DeleteModal from '@/components/board/board_components/DeleteModal.vue';
import ExitUserModal from '@/components/user/user_components/ExitUserModal.vue';

export default {
    name: 'UserCheck',
    components: { DeleteModal, ExitUserModal },
    data() {
        return {
            message: '회원정보 수정을 위해서 인증이 필요합니다. <br>비밀번호를 한번 더 입력해주세요.',
            showPassword: 'password',
            password: '',
            isModal: false,
            exitModal: false,
        };
    },
    computed: {
      ...mapGetters('userStore', ['checkUserInfo', 'nextPath']),
    },
    created() {},
    methods: {
      findUserInfo() {
        if (this.password === this.checkUserInfo.password) {
          if (this.nextPath === '/user/modify') {
            this.$router.push(this.nextPath);
          }else {
            this.isModal = true;
          }
        }else {
          alert("비밀번호가 맞지 않습니다.");
        }
      },
      modalOff() {
        this.isModal = false;
        this.$router.push("/");
      },
      goDelete() {
        this.isModal = false;
        axios.delete(process.env.VUE_APP_MY_BASE_URL+"/user/"+this.checkUserInfo.userId)
        .then(response => {
          console.log(response);
          this.exitModal = true;
        });
      },
    },
};
</script>

<style scoped>
@font-face {
     font-family: 'S-CoreDream-3Light';
     src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-3Light.woff') format('woff');
     font-weight: normal;
     font-style: normal;
}

.login-container {
  display: grid;
  grid-template-columns: 2fr 3fr 2fr;
  grid-template-areas: 'left content right';
}

.left-aside {
    grid-area: left;
}

.right-aside {
    grid-area: right;
}

.check-container {
    grid-area: content;
    display: flex;
    align-items: center;
    justify-content: center;
    min-height: 400px;
    min-width: 650px;
    height: 80vh;
    padding: 0 30px 0 30px;
}

.check {
    box-shadow: -1px 5px 16px rgba(0, 0, 0, 0.13);
    border-radius: 14px;
    padding: 20px 100px 50px 100px;
}

.inner-title {
  margin: 10px 0 5px 0;
  font-weight: 600;
  font-size: 16px;
  color: #5F6073;
}

.check-message {
    margin: 3px 0 40px 0;
    font-weight: 600;
    font-size: 20px;
    color: #5F6073;
}

input {
  border: none;
  outline: none;
  height: 35px;
  width: 100%;
  background: #FFE8EB;
  font-weight: 400;
  letter-spacing: 1px;
  border-radius: 5px;
  padding: 0 10px 0 10px;
  margin: 0 0 20px 0;
}

.password-input-container {
  display: flex;
  flex-direction: column;
  align-items: start;
}

.submit {
  width: 30%;
  height: 35px;
  border: none;
  background: linear-gradient(90deg, #E1306C 0%, #FF699A 38.02%, rgba(252, 175, 69, 0.78) 99.48%);
  border-radius: 4px;
  margin: 20px 0 0 0;
  color: #ffffff;
  font-family: 'S-CoreDream-3Light';
  font-size: 12px;
  font-weight: 600;
  letter-spacing: 3px;
}


</style>
