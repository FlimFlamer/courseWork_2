package Tasked.Repeatability;


import java.time.LocalDateTime;

public interface Repeatability {
    LocalDateTime getTaskNextTime(LocalDateTime dateTime);
}
