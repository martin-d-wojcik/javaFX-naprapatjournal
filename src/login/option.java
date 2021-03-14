package login;

public enum option {
    Admin , User;

    private option() {}

    public String value() {
        return name();
    }

    public static option fromValue(String value) {
        return valueOf(value);
    }
}
