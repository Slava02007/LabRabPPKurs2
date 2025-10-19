package builder;


public class Truck {
    private final String owner;
    private final int year;
    private final String trailer;
    private final String driver;
    private final int stsNumber;

    public Truck(String owner, int year, String trailer, String driver, int stsNumber) {
        this.owner = owner;
        this.year = year;
        this.trailer = trailer;
        this.driver = driver;
        this.stsNumber = stsNumber;
    }

    public void printInfo() {
        System.out.println("=== ИНФОРМАЦИЯ О ГРУЗОВИКЕ ===");
        System.out.println("Владелец: " + (owner != null ? owner : "не указан"));
        System.out.println("Год выпуска: " + year);
        System.out.println("Номер СТС: " + stsNumber);
        System.out.println("Водитель: " + (driver != null ? driver : "не назначен"));
        System.out.println("Прицеп: " + (trailer != null ? trailer : "отсутствует"));
        System.out.println("==============================");
    }

    public static TruckBuilder builder(){
        return new TruckBuilder();
    }

    public static class TruckBuilder{
        private  String owner;
        private  int year;
        private  String trailer;
        private  String driver;
        private  int stsNumber;

        public TruckBuilder owner(String owner){
            this.owner = owner;
            return this;
        }

        public TruckBuilder year(int year){
            this.year = year;
            return this;
        }

        public TruckBuilder trailer(String trailer){
            this.trailer = trailer;
            return this;
        }

        public TruckBuilder driver(String driver){
            this.driver = driver;
            return this;
        }

        public TruckBuilder stsNumber(int stsNumber){
            this.stsNumber = stsNumber;
            return this;
        }

        public Truck build(){
            return new Truck(owner,year,trailer,driver,stsNumber);
        }
    }
}
/*package builder;

import lombok.Builder;

@Builder
public class Truck {
    private final String owner;
    private final int year;
    private final String trailer;
    private final String driver;
    private final int stsNumber;

    public void printInfo() {
        System.out.println("=== ИНФОРМАЦИЯ О ГРУЗОВИКЕ ===");
        System.out.println("Владелец: " + (owner != null ? owner : "не указан"));
        System.out.println("Год выпуска: " + year);
        System.out.println("Номер СТС: " + stsNumber);
        System.out.println("Водитель: " + (driver != null ? driver : "не назначен"));
        System.out.println("Прицеп: " + (trailer != null ? trailer : "отсутствует"));
        System.out.println("==============================");
    }
}*/