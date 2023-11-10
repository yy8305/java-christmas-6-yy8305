package christmas.constants;

public enum Settings {
    EVENT_YEAR(2023),
    EVENT_MONTH(12);

    private Integer value;

    Settings(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
