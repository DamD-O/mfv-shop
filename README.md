# MFV

정육점·과일·채소를 온라인으로 주문하는 쇼핑몰 (Spring Boot 포트폴리오 프로젝트)

## 소개

신선식품 카테고리 (정육·과일·채소)를 한 곳에서 주문하고, AI가 상품 기반 레시피를 추천해 주는 웹 애플리케이션입니다.

## 데모 (배포 서버)

> 포트폴리오용 데모 서버입니다. 실제 개인정보 (이름, 연락처, 주소, 비밀번호)는 입력하지 마세요. 결제는 실제로 이루어지지 않습니다.

| 구분               | 주소                                            |
|--------------------|-------------------------------------------------|
| 사이트             | http://161.33.137.45:8080/index.html            |
| API 문서 (Swagger) | http://161.33.137.45:8080/swagger-ui/index.html |

**데모 계정** (고객): 아이디 `user01` / 비밀번호 `Test2026!@!`

- 데모 계정은 여러 사람이 함께 쓰므로 탈퇴 / 회원 정보 수정이 막혀 있습니다. 직접 회원가입해서 사용해도 됩니다.
- 관리자 화면은 아래 [스크린샷](#스크린샷)에서 확인할 수 있습니다.

## 주요 기능

**고객**

- 회원가입 / 로그인 / 회원정보 수정 / 회원탈퇴 (Spring Security 세션 인증)
- 상품 목록 (카테고리 / 검색 / 정렬 / 페이징), 상품 상세
- 장바구니, 주문 / 결제 (모의 결제), 주문 취소, 포인트 사용·적립·환불
- 배송지 관리 (카카오 우편번호 API)
- 구매 후기 (별점, 사진 첨부, 수정, 삭제)
- 상품 Q&A (비밀글), 고객센터 (공지사항, 1:1 문의)
- 마이페이지 (주문 / 포인트 / 리뷰 / Q&A 내역)
- AI 레시피 추천: 모든 페이지 오른쪽 아래 버튼, 지금 구매 가능한 상품 (판매 중 + 재고 있음)을 기준으로 레시피와 상품 추천 (Google Gemini)

**관리자**

- 대시보드: 주문 추이 (일별 / 월별), 상품 판매 순위, 회원 수 (Chart.js)
- 상품 관리 (등록 / 수정 / 판매 중단, 이미지 여러 장)
- 주문 관리 (상태 변경, 판매사 취소)
- 회원 관리 (목록 / 상세 / 이름 변경)
- Q&A 답변, 공지사항 등록 / 수정

## 스크린샷

### 고객

**홈** (오늘의 추천 상품: 판매 중 + 재고 있는 상품 중 랜덤 3개)

![홈](docs/screenshots/mainhome.png)

**상품 목록** (카테고리 / 정렬, 재고 표시: 재고 있음 / N개 남음 / 품절)

![상품 목록](docs/screenshots/product.png)

**상품 상세** (리뷰 작성자 마스킹, 사진 리뷰)

![상품 상세](docs/screenshots/product_detail.png)

**장바구니**

![장바구니](docs/screenshots/cart.png)

**결제** (배송지 선택, 포인트 사용, 모의 결제)

![결제](docs/screenshots/payment.png)

**AI 레시피 추천** (모든 페이지 오른쪽 아래 버튼)

![AI 레시피 추천](docs/screenshots/ai.png)

<details>
<summary><b>마이페이지</b> (펼치기)</summary>

**주문 / 배송 조회**

![주문 조회](docs/screenshots/my_order.png)

**포인트**

![포인트](docs/screenshots/my_point.png)

**내 리뷰**

![내 리뷰](docs/screenshots/my_review.png)

![리뷰 수정](docs/screenshots/my_review_detail.png)

**내 Q&A**

![내 Q&A](docs/screenshots/my_qna.png)

**회원 정보 수정**

![회원 정보 수정](docs/screenshots/my_info.png)

**배송지 관리**

![배송지 관리](docs/screenshots/my_address.png)

**회원 탈퇴**

![회원 탈퇴](docs/screenshots/withdraw.png)

</details>

### 관리자

**대시보드** (배송 완료 주문 추이, 상품 판매 순위, 회원 수)

![대시보드](docs/screenshots/admin_dashboard.png)

**주문 관리** (상태 필터, 허용된 상태로만 변경)

![주문 관리](docs/screenshots/admin_order.png)

**Q&A 관리**

![Q&A 관리](docs/screenshots/admin_qna.png)

**상품 관리**

![상품 관리](docs/screenshots/admin_product.png)

**회원 관리**

![회원 관리](docs/screenshots/admin_customer.png)

**공지사항 관리**

![공지사항 관리](docs/screenshots/admin_notice.png)

## 기술 스택

| 구분     | 사용 기술                                                              |
|----------|------------------------------------------------------------------------|
| Frontend | HTML, CSS, JavaScript, Bootstrap 5.3.3 (ZayShop 템플릿 기반), Chart.js |
| Backend  | Java 17, Spring Boot 4.1.0, Spring Security, Spring Data JPA, Gradle   |
| Database | MySQL 9.7 (Docker)                                                     |
| AI       | Spring AI 2.0.0, Google Gemini                                         |
| API 문서 | springdoc-openapi (Swagger UI)                                         |
| 배포     | Oracle Cloud (Ubuntu 22.04), Docker (MySQL), systemd                   |
| CI/CD    | GitHub Actions (빌드 → SSH 배포 → 재시작)                              |

## 배포 구성

```
브라우저 ──▶ Oracle Cloud VM (Ubuntu 22.04, 1GB RAM)
              ├── Spring Boot (jar, systemd 서비스로 상시 실행, 포트 8080)
              └── MySQL 9.7 (Docker 컨테이너, 외부 접속 차단)
```

- DB 계정 / API 키는 서버 환경변수 파일로 관리 (저장소에 포함하지 않음)
- 업로드 이미지는 서버 로컬 폴더에 저장

**자동 배포 (GitHub Actions)**

```
main에 push ──▶ GitHub Actions
                 ├── JDK 17 + Gradle bootJar 빌드
                 └── 성공 시 SSH로 jar 전송 → 이전 jar 백업 → systemctl restart
```

- 서버 주소 / SSH 키는 GitHub Secrets로 관리, 배포 전용 SSH 키 사용
- README / docs만 바뀐 커밋은 실행하지 않음 (`paths-ignore`), PR은 빌드만 하고 배포하지 않음

## 주요 구현 / 설계

**도메인 규칙**

| 주제           | 내용                                                                                                         |
|----------------|--------------------------------------------------------------------------------------------------------------|
| 주문 상태 전이 | 허용된 상태 변경만 가능 (예: 결제 완료 → 배송 준비 중), 되돌리기 / 건너뛰기 불가. 취소 시 재고 / 포인트 복구 |
| 포인트         | 결제 시 사용, 배송 완료 시 적립 (주문당 1회), 결제 완료 / 배송 준비 중 취소 시 환불                          |
| 회원 탈퇴      | soft delete + 개인정보 (이름 / 이메일 / 연락처) 마스킹, 진행 중 주문이 있으면 탈퇴 불가                      |
| 개인정보 보호  | 비밀글은 작성자 / 관리자만 원문, 작성자 아이디 마스킹                                                        |

**성능 / 동시성**

| 주제           | 내용                                                                                                                                                           |
|----------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 재고 동시 주문 | 주문 시 비관적 락 (`SELECT ... FOR UPDATE`)으로 초과 판매 방지, 취소 시 `stock = stock + ?` 원자적 UPDATE. 재고 1개에 10건 동시 주문 → 1건만 성공 확인         |
| N+1 해결       | `default_batch_fetch_size`로 연관 조회를 `IN` 쿼리로 묶음. 주문 목록의 리뷰 여부는 양방향 `@OneToOne` (지연 로딩 불가)을 제거하고 `IN` 조회 1번 + Map으로 변경 |
| 페이징         | 공통 응답 형식 (`PageResponse`), 정렬은 서버에서 고정 (+ id 2차 정렬로 페이지 간 중복 / 누락 방지)                                                             |
| 통계           | DB에서 집계 (native query + `GROUP BY`), 주문 없는 날짜는 0으로 채움                                                                                           |

**보안 / 예외 처리**

| 주제             | 내용                                                                                                                      |
|------------------|---------------------------------------------------------------------------------------------------------------------------|
| 예외 응답 분리   | 의도한 예외 (`MfvException`)는 400 + 안내 문구, 그 외는 500 + 고정 문구 (내부 메시지 노출 안 함) + 로그                   |
| XSS 방지         | 화면에 출력하는 사용자 입력은 `escapeHtml` 처리                                                                           |
| 파일 업로드      | 확장자 허용 목록 (svg / html 등 거부), 저장 이름은 UUID, 용량 20MB (화면에서 먼저 검사)                                   |
| 로그인 redirect  | 같은 사이트 주소만 허용 (외부 주소 / `javascript:` 차단)                                                                  |
| 입력값 검증      | DTO 길이 / 형식 검증을 DB 컬럼 길이와 맞춤, 비밀번호 정책은 서버 / 화면 공통 정규식                                       |
| 리소스 노출 방지 | 타인 배송지 / 주문 / 리뷰는 조회 조건에 본인 아이디를 넣어 "없음"과 같은 응답 (아이디를 바꿔 넣어 존재 여부를 알 수 없게) |

**AI 추천**: 구매 가능한 상품 목록을 프롬프트에 넣어 추천, 질문 길이 검증, AI 호출 실패 시 안내 문구

## 테스트

- 기능 / 예외 / 보안 테스트 케이스 154개를 엑셀로 정리해 로컬 DB를 비운 상태에서 전체 진행 ([docs/mfv-test-cases.xlsx](docs/mfv-test-cases.xlsx))
- 1차 실패 4건은 원인을 찾아 수정한 뒤 재테스트해 전체 통과 (이력은 파일 메모 칸에 기록)

| 발견한 문제                              | 원인                                                                                   | 수정                                         |
|------------------------------------------|----------------------------------------------------------------------------------------|----------------------------------------------|
| 타인 배송지 접근 / 리뷰 중복 작성 시 500 | 의도한 예외가 아닌 예외 (`EntityNotFoundException`, DB unique 제약 위반)가 그대로 전파 | `MfvException`으로 통일, 저장 전 중복 검사   |
| 관리자 페이지 로그아웃 시 권한 알림 3번  | 로그아웃 링크 `href="#"`가 `hashchange`를 일으켜 대시보드 API 3개를 다시 호출          | 링크 기본 동작 막음                          |
| 마지막 페이지 1건 상태 변경 시 빈 목록   | 필터에서 빠진 뒤 같은 페이지 번호로 다시 조회                                          | 목록이 비면 앞 페이지로                      |
| 큰 이미지 업로드 시 알림 없이 멈춤       | 제한 초과 시 서버가 응답 없이 연결을 끊어 `fetch`가 예외를 던짐                        | 화면에서 용량 먼저 검사 + 업로드 `try/catch` |

## 로컬 실행

### 사전 요구사항

- JDK 17
- Docker (MySQL 실행용)

### 설치

```bash
git clone https://github.com/DamD-O/mfv-shop.git
cd mfv-shop
```

### DB 실행

Docker로 MySQL 9.7 컨테이너를 실행하고 `shop` 데이터베이스를 생성합니다.

### 환경 변수 설정

| 변수명           | 설명                 |
|------------------|----------------------|
| `DB_USERNAME`    | DB 사용자명          |
| `DB_PASSWORD`    | DB 비밀번호          |
| `GEMINI_API_KEY` | Google Gemini API 키 |
| `DEMO_CUSTOMER_ID` | (선택) 탈퇴 / 정보 수정을 막을 데모 계정 아이디. 비우면 제한 없음 |

`src/main/resources/application.yaml`은 위 환경 변수를 참조하므로, 실행 전에 환경 변수만 설정하면 됩니다.

### 실행

```bash
./gradlew bootRun
```

http://localhost:8080 으로 접속합니다.
API 문서 (Swagger UI)는 http://localhost:8080/swagger-ui/index.html 에서 확인할 수 있습니다.

## 폴더 구조

```
.github/workflows/
└── ci.yaml            # GitHub Actions 빌드 / 배포
src/main/
├── java/com/example/shop/
│   ├── domain/        # 도메인별 패키지 (controller / service / repository / entity / dto)
│   │   ├── admin      # 관리자, 대시보드 통계, 회원 관리
│   │   ├── customer   # 회원가입 / 정보 수정 / 탈퇴
│   │   ├── product    # 상품, 상품 이미지
│   │   ├── order      # 주문 / 결제 / 취소
│   │   ├── point      # 포인트 이력
│   │   ├── address    # 배송지
│   │   ├── review     # 리뷰, 리뷰 이미지
│   │   ├── qna        # 상품 Q&A, 1:1 문의
│   │   ├── notice     # 공지사항
│   │   └── recipe     # AI 레시피 추천
│   └── global/        # 공통 (예외 처리, 페이징 응답, 파일 저장, 검증 패턴, 마스킹 등)
└── resources/
    └── static/        # HTML, CSS, JS (common.js 공통 함수)
docs/
├── mfv-test-cases.xlsx  # 전체 테스트 케이스
└── screenshots/
```
