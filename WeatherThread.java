package ootd;

import java.util.Random;

public class WeatherThread extends Thread {
    private volatile boolean running = true;

    private volatile Weather currentWeather = Weather.Sunny;

    @Override
    public void run() {
        Random random = new Random();
        Weather[] weathers = Weather.values();

        System.out.println("실시간 날씨를 알려드립니다!");

        while (running) {
            try {
                Thread.sleep(6000);

                currentWeather = weathers[random.nextInt(weathers.length)];
                System.out.println("현재 날씨가 " + currentWeather.getWeatherName() + " (으)로 바뀌었습니다.");
                System.out.println("입력해주세요 : ");
            } catch (InterruptedException e) {
                System.out.println("날씨 스레드에 문제가 생겼습니다 : " + e.getMessage());
                break;
            }
        }
    }
    public void stopWeather() {
        this.running = false;
        this.interrupt();
    }

    public Weather getCurrentWeather() {
        return currentWeather;
    }
}

