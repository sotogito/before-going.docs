- 스캐줄러 사용을 위해 Application에 @EnableScheduling 어노테이션 추가했습니다

카드 백업, 체크상태 reset, 기간 만료 미션은 데일리로 특정 시간에 행해져야하는 로직이기 때문에 스캐줄러를 사용했습니다.

### runDailyBackupJob 스캐줄러
- 매일 00시 자정에 행해집니다.

1. default BASIC (userDate = null)인 미션을 어제의 날짜로 백업합니다.
2. default BASIC 미션의 체크상태를 false로 리셋합니다.

### runExpiredMissionCleanupJob 스캐줄러
- 매일 01시에 행해집니다.

1. 보관 기간이 만료된 1달뒤 백업 미션을 삭제합니다. - BASIC, TODAY

미션의 종류는 크게 2개입니다 BASIC, TODAY
TODAY는 추가 날짜에만 적용되는 미션이기 때문에 미션을 추가했을때 추가 날짜가 useDate가 되도록 했습니다. 때문에 백업, 리셋 로직에는 해당되지 않습니다.

BASIC의 경우는 default와 백업이션으로 나뉘는데 사용자가 시나리오를 추가하고, 수정했을때의 미션은 default데이터로 오늘 보여줄 데이터입니다. useDate가 null인 상태입니다. 

미션리스트를 조회 할때 다음과 같은 조건으로 조회하면 됩니다.

- 오늘 : useDate가 null 또는 오늘날짜인 미션들
	- useDate=null : default 미션
	- useDate=오늘날짜 : TODAY 미션

- 과거 : useDate가 과거날짜인 미션들
	-  useDate=과거날짜 : 백업된 BASIC 미션, TODAY 미션

- 미래 : useDate가 미래날짜인 미션들
	- userDate=미래날짜 : 미래 날짜로 추가된 TODAY 미션
클라이언트에서는 미래 날짜로 조회할때, TODAY 미션을 띄우는 부분만 동적으로 미션리스트를 띄우면 될 거 같아요.


CRUD 로직에서 시나리오를 가져오는 쿼리에서 백업 미션까지 일괄적으로 받아오는 이슈가 있어 쿼리를 수정했습니다.
또한 시나리오 삭제시 미션 삭제를 jpa 고아객체를 사용하여 삭제하지 않고 쿼리로 삭제하는 것으로 로직을 수정했습니다.

새롭게 알게된 부분인데요 
엔티티에 고아객체를 설정해두면 쿼리가 한번에 delete가 되는 줄 알았는데 미션 하나하나 삭제 쿼리를 날리더라고요.
대략 하루에 { TODAY(20) + BASIC(20) } * 기간 만료 보관일 30일까지 생각하면 대략 1200개로 성능상에는 문제가 없을거 같지만 scnarioId를 FK로 참조하고있는 미션들을 일괄로 삭제하는 쿼리로 삭제하도록 수정했습니다.
```
Hibernate: delete from mission where id=?
Hibernate: delete from mission where id=?
Hibernate: delete from mission where id=?
Hibernate: delete from mission where id=?
Hibernate: delete from mission where id=?
Hibernate: delete from mission where id=?
Hibernate: delete from mission where id=?
Hibernate: delete from mission where id=?
Hibernate: delete from mission where id=?
Hibernate: delete from scenario where id=?
```


