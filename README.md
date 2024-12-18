# 📅 Calendar with Spring JPA

- with : JAVA, Spring boot, JPA, PostgreSQL
- 구현된 기능 : 회원가입, 로그인, 로그아웃, 캘린더 crud, paging / 회원 crud / 댓글 crud, paging

---

## ⭐ ERD
![img.png](img.png)

---

## ⭐ API 명세서

### User 관련 API

| **기능**       | **Method** | **URL**         | **Request**                               | **Response**                                    | **상태 코드** |
|----------------|------------|-----------------|------------------------------------------|-----------------------------------------------|---------------|
| 사용자 조회     | GET        | /users          | -                                        | <details><summary>UserResponseDto</summary>```json { "id": 1, "name": "홍길동", "email": "hong@example.com", "createdAt": "2024-01-01" }```</details> | 200           |
| 사용자 수정     | PATCH      | /users          | <details><summary>UpdateRequestDto</summary>```json { "name": "홍길동", "newPassword": "NewPass123!", "currentPassword": "OldPass123!" }```</details> | <details><summary>UserResponseDto</summary>```json { "id": 1, "name": "홍길동", "email": "hong@example.com", "createdAt": "2024-01-01" }```</details> | 200           |
| 사용자 삭제     | POST       | /users/delete   | <details><summary>CurrentPasswordRequestDto</summary>```json { "currentPassword": "OldPass123!" }```</details> | <details><summary>성공 메시지</summary>```json { "message": "사용자가 성공적으로 삭제되었습니다." }```</details> | 200           |

---

### Comment 관련 API

| **기능**       | **Method** | **URL**                 | **Request**                           | **Response**                                   | **상태 코드** |
|----------------|------------|-------------------------|--------------------------------------|-----------------------------------------------|---------------|
| 댓글 생성       | POST       | /comments/{calendarId}  | <details><summary>CommentRequestDto</summary>```json { "content": "이것은 댓글 내용입니다." }```</details> | <details><summary>CommentResponseDto</summary>```json { "id": 1, "content": "이것은 댓글 내용입니다.", "name": "홍길동", "created": "2024-01-01 PM 01:00", "updated": "2024-01-01 PM 01:00" }```</details> | 200           |
| 댓글 조회       | GET        | /comments/{calendarId}  | <details><summary>Query Param</summary>```json { "page": 1, "size": 10 }```</details> | <details><summary>Page<CommentResponseDto></summary>```json { "content": [ { "id": 1, "content": "댓글 내용1", "name": "홍길동", "created": "2024-01-01 PM 01:00", "updated": "2024-01-01 PM 01:00" } ], "pageable": { "pageNumber": 1, "pageSize": 10 } }```</details> | 200           |
| 댓글 수정       | PATCH      | /comments/{commentId}   | <details><summary>CommentRequestDto</summary>```json { "content": "수정된 댓글 내용입니다." }```</details> | <details><summary>CommentResponseDto</summary>```json { "id": 1, "content": "수정된 댓글 내용입니다.", "name": "홍길동", "created": "2024-01-01 PM 01:00", "updated": "2024-01-01 PM 02:00" }```</details> | 200           |
| 댓글 삭제       | DELETE     | /comments/{commentId}   | -                                    | <details><summary>성공 메시지</summary>```json { "message": "댓글이 성공적으로 삭제되었습니다." }```</details> | 200           |

---

### Calendar 관련 API

