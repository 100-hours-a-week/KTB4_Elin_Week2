package ootd;

import java.util.Random;

public class WeatherThread extends Thread {
    private volatile boolean running = true;

    private final Random random = new Random();
    private final Weather[] weathers = Weather.values();

    private volatile Weather currentWeather = weathers[random.nextInt(Weather.values().length)];

    @Override
    public void run() {

        while (running) {

            try {
                Thread.sleep(6000);
                currentWeather = weathers[random.nextInt(weathers.length)];

            } catch (InterruptedException e) {
                System.out.println("날씨 업데이트를 종료합니다 .");
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

