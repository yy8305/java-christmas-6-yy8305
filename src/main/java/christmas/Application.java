package christmas;

import christmas.domain.EventPlanner;
import christmas.util.InputView;
import christmas.util.OutputView;

public class Application {
    public static void main(String[] args) {
        EventPlanner planner = new EventPlanner(new OutputView(), new InputView());
        planner.call();
    }
}
