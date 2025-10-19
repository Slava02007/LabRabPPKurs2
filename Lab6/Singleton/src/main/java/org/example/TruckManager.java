package org.example;

public class TruckManager {
    private static TruckManager instance;
    private int truckCount = 0;
    private String companyName = "ООО ЕвроТрак";


    private TruckManager() {
        System.out.println("✓ Создан единственный менеджер для компании: " + companyName);
    }


    public static TruckManager getInstance() {
        if (instance == null) {
            instance = new TruckManager();
        }
        return instance;
    }

    public void addTruck(String truckModel) {
        truckCount++;
        System.out.println("✓ Добавлен грузовик " + truckModel + ". Всего: " + truckCount);
    }

    public void removeTruck() {
        if (truckCount > 0) {
            truckCount--;
            System.out.println("✓ Удален грузовик. Осталось: " + truckCount);
        } else {
            System.out.println("✗ Нет грузовиков для удаления");
        }
    }

    public void printCompanyInfo() {
        System.out.println("🏢 Компания: " + companyName);
        System.out.println("🚛 Количество грузовиков: " + truckCount);
    }

    public int getTruckCount() {
        return truckCount;
    }

    public String getCompanyName() {
        return companyName;
    }
}
