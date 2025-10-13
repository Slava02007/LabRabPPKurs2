import java.util.*;

public class Main {
    private static String currentLanguage = "en";
    private static Scanner scanner = new Scanner(System.in);

    // Сообщения на разных языках
    private static Map<String, Map<String, String>> messages = Map.of(
            "en", Map.of(
                    "welcome", "=== Welcome ===",
                    "hello", "Hello World!",
                    "change_lang", "Change language",
                    "exit", "Exit",
                    "choose", "Choose option",
                    "goodbye", "Goodbye!"
            ),
            "ru", Map.of(
                    "welcome", "=== Добро пожаловать ===",
                    "hello", "Привет Мир!",
                    "change_lang", "Сменить язык",
                    "exit", "Выход",
                    "choose", "Выберите опцию",
                    "goodbye", "До свидания!"
            ),
            "es", Map.of(
                    "welcome", "=== Bienvenido ===",
                    "hello", "¡Hola Mundo!",
                    "change_lang", "Cambiar idioma",
                    "exit", "Salir",
                    "choose", "Elegir opción",
                    "goodbye", "¡Adiós!"
            )
    );

    public static void main(String[] args) {
        while (true) {
            showMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> sayHello();
                case "2" -> changeLanguage();
                case "3" -> {
                    System.out.println(getMessage("goodbye"));
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n" + getMessage("welcome"));
        System.out.println("1. " + getMessage("hello"));
        System.out.println("2. " + getMessage("change_lang"));
        System.out.println("3. " + getMessage("exit"));
        System.out.print(getMessage("choose") + ": ");
    }

    private static void sayHello() {
        System.out.println("\n" + getMessage("hello"));
    }

    private static void changeLanguage() {
        System.out.println("\n=== Select Language ===");
        System.out.println("1. English");
        System.out.println("2. Русский");
        System.out.println("3. Español");
        System.out.print("Choose: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> currentLanguage = "en";
            case "2" -> currentLanguage = "ru";
            case "3" -> currentLanguage = "es";
        }
        System.out.println("Language changed!");
    }

    private static String getMessage(String key) {
        return messages.get(currentLanguage).get(key);
    }
}