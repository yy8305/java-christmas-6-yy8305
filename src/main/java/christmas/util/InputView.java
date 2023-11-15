package christmas.util;

import camp.nextstep.edu.missionutils.Console;
import christmas.domain.MenuCart;
import java.util.function.Supplier;

public class InputView {
    private InputValidator validator = new InputValidator();

    private String getValidInput(Supplier<String> action) {
        boolean isNotEnd = true;
        String input = "";
        do {
            try {
                input = action.get();
                isNotEnd = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (isNotEnd);

        return input;
    }

    public Integer getVisitDay() {
        System.out.println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");

        String validInput = getValidInput(() -> {
            String input = Console.readLine();
            validator.validateVisitDayFormat(input);
            return input;
        });

        return Integer.parseInt(validInput);
    }

    public MenuCart getOrderMenuList() {
        System.out.println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
        String validInput = getValidInput(() -> {
            String input = Console.readLine();
            validator.validateOrderMenu(input);
            return input;
        });

        return MenuCart.from(validInput);
    }
}
