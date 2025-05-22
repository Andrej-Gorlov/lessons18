package helpers;

public class ResultOutput {
    private static String nameMethod = "nameDefault";

    public static void printTestStart(String nameMethod){
        ResultOutput.nameMethod = nameMethod;
        System.out.println("-------------------");
        System.out.println("Отчет тестирования метода " + nameMethod + ":");
    }

    public static void log(String message) {
        System.out.println(message);
    }

    public static void printTestEnd(String nameMethod){
        System.out.println("Tест метода: " + nameMethod + " завершён.");
        System.out.println("-------------------");
    }
}
