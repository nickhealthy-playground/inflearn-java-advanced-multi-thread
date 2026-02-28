# 김영한의 실전 자바 - 고급 1편: 멀티스레드와 동시성

> 학습 중 등장하는 개념·클래스·API를 커밋 단위로 기록.

## 학습 기록

---

### `thread.start` — 스레드 생성과 실행
- `Thread` — 상속 후 `run()` 오버라이드로 작업 정의
- `start()` vs `run()`: `start()`만 새 스레드 생성, `run()` 직접 호출은 메인 스레드에서 실행
- `Thread.currentThread()`, `Thread.getName()`
- `setDaemon(true)` — 데몬 스레드 설정. 모든 유저 스레드 종료 시 데몬 스레드도 자동 종료
- `Thread.sleep(ms)` — 지정 시간만큼 스레드 일시 정지 (InterruptedException 체크 예외)