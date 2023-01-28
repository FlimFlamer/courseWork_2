package Tasked;

import Tasked.Exeption.IncorrectArgumetntExeption;
import Tasked.Repeatability.*;
import Tasked.Service.TaskService;
import Tasked.Type;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Menu {

    private static final TaskService taskService = new TaskService();
    private static final Pattern DATE_TIME_PATTERN = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}\\:\\d{2}");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("ДД.ММ.ГГГГ ЧЧ:ММ");

    public void startMenu() throws IncorrectArgumetntExeption {
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
        }
    }

    private static void inputTask(Scanner scanner) throws IncorrectArgumetntExeption {
        System.out.println("Введите название задачи: ");
        String title = scanner.next();

        System.out.println("Введите описание задачи: ");
        String description = scanner.next();

        System.out.println("Введите тип задачи: 1 - Личная, 2 - Рабочая.");
        Type type = null;

        int taskTypeChoice = scanner.nextInt();

        if (taskTypeChoice == 1) {
            type = Type.PERSONAL;
        } else if (taskTypeChoice == 2) {
            type = Type.WORK;
        } else {
            System.out.println("Тип задачи введен некоректно.");
            scanner.close();
        }

        System.out.println("Введите дату и время в формате ДД.ММ.ГГГГ ЧЧ:ММ ");

        LocalDateTime taskTime = null;
        if (scanner.hasNext(DATE_TIME_PATTERN)) {
            String dateTime = scanner.next(DATE_TIME_PATTERN);
            taskTime = LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
        }

        if (taskTime == null) {
            System.out.println("Введите дату и время в формате ДД.ММ.ГГГГ ЧЧ:ММ ");
            scanner.close();
        }

        System.out.println("Введите повторяемость задачи. 1 - Однократно, 2 - Каждый день, 3 - Еженедельно, 4 - Ежемесячно, 5 - Ежегодно: ");

        Task task = null;
        if (scanner.hasNextInt()) {
            int repeatability = scanner.nextInt();

            switch (repeatability) {
                case 1:
                    task = new OneTimeTask(title,type, taskTime, description);
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
                    scanner.close();
            }
        }

        taskService.add(task);
        System.out.println("Задача добавленна.");
    }

    private static void printMenu() {
        System.out.println("1. Добавить задачу\n2. Удалить задачу\n3. Получить задачу на указанный день.\n0. Выход.");
    }
}
