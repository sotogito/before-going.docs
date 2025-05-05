#### 워크플로우 이름 지정
```yml
name: CI
```

#### 트리거(발동 조건) 설정
```
on:
  push:
    branches:
      - main
  pull_request:
    types: [opened, synchronize, reopened]
```
- main 브랜치에 커밋푸쉬할 때마다 실행
- PR을 열거나 업데이트, 재오픈할 떄마다 실행

#### jobs : 병렬 작업 정의(CI 워크플로우)
```
jobs:
```

```
  sonarcloud:
    name: SonarCloud
    runs-on: ubuntu-latest
```
- name : ui에 표시될 이름

```
    steps:
      - uses: actions/checkout@v4
        with:
          fetch-depth: 0  # Shallow clones should be disabled for a better relevancy of analysis
```
- steps : 각 단계별로 무슨 역할을 하는지

```
      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: 17
          distribution: 'zulu' # Alternative distribution options are available
```
- JDK17 설치
- zulu 배포판 사용
```
      - name: Cache SonarQube packages
        uses: actions/cache@v4
        with:
          path: ~/.sonar/cache
          key: ${{ runner.os }}-sonar
          restore-keys: ${{ runner.os }}-sonar
```
- SonarQube 패키지 캐시
```
      - name: Cache Gradle packages
        uses: actions/cache@v4
        with:
          path: ~/.gradle/caches
          key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle') }}
          restore-keys: ${{ runner.os }}-gradle
```
- Gradle 캐시
```
      - name: Build and analyze
        env:
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
        run: ./gradlew build sonar --info
```
- 빌드 및 Sonar 분석 실행