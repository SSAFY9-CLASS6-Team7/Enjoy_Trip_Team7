<template>
    <div class="container">
        <div class="left-aside"></div>
        <div class="main">
            <div class="profile-image-container">
                <div class="image-container" @click="uploadClick">
                    <input type="file" id="profileUpload" @change="profileUpload" hidden>
                    <img v-if="checkUserInfo.profilePicPath != null && checkUserInfo.profilePicPath != ''" :src="'http://192.168.212.72/profilePath/' + checkUserInfo.profilePicPath" class='profile-image' >
                <img v-if="checkUserInfo.profilePicPath == null || checkUserInfo.profilePicPath == ''" src="@/assets/header_icon/profile.svg" class='profile-image'>
                    <img src="@/assets/user_icons/modify_profile.svg" class="modify-icon">
                </div>
                <div class="user-name">
                    {{  this.checkUserInfo.name }} 님
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

            <div class="password-check-result" :class="{ 'result-success': passwordFlag === true, 'result-fail': passwordFlag === false }"> {{passwordCheckResult}} </div>

            <div class="nickname-container">
                <div class="inner-title">별 명</div>
                <input type="text" class="nickname" v-model="nickname">
            </div>

             <div class="email-container">
                <div class="inner-title">이메일</div>
                <input type="email" class="email" v-model="email">
            </div>

            <div class="phone-container">
                <div class="inner-title">휴대폰 번호</div>
                <div class="phone-input-container">
                    <input type="tel" v-model='phone1' class="phone-input" pattern="[0-9]*" inputmode="numeric" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*?)\..*/g, '$1');" maxlength="3"> -
                    <input type="tel" v-model='phone2' class="phone-input" pattern="[0-9]*" inputmode="numeric" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*?)\..*/g, '$1');" maxlength="4"> -
                    <input type="tel" v-model='phone3' class="phone-input" pattern="[0-9]*" inputmode="numeric" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*?)\..*/g, '$1');" maxlength="4">
                </div>
            </div>
            
            <div class="birth-container">
                <div class="inner-title">생년월일</div>
                <input type="date" class="birth-input" v-model="birth">
            </div>
            
            <div class="gender-container">
                <div class="inner-title">성별</div>
                <div class="gender-input-container">
                    <div class="male-container">
                        <input id="M" type="radio" class="gender-input" name="gender" value="M" v-model="gender" hidden>
                        <label class="gender-radio" for="M" :class="{'male-selected' : gender == 'M'}">남 자</label>
                    </div>
                    <div class="female-container">
                        <input id="F" type="radio" class="gender-input" name="gender" value="F" v-model="gender" hidden>
                        <label class="gender-radio" for="F" :class="{'female-selected' : gender == 'F'}">여 자</label>
                    </div>
                </div>
            </div>

            <div class="buttons">
                <button class="cancel" @click="modifyCancel">취소</button>
                <button class="modify-submit" @click="modifyUser">수정</button>
            </div>

        </div>
        <div class="right-aside"></div>
    </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import axios from 'axios';
