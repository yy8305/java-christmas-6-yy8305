package christmas.domain;

import java.util.ArrayList;
import java.util.List;

public class Calender {
    private final List<Schedule> schedules = new ArrayList<>();

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }
}
