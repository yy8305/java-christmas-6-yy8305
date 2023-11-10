package christmas.constants;

public enum DiscountEvent {
    CRISMAS_D_DAY("크리스마스 디데이 할인", 1_000),
    WEEK_DAY("평일 할인", 2_023),
    WEEK_END("주말 할인", 2_023),
    SPECIAL("특별 할인", 1_000),
    GIFT("증정 이벤트", 0);

    private String name;
    private Integer defaultDiscountAmount;

    DiscountEvent(String name, Integer defaultDiscountAmount) {
        this.name = name;
        this.defaultDiscountAmount = defaultDiscountAmount;
    }
}