export default {
    name: 'UserModify',
    data() {
        return {
            profileImage: '',
            uploaded: '',
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
        }
    },
    computed: {
      ...mapGetters('userStore', ['checkUserInfo']),
      totalPhone() {
            return this.phone1 + '-' + this.phone2 + '-' + this.phone3;
        }
    },
    methods: {
        ...mapActions('userStore', ['userConfirm']),
        uploadClick() {
            const inputElement = document.getElementById("profileUpload");
            inputElement.click();
        },
        profileUpload(event){
            const file = event.target.files[0];
            this.uploaded = file;
            console.log("profile : " + this.uploaded);
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
        modifyCancel(){
            this.$router.push("/");
        },
        async modifyUser(){
            if (!this.passwordFlag && this.passwordFlag != '') {
                alert("새 비밀번호가 올바르게 입력되지 않았습니다!");
            }else {
                let f = new FormData();
                if (this.password != ''){
                    f.append("password", this.password);
                }
                f.append("nickname", this.nickname);
                f.append("email", this.email);
                f.append("phone", this.totalPhone);
                f.append("birth", this.birth);
                f.append("gender", this.gender);
                if (this.uploaded != '') {
                    console.log("profile : " + this.uploaded);
                    f.append("file", this.uploaded);
                }

                await axios.put("http://localhost/user/"+this.checkUserInfo.userId, f);
                let user = {
                    userId: this.checkUserInfo.userId,
                    password: ''
                }
                if (this.password != '') {
                    user.password = this.password;
                }else {
                    user.password = this.checkUserInfo.password;
                }
                await this.userConfirm(user);
                this.$router.push("/");
            }
        }
    },
    created() {
        let userInfo = this.checkUserInfo;
        console.log(userInfo);
        this.gender = userInfo.gender;
        this.email = userInfo.email;
        this.birth = userInfo.birth;
        this.nickname = userInfo.nickname;

        let phoneNumber = userInfo.phone.split("-");
        this.phone1 = phoneNumber[0];
        this.phone2 = phoneNumber[1];
        this.phone3 = phoneNumber[2];
        console.log(this.checkUserInfo.profilePicPath);
        // this.profileImage = 'http://localhost/imagePath/' + this.checkUserInfo.profilePicPath;
    }
}
</script>

<style scoped>
.container {
    width: 100%;  
    height: 82vh;
    min-height: 765px;
    display: grid;
    grid-template-columns: 2fr 3fr 2fr;
    grid-template-areas: 'left main right';
    min-width: 1500px;
}

.left-aside {
    grid-area: left;
}

.right-aside {
    grid-area: right;
}

.status-icon {
    position: absolute;
    top: 5px;
    right: 10px;
}

.main {
    grid-area: main;
    width: 100%;
    display: flex;
    flex-direction: column;
    padding: 10px 80px 10px 80px;
    margin: 20px 0 50px 0;
    box-shadow: -1px 5px 20px rgba(0, 0, 0, 0.2);
    border-radius: 14px;
}

.main div {
    display: flex;
    align-items: center;
    margin: 10px 0 10px 0;
}

.profile-image-container {
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
    width: 60px;
    height: 60px;
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
    padding:0 0 0 30px;
}

input {
    height: 35px;
    outline: none;
    border: none;
    border: 1px solid #DBDBDB;
    border-radius: 4px;
    padding: 0 0 0 10px;
    font-size: 14px;
    font-family: 'S-CoreDream-3Light';
}

.password-container {
    position: relative;
    display: flex;
}

.password-input-container {
    position: absolute;
    width: 80%;
    right: 0;
}

.password-input-container input {
    width: 100%;
}

.password-check-result {
    padding: 0 0 0 20%;
}

.nickname-container input {
    width: 80%;
}

.email-container input {
    width: 80%;
}

.birth-container input {
    width: 80%;
    text-align: center;
}

.phone-input-container {
    width: 80%;
    display: flex;
    justify-content: space-between;
}

.phone-input-container input {
    width: 30%;
    text-align: center;
}

.inner-title {
    margin: 10px 0 5px 0;
    width: 20% !important;
    font-weight: 600;
    font-size: 16px;
    color: #5F6073;
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

.gender-input-container {
    width: 80%;
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

.buttons {
    display: flex;
    justify-content: space-around;
    padding:10px 100px 0 100px;
}

.cancel {
  border: none;
  color: #333333;
  font-family: 'S-CoreDream-3Light';
  font-weight: 800;
  font-size: 18px;
  background: #9b9b9b;
  border-radius: 8px;
  padding: 10px 23px 10px 23px;
  min-width: 100px;
  width: 200px;
  margin-right: 40px;
}

.modify-submit {
  border: none;
  color: #ffffff;
  font-family: 'S-CoreDream-3Light';
  font-weight: 800;
  font-size: 18px;
  background: linear-gradient(95.36deg, #E1306C 2.32%, #FF699A 68.42%, #FCAF45 104.98%);
  background-blend-mode: darken;
  border-radius: 8px;
  padding: 10px 20px 10px 20px;
  min-width: 100px;
  width: 200px;
  margin-left: 40px;
}


</style>