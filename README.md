# AndroidStudio-LadyBug
어린 시절 해봤던 추억의 게임 레이디 버그를 만들어보자

![image](https://github.com/bernadette1008/AndroidStudio-LadyBugGame/assets/103907857/8b5a30f3-0a49-4e5d-97d4-7e8ad986dbe9)

가볍게 만들어본 타이틀 화면

START 버튼을 통해 게임을 시작하고, SCORE 버튼을 통해서는 자신이 갱신시킨 기록들을 확인하며, OPTION에서는 사운드 관련 설정을 만지도록 만들 생각이다.

## 23-06-11
현재까지 만든 것

플레이어의 조작.
적 스폰 및 이동
적과 플레이어의 접촉 이벤트
사망 화면

![image](https://github.com/bernadette1008/AndroidStudio-LadyBugGame/assets/103907857/43b29024-bf99-4f3b-af33-09d5132bdb44)

시작 화면

![image](https://github.com/bernadette1008/AndroidStudio-LadyBugGame/assets/103907857/0149fa90-b129-4898-b379-9e4af2b2dda4)

적의 추척

![image](https://github.com/bernadette1008/AndroidStudio-LadyBugGame/assets/103907857/7e649d49-6b24-4923-b710-f5d9017f8463)

게임 오버 화면

현재는 테스트겸 스와이프를 통해 플레이어를 조작하도록 설정해놨다.

또한 디버깅을 위해 플레이어의 위치를 화면 중앙에 보이도록 하였고

적은 상하좌우 아무 위치에서 랜덤으로 스폰하며
스폰에는 5초란 시간이 걸리며 5초마다 5 마리가 스폰된다.
적은 플레이어의 위치로 추적하도록 코드를 짜놨으며

만약 플레이어와 적이 부딪칠 경우 GAME OVER 화면이 등장하도록 만들었다.

## 23-06-12
수정사항

레이디버그를 다시 플레이해본 결과 이전의 적의 이동 방식이 매우 다르다는 것을 확인했다.

그래서 수정하였다.

수정결과(주관적인편)

적이 플레이어를 계속해서 추적하는 것이 아닌 스폰 당시의 플레이어의 위치로 일정한 속도로 전진하도록 하였다.
이런 식으로 만듬으로써 적이 꽤 쌓였을 때도 피하지 못하고 죽어야만 하는 상황이 이전보다 더 적게 나타날 것으로 보이며
적이 겹쳐 보다 수에 비해서 적어보이던 이전 방식과는 다르게 좀 더 풍성하게 보여 난이도가 꽤 보이는 효과가 있는 것처럼 보인다.
