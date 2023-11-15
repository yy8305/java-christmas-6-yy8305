package christmas.domain;

import christmas.constants.DiscountEvent;
import christmas.constants.Settings;
import christmas.util.InputView;
import christmas.util.OutputView;
import java.time.DayOfWeek;
import java.util.List;

public class EventPlanner {
    private OutputView output;
    private InputView input;
    private Calender calender;

    public EventPlanner(OutputView output, InputView input) {
        this.output = output;
        this.input = input;
    }

    public void call() {
        initSettings();
    }

    private void initSettings() {
        allEventsScheduleSettings();
    }

    private void allEventsScheduleSettings() {
        calender = new Calender();
        final Integer defaultStartDay = 1;
        final Integer defaultEndDay = 31;
        final Integer christmasDay = 25;

        addSpecialDaysSchedule();
        addChristmasEventSchedule(defaultStartDay, christmasDay);
        addWeekDayEventSchedule(defaultStartDay, defaultEndDay);
        addWeekEndEventSchedule(defaultStartDay, defaultEndDay);
        addGiftEventSchedule(defaultStartDay, defaultEndDay);
    }

    private void addSpecialDaysSchedule() {
        final List<Integer> specialDays = List.of(3, 10, 17, 24, 25, 31);
        calender.addSchedule(Schedule.makeSchedules(
                Settings.EVENT_YEAR.getValue(), Settings.EVENT_MONTH.getValue(), specialDays, DiscountEvent.SPECIAL
        ));
    }

    private void addChristmasEventSchedule(Integer startDay, Integer endDay) {
        calender.addSchedule(Schedule.eventScheduleBuild(startDay, endDay, DiscountEvent.CHRISTMAS_D_DAY, null));
    }

    private void addWeekDayEventSchedule(Integer startDay, Integer endDay) {
        calender.addSchedule(
                Schedule.eventScheduleBuild(
                        startDay, endDay, DiscountEvent.WEEK_DAY, List.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY)
                )
        );
    }

    private void addWeekEndEventSchedule(Integer startDay, Integer endDay) {
        calender.addSchedule(
                Schedule.eventScheduleBuild(startDay, endDay, DiscountEvent.WEEK_END, List.of(
                        DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY
                ))
        );
    }

    private void addGiftEventSchedule(Integer startDay, Integer endDay) {
        calender.addSchedule(Schedule.eventScheduleBuild(startDay, endDay, DiscountEvent.GIFT, null));
    }
}
