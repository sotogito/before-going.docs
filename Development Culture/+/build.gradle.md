#### 플러그인 설정
```
plugins {
	id 'java'                                     // 표준 Java 플러그인
	id 'org.springframework.boot' version '3.4.5' // Spring Boot 애플리케이션 빌드·실행
	id 'io.spring.dependency-management' version '1.1.7' // Spring Boot 의존성 관리
	id 'jacoco'                                   // JaCoCo 테스트 커버리지 플러그인
	id 'org.sonarqube' version '6.1.0.5360'       // SonarQube(Cloud) 정적 분석 연동
	id 'org.ec4j.editorconfig' version '0.1.0'    // EditorConfig 규칙 검사
	id 'checkstyle'                               // Checkstyle 코드 스타일 검사
}

```

#### 프로젝트 기본 정보
```
group = 'com.und'          // Maven 좌표상의 그룹ID
version = '0.0.1-SNAPSHOT' // 현재 버전
```

#### Java 툴체인 설정
```
java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17) 
	}
}
```
- Java 17을 사용하도록 강제

#### JoCoCo 플러그인 기본 설정
```
jacoco {
	toolVersion = '0.8.13'
}
```
- 테스트 커버리지 측정을 위함

#### EditorConfig 플러그인 설정
```
editorconfig {
	excludes = ['build']  // build 폴더 내부 파일은 검사 대상에서 제외
}
```
- .editorconfig 규칙은 정용하되, build/ 폴더는 무시

#### 컴파일 인코딩 지정
```
compileJava.options.encoding = 'UTF-8'
compileTestJava.options.encoding = 'UTF-8'
```
- 소스(.java) 및 테스트(.java) 파일 UTF-8로 지정

#### Checkstyle 플러그인 상세 설정
```
checkstyle {
	maxWarnings = 0  // 경고 한 건도 허용하지 않음
	configFile = file("checkstyle/naver-checkstyle-rules.xml") //룰셋
	configProperties = ["suppressionFile": "checkstyle/naver-checkstyle-suppressions.xml"] //예외파일
	toolVersion = "10.23.1" //checkstyle 사용 버전
}
```

#### 컴파일 의존성 범위 확장
```
configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
}
```
- annotationProcessor용 의존성을 compileOnly에도 포함시켜서 Lombok 등 어노테이션 프로세싱 시 클래스패스에 함께 올라가게 함

#### 저장소 설정
```
repositories {
	mavenCentral()
}
```
- 중앙 Maven 레퍼지토리에서 의존성 다운로드

#### 의존성 선언
```
dependencies {
	// 핵심
	implementation 'org.springframework.boot:spring-boot-starter-actuator'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-starter-validation'
	implementation 'com.google.code.gson:gson:2.8.9'
	implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0'

	// 컴파일 시에만
	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'

	// 개발 중 단독 실행용
	developmentOnly 'org.springframework.boot:spring-boot-devtools'

	// 런타임 DB
	runtimeOnly 'com.h2database:h2'

	// 테스트
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}
```

#### check 태스크 의존성 추가
```
tasks.named('check') {
	dependsOn tasks.named('editorconfigCheck')
}
```
- ./gradlew check 실행시 EditorConfig 검사도 함께 수행

#### test 태스트 설정
```
tasks.named('test') {
	useJUnitPlatform()          // JUnit 5 플랫폼 사용
	finalizedBy 'jacocoTestReport' // 테스트 후 JaCoCo 리포트 생성
}
```

#### JaCoCo 리포트 태스크
```
jacocoTestReport {
    reports {
        html.required = true
        xml.required = true
    }
    finalizedBy 'jacocoTestCoverageVerification' // 보고서 생성 후 커버리지 검증
}
```
- HTML, XML 리포트 생성
- 생성 완료 후 커버리지 검증 단계로 연결

#### JaCoCo 커버리지 검증
```
jacocoTestCoverageVerification {
	violationRules {
		rule {
			enabled = true
			element = 'CLASS'
			// 라인 커버리지 ≥ 80%
			limit {
				counter = 'LINE'
				value = 'COVEREDRATIO'
				minimum = 0.80
			}
			// 파일당 최대 500라인
			limit {
				counter = 'LINE'
				value = 'TOTALCOUNT'
				maximum = 500
			}
			// 분기 커버리지 ≥ 70%
			limit {
				counter = 'BRANCH'
				value = 'COVEREDRATIO'
				minimum = 0.70
			}
		}
	}
	afterEvaluate {
		// *Application* 클래스는 검증 대상에서 제외
		classDirectories.setFrom(files(classDirectories.files.collect {
			fileTree(dir: it, excludes: ['**/*Application*'])
		}))
	}
}
```

#### SonarQubr/Cloud 설정
```
sonar {
	properties {
		property 'sonar.projectKey', 'team-UND_before-going-server'
		property 'sonar.organization', 'team-und'
		property 'sonar.host.url', 'https://sonarcloud.io'
		property 'sonar.coverage.jacoco.xmlReportPaths', 'build/reports/jacoco/test/jacocoTestReport.xml'
		property 'sonar.coverage.exclusions', '**/*Application*'
		property 'sonar.java.checkstyle.reportPaths', 'build/reports/checkstyle/main.xml'
	}
}
```
