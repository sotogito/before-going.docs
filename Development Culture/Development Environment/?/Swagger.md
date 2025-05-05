### Swagge란?
API를 편리하게 문서화해주는 오픈소스로 호출과 테스트, 공유를 용이하게 해준다. 
- 코드 변경시 자동 업데이트
- FE, BE 모두 같은 스펙 기반 작업
- 스프링부트에서는 간단하게 의존성만 주입하여 사용 가능

@GetMapping, @PostMapping같은 어노테이션으로 엔드포인트를 선언하면, 자동으로 swagger ui에서 확인 가능

JVM(자바 프로그램을 실행하기 위한 가상의 환경)이 실행시 .class파일이나 외부 라이브러리(JAR)를 찾는 경로가(클래스패스)springdoc-openapi-starter-webmvc-ui 에 있다면 기동 시점에서 스캔을 통해 모든 컨트롤러 매핑 정보를 모아서 /v3/api-docs (기본) 또는 커스터마이즈한 경로로 JSON 스펙을 생성함

 http://localhost:8080/swagger-ui/index.html 에 접속하면  /v3/api-docs에서 읽어온 스펙을 기반으로 화면이 자동 렌더링됨