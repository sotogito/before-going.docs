
- 스케줄러 사용을 위해 Application 클래스에 @EnableScheduling 어노테이션을 추가했습니다.
- 미션 백업, 체크 상태 초기화, 기간 만료 미션 삭제는 특정 시각에 주기적으로 실행되어야 하는 로직이므로 스케줄러로 구현했습니다.

---
### runDailyBackupJob 스케줄러
- 매일 00시 자정에 실행됩니다.
1. useDate가 null인 BASIC 미션을 어제 날짜로 백업합니다.
2. useDate가 null인 BASIC 미션의 체크 상태를 false로 초기화합니다

### runExpiredMissionCleanupJob 스케줄러
- 매일 01시에 실행됩니다.
1. 보관 기간이 만료된 1개월 전 미션(BASIC, TODAY)을 삭제합니다.

---

- 미션은 BASIC과 TODAY 두 종류가 있습니다.
- TODAY는 특정 날짜에만 적용되는 미션이므로 useDate가 해당 날짜로 저장되며, 백업이나 리셋 대상에는 포함되지 않습니다.
- BASIC은 default 미션과 백업 미션으로 나뉩니다.
    - default BASIC: 시나리오를 추가하거나 수정할 때 생성되며 오늘 보여줄 데이터로 useDate는 null입니다.
    - 백업 BASIC: 스케줄러에 의해 과거 날짜로 복제된 데이터입니다.


클라이언트는 조회 일자에 따라 다음 조건으로 미션을 조회하면 됩니다.

- 오늘: useDate가 null 또는 오늘 날짜인 미션
    - null → default BASIC
    - 오늘 날짜 → TODAY
        
- 미래: useDate가 null 또는 미래 날짜인 미션
    - null → default BASIC
    - 미래 날짜 → TODAY
        
- 과거: useDate가 조회 날짜인 미션
    - 해당 날짜의 BASIC 백업 미션, TODAY 미션

---
- 스케줄러는 대량 데이터를 다뤄야 하므로 JPA 대신 네이티브 쿼리로 직접 처리했습니다.
- 백업 및 만료 삭제 쿼리는 MySQL 네이티브 쿼리 기반으로 작성했습니다.
- 로컬 환경은 H2라서 처음에는 동작하지 않을 거라 예상했는데 호환이 되는 거 같습니다
- 로컬 RDB를 로컬 MySQL로 바꿔 테스트해보았는데 정상적으로 동작하는 것 확인했습니다.

---
### 추가 변경 사항

- CRUD 로직에서 시나리오를 가져오는 쿼리에서 백업 미션까지 일괄적으로 받아오는 이슈가 있어 쿼리를 수정했습니다.

- 미래를 조회할때 TODAY 미션을 띄우는 부분만 동적으로 미션리스트를 띄우면 될 거 라고 생각했는데, 과거에서 바로 미래로 조회할 경우 BASIC 미션이 과거 기준으로 고정되어 불일치가 일어날거 같아 조회 쿼리만 수정했습니다.
	- **오늘 → 미래** 이동할 때: BASIC은 그대로 두고, TODAY만 바꿔주면 됨.
	- **과거 → 미래** 이동할 때: BASIC이 과거 기준 데이터였으니, 미래 기준에서는 BASIC도 달라져야 함. = 불일치
	결론적으로 오늘/미래 조회쿼리가 동일하고 과거만 다르게 처리합니다.

- 시나리오 삭제 시 미션 삭제를 JPA 고아 객체 옵션으로 처리하지 않고, 쿼리로 일괄 삭제하도록 변경했습니다.
기존에는 고아 객체 옵션을 사용하면 시나리오 삭제 시 연관된 미션이 한 번에 삭제될 것으로 생각했는데, 실제로는 미션마다 개별 delete 쿼리가 날려지더라고요.
```
Hibernate: delete from mission where id=? 
Hibernate: delete from mission where id=? 
Hibernate: delete from mission where id=? 
Hibernate: delete from mission where id=? 
Hibernate: delete from mission where id=?
```
성능에 큰 문제가 없을 거 같지만 scenarioId를 FK로 참조하는 미션들을 일괄 삭제 쿼리로 처리하도록 수정했습니다.