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
}