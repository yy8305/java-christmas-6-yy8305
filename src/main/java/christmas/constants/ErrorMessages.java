package christmas.constants;

public enum ErrorMessages {
    INVALID_VISIT_DATE("유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    INVALID_ORDER_MENU("유효하지 않은 주문입니다. 다시 입력해 주세요."),
    MAX_ORDER_MENU_COUNT("메뉴는 한 번에 최대 20개 까지만 주문할 수 있습니다. 다시 입력해 주세요."),
    BEVERAGE_ONLY_ORDER_NOT_ALLOWED_MESSAGE("음료만 주문 시, 주문할 수 없습니다.");

    private final String ERROR_TITLE = "[ERROR] ";
    private final String message;

    ErrorMessages(String message) {
        StringBuilder builder = new StringBuilder();
        builder.append(ERROR_TITLE);
        builder.append(message);
        this.message = builder.toString();
    }

    public String getMessage() {
        return message;
    }
}
