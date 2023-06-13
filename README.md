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
게임 오버 화면

![image](https://github.com/bernadette1008/AndroidStudio-LadyBugGame/assets/103907857/43b29024-bf99-4f3b-af33-09d5132bdb44)

시작 화면

![image](https://github.com/bernadette1008/AndroidStudio-LadyBugGame/assets/103907857/0149fa90-b129-4898-b379-9e4af2b2dda4)

적의 추척(빨간색 - 적, 파란색 - 플레이어)

![image](https://github.com/bernadette1008/AndroidStudio-LadyBugGame/assets/103907857/7e649d49-6b24-4923-b710-f5d9017f8463)

게임 오버 화면

### -수정사항

현재는 테스트겸 스와이프를 통해 플레이어를 조작하도록 설정해놨다.

또한 디버깅을 위해 플레이어의 위치를 화면 중앙에 보이도록 하였고

적은 상하좌우 아무 위치에서 랜덤으로 스폰하며
스폰에는 5초란 시간이 걸리며 5초마다 5 마리가 스폰된다.
적은 플레이어의 위치로 추적하도록 코드를 짜놨으며

만약 플레이어와 적이 부딪칠 경우 GAME OVER 화면이 등장하도록 만들었다.

## 23-06-12
### -수정사항

레이디버그를 다시 플레이해본 결과 이전의 적의 이동 방식이 매우 다르다는 것을 확인했다.

그래서 수정하였다.

### -수정결과

적이 플레이어를 계속해서 추적하는 것이 아닌 스폰 당시의 플레이어의 위치로 일정한 속도로 전진하도록 하였다.
이런 식으로 만듬으로써 적이 꽤 쌓였을 때도 피하지 못하고 죽어야만 하는 상황이 이전보다 더 적게 나타날 것으로 보이며
적이 겹쳐 보다 수에 비해서 적어보이던 이전 방식과는 다르게 좀 더 풍성하게 보여 난이도가 꽤 보이는 효과가 있는 것처럼 보인다.

## 23-06-13

![image](https://github.com/bernadette1008/AndroidStudio-LadyBugGame/assets/103907857/c0acd233-dd29-4bcd-be7f-aebb4bbe9677)

스코어와 아이템(초록색 - 아이템)

![image](https://github.com/bernadette1008/AndroidStudio-LadyBugGame/assets/103907857/aeb6c8cd-c747-453b-8fbf-c7c5f7374fbb)

게임 오버 화면 글씨 크기 수정

### -수정사항

스코어를 추가해봤다. 해당 스코어는 100milsecon 마다 1씩 증가하며 좌측 상단에 표시되도록 만들었다.
또한 이번엔 Item 요소를 추가했다. 적을 잡는 기능을 만든 것은 아니지만 7초마다 아이템이 생성되어 무작위의 속도로 무작위의 위치에서 등장하도록 했다.

또한 게임오버 화면에 글씨를 좀 더 키웠다.

### -수정해야하는 부분

구석에 박혀서 움직이지 않는 두 아이템

아이템이 두개 이상 있을 때 아이템을 먹게되면 게임이 꺼지는 오류

게임 오버 화면에서 다시 시작 버튼이나 메인 메뉴로 돌아가는 버튼

게임 화면에서 뒤로가기 버튼 클릭 후 다시 START 버튼을 누르면 게임이 꺼지는 오류
