package christmas.constants;

public enum Settings {
    ZERO(0),
    SINGLE(1),
    MIN_ORDER_COUNT(1),
    MAX_ORDER_COUNT(20),
    MIN_ORDER_AMOUNT(10_000),
    EVENT_YEAR(2023),
    EVENT_MONTH(12),
    CHRISTMAS_ADDITIONAL_AMOUNT(100),
    GIFT_CONDITION(120_000),
    VISIT_DAY_MIN(1),
    VISIT_DAY_MAX(31);

    private Integer value;

    Settings(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
