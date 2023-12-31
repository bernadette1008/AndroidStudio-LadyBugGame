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

## 23-06-16

## -수정사항

이번엔 대부분의 오류를 수정했다.

첫번째로 구석에서 움직이지 않던 아이템은 처음 생성 위치에 따라 가지는 xInt, yInt 값을 수정하여 예를들어 위쪽에서 생성되면 x값을 기준으로 +-값을 가져 구석에 바깥으로 나갈 수 있도록 하였다.

두번째로 아이템이 두개 이상 있을 때 아이템을 먹게되면 게임이 꺼지는 오류는 SurfaceView부분에 아이템 그리는 부분의 ArrayList를 삭제하는 부분을 수정하여 오류를 잡았다.

세번째로 뒤로가기 버튼 클릭 후 다시 START 버튼을 누르면 게임이 꺼지는 오류는 thread부분에 run()에서 c가 null이 아닐 때만 실행하도록 if문을 추가하여 오류를 잡았다.

## 23-06-17

### -수정사항

#### 플레이어와 오브젝트의 접촉 코드 수정
이전에 코드로는 플레이어의 x,y 좌표와 오브젝트의 x,y좌표를 비교하여 접촉을 감지했기 때문에 원의 크기를 늘려도 원의 중심이 있는 부분까지 들어가야 접촉을 감지했지만 이번에는 접촉하는 두 원의 중심점간의 거리와 두 원의 반지름의 합계를 비교하여 하기 때문에 원의 바깥부분에 닿아도 접촉을 감지할 수 있게됐다.

#### 게임오버화면 수정
기존에 게임오버화면은 GAMEOVER 글자만 나왔지만 이미지와 재시작버튼, 메인메뉴 버튼을 추가하여 화면 전환을 할 수 있도록 했다.

## 23-06-18

### -수정사항

#### 아이템 추가
## AT필드

![image](https://github.com/bernadette1008/AndroidStudio-LadyBugGame/assets/103907857/08bddca4-1252-485e-b723-4c92f9b48f0b)

아이템 모양

![image](https://github.com/bernadette1008/AndroidStudio-LadyBugGame/assets/103907857/fb2efcef-63a8-4d1e-a994-78b3eb7d5104)

사용모습

## 자기장

![image](https://github.com/bernadette1008/AndroidStudio-LadyBugGame/assets/103907857/d3281a6a-82f0-4289-b20e-4b80ed65755b)

아이템 모양

![image](https://github.com/bernadette1008/AndroidStudio-LadyBugGame/assets/103907857/22417f51-8897-4248-8af7-9ac7ce7343ae)

아이템은 적을 처치하여 플레이어가 자신을 보호할 수 있는 수단으로 해당 아이템을 이용해 적을 처치 시 10점의 추가 점수를 받게된다.

해당 아이템을 끝으로 게임으로써 필요한 것은 모두 제작했고 이제 DB와 설정 부분을 만들어줌으로써 게임 제작을 끝내겠다.

## 23-06-19

### -수정사항

#### DB 추가(점수 기록 기능)

![image](https://github.com/bernadette1008/AndroidStudio-LadyBugGame/assets/103907857/25a6b644-c261-430d-9bf0-3c5360cee68b)

저장된 점수

sqlite를 연동하여 점수를 저장한 모습이다.
점수는 플레이어가 죽었을 때 저장되도록 하였다.

그리고 설정은 오디오 설정을 할 수 있도록 할려고 했지만, 해당 게임에는 사운드가 없어서 없애버렸다.

따라서 이상으로 해당 게임 제작을 끝마친다.

## 23-6-23

### -수정사항

기능적인 부분은 19일날 마쳤는데 추가하면 좋을게 있어서 추가했다.

## -플레이어 아이콘

![image](https://github.com/bernadette1008/AndroidStudio-LadyBugGame/assets/103907857/6b7d0a66-a0af-4026-98d2-e7cd8deb6259)

플레이어 아이콘

해당 아이콘은 손가락으로 플레이어를 움직임에 따라 이미지가 회전한다.

## - 아이템 아이콘

![image](https://github.com/bernadette1008/AndroidStudio-LadyBugGame/assets/103907857/905669bc-d6c4-4ea4-9b57-7470dc0c8efc)

자기장 아이템

![image](https://github.com/bernadette1008/AndroidStudio-LadyBugGame/assets/103907857/64fc42f2-798e-4bdb-86e8-20993bad6776)

AT필드 아이템


