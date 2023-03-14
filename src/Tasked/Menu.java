package Tasked;

import Tasked.Exeption.IncorrectArgumetntExeption;
import Tasked.Repeatability.*;
import Tasked.Service.TaskService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Menu {

    private static final TaskService taskService = new TaskService();
    private static final Pattern DATE_TIME_PATTERN = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}\\:\\d{2}");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public void startMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                printMenu();
                System.out.println("Выберете пункт меню: ");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            inputTask(scanner);
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка!");
                }
            }
        } catch (IncorrectArgumetntExeption e) {
            throw new RuntimeException(e);
        }
    }


    private static void inputTask(Scanner scanner) throws IncorrectArgumetntExeption {

        String title = inputTaskTittle(scanner);
        String description = inputTaskDescription(scanner);
        Type type = inputTaskType(scanner);
        LocalDateTime taskTime = inputTaskTime(scanner);
        int repeatability = inputRepeatability(scanner);
        createTask(title, type, taskTime, description, repeatability);

    }

    private static void createTask(String title, Type type, LocalDateTime taskTime, String description,
                                   int repeatability) throws IncorrectArgumetntExeption {
        Task task = null;
        switch (repeatability) {
            case 1:
                task = new OneTimeTask(title, type, taskTime, description);
                break;
            case 2:
                task = new DailyTask(title, type, taskTime, description);
                break;
            case 3:
                task = new WeeklyTask(title, type, taskTime, description);
                break;
            case 4:
                task = new MouthlyTask(title, type, taskTime, description);
                break;
            case 5:
                task = new YearlyTask(title, type, taskTime, description);
                break;
            default:
                System.out.println("Повторяемость задачи введена некоректно.");
        }

        if (task != null) {
            taskService.add(task);
            System.out.println("Задача добавленна.");
        } else {
            System.out.println("Введены некоректные данные по задаче.");
        }
    }

    private static String inputTaskTittle(Scanner scanner) {
        System.out.println("Введите название задачи: ");
        String title = scanner.next();

        if (title.isBlank()) {
            System.out.println("Необходимо ввести название задачи!");
            scanner.close();
        }

        return title;
    }

    private static String inputTaskDescription(Scanner scanner) {
        System.out.println("Введите описание задачи: ");
        String description = scanner.next();

        if (description.isBlank()) {
            System.out.println("Необходимо ввести описание задачи!");
            scanner.close();
        }

        return description;
    }

    private static Type inputTaskType(Scanner scanner) {
        System.out.println("Введите тип задачи: 1 - Личная, 2 - Рабочая.");
        Type type = null;

        int taskTypeChoice = scanner.nextInt();


        switch (taskTypeChoice) {
            case 1:
                type = Type.PERSONAL;
                break;
            case 2:
                type = Type.WORK;
                break;
            default:
                System.out.println("Тип задачи введен некоректно.");
                scanner.close();
        }

        return type;
    }

    private static LocalDateTime inputTaskTime(Scanner scanner) {
        scanner.useDelimiter("\n");
        System.out.println("Введите дату и время в формате dd.MM.yyyy HH:mm");

        LocalDateTime taskTime = null;
        if (scanner.hasNext(DATE_TIME_PATTERN)) {
            String dateTime = scanner.next(DATE_TIME_PATTERN);
            return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
        } else {
            System.out.println("Введите дату и время в формате dd.MM.yyyy HH:mm");
            scanner.close();
            return null;
        }
    }

    private static int inputRepeatability(Scanner scanner) {
        System.out.println("Выберете повторяемость задачи. 1 - Однократно, 2 - Каждый день, 3 - Еженедельно, 4 - Ежемесячно, 5 - Ежегодно: ");

        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else {
            System.out.println("Выберете повторяемость задачи!");
            scanner.close();
        }
        return -1;
    }

    private static void removeTask(Scanner scanner){
        System.out.println("Введите id задачи для удаления");
        int id = scanner.nextInt();

        taskService.remove(id);
    }

    private static void printMenu() {
        System.out.println("1. Добавить задачу\n2. Удалить задачу\n3. Получить задачу на указанный день.\n0. Выход.");
    }
}
