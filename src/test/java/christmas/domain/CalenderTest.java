package christmas.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;

import christmas.constants.DiscountEvent;
import christmas.constants.Settings;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalenderTest {
    private Calender calender;
    private final Integer eventYear = Settings.EVENT_YEAR.getValue();
    private final Integer eventMonth = Settings.EVENT_MONTH.getValue();

    @BeforeEach
    void testInit() {
        calender = new Calender();
    }

    @DisplayName("크리스마스 디데이 할인 이벤트 날짜를 달력에 추가한다.")
    @Test
    void testAddChrismasDdayEvent() {
        Schedule schedule = new Schedule.Builder(
                LocalDate.of(eventYear, eventMonth, 1),
                LocalDate.of(eventYear, eventMonth, 25),
                DiscountEvent.CrismasDday
        ).build();

        assertThatNoException().isThrownBy(() -> {
            calender.addSchedule(schedule);
        });
    }

    @DisplayName("평일 할인 이벤트 날짜를 달력에 추가한다.")
    @Test
    void testAddWeekDayEvent() {
        Schedule schedule = new Schedule.Builder(
                LocalDate.of(eventYear, eventMonth, 1),
                LocalDate.of(eventYear, eventMonth, 31),
                DiscountEvent.WeekDay
        ).excludedDays(List.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY)).build();

        assertThatNoException().isThrownBy(() -> {
            calender.addSchedule(schedule);
        });
    }

    @DisplayName("주말 할인 이벤트 날짜를 달력에 추가한다.")
    @Test
    void testAddWeekEndEvent() {
        Schedule schedule = new Schedule.Builder(
                LocalDate.of(eventYear, eventMonth, 1),
                LocalDate.of(eventYear, eventMonth, 31),
                DiscountEvent.WeekEnd
        ).excludedDays(
                List.of(
                        DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
                        DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY
                )
        ).build();

        assertThatNoException().isThrownBy(() -> {
            calender.addSchedule(schedule);
        });
    }

    @DisplayName("특별 할인 이벤트 날짜들를 달력에 추가한다.")
    @Test
    void testAddSpecialEvents() {
        List<Schedule> schedules = Schedule.makeSchedules(
                eventYear, eventMonth, List.of(3, 10, 17, 24, 25, 31), DiscountEvent.Special
        );

        assertThatNoException().isThrownBy(() -> {
            for (Schedule schedule : schedules) {
                calender.addSchedule(schedule);
            }
        });
    }

    @DisplayName("증정 이벤트 날짜를 달력에 추가한다.")
    @Test
    void testAddGiftEvent() {
        Schedule schedule = new Schedule.Builder(
                LocalDate.of(eventYear, eventMonth, 1),
                LocalDate.of(eventYear, eventMonth, 31),
                DiscountEvent.Gift
        ).build();

        assertThatNoException().isThrownBy(() -> {
            calender.addSchedule(schedule);
        });
    }
}