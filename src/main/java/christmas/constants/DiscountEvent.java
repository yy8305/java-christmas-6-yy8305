package christmas.constants;

public enum DiscountEvent {
    CrismasDday("크리스마스 디데이 할인", 1_000),
    WeekDay("평일 할인", 2_023),
    WeekEnd("주말 할인", 2_023),
    Special("특별 할인", 1_000),
    Gift("증정 이벤트", 0);

    private String name;
    private Integer defaultDiscountAmount;

    DiscountEvent(String name, Integer defaultDiscountAmount) {
        this.name = name;
        this.defaultDiscountAmount = defaultDiscountAmount;
    }
}
