package org.example;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class HotelTest {

    private List<Hotel> testHotels;

    @BeforeEach
    void setUp() {
        testHotels = new ArrayList<>();
        testHotels.add(new Hotel("Москва", "Гранд Отель", 5));
        testHotels.add(new Hotel("Москва", "Астория", 4));
        testHotels.add(new Hotel("Санкт-Петербург", "Нева", 4));
        testHotels.add(new Hotel("Санкт-Петербург", "Гранд Отель", 5));
        testHotels.add(new Hotel("Казань", "Волга", 3));
    }

    @Test
    void testToString() {
        Hotel hotel = new Hotel("Москва", "Тест Отель", 5);
        String result = hotel.toString();

        assertTrue(result.contains("Москва"));
        assertTrue(result.contains("Тест Отель"));
        assertTrue(result.contains("5"));
    }


    @Test
    void testPoiskHotelsByCity() {
        System.out.println("Тест поиска отелей по городу:");
        Hotel.poiskHotelsByCity(testHotels, "Москва");
        assertDoesNotThrow(() -> Hotel.poiskHotelsByCity(testHotels, "Москва"));
    }

    @Test
    void testPoiskHotelsByCityNotFound() {
        System.out.println("Тест поиска по несуществующему городу:");
        Hotel.poiskHotelsByCity(testHotels, "Новосибирск");
        assertDoesNotThrow(() -> Hotel.poiskHotelsByCity(testHotels, "Новосибирск"));
    }

    @Test
    void testPoiskCitiesByHotelName() {
        System.out.println("Тест поиска городов по названию отеля:");
        Hotel.poiskCitiesByHotelName(testHotels, "Гранд Отель");
        assertDoesNotThrow(() -> Hotel.poiskCitiesByHotelName(testHotels, "Гранд Отель"));
    }

}