package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import christmas.TestDefault;
import christmas.constants.DiscountEvent;
import christmas.constants.Settings;
import christmas.domain.Schedule.Builder;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalenderTest extends TestDefault {
    private Calender calender;

    @BeforeEach
    void testInit() {
        calender = new Calender();
    }

    @DisplayName("크리스마스 디데이 할인 이벤트 날짜를 달력에 정상적으로 추가된다.")
    @Test
    void testAddChrismasDdayEvent() {
        Schedule schedule = scheduleBuild(defaultStartDay, christmasDay, DiscountEvent.CHRISTMAS_D_DAY, null);

        assertThatNoException().isThrownBy(() -> {
            calender.addSchedule(schedule);
        });
    }

    @DisplayName("평일 할인 이벤트 날짜를 달력에 정상적으로 추가된다.")
    @Test
    void testAddWeekDayEvent() {
        Schedule schedule = scheduleBuild(defaultStartDay, defaultEndDay, DiscountEvent.WEEK_DAY,
                List.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY));

        assertThatNoException().isThrownBy(() -> {
            calender.addSchedule(schedule);
        });
    }

    @DisplayName("주말 할인 이벤트 날짜를 달력에 정상적으로 추가된다.")
    @Test
    void testAddWeekEndEvent() {
        Schedule schedule = scheduleBuild(defaultStartDay, defaultEndDay, DiscountEvent.WEEK_END, List.of(
                DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY
        ));

        assertThatNoException().isThrownBy(() -> {
            calender.addSchedule(schedule);
        });
    }

    @DisplayName("특별 할인 이벤트 날짜들를 달력에 정상적으로 추가된다.")
    @Test
    void testAddSpecialEvents() {
        List<Schedule> schedules = Schedule.makeSchedules(
                eventYear, eventMonth, giftDays, DiscountEvent.SPECIAL
        );

        assertThatNoException().isThrownBy(() -> {
            for (Schedule schedule : schedules) {
                calender.addSchedule(schedule);
            }
        });
    }

    @DisplayName("증정 이벤트 날짜를 달력에 정상적으로 추가된다.")
    @Test
    void testAddGiftEvent() {
        Schedule schedule = scheduleBuild(defaultStartDay, defaultEndDay, DiscountEvent.GIFT, null);

        assertThatNoException().isThrownBy(() -> {
            calender.addSchedule(schedule);
        });
    }

    @DisplayName("달력에 이벤트 등록시 null을 전달할 경우 예외가 발생한다.")
    @Test
    void testAddEventWithNull() {
        assertThatNullPointerException().isThrownBy(() -> {
            new Schedule.Builder(null, null, null).build();
        });
    }

    @DisplayName("12월 3일에는 디데이, 평일, 스페셜, 증정 이벤트가 있다.")
    @Test
    void testGetDiscountEventForDate() {
        allEventsSetup(calender);

        List<DiscountEvent> events = calender.getDiscountEventForDate(
                LocalDate.of(eventYear, eventMonth, giftDays.get(0)));
        assertThat(events).contains(
                DiscountEvent.CHRISTMAS_D_DAY,
                DiscountEvent.WEEK_DAY,
                DiscountEvent.SPECIAL,
                DiscountEvent.GIFT
        );
    }

    @DisplayName("달력에서 날짜 확인시 null을 전달할 경우 예외가 발생한다.")
    @Test
    void testGetDiscountEventForDateWithNull() {
        assertThatNullPointerException().isThrownBy(() -> {
            calender.getDiscountEventForDate(null);
        });
    }
}