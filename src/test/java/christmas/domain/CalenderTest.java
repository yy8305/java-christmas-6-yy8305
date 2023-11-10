package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import christmas.constants.DiscountEvent;
import christmas.constants.Settings;
import christmas.domain.Schedule.Builder;
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

    private Schedule scheduleBuild(Integer start, Integer end, DiscountEvent event, List<DayOfWeek> days) {
        Builder schedule = new Schedule.Builder(
                LocalDate.of(eventYear, eventMonth, start),
                LocalDate.of(eventYear, eventMonth, end),
                event
        );

        if (days != null) {
            schedule.excludedDays(days);
        }

        return schedule.build();
    }

    private void allEventsSetup() {
        List<Schedule> schedules = Schedule.makeSchedules(
                eventYear, eventMonth, List.of(3, 10, 17, 24, 25, 31), DiscountEvent.SPECIAL
        );
        schedules.add(scheduleBuild(1, 25, DiscountEvent.CRISMAS_D_DAY, null));
        schedules.add(scheduleBuild(1, 31, DiscountEvent.WEEK_DAY, List.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY)));
        schedules.add(scheduleBuild(1, 31, DiscountEvent.WEEK_END, List.of(
                DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY
        )));
        schedules.add(scheduleBuild(1, 31, DiscountEvent.GIFT, null));
        for (Schedule schedule : schedules) {
            calender.addSchedule(schedule);
        }
    }

    @BeforeEach
    void testInit() {
        calender = new Calender();
    }

    @DisplayName("크리스마스 디데이 할인 이벤트 날짜를 달력에 정상적으로 추가된다.")
    @Test
    void testAddChrismasDdayEvent() {
        Schedule schedule = scheduleBuild(1, 25, DiscountEvent.CRISMAS_D_DAY, null);

        assertThatNoException().isThrownBy(() -> {
            calender.addSchedule(schedule);
        });
    }

    @DisplayName("평일 할인 이벤트 날짜를 달력에 정상적으로 추가된다.")
    @Test
    void testAddWeekDayEvent() {
        Schedule schedule = scheduleBuild(1, 31, DiscountEvent.WEEK_DAY, List.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY));

        assertThatNoException().isThrownBy(() -> {
            calender.addSchedule(schedule);
        });
    }

    @DisplayName("주말 할인 이벤트 날짜를 달력에 정상적으로 추가된다.")
    @Test
    void testAddWeekEndEvent() {
        Schedule schedule = scheduleBuild(1, 31, DiscountEvent.WEEK_END, List.of(
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
                eventYear, eventMonth, List.of(3, 10, 17, 24, 25, 31), DiscountEvent.SPECIAL
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
        Schedule schedule = scheduleBuild(1, 31, DiscountEvent.GIFT, null);

        assertThatNoException().isThrownBy(() -> {
            calender.addSchedule(schedule);
        });
    }

    @DisplayName("12월 3일에는 디데이, 평일, 스페셜, 증정 이벤트가 있다.")
    @Test
    void testGetDiscountEventForDate() {
        allEventsSetup();

        List<DiscountEvent> events = calender.getDiscountEventForDate(LocalDate.of(eventYear, eventMonth, 3));
        assertThat(events).contains(
                DiscountEvent.CRISMAS_D_DAY,
                DiscountEvent.WEEK_DAY,
                DiscountEvent.SPECIAL,
                DiscountEvent.GIFT
        );
    }
}