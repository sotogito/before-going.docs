| 이름                           | 용도             | 캐시인가?         | 설명                     |
| ---------------------------- | -------------- | ------------- | ---------------------- |
| **Redis**                    | 캐시 (in-memory) | ✅ Yes         | 주로 **임시 저장 / 빠른 응답** 용 |
| **Elasticsearch**            | 검색/분석 전용 DB    | ❌ No (캐시는 아님) | 대용량 텍스트, 로그 검색용        |
| **DB (MySQL, PostgreSQL 등)** | 원본 데이터 저장      | ❌ No          | 핵심 데이터 보존, 트랜잭션 기반     |

```
[클라이언트 요청]
   ↓
[Redis 캐시 있음] → 응답
   ↓ (없으면)
[Elasticsearch (검색 전용)] → 결과 있음 → Redis 저장
   ↓ (없으면)
[DB (원본)] → 조회 → Elasticsearch에 인덱싱 + Redis 저장

```

#### Redis에 검색 결과 캐시 저장
```
// key 예시
"search:강남라멘"

// value (JSON 직렬화 문자열)
[
  {
    "storeId": 101,
    "name": "멘야하나비",
    "location": "서울 강남구 역삼동",
    "tags": ["돈코츠", "미소", "차슈"]
  },
  {
    "storeId": 102,
    "name": "라멘지로",
    "location": "서울 강남구 논현동",
    "tags": ["쇼유", "매운맛"]
  }
]

```

#### Elasticsearch에는 “문서(document)”로 저장
```
// index: ramen_stores
{
  "storeId": 101,
  "name": "멘야하나비",
  "location": "서울 강남구 역삼동",
  "tags": ["돈코츠", "미소", "차슈"],
  "description": "진한 육수의 정통 라멘"
}
```