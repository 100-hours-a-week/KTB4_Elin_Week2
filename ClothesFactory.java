package ootd;

public class ClothesFactory {
    public static Clothes createTop(int choice) {
        Top top = Top.values()[choice - 1];
        return new Clothes("상의",top.getName());
    }

    public static Clothes createBottom(int choice) {
        Bottom bottom = Bottom.values()[choice - 1];
        return new Clothes("하의",bottom.getName());
    }

    public static Clothes createAcc(int choice) {
        Acc acc = Acc.values()[choice - 1];
        return new Clothes("액세서리",acc.getName());
    }


}
