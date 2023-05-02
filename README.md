# SSAFY 9th - 1 Spring PJT

## EnjoyTrip   
<img src="https://user-images.githubusercontent.com/70866410/234868272-9989afdb-e140-4663-a108-ee7c46b0be37.gif">

## 📌소개

 공공 데이터 포털의 오픈 API를 이용하여 `관광지`와 `여행`에 대한 정보를 제공하고 사용자를 위한 편의 기능을 제공하는 서비스입니다.   
 SSAFY 1학기 백엔드 관통 프로젝트로서 진행하였습니다.

## 📍기능
- 여행지 정보 제공
- 여행 계획 작성 기능
- 여행 후기 작성 기능
- 여행 관련 게시판 기능
  - 자유
  - 후기
  - 추천
  - 질문
- 여행 계획 및 후기 공유 기능 ( Ver 2.0 추가 요망 )

## 🛠개발환경
### 협업 : <img src="https://img.shields.io/badge/Notion-000000?style=flat-square&logo=Notion&logoColor=white"/></a> 
### VCS(형상관리) : <img src="https://img.shields.io/badge/Git-000000?style=flat-square&logo=Git&logoColor=red"/></a>, <img src="https://img.shields.io/badge/GitHub-000000?style=flat-square&logo=GitHub&logoColor=white"/></a> 
### Backend : <img src="https://img.shields.io/badge/Spring-000000?style=flat-square&logo=Spring&logoColor=6DB33F"/></a>, <img src="https://img.shields.io/badge/JAVA-000000?style=flat-square&logo=JAVA&logoColor=007396"/></a>, <img src="https://img.shields.io/badge/MyBatis-000000?style=flat-square&logo=MyBatis&logoColor=007396"/></a> 
### DB : <img src="https://img.shields.io/badge/MySQL-000000?style=flat-square&logo=MySQL&logoColor=4479A1"/></a> 

---
## 📚프로젝트 진행    
### [📜API 명세](https://gentle-capricorn-124.notion.site/9a06d8f8fe9144fab2f7470fd99ba63a)
  <details>
  <summary>API 상세 내용</summary><br>
    <img src="https://user-images.githubusercontent.com/70866410/234883237-53e398ee-ebb0-49a4-ab72-b6b4a6edf7c1.png">
  </details>
  

### 🗂프로젝트 패키지 구조   
```
com
 ㄴssafy
   ├─config
   ㄴenjoytrip
     ├─board & comment
     │ ├─controller
     │ ㄴmodel
     │   ├─dto
     │   ├─mapper
     │   ㄴservice
     ├─user
     │ ├─controller
     │ ㄴmodel
     │   ├─dto
     │   ├─mapper
     │   ㄴservice
     ├─plan
     │ ├─controller
     │ ㄴmodel
     │   ├─dto
     │   ├─mapper
     │   ㄴservice
     ├─history
     │ ├─controller
     │ ㄴmodel
     │   ├─dto
     │   ├─mapper
     │   ㄴservice
     ├─image
     │ ├─controller
     │ ㄴmodel
     │   ├─dto
     │   ├─mapper
     ├─attraction
     │ ├─controller
     │ ㄴmodel
     │   ├─dto
     │   ├─mapper
     │   ㄴservice
     ├─filter
     ㄴexception
     
```
### ✒커밋 컨벤션
 |태그|설명|예시|
|---|---|---|
|Feat|새로운 기능 추가|Feat : 게시글 조회|
|Fix|파일, 코드, 버그 수정/삭제|Fix : 댓글 미표기 현상 수정|
|Docs|문서 작성 및 수정|Docs : README.md 수정|
|Refactor|프로덕션 코드 리팩토링|Refactor : 유저 컨트롤러 리팩토링|
