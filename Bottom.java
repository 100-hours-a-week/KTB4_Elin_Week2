package ootd;

public enum Bottom {
    Pants("바지"),
    Skirt("치마");

    private final String name;
    private Bottom(String name) { this.name = name;}
    public String getName() { return name;}
}