package christmas.domain;

import christmas.constants.DiscountEvent;
import christmas.constants.MenuBook;
import christmas.constants.Settings;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Discounts {
    private final Map<DiscountEvent, Integer> amountsByEvent = new HashMap<>();
    private final MenuCart giftMenus = new MenuCart();

    Discounts(List<DiscountEvent> events) {
        Objects.requireNonNull(events);

        setEvents(events);
    }

    private void setEvents(List<DiscountEvent> events) {
        for (DiscountEvent event : events) {
            this.amountsByEvent.put(event, Settings.ZERO.getValue());
        }
    }

    public Map<DiscountEvent, Integer> getAmountsByEvent() {
        return amountsByEvent;
    }

    public void addGiftMenu(MenuBook menu) {
        Objects.requireNonNull(menu);

        giftMenus.addMenu(new Menu(menu), Settings.SINGLE.getValue());
    }

    public MenuCart getGiftMenus() {
        return giftMenus;
    }

    public void addAmountByEvent(DiscountEvent event, Integer amount) {
        Objects.requireNonNull(amount);

        for (DiscountEvent discountEvent : amountsByEvent.keySet()) {
            if (discountEvent.equals(event)) {
                amountsByEvent.merge(discountEvent, amount, Integer::sum);
            }
        }
    }

    public void removeEmptyAmountsByEvent() {
        amountsByEvent.entrySet().removeIf(map -> map.getValue() <= 0);
    }
}
