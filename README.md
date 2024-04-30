<img src="https://github.com/song21677/weather-travel-planner-/assets/55786368/7fd6d80f-9067-489e-b033-130d94a4476d" width="660" height="360"/>

# ▶️ 프로젝트 개요
|❓ 갑자기 폭우로 숙소가 취소됐네.. 당장 갈만한 여행지는 없을까?|
|:-|
| **❓ 여행을 계획할 때 날씨를 같이 봤으면 좋겠다..** |
| **💡 날씨를 제공하면서 여행계획을 동시에 수립하고, 날씨에 따라 여행지를 추천하는 사이트를 만들어보자!** |

<br>

# ▶️ 프로젝트 소개
## ⛲ 프로젝트 플로우
<img src="https://github.com/song21677/weather-travel-planner-/assets/55786368/be847684-24e7-41f4-83ef-ab7c236f79ce"/>
<br><br>

## 📐 프로젝트 아키텍처

<img src="https://github.com/song21677/weather-travel-planner-/assets/55786368/7876605a-59be-4b81-b1c7-8f98fe090c74" width="540" height="280"/>
<br><br>

## 🛠️ 기술 스택
| 분야 | 기술 |
|:-|:-|
| BackEnd | <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/java 8-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/myBatis-343434?style=for-the-badge&logo=myBatis&logoColor=white"/> <img src="https://img.shields.io/badge/oracle 11-F80000?style=for-the-badge&logo=oracle&logoColor=white">|
| FrontEnd | <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> |
| Collaboration | <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/discord-5865F2?style=for-the-badge&logo=discord&logoColor=white"> |
| Tool | <img src="https://img.shields.io/badge/eclipse-2C2255?style=for-the-badge&logo=eclipse ide&logoColor=white"> <img src="https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white"> <img src="https://img.shields.io/badge/visual studio code-007ACC?style=for-the-badge&logo=visual studio code&logoColor=white">|
| Open API | <img src="https://github.com/song21677/weather-travel-planner-/assets/55786368/43248e80-3837-43e2-9024-cf488a6351e3" width="80" height="20"/> <img src="https://github.com/song21677/weather-travel-planner-/assets/55786368/538a9431-9e4f-431a-8630-d50d9a0d7839" width="80" height="40"/> <img src="https://github.com/song21677/weather-travel-planner-/assets/55786368/78038b5d-6c5d-49b3-9416-98e59518f843" width="60" height="40"/>|

## 🌳 브랜치 관리 전략
| Git Flow를 사용하여 브랜치를 관리합니다. |
|:-|

<br>

## 🎨 ERD
|ERD Cloud: https://www.erdcloud.com/d/KuZuaTzwjXJmmcbuZ|
|:-|
![image](https://github.com/song21677/weather-travel-planner-/assets/55786368/efc87897-4a5b-4332-b9ee-77ec91872c34)

<br>

## 🖥️ 기능 별 화면 및 소개
### 1. 로그인/회원가입 페이지
<img src="https://github.com/song21677/weather-travel-planner-/assets/55786368/c10bef96-1eb5-411f-9d48-8ecfc957e723"/>

| 🔑 로그인 후에 날씨 플래너를 이용할 수 있습니다. |
|:-|

|🔐 계정 |
|:-|
| 회원가입 : 회원가입 버튼을 통해 회원가입을 할 수 있습니다. 이때 비밀번호는 암호화됩니다. | 
| 로그인 : 아이디와 비밀번호의 입력을 통해 로그인이 가능합니다. |
| 로그아웃 : 로그인이 성공적일 경우 로그아웃 버튼으로 전환됩니다. |

<br>

### 2. 메인 페이지
<img src="https://github.com/song21677/weather-travel-planner-/assets/55786368/876847b3-c3af-4212-b860-d84a1ac576de" width="450" height="360"/>

|📃 메인 페이지를 통해 날씨 정보를 제공합니다.|
|:-|

|⛅ 메인 페이지|
|:-|
|위치 정보 : 브라우저를 기반으로 회원의 위치 정보를 제공해 줍니다.|
|날씨 지도 : 날씨 데이터에 등록된 단기 예보를 바탕으로 현재 전국 날씨를 보여 줍니다.|
|주간 날씨 : 주간 예보 , 주간 기온, 단기 예보를 바탕으로 앞으로 일주일 간 날씨 정보를 스프레드 시트로 제공합니다.|
|인기 여행지 : 여행 자랑하기에 등록된 인기 여행지를 조회 순으로 보여 줍니다.|

<br>

### 3. 여행 일정 짜기 페이지
<img src="https://github.com/song21677/weather-travel-planner-/assets/55786368/c31791d1-991e-4d9e-9db0-f86f659d88a7" width="480" height="200"/>
<img src="https://github.com/song21677/weather-travel-planner-/assets/55786368/1e2a0209-3ae5-4df0-b026-a7370fd0a7a4" width="480" height="200"/>

|📢 여행 일정 짜기 섹션을 통해 여행 일정을 계획할 수 있습니다.|
|:-|

|📅 여행 일정 짜기|
|:-|
|장소 데이터 : Tour API를 통해 저장된 장소값을 토대로 여행 일정 설계가 가능합니다.|
|날씨 매칭 : 캘린더에 지정된 날짜와 위치를 받아 날씨와 적합 유무를 판단합니다.|
|적합도 : 여행지가 적합할 경우 날씨의 블록 색상이 초록색으로 제공 되며 부적합할 경우 붉게 제공되어 사용자가 쉽게 판단할 수 있습니다.|

<br>

### 4. 추천 페이지
<img src="https://github.com/song21677/weather-travel-planner-/assets/55786368/2ebda961-fada-449c-bcfd-64d6d51cbe19" width="450" height="360"/>

|📢 추천 페이지를 통해 근처의 장소를 카테고리 별로 제공합니다.|
|:-|

|🆗 추천 페이지|
|:-|
|위치 정보 : 위치 정보를 동의하지 않을 경우 서울 시청을 기반으로 나오게 됩니다.|
|연령별 추천 : 기존 회원의 로그를 바탕으로 나이와 비교하여 순서대로 추천 해줍니다.|
|근처 장소 추천 : 현재 위도와 경도를 토대로 100km 이내의 장소를 추천해 줍니다.|

<br>

### 5. 자랑하기 페이지
<img src="https://github.com/song21677/weather-travel-planner-/assets/55786368/b39195ff-cf6a-4fca-87f0-bff0bce4a08c" width="720" height="360"/>

|👌 자랑하기 페이지를 통해 여행기를 자랑할 수 있습니다.|
|:-|

|🔥 자랑하기 페이지|
|:-|
|글쓰기 : 회원이 계획했던 일정을 기반으로 자랑하기 페이지에 글을 쓸 수 있습니다.|
|리뷰 : 회원은 장소에 대한 리뷰와 평점을 남길 수 있습니다.|
|미리보기 : 진입 전 미리보기에서 카탈로그 형식으로 이미지와 제목을 볼 수 있습니다.|
