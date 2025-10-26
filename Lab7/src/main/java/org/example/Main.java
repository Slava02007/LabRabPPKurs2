package org.example;
import java.util.*;



abstract class Drink {
    protected String description = "Неизвестный напиток";
    protected double sizeMultiplier = 1.0;

    public String getDescription() {
        return description;
    }

    public void setSizeMultiplier(double multiplier) {
        this.sizeMultiplier = multiplier;
    }

    public abstract double baseCost();

    public double cost() {
        return baseCost() * sizeMultiplier;
    }
}


class Latte extends Drink {
    public Latte() {
        description = "Латте";
    }

    @Override
    public double baseCost() {
        return 5.0;
    }
}

class Cappuccino extends Drink {
    public Cappuccino() {
        description = "Капучино";
    }

    @Override
    public double baseCost() {
        return 4.5;
    }
}

class Americano extends Drink {
    public Americano() {
        description = "Американо";
    }

    @Override
    public double baseCost() {
        return 3.0;
    }
}

class Espresso extends Drink {
    public Espresso() {
        description = "Эспрессо";
    }

    @Override
    public double baseCost() {
        return 2.5;
    }
}


class BlackTea extends Drink {
    public BlackTea() {
        description = "Чёрный чай";
    }

    @Override
    public double baseCost() {
        return 2.0;
    }
}

class GreenTea extends Drink {
    public GreenTea() {
        description = "Зелёный чай";
    }

    @Override
    public double baseCost() {
        return 2.5;
    }
}

class FruitTea extends Drink {
    public FruitTea() {
        description = "Фруктовый чай";
    }

    @Override
    public double baseCost() {
        return 1.5;
    }
}


abstract class DrinkDecorator extends Drink {
    public abstract String getDescription();
}


class Milk extends DrinkDecorator {
    private final Drink drink;
    private final int quantity;

    public Milk(Drink drink, int quantity) {
        this.drink = drink;
        this.quantity = quantity;
    }

    @Override
    public String getDescription() {
        return drink.getDescription() + ", молоко x" + quantity;
    }

    @Override
    public double baseCost() {
        return drink.cost() + 1.0 * quantity;
    }
}

class Caramel extends DrinkDecorator {
    private final Drink drink;
    private final int quantity;

    public Caramel(Drink drink, int quantity) {
        this.drink = drink;
        this.quantity = quantity;
    }

    @Override
    public String getDescription() {
        return drink.getDescription() + ", карамель x" + quantity;
    }

    @Override
    public double baseCost() {
        return drink.cost() + 2.0 * quantity;
    }
}

class WhippedCream extends DrinkDecorator {
    private final Drink drink;
    private final int quantity;

    public WhippedCream(Drink drink, int quantity) {
        this.drink = drink;
        this.quantity = quantity;
    }

    @Override
    public String getDescription() {
        return drink.getDescription() + ", взбитые сливки x" + quantity;
    }

    @Override
    public double baseCost() {
        return drink.cost() + 2.5 * quantity;
    }
}


class DrinkBuilder {
    private Drink drink;
    private String sizeLabel = "S";


    public void selectBase(String category, int type) {
        if (category.equalsIgnoreCase("кофе")) {
            switch (type) {
                case 1 -> drink = new Latte();
                case 2 -> drink = new Cappuccino();
                case 3 -> drink = new Americano();
                case 4 -> drink = new Espresso();
                default -> throw new IllegalArgumentException("Неизвестный тип кофе");
            }
        } else if (category.equalsIgnoreCase("чай")) {
            switch (type) {
                case 1 -> drink = new BlackTea();
                case 2 -> drink = new GreenTea();
                case 3 -> drink = new FruitTea();
                default -> throw new IllegalArgumentException("Неизвестный тип чая");
            }
        } else {
            throw new IllegalArgumentException("Неизвестная категория напитка. Введите 'кофе' или 'чай'.");
        }
    }

    public void selectSize(String size) {
        switch (size.toUpperCase()) {
            case "S" -> { drink.setSizeMultiplier(1.0); sizeLabel = "S"; }
            case "M" -> { drink.setSizeMultiplier(1.2); sizeLabel = "M"; }
            case "L" -> { drink.setSizeMultiplier(1.5); sizeLabel = "L"; }
            default -> throw new IllegalArgumentException("Неверный размер стакана");
        }
        drink.description += " (" + sizeLabel + ")";
    }

