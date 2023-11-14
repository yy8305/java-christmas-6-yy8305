package christmas.domain;

import christmas.constants.DiscountEvent;
import christmas.constants.MenuBook;
import christmas.constants.Settings;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Discounts {
    private final Map<DiscountEvent, Integer> amounts = new HashMap<>();
    private final MenuCart giftMenus = new MenuCart();

    Discounts(List<DiscountEvent> events) {
        Objects.requireNonNull(events);

        setEvents(events);
    }

    private void setEvents(List<DiscountEvent> events) {
        for (DiscountEvent event : events) {
            this.amounts.put(event, Settings.ZERO_PRICE.getValue());
        }
    }

    public Map<DiscountEvent, Integer> getEvents() {
        return amounts;
    }

    public void addGiftMenu(MenuBook menu) {
        Objects.requireNonNull(menu);

        giftMenus.addMenu(new Menu(menu), Settings.SINGLE.getValue());
    }

    public MenuCart getGiftMenus() {
        return giftMenus;
    }
}
