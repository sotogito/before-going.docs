
### 주요 변경 사항

**오늘 미션 체크/리셋**
- 기존: 오늘 BASIC 체크상태 일괄 false
- 변경: 미래 체크 상태까지 반영하여 reset

**미래 미션 체크**
- `missionId + date`를 기반으로 처리
- date가 미래일 경우 → `parentMissionId = missionId`, `useDate = 미래 날짜`,`isChecked = true`로 새로운 row 생성

**미래 미션 리스트 출력**
- 기본(default) 미션 리스트를 기반으로
- 미래 미션 row의 체크 상태를 반영해 DTO 반환

**삭제/업데이트 시 처리**
- 시나리오 삭제 시: 해당 시나리오의 모든 미래 체크 상태 row 일괄 삭제
- 시나리오 업데이트 시: 삭제된 mission의 `id`를 `parentMissionId`로 가진 데이터 삭제

