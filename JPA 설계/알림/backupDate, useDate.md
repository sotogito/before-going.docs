default 카드 : 이 카드는 backupDate = null인 상태의 카드로 사용자에게 오늘 날짜로 보여지는 기본 카드입니다.

- 자정 00시가 되면
1. tbl_card에서 default 카드를 가져와서 복사(=딥클론)
2. backupDate = 어제 날짜로 설정
3. tbl_card에 저장

예를들어서 6월 20일 00시라면 backupDate = 6월 19일인 백업카드가 생성되는 겁니다.

- backupDate = null -> 오늘 보여줄 카드
- backupDate = 과거 날짜 -> 과거로 백업된 카드
- backupDate = 미래 날짜 -> 사용자가 미래 카드에 체크리스트를 추가했을 때 생성되는 카드
- 오늘 띄울 카드(default카드)와 백업 카드, 미래 카드는 똑같이 tbl_card에 저장됩니다.




1️⃣ backupDate 방식
- 오늘 보여줄 카드(default 카드)
	- backupDate = null
- 과거 카드
	- backupDate = 과거 날짜
	- 매일 자정마다 `backupDate = 어제날짜`로 복사한 카드가 생김
- 미래 카드
	- backupDate = 미래 날짜
	- 사용자가 미래에 체크리스트 추가시 `backupDate = 추가한날짜` 로 저장됨
	- 추후 추가한 날짜가 되면 default 카드에 병합되고 삭제됨

2️⃣ useDate 방식
- 오늘 보여줄 카드
	- useDate = 오늘 날짜
	- 00시에 useDate가 = 어제날짜 인 카드 딥클론
- 과거 카드
	- useDate = 과거 날짜
- 미래 카드
	- useDate = 미래 날짜
	- 사용자가 미래 체크리스트 추가시 backupDate = 추가한날짜로 저장
	- 추후 추가한 날짜가 되면 오늘 카드에 병합되고 삭제