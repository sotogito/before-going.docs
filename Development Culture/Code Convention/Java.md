[캠퍼스 핵데이 Java 코딩 컨벤션](https://naver.github.io/hackday-conventions-java/#indentation-tab)

- 인터페이스에 명사/명사절 or 형용사/형용사절 사용 - RowMapper, AutoClosable
- 메서드 이름은 동사 전치사로 시작, 다른 타입으로 전환하는 메서드는 전치사를 앞에 쓸 수 있다. -> toString() - 호출할 때 메시지가 될 수 있게
- 클래스/메서드/멤버 변수의 제한자는 아래 순서로 작성한다.
	1. public
	2. protected
	3. private
	4. abstract
	5. static
	6. final
	7. transient
	8. volatitle
	9. synchronized
	10. native
	11. stricfp
- 배열 선언시 대괄호는 타입 뒤에 선언 Strring name[];
- long 타입은 값 마지막에 L 붙이기
- 닫는 줄괄호와 같은 줄에 else, catch, finally, while 선언
- 빈 블록을 선언할때는 같은 줄에 중괄호를 닫는 것을 허용
	`public void close() {}`
- 조건/반복문에 짧더라도 중괄호 필수 사용
- 최대 줄 너비는 120으로 하며 줄을 바꾸는 위치는 다음 중 하나로 한다.
	- `extends` 선언 후
	- `implements` 선언 후
	- `throws` 선언 후
	- 여는 소괄호 선언 후
	- 콤마 후
	- 온점 전
	- 연산자 전
	    - `+`, `-`, `*`, `/`, `%`
	    - `==`, `!=`, `>=`, `>`,`⇐`, `<`, `&&`, `||`
	    - `&`, `|`, `^`, `>>>`, `>>`, `<<`, `?`
	    - `instanceof`