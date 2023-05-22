<template>
    <div class="container">
        <sign-up-modal v-if='modal' @modalOff="moveLogin"></sign-up-modal>
        <div class="left-aside"></div>
        <div class="main-container">
            <div class="inner-title">아이디</div>
            <div class="id-input-container">
                <input type="text" class="id-input" :class="{ 'success': idFlag === true, 'fail': idFlag === false }" v-model="id" @change="idEmpty"/>
                <img v-if="idFlag === true" src="@/assets/user_icons/success.svg" class="id-status-icon">
                <img v-if="idFlag === false" src="@/assets/user_icons/fail.svg" class="id-status-icon">
                <button class="id-check-button" @click="idCheck">중복 확인</button>
            </div>
            <div class="id-check-result" :class="{ 'result-success': idFlag === true, 'result-fail': idFlag === false }">{{ idCheckResult }}</div>

            <div class="inner-title">비밀번호</div>
            <div class="password-input-container" :class="{ 'success': passwordFlag === true, 'fail': passwordFlag === false }">
                <input type="password" class="password-input" @change="passwordCheck" v-model='password'>
                <img v-if="passwordFlag === true" src="@/assets/user_icons/success.svg" class="status-icon">
                <img v-if="passwordFlag === false" src="@/assets/user_icons/fail.svg" class="status-icon">
            </div>
            
            <div class="inner-title">비밀번호 확인</div>
            <div class="password-input-container" :class="{ 'success': passwordFlag === true, 'fail': passwordFlag === false }">
                <input type="password" class="password-check-input" @change="passwordCheck" v-model='passwordSecond'>
                <img v-if="passwordFlag === true" src="@/assets/user_icons/success.svg" class="status-icon">
                <img v-if="passwordFlag === false" src="@/assets/user_icons/fail.svg" class="status-icon">
            </div>
            
            <div class="password-check-result" :class="{ 'result-success': passwordFlag === true, 'result-fail': passwordFlag === false }"> {{passwordCheckResult}} </div>

            <div class="inner-title">이름</div>
            <input type="text" class="name-input" v-model="username">
            

            <div class="inner-title">별명</div>
            <input type="text" class="nickname-input" v-model="nickname">
            
            <div class="inner-title">휴대폰 번호</div>
            <div class="phone-input-container">
                <input type="tel" v-model='phone1' class="phone-input" pattern="[0-9]*" inputmode="numeric" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*?)\..*/g, '$1');" maxlength="3"> -
                <input type="tel" v-model='phone2' class="phone-input" pattern="[0-9]*" inputmode="numeric" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*?)\..*/g, '$1');" maxlength="4"> -
                <input type="tel" v-model='phone3' class="phone-input" pattern="[0-9]*" inputmode="numeric" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*?)\..*/g, '$1');" maxlength="4">
            </div>

            <div class="inner-title">생년월일</div>
            <input type="date" class="birth-input" v-model="birth">
            

            <div class="inner-title">성별</div>
            <div class="gender-input-container">
                <div class="male-container">
                    <input id="male" type="radio" class="gender-input" name="gender" value="M" v-model="gender" hidden>
                    <label class="gender-radio" for="male" :class="{'male-selected' : gender == 'M'}">남 자</label>
                </div>
                <div class="female-container">
                    <input id="female" type="radio" class="gender-input" name="gender" value="F" v-model="gender" hidden>
                    <label class="gender-radio" for="female" :class="{'female-selected' : gender == 'F'}">여 자</label>
                </div>
            </div>

            <div class="inner-title">이메일</div>
            <input type="email" class="email-input" v-model="email">
            
            <div class="sign-up-container">
                <button class="sign-up" @click="signUp">회원가입</button>
            </div>
        </div>
        <div class="right-aside"></div>
    </div>
</template>

<script>
import SignUpModal from './user_components/SignUpModal.vue';
import axios from 'axios';

