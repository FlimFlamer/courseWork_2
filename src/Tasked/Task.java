package Tasked;

import Tasked.Exeption.IncorrectArgumetntExeption;
import Tasked.Repeatability.Repeatability;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Task implements Repeatability {
    private String title;
    private final int id;
    private final LocalDateTime dateTime;
    private String description;
    private Type type;

    private static int idGenerator = 0;


    public Task(String title,
                Type type,
                LocalDateTime dateTime,
                String description) {
        this.title = title;
        this.dateTime = dateTime;
        this.description = description;
        this.id = idGenerator;
        idGenerator += 1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws IncorrectArgumetntExeption {
        if (title != null && !title.isEmpty()){
        this.title = title;}
        else {
            throw new IncorrectArgumetntExeption("заголовок задачи");
        }
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws IncorrectArgumetntExeption {
        if (description != null && !description.isEmpty()){
        this.description = description;}
    else {
        throw new IncorrectArgumetntExeption("описание задачи");
    }
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) throws IncorrectArgumetntExeption {
        if (type != null ){
        this.type = type;}
    else {
        throw new IncorrectArgumetntExeption("тип задачи");
    }
    }


    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && title.equals(task.title) && dateTime.equals(task.dateTime) && description.equals(task.description) && type.equals(task.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, id, dateTime, description, type);
    }
}
