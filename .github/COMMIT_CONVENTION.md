# 커밋 컨벤션

[Conventional Commits](https://www.conventionalcommits.org/) 형식을 따릅니다.

## 형식

```
<type>: <한국어 설명>
```

부연 설명이 필요한 경우:

```
<type>: 주제 - 상세 설명
```

## Type 목록

| Type       | 설명                            |
|------------|---------------------------------|
| `feat`     | 새로운 기능 또는 학습 코드 추가  |
| `fix`      | 버그 수정                        |
| `refactor` | 동작 변경 없는 코드 개선         |
| `docs`     | 문서 수정 (README 등)            |
| `test`     | 테스트 코드 추가/수정            |
| `chore`    | 빌드 설정, 의존성 등 기타 변경   |

## 규칙

- 설명은 **한국어**, **현재형**, **마침표 없음**
- 메시지는 변경의 **"what"보다 "why"**에 집중
- 여러 도메인 변경 시 가장 핵심적인 변경 기준으로 type 결정

## 예시

```
feat: ReentrantLock.tryLock()으로 비차단 락 시도 학습
fix: BankAccount 잔액 음수 허용 버그 수정
refactor: BankAccountV3 임계 영역 범위 synchronized 블록으로 최소화
docs: README 학습 기록 업데이트
```
