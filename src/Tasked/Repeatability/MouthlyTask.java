package Tasked.Repeatability;

import Tasked.Task;
import Tasked.Type;

import java.time.LocalDateTime;

public class MouthlyTask extends Task {
    public MouthlyTask(String title, Type type, LocalDateTime dateTime, String description) {
        super(title, type, dateTime, description);
    }

    @Override
    public LocalDateTime getTaskNextTime(LocalDateTime dateTime) {
        return dateTime.plusMonths(1);
    }
}
