package Tasked.Exeption;

public class IncorrectArgumetntExeption extends Exception {
    private final String argument;

    public IncorrectArgumetntExeption(String argument) {
        this.argument = argument;
    }

    @Override
    public String getMessage() {
        return "Параметр " + argument + " задан некорректно.";
    }
}
