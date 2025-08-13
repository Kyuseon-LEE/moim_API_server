# moim_API_server - Java 백엔드 API 서버
Java와 Spring Boot를 활용하여 모임 서비스의 백엔드 API를 구현한 프로젝트입니다.

##  사용 기술 스택
- Backend: Java, Spring Boot
- Database: MySQL (JPA/Hibernate)
- 인증 및 보안: JWT (JSON Web Token)
- 환경 관리: Spring Profiles, application.properties
- 빌드 도구: Gradle
- 배포 및 관리: Docker, Docker Compose
---

##  주요 기능
- **사용자 회원가입 및 로그인 API**
- **JWT를 이용한 인증 및 권한 관리**
- **모임 생성, 조회, 수정, 삭제 기능**
- **사용자 정보 조회 및 수정 기능 UI**
- **API 요청에 대한 유효성 검사 및 에러 처리**
- **Swagger를 통한 API 문서화**
---

##  프로젝트 구조

```
moim_API_server/
├── build.gradle             # Gradle 빌드 설정 파일
├── gradle/                  # Gradle wrapper 관련 파일
├── logs/                    # 로그 파일 저장 디렉토리
├── src/                     # 소스 코드 디렉토리
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── moim/
│   │   │           └── api/
│   │   │               ├── controller/    # REST API 컨트롤러
│   │   │               ├── dto/           # 데이터 전송 객체
│   │   │               ├── exception/     # 예외 처리 클래스
│   │   │               ├── model/         # 엔티티 클래스
│   │   │               ├── repository/    # JPA 레포지토리
│   │   │               ├── security/      # 보안 관련 설정
│   │   │               ├── service/       # 서비스 로직
│   │   │               └── util/          # 유틸리티 클래스
│   │   └── resources/
│   │       ├── application.properties       # 환경 설정 파일
│   │       ├── static/                      # 정적 파일
│   │       └── templates/                   # 템플릿 파일
├── .dockerignore            # Docker 빌드 시 제외할 파일 목록
├── .gitignore               # Git에서 추적하지 않을 파일 목록
├── Dockerfile               # Docker 이미지 빌드를 위한 설정 파일
└── README.md                # 프로젝트 설명서
```


---

##  API 문서
- 회원가입: POST /api/auth/register
- 로그인: POST /api/auth/login
- 모임 생성: POST /api/groups
- 모임 조회: GET /api/groups/:id
- 모임 수정: PUT /api/groups/:id
- 모임 삭제: DELETE /api/groups/:id
- 사용자 정보 조회: GET /api/users/:id
- 사용자 정보 수정: PUT /api/users/:id

---
