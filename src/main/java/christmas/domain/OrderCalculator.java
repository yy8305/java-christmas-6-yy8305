package christmas.domain;

import christmas.constants.DiscountEvent;
import christmas.constants.EventBadge;
import christmas.constants.MenuBook;
import christmas.constants.MenuType;
import christmas.constants.Settings;
import java.util.Map;
import java.util.Objects;

public class OrderCalculator {
    private final Calender calender;
    private final MenuCart orderMenus;
    private final Discounts discounts;
    private Integer totalDiscountsAmount;

    OrderCalculator(Calender calender, MenuCart orderMenus, Discounts discounts) {
        Objects.requireNonNull(calender);
        Objects.requireNonNull(orderMenus);
        Objects.requireNonNull(discounts);

        this.calender = calender;
        this.orderMenus = orderMenus;
        this.discounts = discounts;
    }

    public void setDiscountsAmountByEvent() {
        for (DiscountEvent event : discounts.getAmountsByEvent().keySet()) {
            if(event.equals(DiscountEvent.CHRISTMAS_D_DAY)) setChristmasEventDiscount(event);
            if(event.equals(DiscountEvent.WEEK_DAY)) setWeekDayEventDiscount(event);
            if(event.equals(DiscountEvent.WEEK_END)) setWeekEndEventDiscount(event);
            if(event.equals(DiscountEvent.SPECIAL)) setSpecialEventDiscount(event);
            if(event.equals(DiscountEvent.GIFT)) setGiftEventDiscount(event);
        }
        discounts.removeEmptyAmountsByEvent();
    }

    private void setChristmasEventDiscount(DiscountEvent event) {
        Integer daysUntilEvent = calender.getDaysSinceEventStart(event);
        Integer discountAmount = event.getDefaultDiscountAmount();
        discountAmount += daysUntilEvent * Settings.CHRISTMAS_ADDITIONAL_AMOUNT.getValue();
        discounts.addAmountByEvent(event, discountAmount);
    }

    private void setWeekDayEventDiscount(DiscountEvent event) {
        Integer discountAmount = Settings.ZERO.getValue();
        discountAmount += event.getDefaultDiscountAmount() * orderMenus.getMenuTypeCount(MenuType.DESSERT);
        discounts.addAmountByEvent(event, discountAmount);
    }

    private void setWeekEndEventDiscount(DiscountEvent event) {
        Integer discountAmount = Settings.ZERO.getValue();
        discountAmount += event.getDefaultDiscountAmount() * orderMenus.getMenuTypeCount(MenuType.MAIN);
        discounts.addAmountByEvent(event, discountAmount);
    }

    private void setSpecialEventDiscount(DiscountEvent event) {
        Integer discountAmount = event.getDefaultDiscountAmount();
        discounts.addAmountByEvent(event, discountAmount);
    }

    private void setGiftEventDiscount(DiscountEvent event) {
        if (orderMenus.getTotalOrderAmount() >= Settings.GIFT_CONDITION.getValue()) {
            MenuBook giftMenu = MenuBook.CHAMPAGNE;
            discounts.addGiftMenu(giftMenu);
            discounts.addAmountByEvent(event, giftMenu.getAmount());
        }
    }

    public Integer getTotalDiscountsAmount() {
        Integer amount = 0;
        for (Map.Entry<DiscountEvent, Integer> amountsByEvent : discounts.getAmountsByEvent().entrySet()) {
            amount += amountsByEvent.getValue();
        }
        totalDiscountsAmount = amount;

        return amount;
    }

    public Integer getTotalPaymentAmount() {
        Integer amount = 0;
        if (totalDiscountsAmount == null) {
            getTotalDiscountsAmount();
        }
        amount += orderMenus.getTotalOrderAmount();
        amount -= totalDiscountsAmount;

        MenuCart giftMenus = discounts.getGiftMenus();
        if (giftMenus.getMenus().size() > Settings.ZERO.getValue()) {
            amount += giftMenus.getTotalOrderAmount();
        }

        return amount;
    }

    public EventBadge getEventBadge() {
        if (totalDiscountsAmount == null) {
            getTotalDiscountsAmount();
        }
        Integer totalDiscount = totalDiscountsAmount;

        for (EventBadge badge : EventBadge.values()) {
            if (badge.getThreshold() <= totalDiscount) {
                return badge;
            }
        }

        return EventBadge.EMPTY;
    }
}
