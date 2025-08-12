### 희철님 코드리뷰


- ~~어노테이션 간격 공백 삭제~~
- ~~공백 삭제~~
- ~~중괄호 줄바꿈~~
---
### entity
- ~~setter -> method~~
- ~~nullable~~
- ~~size및 제약조건~~

---
### dto
1. ~~Notificatoin, Notif 통일~~
- dto setter
- dto Schema
- dto of, from
- dto interface

dto 다 하고나서 시나리오 검증 변경하기

---

2. enum 대소문자 확인

3. 예약어
4. 레퍼지토리 notnull
5. 레퍼지토리 
- 어노테이션 순서
---
#### controller
- 스키마 달기


#### record와 POJO
record를 사용하지 않고 POJO를 사용하는 이유
-> record는 불변셩이 있고 모든 필드를 추가
하지만 새롭게 시나리오를 추가하는 로직과, 수정하는 로직에서 달라지는 것은 id가 있냐 없냐의 차이
id가 들어가지 않는 경우도 있기 땜누에 POJO로 구현
새로운 등록을 할때는 notificationId가 필요가 없거든? 등록과 수정의 양식이 똑같기 때문에 만약 등록할 경우에는 Id가 필요없고, 수정의경우는 피요해서 POJO로 사용했는데
setter를 사용하지 않아서 지우는게 좋을거같네요!

```
public interface OnCreate {}
public interface OnUpdate {}

public record NotificationRequest(
    @Null(groups = OnCreate.class) @NotNull(groups = OnUpdate.class)
    Long notificationId,
    @NotNull Boolean isActive,
    @NotNull NotifType notificationType,
    @NotNull NotifMethodType notificationMethodType,
    @Size(max=7) @UniqueElements List<@NotNull @Min(0) @Max(6) Integer> dayOfWeekOrdinalList
) {}
```

response에서는 값을 꺼낼때, 생각해보니 rest방식이 아니였더라구요

#### enum값 대소문자
클라이언트에게 TIME과 같이 대문자가 아니라 time처럼 소문자로 받도록 하는 게 어떨까요? 대문자를 입력하는 건 번거롭기도 하고 provider는 소문자로 받도록 하고 있어 통일하면 좋을 것 같습니당.
->Enum값이고 상수이기 때문에 당연히 대문자로 받아야한다고 생각했느데 소문자로도 받는것도 일반적이네요! 처음알았어요

그럼 클라이언트에게 

#### 백틱이 들어간 이유
h2환경에서 예약어와 겹쳐서 - sort_order로 변경하기


### Notif
부캠하면서 들었던 조언중 하나는 생각보다 줄인말을 많이 사용한다고 하더라고요. 하지만  공통화가 안되었던거같ㄱ습니다! Notification으로 수정했스빈다. 

# application.yml
spring:
  jackson:
    mapper:
      accept-case-insensitive-enums: true


request record변경

빨리빨리 코드를치는것에 잠시익숙해져있었는데 줄간격 하나하나에 신경쓰시는 모습 보고 감동했습니다! 앞으로 컨벤션에 유의해서 작성하도록하겠습니당

혼자 프론트까지 개발을 하다보니 ~~ 


알림 없이 처음 -> fasle, time
알림 없이 업데이트 -> false, time

알림이 없는 상태에서 또 알림을 보내지 않을 떄

---
- ~~어노테이션 순서 selector findNotiInfoDtoByNotiType~~
- 리스트 대신 s
- @Transactional(readOnly = true) 서비스 전체에?
- 크게 서비스 로직에는 3가지의 종류가 있는데요
1. 조회
2. 트랜젝션
3. 분리된 메서드


---
서비스에 선언을 하는 경우는 해당 서비스 클래스의메서드 전체가 읽기전용일때만 추천하는걸로 알고있어요!

- Notification은 풀네임
-> 처름에 Notif를 사용한 이유는 실제 회사에서 축약표현을 많이 사용하나독 해서 사용했는데요. 그 전에 공통화가 되지 못한 실수가 있었던거 같습니다. 아예 축약을 전체 사용해야한다고 생각하는데, 그 경계가 애매한거같아서 길더라고 Notification을 사용했습니다.

- updateNotification 설명
저희 서비스는 기획을 보면아시겠지만 시나리오를 생성하는 form과 수정하는 form이 같습니다. 때문에 수정시, 만약 사용자가 시나리오의 메모만 변경한다고 하더라도 전체form을 가져와서 업데이트시켜야합니다.
변경사항이 있는 데이터만 최소한으로 변경하고자 업데이트해야하는 목록을 비교하는 로직떄문에 업데이트로직이 유난히 긴거같아요
시나리오는 알림을 하나씩 가지고있습니다. 이는 알림이 비활성화되었을 경우도 마찬가지인데요
TimeNotification, LocaionNotification은 알림이 활성화 되었을때만 갖게되는 알림 조건들입니다.

만약 사용자가 시간 타입 알림을 가지고 월,화,수. 9:30분으로 등록한다면
Notification은 시간타입, 알림메서드, 알림활성화여부를 저장하게되고
시간이기 때문에 TimeNotification데이터가 추가되게됩니다.
여기서 각 타입에 맞는 서비스 분기처리를 해주는 로직이 Selector입니다.
Timenotificaation은 다음과같이 저장됩니다.
(pk. 1)월-9:30
(pk. 2)화-9:30
(pk. 3)수-9:30

만약에 사용자가 업데이트를 아래와같이 했을 경우 처리 과정입니다.
- 월 화 목 금
- 10:00

- 알림 조건만 업데이트(시간) : 월, 화
- 삭제 : 수
- 새롭게 추가 : 목, 금

(pk. 1)월-10:00
(pk. 2)화-10:00
(pk. 4)목-10:00
(pk. 5)금-10:00


+왜 요일에 따라서 row를 생성하냐르르 추가로 설명해보면
처음에는 요일테이블을 따로 두려고했습니다. 알림 시간이 시간을 요일수만큼 변경하지 않나도 되며, 수정하는 부분에서 더 수월하다고 생각했습니다.
근데 문제는 알림을 울리는 로직을 구현하면서 요일 테이블까지 추가하게 되면 성능이 많이 저하될거라고 생각했스빈다.
중복이 되는 데이터도 일주일 최대 7일로 무겁지 않은 양이라고 생각했습니당

그래서 알림을 찾을때
- 요일
- 시간
- 분
- 사요자 푸시 알림 여부
를 판단하게 되는데 요일-시간-분이 만약에 같은 테이블안에 있을 겨웅에는 인덱스 를 설정하여 더 빨리 조회할수있을거라고 생각했스빈당

정리하자만 알림처리를 위해 요일당 row로 저장했습니다.



- TimeNotification 설명
- 알림의 구조는 다음과 같습니다. 저희 로직은 알림을 꼭 사용해야하는 로직인데요


---
대문자로 반환한 이유는 값이 Enum상수이기 때문인데요
클라이언트 -> 백 : 소문자
백 -> 클라이언트 : 대문자
가 일반적인거 같아 일단 클라이언트->백 단방향으로만 소문자로 처리하도록 했습니다!

백->클라이언트에서 상수도 소문자로 처리해야될까요?