    public void addMilk(int qty) {
        drink = new Milk(drink, qty);
    }

    public void addCaramel(int qty) {
        drink = new Caramel(drink, qty);
    }

    public void addWhippedCream(int qty) {
        drink = new WhippedCream(drink, qty);
    }

    public Drink build() {
        return drink;
    }
}


class Snack {
    private final String name;
    private final double price;
    private final int quantity;

    public Snack(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getDescription() {
        return name + " x" + quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }
}


class OrderManager {
    private static OrderManager instance;
    private final List<String> orders = new ArrayList<>();

    private OrderManager() {}

    public static synchronized OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    public void addOrder(String description) {
        orders.add(description);
    }

    public void showAllOrders() {
        System.out.println("\n=== Все заказы ===");
        if (orders.isEmpty()) {
            System.out.println("Пока заказов нет.");
        } else {
            for (int i = 0; i < orders.size(); i++) {
                System.out.println((i + 1) + ". " + orders.get(i));
            }
        }
    }


    public int ordersCount() {
        return orders.size();
    }

    public void clearOrders() {
        orders.clear();
    }
}



public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OrderManager manager = OrderManager.getInstance();
        boolean k = true;

        while (k) {
            System.out.println("Добро пожаловать в кофейню!");
            System.out.print("Выберите категорию (кофе / чай): ");
            String category = scanner.nextLine();

            DrinkBuilder builder = new DrinkBuilder();

            if (category.equalsIgnoreCase("кофе") ) {
                System.out.println("Выберите тип кофе:");
                System.out.println("1 - Латте");
                System.out.println("2 - Капучино");
                System.out.println("3 - Американо");
                System.out.println("4 - Эспрессо");
            } else if (category.equalsIgnoreCase("чай")) {
                System.out.println("Выберите тип чая:");
                System.out.println("1 - Чёрный");
                System.out.println("2 - Зелёный");
                System.out.println("3 - Фруктовый");
            } else {
                System.out.println("Неизвестная категория!");
                continue;
            }

            int type = Integer.parseInt(scanner.nextLine());
            builder.selectBase(category, type);

            System.out.print("Выберите размер стакана (S / M / L): ");
            String size = scanner.nextLine();
            builder.selectSize(size);


            boolean a= true;
            while (a) {
                System.out.println("Добавки:");
                System.out.println("1 - Молоко (1 руб.)");
                System.out.println("2 - Карамель (2 руб.)");
                System.out.println("3 - Взбитые сливки (2.5 руб.)");
                System.out.println("0 - Готово");

                int choice = Integer.parseInt(scanner.nextLine());
                if (choice == 0) break;

                System.out.print("Введите количество: ");
                int qty = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> builder.addMilk(qty);
                    case 2 -> builder.addCaramel(qty);
                    case 3 -> builder.addWhippedCream(qty);
                    default -> System.out.println("Неверный выбор.");
                }
            }

            Drink drink = builder.build();


            System.out.println("Хотите добавить закуску?");
            System.out.println("1 - Круассан (3 руб.)");
            System.out.println("2 - Чизкейк (4.5 руб.)");
            System.out.println("3 - Маффин (2.5 руб.)");
            System.out.println("0 - Без закуски");

            Snack snack = null;
            int snackChoice = Integer.parseInt(scanner.nextLine());
            if (snackChoice != 0) {
                System.out.print("Введите количество: ");
                int qty = Integer.parseInt(scanner.nextLine());
                switch (snackChoice) {
                    case 1 -> snack = new Snack("Круассан", 3, qty);
                    case 2 -> snack = new Snack("Чизкейк", 4.5, qty);
                    case 3 -> snack = new Snack("Маффин", 2.5, qty);
                    default -> System.out.println("Неверный выбор.");
                }
            }

            double total = drink.baseCost() + (snack != null ? snack.getTotalPrice() : 0);
            String description = drink.getDescription() +
                    (snack != null ? " + " + snack.getDescription() : "") +
                    " — " + total + " руб.";

            System.out.println("Ваш заказ: " + description);
            manager.addOrder(description);

            System.out.print("Сделать еще один заказ? (да/нет): ");
            String again = scanner.nextLine().toLowerCase();
            k = again.equals("да");
        }

        manager.showAllOrders();
        System.out.println("Спасибо за заказ! Хорошего дня ☕🍰");
        scanner.close();
    }
}
