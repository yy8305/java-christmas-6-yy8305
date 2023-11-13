package christmas.domain;

import christmas.constants.MenuBook;
import java.util.Objects;

public class Menu {
    private final MenuBook menu;

    Menu(String name) {
        this.menu = getMenu(name);
    }

    Menu(MenuBook menu) {
        this.menu = menu;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Menu)) {
            return false;
        }

        MenuBook target = ((Menu) obj).getMenu();
        return Objects.equals(target.getName(), menu.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(menu.getName());
    }
}
