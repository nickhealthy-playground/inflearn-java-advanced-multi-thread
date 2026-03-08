# 김영한의 실전 자바 - 고급 1편: 멀티스레드와 동시성

> 학습 중 등장하는 개념·클래스·API를 커밋 단위로 기록.

## 학습 기록

---

### `thread.sync.lock` — LockSupport로 스레드 WAITING/TIMED_WAITING 제어
- `LockSupportMainV2` — `LockSupport.parkNanos()`로 나노초 단위 타임아웃 TIMED_WAITING — 지정 시간 경과 후 자동 복귀
- `LockSupportMainV1` — `LockSupport.park()`로 스레드를 WAITING 상태로 전환, `unpark()` 또는 `interrupt()`로 깨우는 두 가지 방식 비교
- `park()` 후 `unpark()`로 깨우면 인터럽트 상태 false 유지, `interrupt()`로 깨우면 true 유지
- `park()`(무한 대기) vs `parkNanos()`(타임아웃) — `synchronized`의 BLOCKED와 달리 명시적 해제 가능
- `ReentrantLock` 구현의 기반 저수준 API
- 주요 API: `LockSupport.park()`, `LockSupport.parkNanos(long)`, `LockSupport.unpark(Thread)`

---

### `thread.sync.test` — synchronized 연습 문제
- `SyncTest2Main` — 로컬 변수(`localValue`)만 사용하는 카운터 — 스택에만 존재해 스레드 간 공유 없음 → `synchronized` 불필요, 동기화 대상 판단 기준 학습
- `SyncTest1BadMain` — 두 스레드가 각 10,000회 `increment()`를 호출하는 카운터 예제 — `increment()`는 `synchronized`로 보호되나 `getCount()`는 미동기화로 가시성 문제 잠재
- 임계 영역 보호 범위, 가시성 보장, 공유 자원 여부 판단을 실습으로 확인
- 주요 API: `synchronized` (메서드 레벨), `Thread.join()`

---

### `thread.sync` — ReentrantLock.tryLock()으로 비차단 락 시도
- `BankAccountV5` — `lock.tryLock()` — 락 획득 실패 시 대기 없이 즉시 `false` 반환, 이미 처리 중인 작업이 있으면 거래 거부
- `lock.lock()`(블로킹) vs `lock.tryLock()`(논블로킹): tryLock은 경합 시 대기하지 않아 응답성 우선 시나리오에 적합
- 주요 API: `ReentrantLock.tryLock()`

---

### `thread.sync` — ReentrantLock으로 synchronized 대체
- `BankAccountV4` — `ReentrantLock`을 이용한 명시적 락 — `lock()` / `unlock()`을 직접 호출, `try-finally`로 반드시 unlock 보장
- `synchronized`(암시적 락)와 동일한 상호 배제·가시성 보장, 단 공정성(fair) 모드·tryLock·타임아웃 등 고급 기능 제공
- `getBalance()`도 락으로 보호하여 읽기 시 가시성 보장
- 주요 API: `ReentrantLock.lock()`, `ReentrantLock.unlock()`, `Lock` 인터페이스

---

### `thread.sync` — synchronized 블록으로 임계 영역 범위 최소화
- `BankAccountV3` — `synchronized(this)` 블록으로 검증~출금 구간만 동기화 — 로그 출력 등 비임계 로직은 락 밖에 위치시켜 락 점유 시간 단축
- 메서드 레벨 `synchronized` vs 블록 레벨 `synchronized(this)`: 동작은 동일하나 블록이 더 세밀한 임계 영역 제어 가능
- 주요 API: `synchronized(this) {}`, `synchronized` (메서드 vs 블록)

---

### `thread.sync` — synchronized를 이용한 임계 영역 보호
- `BankAccountV2` — `synchronized` 메서드로 출금·잔액 조회 동기화 — 모니터 락을 획득한 스레드만 임계 영역 진입, 나머지는 BLOCKED 상태로 대기
- 모든 객체(인스턴스)는 내부에 자신만의 모니터 락(monitor lock)을 보유
- `synchronized`는 가시성(volatile) + 원자성을 동시에 보장
- 주요 API: `synchronized` (메서드 레벨)

