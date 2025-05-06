#### CLI = Command Line Interface (명령줄 인터페이스)
-> 즉 마우스 없이 명령어를 입력하여 조작함

| 구분  | 이름                     | 설명                     |
| --- | ---------------------- | ---------------------- |
| GUI | Graphic User Interface | 마우스로 조작, 버튼, 창, 아이콘 있음 |
| CLI | Command Line Interface | 명령어 입력으로 조작, 텍스트 기반    |
|     |                        |                        |
git, gh, python같은 프로그램이 있고 이를 조작할 수 있는 연결 매개체 중 하나가 CLI이다.

사실 터미널만 있어도 git commit, git push를 사용할 수 있지만 CLI를 설치하면 추가 기능을 더 사용할 수 있다.

| 항목    | `git` (Git CLI)          | `gh` (GitHub CLI)                 |
| ----- | ------------------------ | --------------------------------- |
| 용도    | **코드 버전 관리**             | **GitHub 기능 제어**                  |
| 사용 대상 | 로컬 저장소 + 원격 저장소(git)     | GitHub 플랫폼 자체                     |
| 예시    | `git commit`, `git push` | `gh pr create`, `gh issue create` |


|기능|gh 명령어 예시|설명|
|---|---|---|
|✅ PR 만들기|`gh pr create`|웹 안 들어가고 터미널에서 바로 PR 생성|
|✅ PR 보기/머지|`gh pr view`, `gh pr merge`|어떤 코드 바뀌었는지 확인하고 병합까지 가능|
|✅ 이슈 만들기|`gh issue create`|간단한 양식으로 이슈 작성 가능|
|✅ 이슈 목록 보기|`gh issue list`|누구 할당됐는지, 어떤 이슈 열려 있는지 조회|
|✅ 리포지토리 만들기|`gh repo create`|GitHub 웹 들어갈 필요 없이 새 저장소 생성|
|✅ 리포지토리 클론|`gh repo clone user/repo`|복제도 훨씬 간단하게 가능|
|✅ GitHub Actions 상태 확인|`gh run list`, `gh run view`|워크플로우 실행 상태 확인 (자동 배포 등)|