package ootd;

public enum Acc {
    Cap("모자"),
    Glasses("안경");

    private final String name;
    private Acc(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

}
