# MFV

정육점·과일·채소를 온라인으로 주문하는 쇼핑몰 (Spring Boot 포트폴리오 프로젝트)

## 소개

신선식품 카테고리(정육·과일·채소)를 한 곳에서 주문하고, AI가 상품 기반 레시피를 추천해 주는 웹 애플리케이션입니다.

## 주요 기능

- 회원가입 / 로그인 / 회원탈퇴 (Spring Security 세션 인증)
- 상품 조회 및 관리
- 주문 / 결제 / 포인트 적립·사용
- 상품 Q&A
- 구매 후기 작성 (사진 첨부, 수정, 삭제)
- 마이페이지 (주문, 포인트, 리뷰 내역)
- AI 레시피 추천 (Google Gemini)

## 스크린샷


## 기술 스택

| 구분 | 사용 기술 |
|---|---|
| Frontend | HTML, CSS, JavaScript, Bootstrap 5.3.3 (ZayShop 템플릿 기반) |
| Backend | Java 17, Spring Boot 4.1.0, Spring Security, Spring Data JPA, Gradle |
| Database | MySQL 9.7 (Docker) |
| AI | Spring AI 2.0.0, Google Gemini |
| 배포 | <!-- 배포 완료 후 작성 --> |

## 시작하기

### 사전 요구사항

- JDK 17
- Docker (MySQL 실행용)

### 설치

```bash
git clone  https://github.com/DamD-O/mfv-shop.git
cd mfv-shop
```

### DB 실행

Docker로 MySQL 9.7 컨테이너를 실행하고 `shop` 데이터베이스를 생성합니다.

### 환경 변수 설정

| 변수명 | 설명 |
|---|---|
| `DB_USERNAME` | DB 사용자명 |
| `DB_PASSWORD` | DB 비밀번호 |
| `GEMINI_API_KEY` | Google Gemini API 키 |

`application.yml`은 저장소에 포함되어 있지 않으므로 직접 생성해 위 변수를 참조하도록 설정합니다.

### 실행

```bash
./gradlew bootRun
```

http://localhost:8080 으로 접속합니다. <!-- 포트를 바꿨다면 수정 -->

## 폴더 구조

```
src/main/
├── java/com/example/shop/
│   └── (도메인별 패키지, dto 포함)
└── resources/
    └── static/   # HTML, CSS, JS
```
