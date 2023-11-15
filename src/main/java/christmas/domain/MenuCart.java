package christmas.domain;

import christmas.constants.ErrorMessages;
import christmas.constants.MenuType;
import christmas.constants.Settings;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MenuCart {
    private static final Integer MENU_INDEX = 0;
    private static final Integer QUANTITY_INDEX = 1;
    private final Map<Menu, Integer> menus = new HashMap<>();
    private Integer allOrderCount = 0;
    private final Map<MenuType, Integer> menuTypeCounts = new HashMap<>();

    public static MenuCart from(String input) {
        MenuCart cart = new MenuCart();
        for (String menu : getSplitMenu(input)) {
            setMenuAndQuantityFromInput(menu, cart);
        }

        return cart;
    }

    private static List<String> getSplitMenu(String input) {
        try {
            return List.of(input.split(","));
        } catch (Exception e) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_ORDER_MENU.getMessage());
        }
    }

    private static void setMenuAndQuantityFromInput(String input, MenuCart cart) {
        try {
            List<String> menuAndQuantity = List.of(input.split("-"));
            Menu menu = new Menu(menuAndQuantity.get(MENU_INDEX));
            Integer quantity = Integer.parseInt(menuAndQuantity.get(QUANTITY_INDEX));

            cart.addMenu(menu, quantity);
        } catch (Exception e) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_ORDER_MENU.getMessage());
        }
    }

    public void addMenu(Menu menu, Integer orderCount) {
        Objects.requireNonNull(menu);
        Objects.requireNonNull(orderCount);

        validationOrderMenu(menu, orderCount);
        this.menus.put(menu, orderCount);
        allOrderCount += orderCount;
        menuTypeCounts.merge(menu.getMenu().getMenuType(), orderCount, Integer::sum);
    }

    private void validationOrderMenu(Menu menu, Integer orderCount) {
        if (this.menus.containsKey(menu)) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_ORDER_MENU.getMessage());
        }

        if (orderCount < Settings.MIN_ORDER_COUNT.getValue()) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_ORDER_MENU.getMessage());
        }

        if ((allOrderCount + orderCount) >= Settings.MAX_ORDER_COUNT.getValue()) {
            throw new IllegalArgumentException(ErrorMessages.MAX_ORDER_MENU_COUNT.getMessage());
        }
    }

    public Map<Menu, Integer> getMenus() {
        return menus;
    }

    public Integer getMenuTypeCount(MenuType type) {
        Integer menuTypeCount = menuTypeCounts.get(type);
        if (menuTypeCount == null) {
            return Settings.ZERO.getValue();
        }

        return menuTypeCount;
    }

    public Integer getTotalOrderAmount() {
        Integer amount = 0;
        for (Menu menu : menus.keySet()) {
            amount += menu.getMenu().getAmount() * menus.get(menu);
        }

        return amount;
    }
}
