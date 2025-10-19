package builder;

public class App {

    public Truck buy(){
        return Truck.builder().owner("ООО ЕвроТрак").stsNumber(9112234).year(2007).build();
        }

    public Truck buyAvito(){
        return Truck.builder().owner("ООО ЕвроТрак").stsNumber(9112234).year(2007).driver("Слава").build();

    }

    public Truck send(){
        return Truck.builder().owner("ООО ЕвроТрак").stsNumber(9112234).year(2007).driver("Слава").trailer("Есть").build();

    }

    public void demonstrate() {
        System.out.println("=== ДЕМОНСТРАЦИЯ РАБОТЫ ГРУЗОВИКОВ ===\n");

        System.out.println("1. Базовый грузовик:");
        Truck basicTruck = buy();
        basicTruck.printInfo();

        System.out.println("\n2. Грузовик с водителем:");
        Truck truckWithDriver = buyAvito();
        truckWithDriver.printInfo(); // Используем альтернативный метод

        System.out.println("\n3. Полностью укомплектованный грузовик:");
        Truck fullTruck = send();
        fullTruck.printInfo();
    }

    public static void main(String[] args) {
        App app = new App();
        app.demonstrate();
    }
}
