# 김영한의 실전 자바 - 고급 1편: 멀티스레드와 동시성

> 학습 중 등장하는 개념·클래스·API를 커밋 단위로 기록.

## 학습 기록

---

### `thread.start.test` — 스레드 생성 방식 실습
- `StartTest1Main` — `Thread` 상속으로 카운터 스레드 구현
- `StartTest2Main` — `Runnable` 구현체 + `Thread` 생성자에 이름 지정
- `StartTest3Main` — 익명 클래스로 `Runnable` 구현
- `StartTest4Main` — 두 `Runnable`을 서로 다른 주기(1000ms / 500ms)로 병렬 실행
- Thread 상속 → Runnable 익명 클래스 → 람다 순서로 코드 간결화 흐름 확인
- 주요 API: `Thread(Runnable, name)`, `Thread.sleep(ms)`

---

### `util` — 스레드 로깅 유틸리티
- `MyLogger` — 스레드 이름·시간을 포함한 로그 출력 추상 클래스 (static 메서드로 사용)
- 멀티스레드 환경에서 어느 스레드가 어느 시점에 실행 중인지 한눈에 파악하기 위한 공통 유틸
- 주요 API: `log(Object)`, `Thread.currentThread().getName()`, `DateTimeFormatter`

---

### `thread.start` — 스레드 생성과 실행
- `Thread` — 상속 후 `run()` 오버라이드로 작업 정의
- `start()` vs `run()`: `start()`만 새 스레드 생성, `run()` 직접 호출은 메인 스레드에서 실행
- `Thread.currentThread()`, `Thread.getName()`
- `Runnable` — 스레드 작업을 분리하는 인터페이스. `Thread(Runnable)` 생성자에 전달해 사용
  - `Thread` 상속 vs `Runnable` 구현: `Runnable`이 작업과 스레드를 분리해 유연성 높음 (단일 상속 제약 없음)
- `setDaemon(true)` — 데몬 스레드 설정. 모든 유저 스레드 종료 시 데몬 스레드도 자동 종료
- `Thread.sleep(ms)` — 지정 시간만큼 스레드 일시 정지 (InterruptedException 체크 예외)