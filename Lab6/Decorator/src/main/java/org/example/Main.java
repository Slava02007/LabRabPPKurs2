package org.example;

interface Truck {
    void printInfo();
    double getCost();
}


class BasicTruck implements Truck {
    public void printInfo() {
        System.out.println("Базовый грузовик");
    }

    public double getCost() {
        return 1000000;
    }
}


abstract class TruckDecorator implements Truck {
    protected Truck truck;

    public TruckDecorator(Truck truck) {
        this.truck = truck;
    }

    public void printInfo() {
        truck.printInfo();
    }

    public double getCost() {
        return truck.getCost();
    }
}


class DriverDecorator extends TruckDecorator {
    public DriverDecorator(Truck truck) {
        super(truck);
    }

    public void printInfo() {
        super.printInfo();
        System.out.println("+ Водитель");
    }

    public double getCost() {
        return super.getCost() + 50000;
    }
}

class TrailerDecorator extends TruckDecorator {
    public TrailerDecorator(Truck truck) {
        super(truck);
    }

    public void printInfo() {
        super.printInfo();
        System.out.println("+ Прицеп");
    }

    public double getCost() {
        return super.getCost() + 200000;
    }
}

class InsuranceDecorator extends TruckDecorator {
    public InsuranceDecorator(Truck truck) {
        super(truck);
    }

    public void printInfo() {
        super.printInfo();
        System.out.println("+ Страховка");
    }

    public double getCost() {
        return super.getCost() + 100000;
    }
}


class RasxodDecorator extends TruckDecorator {
    public RasxodDecorator(Truck truck) {
        super(truck);
    }

    public void printInfo() {
        super.printInfo();
        System.out.println("+ Другие расходы");
    }

    public double getCost() {
        return super.getCost() + 200000;
    }
}

public class Main {
    public static void main(String[] args) {
        Truck truck = new BasicTruck();
        truck = new DriverDecorator(truck);
        truck = new TrailerDecorator(truck);
        truck = new InsuranceDecorator(truck);
        truck = new RasxodDecorator(truck);

        truck.printInfo();
        System.out.println("Общая стоимость: " + truck.getCost() + " руб.");
    }
}