---

### `thread.sync` — 동기화 없는 동시 출금과 경쟁 조건
- `BankAccount` — 출금·잔액 조회 계약을 정의한 인터페이스
- `BankAccountV1` — `synchronized` 없는 구현체 — 검증과 출금 사이에 컨텍스트 스위치 발생 시 두 스레드가 동시에 출금 성공 (음수 잔액 발생)
- `WithdrawTask` — `BankAccount`를 주입받아 `withdraw()`를 실행하는 `Runnable` — 두 스레드가 동일 계좌 객체를 공유
- `BankMain` — 잔액 1000에서 두 스레드가 각 800 출금 시도 → 경쟁 조건 재현
- 임계 영역(critical section) 개념, 원자성(atomicity) 부재로 인한 Race Condition 학습
- 주요 API: `Runnable`, `Thread`, `Thread.join()`, `Thread.sleep()`

---

### `thread.control.volatile1` — volatile 키워드와 메모리 가시성
- `VolatileFlagMain` — `boolean` vs `volatile boolean` 플래그로 스레드 중단 — 비volatile 시 CPU 캐시로 인해 변경 감지 불가
- `VolatileCountMain` — `volatile flag` + `volatile count`로 카운터 가시성 확인 — volatile 없으면 메인 스레드의 쓰기가 작업 스레드에 전달 안 됨
- `volatile`은 읽기/쓰기 원자성 보장, 단 복합 연산(count++)엔 적용 안 됨
- 주요 API: `volatile` 키워드

---

### `thread.control.yield` — Thread.yield() 학습
- `YieldMain` — 1000개 스레드가 각 10회 출력하며 `Thread.yield()`로 CPU 양보 동작 확인
- `yield()`는 현재 스레드가 다른 스레드에 실행 기회를 넘기지만 보장은 없음 (힌트 수준)
- `sleep(0)` 대비 OS 스케줄러 의존도 높고 플랫폼마다 동작 상이
- 주요 API: `Thread.yield()`

---

### `thread.control.printer` — 인터럽트 적용 프린터 실습
- `MyPrinterV1` — `volatile boolean work` 플래그로 중단 — `sleep()` 중 플래그 확인 불가 (지연 중단 문제)
- `MyPrinterV2` — `work = false` + `printThread.interrupt()` 조합 — `sleep()` 중 `InterruptedException` 즉시 발생
- `MyPrinterV3` — `volatile` 플래그 제거, `Thread.interrupted()` + `interrupt()` 단독 사용 — 인터럽트로 모든 중단 제어 (권장 패턴)
- `MyPrinterV4` — V3에 `Thread.yield()` 추가 — 빈 큐 대기 시 busy-wait 대신 yield로 CPU 양보
- 주요 API: `ConcurrentLinkedQueue`, `volatile`, `thread.interrupt()`, `Thread.interrupted()`, `Thread.yield()`

---

### `thread.interrupt` — 스레드 인터럽트로 작업 중단
- `ThreadStopMainV1` — `volatile boolean runFlag`로 중단 — `sleep()` 중 플래그 확인 불가로 지연 중단
- `ThreadStopMainV2` — `thread.interrupt()`로 중단 — `sleep()` 중 `InterruptedException` 발생, catch 후 인터럽트 상태 false로 초기화
- `ThreadStopMainV3` — `isInterrupted()`로 루프 탈출 — 인터럽트 상태 true 유지로 자원 정리 중 sleep에서 또 예외 발생
- `ThreadStopMainV4` — `Thread.interrupted()`로 루프 탈출 — 상태를 false로 초기화하여 자원 정리 정상 완료 (권장 패턴)
- 주요 API: `thread.interrupt()`, `Thread.currentThread().isInterrupted()`, `Thread.interrupted()`, `volatile`

---

