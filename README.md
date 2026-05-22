# 2주차 과제 회고

CLI 프로그램 제작 (전주에 제작한 동기 프로그램을 비동기 프로그램으로 만들기)
1-1. 간단한 스레드 구현 (예: 시간 흐름, 날씨 변화, 음악 플레이 등)
1-2. (선택) 스레드간 상호작용할 수 있는 기능 구현 (예 : 사람 스레드와 몹 스레드가 싸워서 사람스레드의 체력이 줄어듬)

실습코드리뷰 :  [https://www.notion.so/2-3667b931b9fe80d0a0c6d9aaf4cf0b26?source=copy_link](https://www.notion.so/2-3667b931b9fe80d0a0c6d9aaf4cf0b26?pvs=21)

---

## 1-1 날씨 변화 스레드 구현하기

외출 전 옷 고르기 프로그램에 덧붙여 날씨 변화에 따른 필요한 물품을 챙기라는 메세지를 호출하는 스레드를 구현하고자 했다. 

또한, 3초마다 실시간으로 날씨가 변하도록 WeatherThread를 구현했다.

`Thread.sleep(3000);`을 활용해 3초로 설정하였고, 스레드 간 상태 공유를 위해서 `volatile`를 사용하였다. 

#### synchronized 대신 volatile을 사용한 이유?

변수에 `volatile`을 선언할 경우, 읽고 쓸 때마다 무조건 메인 메모리에 직접 접근하기 때문에 한 스레드가 플래그를 변경하면 다른 스레드가 즉각적으로 변경된 값을 볼 수 있기 때문이다.

ex) `running = false`로 변경해도 루프를 도는 스레드가 cpu 캐시에 남아있는 true값을 계속 읽는다면? 프로그램은 무한 루프에 빠지게 될 것이다. 

또한 이 스레드의 경우에 복잡한 연산이 포함되지 않고, `running = false`처럼 단일 연산을 하기 때문에 값이 꼬이지 않는다. 따라서 원자성까지 보장하는 무거운 `synchronized`를 사용할 필요가 없다고 판단하였다. 

---

#### 예외 처리에 대해서 고민하기

```java
 public void stopWeather() {
        this.running = false;
        this.interrupt();
    }
```

기존 stopWeather()에는 `running = false`만 있어서, 스레드가 `Thread.sleep(3000);` 상태일 때 중단 요청이 오면 3초를 기다려야 종료되는 문제가 있었다. 오늘 팀원들과의 딥다이브 과정에서 `interrupt()` 개념을 설명해주신 게 떠올랐고, 이 문제를 해결하기 위해 `this.interrupt();`를 추가했다.

```java
while (running) {
    try {
        Thread.sleep(3000);

        currentWeather = weathers[random.nextInt(weathers.length)];
        System.out.println("현재 날씨가 " + currentWeather.getWeatherName() + " (으)로 바뀌었습니다.");
        System.out.println("입력해주세요 : ");
    } catch (InterruptedException e) {
        System.out.println("날씨 스레드에 문제가 생겼습니다 : " + e.getMessage());
        break;
    }
}
```

이제 대기 중인 스레드에 interrupt()가 호출되면 즉시 `InterruptedException`이 발생한다. 남은 시간을 기다리거나 불필요한 날씨 업데이트 로직을 실행할 필요 없이, 실행 흐름이 바로 catch 블록으로 이동한다. 그리고 catch 문 안의 `break;`를 통해 무한 루프를 빠져나와 스레드가 바로 종료된다.

## 1-2 날씨에 따라 필요한 물품 알려주기

2주차 과제를 진행하기 전에 1주차 과제를 리팩토링 하면서 Enum이라는 새로운 개념에 대해 알게 되었다. 처음에 Weather을 작성할 때 String[] 배열로 작성했는데, 다시 enum으로 바꿔 작성하였다. 

#### Enum으로 작성한 이유?

 String으로 작성했을 때 ClothesMain에서 `if (weather.equals(”비”)) {}`와 같은 조건문을 여러 개 작성해야했다. 저번 피드백 때도 switch문을 해결하기 위해 고민했었는데, `enum`을 사용하면 if-else문의 양이 엄청 줄어들어 가독성이 좋아졌었다. 그리고 예전에 강의를 들을 때 항상 **1개가 아니라 100개 이상 있다고 가정하고 코드를 작성하라**는 말이 맴돌아서, 이 경우에도 날씨아이템을 지금은 5개로 만들었지만 더 많이 늘어난다고 생각해봤다. 그렇다면 `enum`을 사용하면 스레드코드나 메인 클래스는 고칠 필요가 없기 때문에 OCP원칙에 있어서도 좋다고 판단했다. 

```java
public enum Weather {
    Sunny("맑음", Item.Sunglasses),
    Heatwave("폭염", Item.Fan),
    Rainy("비", Item.Umbrella),
    Snowy("눈",Item.Muffler),
    Dusty("미세먼지",Item.Mask);
...
```

---

#### 문제점

이렇게 작성하였지만 원하는 코드가 안 나왔다. 옷을 고르는 도중에 날씨가 계속 바뀌는 안내가 뜨고, 너무 휙휙 바뀌는 느낌이라 수정이 필요했다. 

#### 해결 방법

먼저, 주기가 너무 짧았다. 3초 안에 선택하지 않으면 날씨가 변했기 때문에, `Thread.sleep()`을 조금 더 길게 잡아야겠다고 판단했다.

→ `.sleep(3000)`을 `.sleep(6000)`(6초)로 수정하여 날씨가 급변하는 느낌을 줄였다. 

또한, 필요한 물품을 나타내는 Item `enum`을 추가해 Weather `enum`이 Item을 통해 각 날씨에 필요한 물품을 1:1 객체 조합으로 가져오도록 했다. 

→ 결과적으로 메인 스레드에서 옷 고르기가 끝나는 시점에 WeatherThread로부터 최종 날씨 객체를 전달받아서 현재 날씨는 어떤 상태인지, 무엇을 챙겨야하는지 상황에 맞는 메세지를 동적으로 출력하도록 했다. 이렇게 상호작용 기능을 완성하였다.