export default {
    name: 'UserSignUp',
    components: {SignUpModal },
    data() {
        return {
            id: '',
            idFlag: '',
            idCheckResult: '',
            passwordFlag: '',
            password: '',
            passwordSecond: '',
            passwordCheckResult: '',
            gender: '',
            phone1: '',
            phone2: '',
            phone3: '',
            email: '',
            birth: '',
            username: '',
            nickname: '',
            modal: false,
        };
    },
    computed: {
        totalPhone() {
            return this.phone1 + '-' + this.phone2 + '-' + this.phone3;
        }
    },
    created() {

    },
    methods: {
        idCheck(){
            const alphanumericRegex = /^[a-zA-Z0-9]+$/;
            if (this.id.length > 15 || this.id.length < 1) {
                this.idFlag = false;
                this.idCheckResult = '아이디는 1자 이상 15자 이하입니다.';    
            }else if (this.id.length == 0) {
                this.idFlag = '';
                this.idCheckResult = '';    
            }else {
                if (!alphanumericRegex.test(this.id)) {
                    this.idFlag = false;
                    this.idCheckResult = '아이디는 영어와 숫자의 조합이어야 합니다.';
                } else {
                    // 중복 체크 로직
                    axios.get('http://localhost/user/'+this.id)
                    .then(response => {
                        if (response.data.message == 'success') {
                            this.idFlag = false;
                            this.idCheckResult = '중복된 아이디가 있습니다';
                        } else {
                            this.idFlag = true;
                            this.idCheckResult = '사용할 수 있는 아이디입니다.';
                        }
                    });
                }
            }
        },
        idEmpty(){
            if(this.id.length == 0) {
                this.idFlag = '';
                this.idCheckResult = '';  
            }
        }, 
        passwordCheck() {
            if (this.password.length < 8 && this.password.length > 0) {
                this.passwordFlag = false;
                this.passwordCheckResult = '비밀번호는 8자 이상입니다.';
            }else if (this.password.length >= 8){
                if (this.password === this.passwordSecond) {
                    this.passwordFlag = true;
                    this.passwordCheckResult = '비밀번호와 비밀번호 확인이 일치합니다.';
                }else {
                    this.passwordFlag = false;
                    this.passwordCheckResult = '비밀번호와 비밀번호 확인이 일치하지 않습니다.';
                }
            }else if (this.password === '' && this.passwordSecond === '') {
                this.passwordFlag = ''
                this.passwordCheckResult= ''
            }
        },
        moveLogin() {
            this.modal = false;
            this.$router.push('/user/login');
        },
        signUp() {
            if (this.idFlag && this.passwordFlag && this.username != '' && this.nickname != '' && this.email != '' && this.totalPhone.length >= 11 && this.birth != '' && this.gender != '') {
                let user =  {
                    userId: this.id,
                    password: this.password,
                    name: this.username,
                    nickname: this.nickname,
                    phone: this.totalPhone,
                    email: this.email,
                    gender: this.gender,
                    birth: this.birth,
                }

                axios.post("http://localhost/user", user);
                this.modal = true;

            }else {
                alert("입력되지 않은 항목이 있습니다");
            }
        },
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

input {
    font-size: 14px;
    font-family: 'S-CoreDream-3Light';
}

.container {
    display: grid;
    grid-template-columns: 2fr 3fr 2fr;
    grid-template-areas: 'left main right';    
}

.left-aside {
    grid-area: left;
    /* border: 1px solid #333333; */
}

.right-aside {
    grid-area: right;
    /* border: 1px solid #333333; */
}

.main-container {
    grid-area: main;
    min-width: 600px;
    margin: 40px 40px 40px 40px;
    padding: 5% 15% 5% 15%;
    display: flex;
    flex-direction: column;
    align-items: start;
    box-shadow: -1px 5px 20px rgba(0, 0, 0, 0.2);
    border-radius: 14px;
}

.main-container div + input{
    width: 100%;
    height: 35px;
}

.main-container >>> input {
    outline: none;
    border: none;
    border: 1px solid #DBDBDB;
    border-radius: 4px;
    padding: 0 10px 0 10px;
}

.id-input-container {
    width: 100%;
    position: relative;
    display: flex;
    justify-content: space-between;
}

.id-status-icon {
    position: absolute;
    right: 19%;
}

.password-input-container {
    position: relative;
    width: 100%;
    border: 1px solid #DBDBDB;
    border-radius: 4px;
    padding: 0 20px 0 0;
}

.password-input-container input {
    height: 35px;
    width: 100%;
    outline: none;
    border: none;
    border-radius: 4px;
    padding: 0 10px 0 10px;
}

.status-icon {
    position: absolute;
    top: 5px;
    right: 10px;
}

.inner-title {
    margin: 10px 0 5px 0;
    font-weight: 600;
    font-size: 16px;
    color: #5F6073;
}

.id-input-container {
    width: 100%;
    display: flex;
    align-items: center;
}

.id-input {
    height: 35px;
    width: 83%;
}

.id-check-button {
    font-size: 14px;
    font-family: 'S-CoreDream-3Light';
    height: 35px;
    background: #5F6073;
    color: #ffffff;
    border:none;
    padding: 0 10px 0 10px;
    border: none;
    border-radius: 4px;
}

.success {
    border-radius: 4px;
    border: 1px solid #09A552 !important;
}

.fail {
    border-radius: 4px;
    border: 1px solid #FB0808 !important;
}

.result-success {
    color: #09A552 !important;
}

.result-fail {
    color: #FB0808 !important;
}

.duplicate-check {
    position: absolute;
    margin-left: 2%;
    right: 1%;
    width: 16%;
    font-size: 14px;
    font-family: 'S-CoreDream-3Light';
    height: 35px;
    border: none;
    border-radius: 4px;
}

.id-check-result {
    height: 20px;
    font-size: 14px;
    font-family: 'S-CoreDream-3Light';
    margin: 10px 0 0 0;
}


.password-check-result {
    height: 20px;
    font-family: 'S-CoreDream-3Light';
}

.phone-input-container {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
}

.phone-input {
    height: 35px;
    width: 30%;
    text-align: center;
}

.birth-input {
    text-align: center;
}
.gender-input-container {
    width: 100%;
    display: flex;
    justify-content: space-between;
}

.male-container {
    width: 45%;
}

.female-container {
    width: 45%;
}

.gender-radio {
    height: 35px;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    border: 1px solid #DBDBDB;
    border-radius: 4px;
    font-size: 14px;
    font-weight: 800;
    font-family: 'S-CoreDream-3Light';
}

.gender-radio:hover {
    cursor: pointer;
}

.male-selected {
    background: #315EFB; 
    color: #ffffff;
    border:none;
}

.female-selected {
    background: #E1306C; 
    color: #ffffff;
    border:none;
}

.sign-up-container {
    width: 100%;
    display: flex;
    justify-content: center;
}

.sign-up {
  width: 50%;
  height: 35px;
  border: none;
  background: linear-gradient(90deg, #E1306C 0%, #FF699A 38.02%, rgba(252, 175, 69, 0.78) 99.48%);
  border-radius: 4px;
  margin: 20px 0 0 0;
  color: #ffffff;
  font-family: 'S-CoreDream-3Light';
  font-size: 16px;
  font-weight: 700;
  letter-spacing: 3px;
}

</style>