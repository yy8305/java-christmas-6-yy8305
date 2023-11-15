package christmas.constants;

public enum EventBadge {
    SANTA("산타", 20_000),
    TREE("트리", 10_000),
    STAR("별", 5_000),
    EMPTY("없음", 0);

    private final String name;
    private final Integer threshold;

    EventBadge(String name, Integer threshold) {
        this.name = name;
        this.threshold = threshold;
    }

    public String getName() {
        return name;
    }

    public Integer getThreshold() {
        return threshold;
    }
}
