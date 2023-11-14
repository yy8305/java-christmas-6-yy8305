package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import christmas.constants.DiscountEvent;
import christmas.constants.MenuBook;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountsTest {
    @DisplayName("저장 했던 할인 이벤트 리스트와 저장 되어 있는 할인 이벤트 리스트가 일치 한다.")
    @Test
    void testSetEvents() {
        List<DiscountEvent> addEvents = List.of(
                DiscountEvent.CHRISTMAS_D_DAY,
                DiscountEvent.GIFT,
                DiscountEvent.SPECIAL
        );

        Discounts discounts = new Discounts(addEvents);

        for (DiscountEvent event : addEvents) {
            assertThat(discounts.getAmountsByEvent()).containsKey(event);
        }
    }

    @DisplayName("할인 이벤트로 null을 전달할 경우 예외가 발생 한다.")
    @Test
    void testSetEventsNullExceptionCheck() {
        assertThatNullPointerException().isThrownBy(() -> {
            new Discounts(null);
        });
    }

    @DisplayName("추가 했던 증정 메뉴와 저장 되어 있는 증정 메뉴가 일치 한다.")
    @Test
    void testAddGiftMenu() {
        MenuBook giftMenu = MenuBook.CHAMPAGNE;
        Discounts discounts = new Discounts(List.of(DiscountEvent.GIFT));

        discounts.addGiftMenu(giftMenu);

        assertThat(discounts.getGiftMenus().getMenus())
                .containsKey(new Menu(giftMenu));
    }

    @DisplayName("증정 메뉴로 null을 전달할 경우 예외가 발생 한다.")
    @Test
    void testAddGiftMenuNullExceptionCheck() {
        Discounts discounts = new Discounts(List.of(DiscountEvent.GIFT));

        assertThatNullPointerException().isThrownBy(() -> {
            discounts.addGiftMenu(null);
        });
    }

    @DisplayName("저장되어 있는 크리스마스 이벤트에 대한 할인금액 0원에 100원을 더한다.")
    @Test
    void testAddAmountByEvent() {
        final Integer addAmount = 100;
        DiscountEvent christmasEvent = DiscountEvent.CHRISTMAS_D_DAY;
        Discounts discounts = new Discounts(List.of(christmasEvent));
        discounts.addAmountByEvent(christmasEvent, addAmount);

        assertThat(discounts.getAmountsByEvent().get(christmasEvent)).isEqualTo(addAmount);
    }

    @DisplayName("이벤트별 할인 금액을 더할때 null을 전달할 경우 예외가 발생한다.")
    @Test
    void testAddAmountByEventHandlesNullException() {
        DiscountEvent christmasEvent = DiscountEvent.CHRISTMAS_D_DAY;
        Discounts discounts = new Discounts(List.of(christmasEvent));

        assertThatNullPointerException().isThrownBy(() -> {
            discounts.addAmountByEvent(christmasEvent, null);
        });
    }
}