| **기능**       | **Method** | **URL**                 | **Request**                           | **Response**                                   | **상태 코드** |
|----------------|------------|-------------------------|--------------------------------------|-----------------------------------------------|---------------|
| 캘린더 생성     | POST       | /calendars              | <details><summary>CalendarRequestDto</summary>```json { "title": "회의 일정", "content": "회의 내용입니다." }```</details> | <details><summary>CalendarResponseDto</summary>```json { "id": 1, "title": "회의 일정", "content": "회의 내용입니다.", "name": "홍길동", "commentNum": 0, "created": "2024-01-01 PM 01:00", "updated": "2024-01-01 PM 01:00" }```</details> | 200           |
| 캘린더 조회     | GET        | /calendars/{calendarId} | -                                    | <details><summary>CalendarResponseDto</summary>```json { "id": 1, "title": "회의 일정", "content": "회의 내용입니다.", "name": "홍길동", "commentNum": 0, "created": "2024-01-01 PM 01:00", "updated": "2024-01-01 PM 01:00" }```</details> | 200           |
| 캘린더 수정     | PATCH      | /calendars/{calendarId} | <details><summary>CalendarRequestDto</summary>```json { "title": "수정된 일정", "content": "수정된 내용입니다." }```</details> | <details><summary>CalendarResponseDto</summary>```json { "id": 1, "title": "수정된 일정", "content": "수정된 내용입니다.", "name": "홍길동", "commentNum": 0, "created": "2024-01-01 PM 01:00", "updated": "2024-01-01 PM 02:00" }```</details> | 200           |
| 캘린더 삭제     | DELETE     | /calendars/{calendarId} | -                                    | <details><summary>성공 메시지</summary>```json { "message": "캘린더가 성공적으로 삭제되었습니다." }```</details> | 200           |
| 캘린더 목록 조회 | GET        | /calendars              | <details><summary>Query Param</summary>```json { "page": 1, "size": 10 }```</details> | <details><summary>Page<CalendarResponseDto></summary>```json { "content": [ { "id": 1, "title": "회의 일정", "content": "회의 내용입니다.", "name": "홍길동", "commentNum": 0, "created": "2024-01-01 PM 01:00", "updated": "2024-01-01 PM 01:00" } ], "pageable": { "pageNumber": 1, "pageSize": 10 } }```</details> | 200           |

---

### Auth 관련 API

| **기능**       | **Method** | **URL**          | **Request**                           | **Response**                  | **상태 코드** |
|----------------|------------|------------------|--------------------------------------|--------------------------------|---------------|
| 로그인          | POST       | /auth/login      | <details><summary>LoginRequestDto</summary>```json { "email": "hong@example.com", "password": "SecurePass123!" }```</details> | <details><summary>성공 메시지</summary>```json { "message": "로그인에 성공했습니다." }```</details> | 200           |
| 로그아웃        | POST       | /auth/logout     | -                                    | <details><summary>성공 메시지</summary>```json { "message": "로그아웃에 성공했습니다." }```</details> | 200           |
| 회원가입        | POST       | /auth/signup     | <details><summary>SignupRequestDto</summary>```json { "name": "홍길동", "email": "hong@example.com", "password": "SecurePass123!" }```</details> | <details><summary>성공 메시지</summary>```json { "message": "회원가입에 성공했습니다." }```</details> | 200           |


---

## 🌼 유지 보수가 편리한 스케줄러를 만들었습니다!
1. 3-Layered Architecture 를 따르도록 리팩토링 했습니다.

2. SOLID 원칙을 최대한 생각하며, 새 코드를 추가할 때 불편함이 없도록 구현했습니다.

3. 클래스, 메서드 네이밍을 확실히 했습니다.

4. 예외 클래스에 추상화를 적용하고, 에러코드를 enum 으로 관리하며 확장성과 사용성을 높였습니다.

---


## 🎓 최대한 많이 배우려고 노력했습니다!
1. jpql, persistence context, filter/session 등 낯선 기능들을 공부하며 써보려 노력했습니다.

---

## 🛠️ 앞으로 개선하고 싶은 점도 많습니다.

1. repository 계층을 좀 더 간결하게 리팩토링 할 수 있지 않았나 생각이 듭니다.

2. spring security 와 filterConfig 를 합치려다 대형사고가 났는데, 다음 프로젝트에선 다시 도전해보고싶습니다.

---

## ⌨️ 내가 고민했던 것들

- [
  트러블슈팅 : 필수과제](https://roqkfchqh.tistory.com/104)
- [
  java persistence api 도전과제 도전기](https://roqkfchqh.tistory.com/106)

****