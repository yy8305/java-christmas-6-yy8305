package christmas;

import christmas.constants.DiscountEvent;
import christmas.constants.Settings;
import christmas.domain.Calender;
import christmas.domain.Schedule;
import christmas.domain.Schedule.Builder;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class TestDefault {
    protected final Integer eventYear = Settings.EVENT_YEAR.getValue();
    protected final Integer eventMonth = Settings.EVENT_MONTH.getValue();
    protected final List<Integer> giftDays = List.of(3, 10, 17, 24, 25, 31);
    protected final Integer defaultStartDay = 1;
    protected final Integer defaultEndDay = 31;
    protected final Integer christmasDay = 25;

    protected Schedule scheduleBuild(Integer start, Integer end, DiscountEvent event, List<DayOfWeek> days) {
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

    protected void allEventsSetup(Calender calender) {
        List<Schedule> schedules = Schedule.makeSchedules(
                eventYear, eventMonth, giftDays, DiscountEvent.SPECIAL
        );
        schedules.add(scheduleBuild(defaultStartDay, christmasDay, DiscountEvent.CHRISTMAS_D_DAY, null));
        schedules.add(scheduleBuild(defaultStartDay, defaultEndDay, DiscountEvent.WEEK_DAY, List.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY)));
        schedules.add(scheduleBuild(defaultStartDay, defaultEndDay, DiscountEvent.WEEK_END, List.of(
                DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY
        )));
        schedules.add(scheduleBuild(defaultStartDay, defaultEndDay, DiscountEvent.GIFT, null));
        for (Schedule schedule : schedules) {
            calender.addSchedule(schedule);
        }
    }
}
