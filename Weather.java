package ootd;

public enum Weather {
    Sunny("맑음", Item.Sunglasses),
    Heatwave("폭염", Item.Fan),
    Rainy("비", Item.Umbrella),
    Snowy("눈",Item.Muffler),
    Dusty("미세먼지",Item.Mask);

    private final String weatherName;
    private final Item necessities;

    Weather(String weatherName, Item necessities) {
        this.weatherName = weatherName;
        this.necessities = necessities;
    }

    public String getWeatherName() {
        return weatherName;
    }

    public Item getNecessities() {
        return necessities;
    }
}
