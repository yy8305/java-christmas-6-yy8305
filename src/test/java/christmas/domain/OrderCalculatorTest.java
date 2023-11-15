package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.TestDefault;
import christmas.constants.MenuBook;
import christmas.constants.Settings;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderCalculatorTest extends TestDefault {
    private OrderCalculator calculator;
    private Calender calender;
    private MenuCart orderMenus;
    private Discounts discounts;

    private void setCalender() {
        calender = new Calender();
        allEventsSetup(calender);
    }

    private void setOrderMenus() {
        orderMenus = new MenuCart();
        Integer minOrderCount = Settings.MIN_ORDER_COUNT.getValue();
        orderMenus.addMenu(new Menu(MenuBook.T_BONE_STEAK), minOrderCount);
        orderMenus.addMenu(new Menu(MenuBook.BBQ_RIBS), minOrderCount);
        orderMenus.addMenu(new Menu(MenuBook.CHOCOLATE_CAKE), minOrderCount + minOrderCount);
        orderMenus.addMenu(new Menu(MenuBook.ZERO_COLA), minOrderCount);
    }

    @BeforeEach
    void testInit() {
        setCalender();
    }

    @DisplayName("이벤트별 할인 금액 계산시 예상 했던 금액과 일치 한다.")
    @Test
    void testSetDiscountsAmountByEvent() {
        setOrderMenus();
        final LocalDate reservationDate = LocalDate.of(eventYear, eventMonth, 3);
        final List<Integer> expectedDiscountAmounts = List.of(1_200, 4_046, 1_000, 25_000);
        calender.setReservationDate(reservationDate);
        discounts = new Discounts(calender.getDiscountEventForDate(reservationDate));
        calculator = new OrderCalculator(calender, orderMenus, discounts);

        calculator.setDiscountsAmountByEvent();

        for (Integer amount : expectedDiscountAmounts) {
            assertThat(discounts.getAmountsByEvent()).containsValue(amount);
        }
    }

    @DisplayName("적용된 할인 이벤트가 없을 경우 discounts에 저장된 이벤트는 없다.")
    @Test
    void testSetDiscountsAmountWithoutEvent() {
        orderMenus = new MenuCart();
        final LocalDate reservationDate = LocalDate.of(eventYear, eventMonth, 26);
        calender.setReservationDate(reservationDate);
        discounts = new Discounts(calender.getDiscountEventForDate(reservationDate));
        calculator = new OrderCalculator(calender, orderMenus, discounts);

        calculator.setDiscountsAmountByEvent();

        assertThat(discounts.getAmountsByEvent().size()).isEqualTo(Settings.ZERO.getValue());
    }
}