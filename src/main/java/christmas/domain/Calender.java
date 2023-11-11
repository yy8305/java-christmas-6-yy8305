package christmas.domain;

import christmas.constants.DiscountEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Calender {
    private final List<Schedule> schedules = new ArrayList<>();

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    public List<DiscountEvent> getDiscountEventForDate(LocalDate date) {
        Objects.requireNonNull(date);

        List<DiscountEvent> events = new ArrayList<>();
        for (Schedule schedule : schedules) {
            if (schedule.isWithinRange(date)) {
                events.add(schedule.getEvent());
            }
        }

        return events;
    }
}
