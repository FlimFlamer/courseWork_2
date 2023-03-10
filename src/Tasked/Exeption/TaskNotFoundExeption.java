package Tasked.Exeption;


public class TaskNotFoundExeption extends Exception {
    public TaskNotFoundExeption(Integer taskId) {
        super("Задача с id = " + taskId + " не найдена!");
    }
}
