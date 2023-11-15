package christmas.util;

import christmas.constants.DiscountEvent;
import christmas.domain.Menu;
import christmas.domain.MenuCart;
import java.text.DecimalFormat;
import java.util.Map;

public class OutputView {
    private final DecimalFormat PRICE_FORMAT = new DecimalFormat("###,###");

    public void showGreeting() {
        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
    }

    public void showOrderMenus(MenuCart orderMenus) {
        System.out.println("<주문 메뉴>");
        for (Map.Entry<Menu, Integer> order : orderMenus.getMenus().entrySet()) {
            showMenuAndQuantity(order.getKey(), order.getValue());
        }
        System.out.println();
    }

    private void showMenuAndQuantity(Menu menu, Integer quantity) {
        StringBuilder builder = new StringBuilder();
        builder.append(menu.getMenu().getName());
        builder.append(" ");
        builder.append(quantity);
        builder.append("개");
        System.out.println(builder.toString());
    }

    public void showPreDiscountTotalOrderAmount(Integer amount) {
        System.out.println("<할인 전 총주문 금액>");
        System.out.println(getFormattedPriceString(amount));
        System.out.println();
    }

    private String getFormattedPriceString(Integer price) {
        StringBuilder builder = new StringBuilder();
        builder.append(PRICE_FORMAT.format(price));
        builder.append("원");
        return builder.toString();
    }

    public void showGiftMenu(MenuCart cart) {
        System.out.println("<증정 메뉴>");
        Map<Menu, Integer> menus = cart.getMenus();
        if (isSizeEmpty(menus.size())) {
            return;
        }

        for (Map.Entry<Menu, Integer> gift : menus.entrySet()) {
            showMenuAndQuantity(gift.getKey(), gift.getValue());
        }
        System.out.println();
    }

    private boolean isSizeEmpty(Integer size) {
        if (size <= 0) {
            System.out.println("없음");
            System.out.println();
            return true;
        }

        return false;
    }

    public void showDiscounts(Map<DiscountEvent, Integer> amountByEvent) {
        System.out.println("<혜택 내역>");

        if (isSizeEmpty(amountByEvent.size())) {
            return;
        }
        for (Map.Entry<DiscountEvent, Integer> discount : amountByEvent.entrySet()) {
            StringBuilder builder = new StringBuilder();
            builder.append(discount.getKey().getName());
            builder.append(": ");
            builder.append(getFormattedMinusPriceString(discount.getValue()));
            System.out.println(builder.toString());
        }
        System.out.println();
    }

    private String getFormattedMinusPriceString(Integer amount) {
        StringBuilder builder = new StringBuilder();
        builder.append("-");
        builder.append(getFormattedPriceString(amount));
        return builder.toString();
    }

    public void showTotalDiscountAmount(Integer amount) {
        System.out.println("<총혜택 금액>");
        if (amount >= 0) {
            System.out.println(getFormattedPriceString(amount));
            System.out.println();
            return;
        }

        System.out.println(getFormattedMinusPriceString(amount));
        System.out.println();
    }

    public void showTotalPaymentAmount(Integer amount) {
        System.out.println("<할인 후 예상 결제 금액>");
        System.out.println(getFormattedPriceString(amount));
        System.out.println();
    }

    public void showEventBadge(String badgeName) {
        System.out.println("<12월 이벤트 배지>");
        System.out.println(badgeName);
        System.out.println();
    }
}
