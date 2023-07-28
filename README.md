# GURU2-Android-Project-VEGEVISION App
코틀린 + 파이썬


## 🖥️ 프로젝트 소개
Python으로 CNN 모델을 MobileNetV2로 전이학습시켜 신선한 농산물과 썩은 농산물을 구분하는 딥러닝 모델을 학습시킨 뒤, 이를 tflite 파일로 전환해 android app에 적용시켜 신선한 농산물을 구별하는 카메라 어플을 구현
<br>

## 🕰️ 개발 기간
* 23.07.19 - 23.08.01 (약 2주)

### 🧑‍🤝‍🧑 맴버구성
 - 팀대표: 오서연 - 개발(코틀린+딥러닝) / DB
 - 팀원1 : 이유진 - 개발
 - 팀원2 : 김유빈 - UI
 - 팀원3 : 박서진 - UI

### ⚙️ 개발 환경
- `Kotlin` for making application
- `Python` for DL model
- **IDE** : Android Studio Flamingo 2022.2.1
- **Framework** : Android
- **Database** : FireBase

## 📌 주요 기능

#### 시작 페이지
- OnboardingActivity.kt, page_onboadrding.xml
- 앱 구동하면 제일 먼저 나오는 화면
- 사과버튼을 눌러야 다음 화면으로 넘어감

#### 로그인 및 회원가입
- LoginActivity.kt, login_page.xml
- firebase와 연동
- 이메일과 패스워드 입력
  - DB에 없는 이메일과 패스워드일 경우, 자동 회원가입 후 로그인
  - DB에 있는 이메일과 패스워드일 경우, 로그인

#### 사진 분류
- MainActivity.kt, activity.xml (사진 업로드) / ResultActivity.kt, page_result.xml (결과)
- 카메라에서 사진 업로드
- 갤러리에서 사진 업로드
- 신선도 결과 체크 페이지
- 딥러닝 모델 활용

#### 신선도 기준 제공
- TipActivity.kt (농산물 고르기) / TipsforVeges.kt (결과)
- 딥러닝 모델에 학습시키지 않은 농산물의 신선도 기준을 제공
- 스크롤 제공

#### 마이 페이지 (로그아웃 및 회원 탈퇴)
- 로그인한 이메일 계정 show
- 로그아웃
- 회원 탈퇴
  - 정말 탈퇴할 것인지 한 번 더 되물음
- 두 버튼 모두 토스트 메시지 발생
