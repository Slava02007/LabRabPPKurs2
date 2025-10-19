package org.example;


public class Main {
    public static void main(String[] args) {
        System.out.println("=== ДЕМОНСТРАЦИЯ SINGLETON ===");

        TruckManager manager1 = TruckManager.getInstance();
        TruckManager manager2 = TruckManager.getInstance();

        System.out.println("\n1. Добавляем грузовики:");
        manager1.addTruck("Volvo FH16");
        manager2.addTruck("MAN TGX");
        manager1.addTruck("Scania R500");

        System.out.println("\n2. Информация о компании:");
        manager1.printCompanyInfo();

        System.out.println("\n3. Удаляем грузовик:");
        manager2.removeTruck();

        System.out.println("\n4. Проверка, что менеджер один:");
        System.out.println("manager1 == manager2: " + (manager1 == manager2));
        System.out.println("Количество через manager1: " + manager1.getTruckCount());
        System.out.println("Количество через manager2: " + manager2.getTruckCount());

        System.out.println("\n5. Финальная информация:");
        manager1.printCompanyInfo();
    }
}