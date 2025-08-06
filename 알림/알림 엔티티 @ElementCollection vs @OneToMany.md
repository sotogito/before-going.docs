1. 어떤 사용자의 어떤 시나리오인지가 필요하다.

그럼 내가 원래 설계했던 방식이라면 또 어떤 시나리오인지를 알아오는데에 문제가 생긴다.
너무 꼬여버리는 느낌이다.

그럼 차라리 깔끔하게 Scenario에서 조회하는것도 나쁘지 않을거같다.


- Member -> 푸시알림 동의자인지 확인
- Notification -> 알림 활성화중인지 확인
- dayOfWeek -> 요일 확인
- hour/minute -> 시간 확인

1. 요일에 따라서 중복 row 생성을 허락해야하나?

조회는 해당 울릴 Notification_id를 가지고 있는 Scenario를 찾아야한다.

```
SELECT s.*
FROM scenario s
JOIN notification n ON s.notification_id = n.id
JOIN time_notif tn ON tn.notification_id = n.id
WHERE tn.day_of_week = 'MONDAY'
  AND tn.hour = 9
  AND tn.minute = 30
  AND n.is_active = true;

```

```
SELECT s.*
FROM scenario s
JOIN notification n ON s.notification_id = n.id
JOIN notification_days nd ON nd.notification_id = n.id
JOIN time_notif tn ON tn.notification_id = n.id
WHERE nd.day_of_week = 'MONDAY'
  AND tn.hour = 9
  AND tn.minute = 30
  AND n.is_active = true;

```


보통은 일대다를 선호하는것을 너무너무 잘 알지만
요일만 요소가 다를뿐 다른 요소들을 똑같이 가져가야한다는 점이 마음에 걸린다.

사용자가 시간만 수정을 핫 업쇼잖아?
어쩃ㅅ든 시간을 수정하면 다른것들 다 더디체크를 해야하는데

그럼 요일만 수정했을떄
- 요일테이블 분리 : 요일 리스트 더티체크, 시간 변경 체크
- 통일테이블 : notification_id기준으로 다 조회를 해온다음에 for문 돌려서 

@ElementCollection은 사용하지 않기로 했다.

요일별 row가 생성되도록 하였다.
일단은 알림을 울릴때 성능이 가장 중요하다