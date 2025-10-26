package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class CoffeeShopTests {

    @Test
    @DisplayName("Создание кофе Латте через Builder")
    void testCreateLatte() {
        DrinkBuilder builder = new DrinkBuilder();
        builder.selectBase("кофе", 1); // Латте
        builder.selectSize("M");

        Drink drink = builder.build();

        assertTrue(drink.getDescription().contains("Латте"));
        assertEquals(5.0 * 1.2, drink.cost(), 0.001);
    }

    @Test
    @DisplayName("Добавление молока как декоратора")
    void testAddMilk() {
        Drink base = new Espresso();
        Drink milked = new Milk(base, 2);

        assertTrue(milked.getDescription().contains("молоко x2"));
        assertEquals(base.cost() + 2.0, milked.baseCost(), 0.001);
    }

    @Test
    @DisplayName("Добавление нескольких добавок подряд")
    void testMultipleDecorators() {
        Drink drink = new Latte();
        drink = new Milk(drink, 1);
        drink = new Caramel(drink, 2);

        String desc = drink.getDescription();
        assertTrue(desc.contains("Латте"));
        assertTrue(desc.contains("молоко"));
        assertTrue(desc.contains("карамель"));
    }

    @Test
    @DisplayName("Проверка Singleton OrderManager")
    void testSingletonOrderManager() {
        OrderManager m1 = OrderManager.getInstance();
        OrderManager m2 = OrderManager.getInstance();

        assertSame(m1, m2, "OrderManager должен быть одним экземпляром (Singleton)");

        m1.addOrder("Тестовый заказ");
        assertEquals(1, m2.ordersCount());
    }



    @Test
    @DisplayName("Проверка добавления закуски")
    void testSnackTotal() {
        Snack s = new Snack("Круассан", 3, 2);
        assertEquals(6.0, s.getTotalPrice(), 0.001);
    }

    @Test
    @DisplayName("Проверка стоимости напитка с размером и добавкой")
    void testDrinkWithSizeAndAddOn() {
        DrinkBuilder builder = new DrinkBuilder();
        builder.selectBase("кофе", 2);
        builder.selectSize("L");
        builder.addCaramel(1);

        Drink drink = builder.build();
        double expected = (4.5 * 1.5) + 2.0;
        assertEquals(expected, drink.baseCost(), 0.001);
    }
}