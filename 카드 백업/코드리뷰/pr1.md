생성한 TImeConfig의 db, 서버가 UTC로 사용하는데 설정을 KST로 했을때의 문제점
DB 저장시 시간이 어긋나버리는 문제가 발생한다.
어플리케이션에서 LocalDateTime.now()로 `2025-09-03 00:00` (KST) 저장했는데
DB는 UTC 기준으로 저장하기때문에 실제 저장값은 `2025-09-02 15:00` (UTC)이 되어버리는문제가 발생한다.
즉 DB의 값과 앱의 값이 달라질 수 있기 때문에 설정에서는 db, 서버 기준을 설정해야한다.

스캐줄러 같은 경우는 zone="Asia/Sounl"이기 때문에 00시에 맞춰서 실행되지만 내부에서 LocalDate.now(clockKST)로 얻은 값과 db에서 UTC 기준으로 처리하는 값이 달라서 어제/ 오늘의 기준이 틀어질 수 있다.


TimeConfig의 시간을 UTC로 두는 이유
서버, DB, 어플리케이션의 시간 환경을 통이랗기 위해서

TimeConfig에서는 UTC로 사용하고, 실 사용할때는 KST로 보여준다.
```
@Configuration  
public class TimeConfig {  
  
    @Bean  
    public Clock clock() {  
       return Clock.systemUTC();  
    }  
  
}
```


```
LocalDate today = LocalDate.now(clock.withZone(ZoneId.of("Asia/Seoul")));
```


pr에 추가 푸시해두었습니다! 서버, DB를 UTC기준으로 관리한다는건 처음알았네요! 한국 외 다른 지역의 사용자가 생겨도 통합해서 관리할 수 있다는게 장점인 거 같습니다. TimeConfig는 UTC 기준으로 동작하도록 수정했고, 한국 시간대가 필요한 경우에는 `Asia/Seoul`로 변환하여 사용하도록 하였습니다. 서버가 UTC 기준이라면 LocalDate, LocalDateTime호출시 미국 시간대로 가져오는 잠재적인 문제가 있을 거 같아 clock을 명시적으로 주입해주었습니다.

`LocalDate today = LocalDate.now(clock.withZone(ZoneId.of("Asia/Seoul")));`

DB 저장 : UTC 기준 사용자 응답 및 노출, useDate : KST 기준 미션 백업 외에 오늘/내일/과거 판단 로직과 날씨 관련 부분도 동일하게 수정하였습니다. 추후 api 연동시 클라이언트에서는 한국 기준으로 Date 보내주시면 될 거 같아요