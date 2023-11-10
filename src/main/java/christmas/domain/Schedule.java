package christmas.domain;

import christmas.constants.DiscountEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Schedule {
    LocalDate startDate;
    LocalDate endDate;
    DiscountEvent event;
    List<DayOfWeek> excludedDays;

    public static class Builder {
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final DiscountEvent event;
        private List<DayOfWeek> excludedDays;

        public Builder(LocalDate startDate, LocalDate endDate, DiscountEvent event) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.event = event;
        }

        public Builder excludedDays(List<DayOfWeek> excludedDays) {
            this.excludedDays = excludedDays;
            return this;
        }

        public Schedule build() {
            return new Schedule(this);
        }
    }

    private Schedule(Builder builder) {
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.event = builder.event;
        this.excludedDays = builder.excludedDays;
    }

    public static List<Schedule> makeSchedules(Integer year, Integer month, List<Integer> days, DiscountEvent event) {
        List<Schedule> schedules = new ArrayList<>();
        for (Integer day : days) {
            schedules.add(new Schedule.Builder(
                    LocalDate.of(year, month, day),
                    LocalDate.of(year, month, day),
                    event
            ).build());
        }
        return schedules;
    }
}
