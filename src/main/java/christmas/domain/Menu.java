package christmas.domain;

import christmas.constants.MenuBook;
import java.util.Objects;

public class Menu {
    private MenuBook menu;

    Menu(String name) {
        this.menu = getMenu(name);
    }

    private MenuBook getMenu(String menuName) {
        Objects.requireNonNull(menuName);

        for (MenuBook menu : MenuBook.values()) {
            if (menu.getName().equals(menuName)) {
                return menu;
            }
        }

        throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    public MenuBook getMenu() {
        return menu;
    }
}
