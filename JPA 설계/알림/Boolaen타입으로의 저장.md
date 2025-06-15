카드의 체크리스트의 체크표시를 구분하기 위해서 크게는 활설/비활성이 있다.
타입을 boolean으로 할 경우
```java
@Column(name = "is_active", nullable = false)  
private boolean isActive;
```

`0 => false`
`1 => true`
다음과 같이 저장된다.

데이터로 파이트하고 간단하여 좋다고 생각하지만 어차피 0이 화성인지 1이 활성인지 (다 알테지만) 상수로 정의해줘야한다고 생각했을 때 그냥 Enum.String으로 넣어도 될 거 같다.