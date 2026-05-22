package ootd;

public enum Item {
    Umbrella("우산"),
    Fan("휴대용 선풍기"),
    Sunglasses("선글라스"),
    Muffler("목도리"),
    Mask("마스크");

    private final String itemName;

    Item(String itemName) {
        this.itemName = itemName;
    }
    public String getItemName() {
        return itemName;
    }
}
