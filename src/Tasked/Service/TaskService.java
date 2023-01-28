package Tasked.Service;


import Tasked.Exeption.TaskNotFoundExeption;
import Tasked.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TaskService {
    private final Map<Integer, Task> taskMap = new HashMap<>();

    public void add(Task task){
        this.taskMap.put(task.getId(), task);
    }
    public void remove(Integer taskId) throws TaskNotFoundExeption {
        if (this.taskMap.containsKey(taskId)){
            this.taskMap.remove(taskId);
        } else {
            throw new TaskNotFoundExeption(taskId);
        }
    }
    public Collection<Task> getAllByDate (LocalDate date) {
        Collection<Task> taskByDay = new ArrayList<>();

        for (Task task : taskMap.values()) {
            LocalDateTime taskTime = task.getDateTime();
            LocalDateTime taskNextTime = task.getTaskNextTime(taskTime);

            if(taskNextTime == null || taskTime.toLocalDate().equals(date)){
                taskByDay.add(task);
                continue;
            }
            do {
                if(taskNextTime.toLocalDate().equals(date)){
                    taskByDay.add(task);
                    break;
                }
                taskNextTime = task.getTaskNextTime(taskNextTime);
            } while (taskNextTime.toLocalDate().isBefore(date));
        }
        return taskByDay;
    }
}
