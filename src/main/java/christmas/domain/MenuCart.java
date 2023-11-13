package christmas.domain;

import christmas.constants.MenuType;
import christmas.constants.Settings;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MenuCart {
    private final Map<Menu, Integer> menus = new HashMap<>();
    private Integer allOrderCount = 0;
    private final Map<MenuType, Integer> menuTypeCount = new HashMap<>();

    public void addMenu(Menu menu, Integer orderCount) {
        Objects.requireNonNull(menu);
        Objects.requireNonNull(orderCount);

        validationOrderMenu(menu, orderCount);
        this.menus.put(menu, orderCount);
        allOrderCount += orderCount;
        menuTypeCount.merge(menu.getMenu().getMenuType(), orderCount, Integer::sum);
    }

    private void validationOrderMenu(Menu menu, Integer orderCount) {
        if (this.menus.containsKey(menu)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }

        if (orderCount < Settings.MIN_ORDER_COUNT.getValue()) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }

        if ((allOrderCount + orderCount) >= Settings.MAX_ORDER_COUNT.getValue()) {
            throw new IllegalArgumentException("[ERROR] 메뉴는 한 번에 최대 20개 까지만 주문할 수 있습니다. 다시 입력해 주세요.");
        }
    }

    public Map<Menu, Integer> getMenus() {
        return menus;
    }

    public Integer getMenuTypeCount(MenuType type) {
        return menuTypeCount.get(type);
    }
}
