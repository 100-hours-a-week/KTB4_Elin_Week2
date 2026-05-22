package ootd;

public enum Top {
    Blouse("블라우스"),
    Tshirt("티셔츠"),
    Knit("니트");

    private final String name;
    private Top(String name) { this.name = name;}
    public String getName() { return name;}
}