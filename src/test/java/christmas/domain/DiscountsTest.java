package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.constants.DiscountEvent;
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

        assertThat(discounts.getEvents().keySet().toArray()).isEqualTo(addEvents.toArray());
    }
}