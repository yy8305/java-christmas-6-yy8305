package christmas.constants;

public enum MenuBook {
    MUSHROOM_SOUP(MenuType.APPETIZER, "양송이수프", 6_000),
    TAPAS(MenuType.APPETIZER, "타파스", 5_500),
    CAESAR_SALAD(MenuType.APPETIZER, "시저샐러드", 8_000),

    T_BONE_STEAK(MenuType.MAIN, "티본스테이크", 55_000),
    BBQ_RIBS(MenuType.MAIN, "바비큐립", 54_000),
    SEAFOOD_PASTqA(MenuType.MAIN, "해산물파스타", 35_000),
    CHRISTMAS_PASTA(MenuType.MAIN, "크리스마스파스타", 25_000),

    CHOCOLATE_CAKE(MenuType.DESSERT, "초코케이크", 15_000),
    ICE_CREAM(MenuType.DESSERT, "아이스크림", 5_000),

    ZERO_COLA(MenuType.BEVERAGE, "제로콜라", 3_000),
    RED_WINE(MenuType.BEVERAGE, "레드와인", 60_000),
    CHAMPAGNE(MenuType.BEVERAGE, "샴페인", 25_000);

    private final MenuType menuType;
    private final String name;
    private final Integer amount;

    MenuBook(MenuType menuType, String name, Integer amount) {
        this.menuType = menuType;
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }
}
