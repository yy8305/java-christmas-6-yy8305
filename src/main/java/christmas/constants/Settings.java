package christmas.constants;

public enum Settings {
    MIN_ORDER_COUNT(1),
    MAX_ORDER_COUNT(20),
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
