지속적
1. 통합
2. 제공/배포
를 의미하는 소프트웨어 개발 및 배포 프로세스를 자동화하는 방법
### CI  (Continuous Integration)
여러 개발자가 작성한 코드를 자주 중간 저장소에 병합하고(feature -> main) 자동으로 빌드 -> 단위테스트 과정을 돌려서 코드 품질을 즉시 검증하는 문화 방법론
### CD(Continuous Delivery/Deployment)
CI를 통과한 결과물을 자동으로 스테이징(테스트), 운영 환경에서 배포하여 사용자에게 빠르게 새 기능을 제공하거나(Delivery), 설정에 따라 사람 개입 없이 운영에 바로 반영(Deployment)하는 단계

#### 파이프라인
- CI/CD을 구현하기 위해 정의하는 자동화된 워크플로우
	ex) 코드체크아웃 -> 빌드 -> 단위테스트 -> 커버리지측정 -> 정적분석 -> 아티팩트생성  -> 배포
- Jenkinsfile, GitHub Actions YAML, GitLab CI YAML 같은 설정 파일 한 덩어리 -> `.github/workflows/ci.yml`
```yml
# .github/workflows/ci.yml
name: CI Pipeline

on:
  push:
    branches: [ main, feature/** ]
  pull_request:

jobs:
  build-and-test:
    runs-on: ubuntu-latest
    steps:
      - name: 코드 체크아웃
        uses: actions/checkout@v3

      - name: JDK 설정
        uses: actions/setup-java@v3
        with:
          java-version: '17'

      - name: 빌드 & 단위테스트
        run: ./gradlew clean build

      - name: 코드 커버리지 생성
        run: ./gradlew jacocoTestReport

      - name: 정적분석 (Checkstyle)
        run: ./gradlew checkstyleMain

      - name: 아티팩트 패키징
        run: ./gradlew assemble

  deploy:
    needs: build-and-test
    runs-on: ubuntu-latest
    if: github.ref == 'refs/heads/main'
    steps:
      - name: 아티팩트 다운로드
        uses: actions/download-artifact@v3

      - name: 배포 스크립트 실행
        run: ./deploy.sh
```

---
계산기 프로젝트를 예로 든다면
 1. 개발자가 기능 커밋& 푸쉬
 2. 파이프라인 자동 시작
 3. CI 파이프라인
	 1. 코드 체크아웃
	 2. 빌드: 컴파일 오류 체크
	 3. 단위 테스트: 단위테스트 실행
	 4. 커버리지 측정: jococo
	 5. 정적분석: checkstyle, sonarQube/Cloud 스캔 실행 - 코드 스타일ㅣ이나 버그 취약점을 검사
	 6. 결과리포트
4. CD 파이프라인
	1. merge감지
	2. 아티팩트 생성: ./gradlew clean bulid -> JAR/ZIP 패키징
	3. 스테이징 배포: 성공한 빌드 결과물을 스테이징 서버에 자동 배포
	4. (운영 배포)


| 내가 지금까지 했던 방식                                                                       | 자동화                                                                                                                            |
| ----------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------ |
| 1. 브랜치에서 커밋 & 푸시  <br>2. 기능 끝나면 PR 생성  <br>3. 리뷰어가 수동으로 빌드·테스트 확인  <br>4. 충돌 없으면 머지 | 1. 브랜치에 커밋 & 푸시 즉시 CI 자동 실행  <br>2. 실패하면 바로 피드백(로그·테스트 리포트)  <br>3. 모든 체크가 통과된 커밋만 PR 생성  <br>4. 보호된 main 브랜치엔 “통과된 PR”만 머지 가능 |
- 푸쉬만 해도 ci가 돌아가기 때문에 빌드테스트커버리지 스타일 검사자 자동으로 수행됨
- 실패된 커밋은 머지 전에 사전에 걸러짐
- 푸쉬할때는 코드가 정상적으로 동작(오류x)하는 상태여야함
- pr은 수동 내가 올려야되지만 통과가 된 코드만 올림(깃허브 브랜치 보호 설정을 통해 통과되지 않은 pr은 merge 버튼 불활성화)