# 📅 Calendar with Spring JPA

- with : JAVA, Spring boot, JPA, PostgreSQL
- 구현된 기능 : 회원가입, 로그인, 로그아웃, 캘린더 crud, paging / 회원 crud / 댓글 crud, paging

---

## ⭐ ERD
![img.png](img.png)

---

## ⭐ API 명세서

### **User 관련 API**
| **기능**       | **Method** | **URL**          | **Request**                         | **Response**          | **상태 코드** |
|----------------|------------|------------------|--------------------------------------|-----------------------|---------------|
| 사용자 조회     | `GET`      | `/users`         | -                                    | 사용자 정보(`UserResponseDto`) | `200`         |
| 사용자 수정     | `PATCH`    | `/users`         | 수정 정보(`UpdateRequestDto`)        | 수정된 사용자 정보(`UserResponseDto`) | `200`         |
| 사용자 삭제     | `POST`     | `/users/delete`  | 현재 비밀번호(`CurrentPasswordRequestDto`) | 삭제 성공 메시지         | `200`         |

### **Comment 관련 API**
| **기능**       | **Method** | **URL**                  | **Request**                     | **Response**                   | **상태 코드** |
|----------------|------------|--------------------------|----------------------------------|--------------------------------|---------------|
| 댓글 생성       | `POST`     | `/comments/{calendarId}` | 댓글 내용(`CommentRequestDto`)  | 생성된 댓글 정보(`CommentResponseDto`) | `200`         |
| 댓글 조회       | `GET`      | `/comments/{calendarId}` | 페이지 번호, 크기(Query Param)   | 댓글 목록(`Page<CommentResponseDto>`) | `200`         |
| 댓글 수정       | `PATCH`    | `/comments/{commentId}`  | 수정 내용(`CommentRequestDto`)  | 수정된 댓글 정보(`CommentResponseDto`) | `200`         |
| 댓글 삭제       | `DELETE`   | `/comments/{commentId}`  | -                                | 삭제 성공 메시지               | `200`         |

### **Calendar 관련 API**
| **기능**       | **Method** | **URL**               | **Request**                      | **Response**                       | **상태 코드** |
|----------------|------------|-----------------------|-----------------------------------|------------------------------------|---------------|
| 캘린더 생성     | `POST`     | `/calendars`          | 캘린더 내용(`CalendarRequestDto`) | 생성된 캘린더 정보(`CalendarResponseDto`) | `200`         |
| 캘린더 조회     | `GET`      | `/calendars/{calendarId}` | -                                 | 캘린더 정보(`CalendarResponseDto`)        | `200`         |
| 캘린더 수정     | `PATCH`    | `/calendars/{calendarId}` | 수정 내용(`CalendarRequestDto`)  | 수정된 캘린더 정보(`CalendarResponseDto`) | `200`         |
| 캘린더 삭제     | `DELETE`   | `/calendars/{calendarId}` | -                                 | 삭제 성공 메시지                   | `200`         |
| 캘린더 목록 조회 | `GET`      | `/calendars`          | 페이지 번호, 크기(Query Param)    | 캘린더 목록(`Page<CalendarResponseDto>`) | `200`         |

### **Auth 관련 API**
| **기능**       | **Method** | **URL**          | **Request**                | **Response**   | **상태 코드** |
|----------------|------------|------------------|----------------------------|----------------|---------------|
| 로그인          | `POST`     | `/auth/login`    | 로그인 정보(`LoginRequestDto`) | 로그인 성공 메시지 | `200`         |
| 로그아웃        | `POST`     | `/auth/logout`   | -                          | 로그아웃 성공 메시지 | `200`         |
| 회원가입        | `POST`     | `/auth/signup`   | 회원가입 정보(`SignupRequestDto`) | 회원가입 성공 메시지 | `200`         |

---

## 🌼 유지 보수가 편리한 스케줄러를 만들었습니다!
1. Layered Architecture 를 따르도록 리팩토링 했습니다.

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