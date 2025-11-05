/*package org.example;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String filename = "hotel.txt";

        try {
            List<Hotel> hotels = Hotel.readHotels(filename);

            if (hotels.isEmpty()) {
                System.out.println("Файл пуст или отели не найдены.");
                return;
            }

            Hotel.hotelsSorted(hotels);
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите город для поиска отелей: ");
            String city = scanner.nextLine();
            Hotel.poiskHotelsByCity(hotels, city);

            System.out.print("Введите название отеля для поиска городов: ");
            String hotelName = scanner.nextLine();
            Hotel.poiskCitiesByHotelName(hotels, hotelName);

            scanner.close();

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        }
    }
}*/

package org.example;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String filename = "hotel.txt";

        try {
            List<Hotel> hotels = Hotel.readHotels(filename);

            if (hotels.isEmpty()) {
                System.out.println("Файл пуст или отели не найдены.");
                return;
            }

            Hotel.hotelsSorted(hotels);

            Scanner scanner = new Scanner(System.in);

            System.out.print("Введите город для поиска отелей: ");
            String city = scanner.nextLine().trim();
            Hotel.poiskHotelsByCity(hotels, city);

            System.out.print("Введите название отеля для поиска городов: ");
            String hotelName = scanner.nextLine().trim();
            Hotel.poiskCitiesByHotelName(hotels, hotelName);

            scanner.close();

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        }
    }
}
