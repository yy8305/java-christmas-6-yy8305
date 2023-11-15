package christmas.domain;

import christmas.constants.DiscountEvent;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Calender {
    private final List<Schedule> schedules = new ArrayList<>();
    private LocalDate reservationDate;

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    public void addSchedule(List<Schedule> schedules) {
        for (Schedule schedule : schedules) {
            this.schedules.add(schedule);
        }
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

    public void setReservationDate(LocalDate reservationDate) {
        Objects.requireNonNull(reservationDate);

        this.reservationDate = reservationDate;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public Integer getDaysSinceEventStart(DiscountEvent event) {
        Objects.requireNonNull(reservationDate);

        Integer daysUntilEvent = 0;
        for (Schedule schedule : schedules) {
            if (schedule.getEvent().equals(event) && schedule.isWithinRange(reservationDate)) {
                daysUntilEvent = (int) schedule.getStartDate().until(reservationDate, ChronoUnit.DAYS);
            }
        }

        return daysUntilEvent;
    }
}