### `thread.control.test` — Thread.join() 실습
- `JoinTest1Main` — `start → join` 순차 반복으로 스레드를 직렬 실행 (이전 스레드 완료 후 다음 시작)
- `JoinTest2Main` — 모두 `start` 후 순서대로 `join`으로 병렬 실행 후 대기
- 직렬 실행(총 9초) vs 병렬 실행(총 3초) 성능 차이 비교
- 주요 API: `Thread.start()`, `Thread.join()`

---

### `thread.control.join` — Thread.join() 학습
- `JoinMainV0` — `join()` 없이 스레드 실행 후 메인 스레드가 먼저 종료되는 문제 확인
- `JoinMainV1` — `join()` 없이 스레드 계산 결과를 가져오려다 항상 0이 나오는 경쟁 조건 확인
- `JoinMainV2` — `sleep()`으로 타이밍을 맞추는 방식 — 정확한 대기 시간 예측이 어려움을 시연
- `JoinMainV3` — `thread.join()`으로 스레드 종료 시까지 무한 대기 — 경쟁 조건 해결
- `JoinMainV4` — `thread.join(ms)`으로 최대 N밀리초만 대기하는 타임아웃 join 확인
- `join()` 미사용 시 메인 스레드가 작업 완료 전에 결과를 읽어 올바른 값을 얻지 못함
- `join(ms)` 사용 시 타임아웃 내 완료 여부와 무관하게 대기 해제
- 주요 API: `Thread.join()`, `Thread.join(long millis)`, `Runnable`

---

### `thread.control` — 스레드 정보 조회 및 생명주기
- `ThreadInfoMain` — `Thread` 객체의 메타정보(ID, 이름, 우선순위, 그룹, 상태) 출력
- `ThreadStateMain` — 스레드 생명주기 상태(NEW → RUNNABLE → TIMED_WAITING → TERMINATED) 전환 확인
- `sleep()` 중인 스레드는 외부에서 `TIMED_WAITING`으로, 스레드 내부에서는 `RUNNABLE`로 보임
- 주요 API: `threadId()`, `getName()`, `getPriority()`, `getThreadGroup()`, `getState()`, `Thread.State`

---

### `thread.start.test` — 스레드 생성 방식 실습
- `StartTest1Main` — `Thread` 상속으로 카운터 스레드 구현
- `StartTest2Main` — `Runnable` 구현체 + `Thread` 생성자에 이름 지정
- `StartTest3Main` — 익명 클래스로 `Runnable` 구현
- `StartTest4Main` — 두 `Runnable`을 서로 다른 주기(1000ms / 500ms)로 병렬 실행
- Thread 상속 → Runnable 익명 클래스 → 람다 순서로 코드 간결화 흐름 확인
- 주요 API: `Thread(Runnable, name)`, `Thread.sleep(ms)`

---

### `util` — 스레드 공통 유틸리티
- `ThreadUtils` — `Thread.sleep()` 체크 예외를 래핑해 언체크 예외로 변환하는 유틸 추상 클래스
- `MyLogger` — 스레드 이름·시간을 포함한 로그 출력 추상 클래스 (static 메서드로 사용)
- 멀티스레드 환경에서 어느 스레드가 어느 시점에 실행 중인지 한눈에 파악하기 위한 공통 유틸
- 주요 API: `ThreadUtils.sleep(millis)`, `log(Object)`, `Thread.currentThread().getName()`, `DateTimeFormatter`

---

### `thread.start` — 스레드 생성과 실행
- `Thread` — 상속 후 `run()` 오버라이드로 작업 정의
- `start()` vs `run()`: `start()`만 새 스레드 생성, `run()` 직접 호출은 메인 스레드에서 실행
- `Thread.currentThread()`, `Thread.getName()`
- `Runnable` — 스레드 작업을 분리하는 인터페이스. `Thread(Runnable)` 생성자에 전달해 사용
  - `Thread` 상속 vs `Runnable` 구현: `Runnable`이 작업과 스레드를 분리해 유연성 높음 (단일 상속 제약 없음)
- `setDaemon(true)` — 데몬 스레드 설정. 모든 유저 스레드 종료 시 데몬 스레드도 자동 종료
- `Thread.sleep(ms)` — 지정 시간만큼 스레드 일시 정지 (InterruptedException 체크 예외)