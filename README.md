# 1. 기능 요구 사항
- 전체 투자상품 조회, 투자하기 나의 투자상품 조회 API를 구현합니다.
    - 요청한 사용자 식별값은 숫자 형태이며 "X-USER-ID"라는 HTTP Header로 전달됩니다.
- 작성하신 어플리케이션이 다수의 인스턴스로 동작하더라도 기능에 문제가 없도록 설계되어야 합니다.
- 각 기능 및 제약사항에 대한 단위테스트를 반드시 작성합니다. 

# 2. 개발 환경
 - Language : JAVA 11
 - FrameWork : Spring Boot 2.5.5 + Spring JPA
 - Database : H2

# 3. API Spec
## 1) 요청

|Header|Description|
|---|---|
|X-USER_ID|사용자 ID|


## 2) 응답
- 정상 응답

|Contents|Description|
|---|---|
|code|응답 코드|
|message|응답 메세지|
|data|응답 내용|

```
{
    "code": "200",
    "message": "정상 처리",
    "data": [
        {
            "productId": 1,
            "title": "개인신용 포트폴리오",
            "totalInvestingAmount": 1000000,
            "currentAmount": 0,
            "investerCnt": 0,
            "investStatus": "모집중",
            "startedAt": "2021-10-01 00:00:00",
            "finishedAt": "2021-11-08 00:00:00"
        }
    ]
}
```

- 정상 응답 : SOLD-OUT일 경우

|Contents|Description|
|---|---|
|message|응답 내용|

```
{
    "message": "SOLD-OUT"
}
```


 - 오류 응답

|Contents|Description|
|---|---|
|err|오류 타입|
|code|오류 코드|
|message|응답 내용|

```
{
    "err": "NOT_PRODUCT",
    "code": 302,
    "message": "존재하지 않는 상품입니다."
}
```

## 3) 오류 유형
- 오류 메세지 정의

|Err|Code|Message|
|---|---|---|
|PARAM_MISMATCH|100|헤더값 또는 파라미터값이 유효하지 않습니다.|
|PARAM_EXCEPTION|101|파라미터명이 유효하지 않습니다.|
|SOLD_OUT|200|SOLD-OUT|
|FINISH_INVEST|300|모집 중인 상품이 없습니다.|
|PRODUCT_NOT_INVEST|301|모집 중인 상품이 아닙니다.|
|NOT_PRODUCT|302|존재하지 않는 상품입니다.|
|OVER_AMOUNT_TOTAL|400|투자 금액이 총 모집금액보다 큽니다.|
|OVER_AMOUNT_POSSIBLE|401|투자 금액이 투자 가능 금액보다 큽니다.|
|NOT_USER|500|투자한 이력이 없습니다.|

- 예외 클래스

|ExceptionClass|Description|
|---|---|
|MethodArgumentTypeMismatchException|헤더 또는 파라미터 값이 유효하지 않을 경우|
|MissingServletRequestParameterException|파라미터명이 유효하지 않을 경우|
|ApiException|오류 유형별로 내부 오류 처리하는 경우|
|SoldoutException|상품이 Sold-out된 경우(별도 처리)|

# 4. 상세 API

## 1) 전체 상품 조회 API

**URL** : /all

**방식** : GET


 - 응답
 
|변수명|타입|설명|
|---|---|---|
|productId|int|상품ID|
|title|String|상품 제목|
|totalInvestingAmount|int|총 모집금액|
|currentAmount|int|현재 모집금액|
|investerCnt|int|투자자 수|
|investStatus|String|투자모집상태(모집중, 모집완료)|
|startedAt|LocalDateTime|투자시작일시|
|finishedAt|LocalDateTime|투자종료일시|

```
{
    "code": "200",
    "message": "정상 처리",
    "data": [
        {
            "productId": 1,
            "title": "개인신용 포트폴리오",
            "totalInvestingAmount": 1000000,
            "currentAmount": 0,
            "investerCnt": 0,
            "investStatus": "모집중",
            "startedAt": "2021-10-01 00:00:00",
            "finishedAt": "2021-11-08 00:00:00"
        },
        {
            "productId": 2,
            "title": "부동산 포트폴리오",
            "totalInvestingAmount": 5000000,
            "currentAmount": 0,
            "investerCnt": 0,
            "investStatus": "모집중",
            "startedAt": "2021-03-02 00:00:00",
            "finishedAt": "2022-03-09 00:00:00"
        }
    ]
}
```

- 기능 및 제약사항

    - 상품 모집기간(투자시작일시 ~ 투자종료일시)) 내의 상품만 응답
    - 개인신용/부동산 상품 모두 상품 모집기간이 아닐 경우 오류 응답
    - 개인신용/부동산 상품 중 한가지만 상품 모집기간 내에 속할 경우 속한 상품만 반환



## 2) 투자하기 API

**URL** : /invest

**방식** : POST


 - 요청
 
|유형|파라미터|타입|설명|
|---|---|---|---|
|Header|X-USER-ID|int|사용자 식별값|
|Body|productId|int|상품ID|
|Body|amount|int|투자금액|

 - 응답(SOLD-OUT 상태일 경우에만)
 
|Contents|Description|
|---|---|
|message|응답 내용|

```
{
    "message": "SOLD-OUT"
}
```

- 기능 및 제약사항

    - 총 투자모집 금액을 넘어서면 sold-out 상태 응답
        - 이미 모집완료(총 모집금액 = 현재 금액)인 상품 투자 시 sold-out 상태 응답
    - 사용자 한 명이 여러 번 투자 가능
    - 투자 금액이 상품의 총 모집금액보다 클 경우 오류 응답
    - 존재하지 않는 상품 투자 시 오류 응답
    - 상품 모집기간에 속하지 않는 상품 투자 시 오류 응답
    - 투자 금액이 투자 가능한 금액(총 모집금액 - 현재 모집금액)보다 클 경우 오류 응답



## 3) 나의 투자내역 API

**URL** : /myinvest

**방식** : GET


 - 요청
 
|유형|파라미터|타입|설명|
|---|---|---|---|
|Header|X-USER-ID|int|사용자 식별값|

 - 응답
 
|변수명|타입|설명|
|---|---|---|
|productId|int|상품ID|
|title|String|상품 제목|
|totalInvestingAmount|int|총 모집금액|
|amount|int|나의 투자금액|
|investat|LocalDateTime|투자일시|

```
{
    "code": "200",
    "message": "정상 처리",
    "data": [
        {
            "productId": 1,
            "title": "개인신용 포트폴리오",
            "totalInvestingAmount": 1000000,
            "amount": 6000,
            "investat": "2021-10-16 01:40:07"
        },
        {
            "productId": 2,
            "title": "부동산 포트폴리오",
            "totalInvestingAmount": 5000000,
            "amount": 10000,
            "investat": "2021-10-16 01:40:15"
        }
    ]
}
```

- 기능 및 제약사항

    - 내가 투자한 상품 반환
    - 투자 이력이 없는 사용자 식별값 요청 시 오류 응답