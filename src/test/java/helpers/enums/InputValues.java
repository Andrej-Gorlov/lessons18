package helpers.enums;

public enum InputValues {
    COMPANY("Bank of Russia"),
    CONTACT("Anonymous"),
    COUNTRY("Russian Federation");

    private final String value;

    InputValues(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
