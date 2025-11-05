/*package org.example;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Hotel {
    private String city;
    private String hotelName;
    private int stars;

    public Hotel(String city, String hotelName, int stars) {
        this.city = city;
        this.hotelName = hotelName;
        this.stars = stars;
    }

    public String getCity() {
        return city;
    }

    public String getHotelName() {
        return hotelName;
    }

    public int getStars() {
        return stars;
    }

    @Override
    public String toString() {
        return String.format("Город: %-15s Отель: %-20s Звезды: %d", city, hotelName, stars);
    }

    public static List<Hotel> readHotels(String filename) throws IOException {
        List<Hotel> hotels = new ArrayList<>();

        try (Scanner in = new Scanner(new File(filename))) {
            while (in.hasNextLine()) {
                String city = in.nextLine().trim();
                if (city.isEmpty()) continue;

                if (!in.hasNextLine()) break;
                String hotelLine = in.nextLine().trim();
                if (hotelLine.isEmpty()) continue;

                String[] parts = hotelLine.split(" ");
                if (parts.length < 2) continue;

                int stars = Integer.parseInt(parts[parts.length - 1]);

                String hotelName = String.join(" ",
                        Arrays.copyOfRange(parts, 0, parts.length - 1));

                hotels.add(new Hotel(city, hotelName, stars));
            }
        }
        return hotels;
    }

    public static void hotelsSorted(List<Hotel> hotels) {
        System.out.println("Все отели (сортировка по городу и убыванию звезд):");
        System.out.println("--------------------------------------------------");

        hotels.sort(new Comparator<Hotel>() {
            @Override
            public int compare(Hotel h1, Hotel h2) {
                int cityCompare = h1.getCity().compareTo(h2.getCity());
                if (cityCompare != 0) {
                    return cityCompare;
                }
                return Integer.compare(h2.getStars(), h1.getStars());
            }
        });

        for (Hotel hotel : hotels) {
            System.out.println(hotel);
        }
        System.out.println();
    }

    public static void poiskHotelsByCity(List<Hotel> hotels, String cityName) {
        System.out.println("Отели в городе '" + cityName + "':");
        System.out.println("--------------------------------");

        boolean found = false;
        for (Hotel hotel : hotels) {
            if (hotel.getCity().equalsIgnoreCase(cityName)) {
                System.out.println(hotel);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Отелей в городе '" + cityName + "' не найдено.");
        }
        System.out.println();
    }

    public static void poiskCitiesByHotelName(List<Hotel> hotels, String hotelName) {
        System.out.println("Города с отелем '" + hotelName + "':");
        System.out.println("--------------------------------");

        Set<String> cities = new TreeSet<>();
        boolean found = false;

        for (Hotel hotel : hotels) {
            if (hotel.getHotelName().equalsIgnoreCase(hotelName)) {
                cities.add(hotel.getCity());
                found = true;
            }
        }

        if (found) {
            for (String city : cities) {
                System.out.println(city);
            }
        } else {
            System.out.println("Отель с названием '" + hotelName + "' не найден.");
        }
        System.out.println();
    }
}*/


package org.example;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Hotel {
    private String city;
    private ArrayList<HotelCity> hotels = new ArrayList<>();

    class HotelCity {
        String hotelName;
        int stars;

        public HotelCity(String hotelName, int stars) {
            this.hotelName = hotelName;
            this.stars = stars;
        }

        @Override
        public String toString() {
            return String.format("Отель: %-20s Звёзды: %d", hotelName, stars);
        }
    }

    public Hotel(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public List<HotelCity> getHotels() {
        return hotels;
    }

    @Override
    public String toString() {
        return String.format("Город: %-15s Кол-во отелей: %d", city, hotels.size());
    }

    public static List<Hotel> readHotels(String filename) throws IOException {
        List<Hotel> cityList = new ArrayList<>();
        Map<String, Hotel> cityMap = new HashMap<>();

        try (Scanner in = new Scanner(new File(filename))) {
            while (in.hasNextLine()) {
                String city = in.nextLine().trim();
                if (city.isEmpty()) continue;

                if (!in.hasNextLine()) break;
                String hotelLine = in.nextLine().trim();
                if (hotelLine.isEmpty()) continue;

                String[] parts = hotelLine.split(" ");
                if (parts.length < 2) continue;

                int stars = Integer.parseInt(parts[parts.length - 1]);
                String hotelName = String.join(" ", Arrays.copyOfRange(parts, 0, parts.length - 1));

                cityMap.putIfAbsent(city, new Hotel(city));
                Hotel hotelCityObj = cityMap.get(city);

                hotelCityObj.hotels.add(hotelCityObj.new HotelCity(hotelName, stars));
            }
        }

        cityList.addAll(cityMap.values());
        return cityList;
    }

    public static void hotelsSorted(List<Hotel> hotels) {
        System.out.println("Все отели (сортировка по городу и убыванию звёзд):");
        System.out.println("--------------------------------------------------");

        hotels.sort(Comparator.comparing(Hotel::getCity));

        for (Hotel city : hotels) {
            city.hotels.sort((h1, h2) -> Integer.compare(h2.stars, h1.stars));
            for (HotelCity h : city.hotels) {
                System.out.printf("Город: %-15s %s%n", city.getCity(), h);
            }
        }
        System.out.println();
    }

    public static void poiskHotelsByCity(List<Hotel> hotels, String cityName) {
        System.out.println("Отели в городе '" + cityName + "':");
        System.out.println("--------------------------------");

        boolean found = false;
        for (Hotel city : hotels) {
            if (city.getCity().equalsIgnoreCase(cityName)) {
                for (HotelCity h : city.hotels) {
                    System.out.println(h);
                }
                found = true;
            }
        }

        if (!found) {
            System.out.println("Отелей в городе '" + cityName + "' не найдено.");
        }
        System.out.println();
    }

    public static void poiskCitiesByHotelName(List<Hotel> hotels, String hotelName) {
        System.out.println("Города с отелем '" + hotelName + "':");
        System.out.println("--------------------------------");

        Set<String> foundCities = new TreeSet<>();
        boolean found = false;

        for (Hotel city : hotels) {
            for (HotelCity h : city.hotels) {
                if (h.hotelName.equalsIgnoreCase(hotelName)) {
                    foundCities.add(city.getCity());
                    found = true;
                }
            }
        }

        if (found) {
            for (String c : foundCities) {
                System.out.println(c);
            }
        } else {
            System.out.println("Отель с названием '" + hotelName + "' не найден.");
        }

        System.out.println();
    }
}
