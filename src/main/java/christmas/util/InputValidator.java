package christmas.util;

import christmas.constants.ErrorMessages;
import christmas.constants.MenuType;
import christmas.constants.Settings;
import christmas.domain.MenuCart;
import java.util.regex.Pattern;

public class InputValidator {
    private final Pattern NUMBER_PATTERN = Pattern.compile(".*[^0-9].*");

    public void validateVisitDayFormat(String input) {
        validateNumeric(input, ErrorMessages.INVALID_VISIT_DATE);

        Integer inputNumber = Integer.parseInt(input);
        if (inputNumber < Settings.VISIT_DAY_MIN.getValue()) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_VISIT_DATE.getMessage());
        }
        if (inputNumber > Settings.VISIT_DAY_MAX.getValue()) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_VISIT_DATE.getMessage());
        }
    }

    private void validateNumeric(String input, ErrorMessages error) {
        if (NUMBER_PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException(error.getMessage());
        }

        try {
            if (Integer.parseInt(input) >= Integer.MAX_VALUE) {
                throw new IllegalArgumentException(error.getMessage());
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(error.getMessage());
        }
    }

    public void validateOrderMenu(String input) {
        MenuCart cart = MenuCart.from(input);
        if (cart.getMenuTypeCount(MenuType.BEVERAGE) == cart.getMenus().size()) {
            throw new IllegalArgumentException(ErrorMessages.BEVERAGE_ONLY_ORDER_NOT_ALLOWED_MESSAGE.getMessage());
        }
    }
}
