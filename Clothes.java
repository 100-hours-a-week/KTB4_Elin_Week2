package ootd;

public class Clothes implements Wearable {
    private final String category;
    private final String name;

    public Clothes(String category, String name) {
        this.category = category;
        this.name = name;
    }
    public String getName() { return name; }
    @Override
    public void wear() {
        if (category.equals("액세서리")) {
            System.out.println(name + "을(를) 씁니다.");
        } else {
            System.out.println(name + "를 입습니다.");
        }
    }
}
