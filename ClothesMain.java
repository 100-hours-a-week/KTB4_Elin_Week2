package ootd;

public class ClothesMain {

    public static void main(String[] args) {
        WeatherThread weatherThread = new WeatherThread();
        weatherThread.start();

        System.out.println("현재 날씨는 " + weatherThread.getCurrentWeather().getWeatherName() + "입니다.\n");
        System.out.println("외출 준비를 시작합니다. 옷을 골라주세요!");

        MenuView menu = new MenuView();

        int topChoice = menu.getTop();
        int bottomChoice = menu.getBottom();
        int accChoice= menu.getAcc();

        weatherThread.stopWeather();

        try {
            weatherThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Clothes top = ClothesFactory.createTop(topChoice);
        Clothes bottom = ClothesFactory.createBottom(bottomChoice);
        Clothes acc = ClothesFactory.createAcc(accChoice);

        System.out.println();
        top.wear();
        bottom.wear();
        acc.wear();
        System.out.println();
        System.out.println("오늘의 코디는 " + top.getName() + " + " + bottom.getName() + " + " + acc.getName() + "입니다.");

        Weather OutsideWeather = weatherThread.getCurrentWeather();
        System.out.println("현재 날씨는 " + OutsideWeather.getWeatherName() + "입니다.");

        Item OutsideNecessities = OutsideWeather.getNecessities();
        System.out.println(OutsideNecessities.getItemName() +"(을)를 챙기세요:)");
        System.out.println("좋은 하루 보내세요 !");
    }
}
