package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.TestDefault;
import christmas.constants.MenuBook;
import christmas.constants.Settings;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

    @Nested
    @DisplayName("주문 & 할인이 있을 경우")
    class InCaseOfDiscount {
        @BeforeEach
        void testInit() {
            setCalender();
            setOrderMenus();
            final LocalDate reservationDate = LocalDate.of(eventYear, eventMonth, 3);
            calender.setReservationDate(reservationDate);
            discounts = new Discounts(calender.getDiscountEventForDate(reservationDate));
            calculator = new OrderCalculator(calender, orderMenus, discounts);
            calculator.setDiscountsAmountByEvent();
        }

        @DisplayName("이벤트별 할인 금액 계산시 예상 했던 금액과 일치 한다.")
        @Test
        void testSetDiscountsAmountByEvent() {
            final List<Integer> expectedDiscountAmounts = List.of(1_200, 4_046, 1_000, 25_000);

            for (Integer amount : expectedDiscountAmounts) {
                assertThat(discounts.getAmountsByEvent()).containsValue(amount);
            }
        }

        @DisplayName("총 할인 금액은 31,246원 이다. (크리스마스 디데이 할인: -1,200원, 평일 할인: -4,046원, 특별 할인: -1,000원, 증정 이벤트: -25,000원)")
        @Test
        void testGetTotalDiscountsAmount() {
            final Integer expectedTotalDiscountAmount = 31_246;

            Integer actual = calculator.getTotalDiscountsAmount();

            assertThat(actual).isEqualTo(expectedTotalDiscountAmount);
        }

        @DisplayName("총 결제 금액은 135,754원 이다. (총 주문 금액: 142,000,할인 금액 : 6,246원)")
        @Test
        void testGetTotalPaymentAmount() {
            final Integer expectedTotalPaymentAmount = 135_754;

            Integer actual = calculator.getTotalPaymentAmount();

            assertThat(actual).isEqualTo(expectedTotalPaymentAmount);
        }
    }

    @Nested
    @DisplayName("주문 & 할인이 없을 경우")
    class InCaseOfNoDiscount {
        @BeforeEach
        void testInit() {
            setCalender();
            orderMenus = new MenuCart();
            final LocalDate reservationDate = LocalDate.of(eventYear, eventMonth, 26);
            calender.setReservationDate(reservationDate);
            discounts = new Discounts(calender.getDiscountEventForDate(reservationDate));
            calculator = new OrderCalculator(calender, orderMenus, discounts);
            calculator.setDiscountsAmountByEvent();
        }

        @DisplayName("적용된 할인 이벤트가 없을 경우 discounts에 저장된 이벤트는 없다.")
        @Test
        void testSetDiscountsAmountWithoutEvent() {
            assertThat(discounts.getAmountsByEvent().size()).isEqualTo(Settings.ZERO.getValue());
        }

        @DisplayName("총 할인 금액은 0원 이다.")
        @Test
        void testGetTotalDiscountsAmount() {
            final Integer expectedTotalDiscountAmount = 0;

            Integer actual = calculator.getTotalDiscountsAmount();

            assertThat(actual).isEqualTo(expectedTotalDiscountAmount);
        }

        @DisplayName("총 결제 금액은 0원 이다.")
        @Test
        void testGetTotalPaymentAmount() {
            final Integer expectedTotalPaymentAmount = 0;

            Integer actual = calculator.getTotalPaymentAmount();

            assertThat(actual).isEqualTo(expectedTotalPaymentAmount);
        }
    }
}