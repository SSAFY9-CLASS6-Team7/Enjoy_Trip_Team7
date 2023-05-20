<template>
    <div class="container">
        <div class="left-aside"></div>
        <div class="main">
            <div class="profile-image-container">
                <div class="image-container" @click="modifyProfileImage">
                    
                    <img v-if="profileImage == ''" src='@/assets/header_icon/profile.svg' class="profile-image">
                    <img v-if="profileImage != ''" src='profileImage' class="profile-image">
                    <img src="@/assets/user_icons/modify_profile.svg" class="modify-icon">
                </div>
                <div class="user-name">
                    전 준 영 님
                </div>
            </div>

            <div class="password-container">
                <div class="inner-title">비밀번호</div>
                <div class="password-input-container" :class="{ 'success': passwordFlag === true, 'fail': passwordFlag === false }">
                    <input type="password" class="password-input" @change="passwordCheck" v-model='password'>
                    <img v-if="passwordFlag === true" src="@/assets/user_icons/success.svg" class="status-icon">
                    <img v-if="passwordFlag === false" src="@/assets/user_icons/fail.svg" class="status-icon">
                </div>
            </div>

            <div class="password-container">
                <div class="inner-title">비밀번호 확인</div>
                <div class="password-input-container" :class="{ 'success': passwordFlag === true, 'fail': passwordFlag === false }">
                    <input type="password" class="password-check-input" @change="passwordCheck" v-model='passwordSecond'>
                    <img v-if="passwordFlag === true" src="@/assets/user_icons/success.svg" class="status-icon">
                    <img v-if="passwordFlag === false" src="@/assets/user_icons/fail.svg" class="status-icon">
                </div>
            </div>

            <div class="nickname-container">
                <div class="inner-title">별 명</div>
                <input type="text" class="nickname">
            </div>

             <div class="email-container">
                <div class="inner-title">이메일</div>
                <input type="email" class="email">
            </div>

            <div class="phone-container">
                <div class="inner-title">휴대폰 번호</div>
                <div class="phone-input-container">
                    <input type="tel" v-model='phone1' class="phone-input" pattern="[0-9]*" inputmode="numeric" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*?)\..*/g, '$1');" maxlength="3"> -
                    <input type="tel" v-model='phone2' class="phone-input" pattern="[0-9]*" inputmode="numeric" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*?)\..*/g, '$1');" maxlength="4"> -
                    <input type="tel" v-model='phone3' class="phone-input" pattern="[0-9]*" inputmode="numeric" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*?)\..*/g, '$1');" maxlength="4">
                </div>
            </div>

            <div class="inner-title">생년월일</div>
            <input type="date" class="birth-input">
            

            <div class="inner-title">성별</div>
            <div class="gender-input-container">
                <div class="male-container">
                    <input id="male" type="radio" class="gender-input" name="gender" value="male" v-model="gender" hidden>
                    <label class="gender-radio" for="male" :class="{'male-selected' : gender == 'male'}">남 자</label>
                </div>
                <div class="female-container">
                    <input id="female" type="radio" class="gender-input" name="gender" value="female" v-model="gender" hidden>
                    <label class="gender-radio" for="female" :class="{'female-selected' : gender == 'female'}">여 자</label>
                </div>
            </div>

        </div>
        <div class="right-aside"></div>
    </div>
</template>

<script>
export default {
    name: 'UserModify',
    data() {
        return {
            profileImage: '',
            passwordFlag: '',
            password: '',
            passwordSecond: '',
            passwordCheckResult: '',
            gender: '',
            phone1: '',
            phone2: '',
            phone3: '',
        }
    },
    methods: {
        modifyProfileImage() {
            
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
    },
}
</script>

<style scoped>
.container {
    width: 100%;  
    height: 82vh;
    min-height: 765px;
    display: grid;
    grid-template-columns: 2fr 5fr 2fr;
    grid-template-areas: 'left main right';
    min-width: 1900px;
}

.left-aside {
    grid-area: left;
}

.right-aside {
    grid-area: right;
}

.main {
    grid-area: main;
    border: 1px solid #333333;
    width: 100%;
    display: flex;
    flex-direction: column;
    padding: 10px 20px 10px 20px;
}

.main div {
    position: relative;
    display: flex;
    align-items: center;
    margin: 10px 0 10px 0;
}

.profile-image-container {
    border: 1px solid #333333;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 20px 0 20px 0;
}

.image-container {
    width: fit-content;
    position: relative;
}

.image-container:hover {
    cursor: pointer;
}

.profile-image {
    width: 40px;
    border-radius: 45%;
}

.modify-icon {
    position:absolute;
    bottom: 0;
    width: 25px;
    right: -5px;
}

.user-name {
    font-size: 30px;
    font-weight: 600;
    margin-left: 30px;
}

input {
    height: 35px;
    outline: none;
    border: none;
    border: 1px solid #DBDBDB;
    border-radius: 4px;
    font-size: 14px;
    font-family: 'S-CoreDream-3Light';
}

.password-container {
    position: relative;
    border: 1px solid #333333;
    display: flex;
}

.password-input-container {
    position: absolute;
    right: 0;
}

.nickname-container {
    border: 1px solid #333333;
}

.inner-title {
    margin: 10px 0 5px 0;
    width: 20% !important;
    font-weight: 600;
    font-size: 16px;
    color: #5F6073;
    border:1px solid #333333;
}

.success {
    border: 1px solid #09A552 !important;
}

.fail {
    border: 1px solid #FB0808 !important;
}

.result-success {
    color: #09A552 !important;
}

.result-fail {
    color: #FB0808 !important;
}

</style>