package christmas.domain;

import christmas.constants.DiscountEvent;
import christmas.constants.Settings;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Discounts {
    private final Map<DiscountEvent, Integer> amounts = new HashMap<>();

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
}
