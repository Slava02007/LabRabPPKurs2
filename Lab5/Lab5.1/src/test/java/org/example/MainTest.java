package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @org.junit.jupiter.api.Test
    void listInSort_Test1() {
        List<Integer> inputList= Arrays.asList(-1,-2,-3,1,2,3);
        List<Integer> outputList= Arrays.asList(3,2,1,-3,-2,-1);
        Main.listInSort(inputList);
        assertEquals(outputList,inputList);
    }

    @org.junit.jupiter.api.Test
    void listInSort_Test2(){
        List<Integer> inputList= Arrays.asList(1,2,3,4,5,6);
        List<Integer> outputList= Arrays.asList(1,2,3,4,5,6);
        Main.listInSort(inputList);
        assertEquals(outputList,inputList);
    }

    @org.junit.jupiter.api.Test
    void listInSort_Test3(){
        List<Integer> inputList= Arrays.asList(-1,-2,-3,-4,-5,-6);
        List<Integer> outputList= Arrays.asList(-1,-2,-3,-4,-5,-6);
        Main.listInSort(inputList);
        assertEquals(outputList,inputList);
    }

    @org.junit.jupiter.api.Test
    void listInSort_Test4(){
        List<Integer> inputList= new ArrayList<>();
        List<Integer> outputList= new ArrayList<>();
        Main.listInSort(inputList);
        assertEquals(outputList,inputList);
    }

    @Test
    void listInSort_Test5() {
        List<Integer> inputList = Arrays.asList(5, -3, 0, -1, 2, -4);
        List<Integer> outputList = Arrays.asList(5, 2, 0, -1, -3, -4);
        Main.listInSort(inputList);
        assertEquals(outputList, inputList);
    }

    @Test
    void listInSort_Test6() {
        List<Integer> inputList = Arrays.asList(0, 0, 0);
        List<Integer> outputList = Arrays.asList(0, 0, 0);
        Main.listInSort(inputList);
        assertEquals(outputList, inputList);
    }

    @Test
    void listInSort_Test7() {
        List<Integer> inputList = Arrays.asList(0, -1, 1, 0, -2, 2);
        List<Integer> outputList = Arrays.asList(0, 2, 1, 0, -2, -1);
        Main.listInSort(inputList);
        assertEquals(outputList, inputList);
    }

    @Test
    void listInSort_Test8() {
        List<Integer> inputList = Arrays.asList(42);
        List<Integer> outputList = Arrays.asList(42);
        Main.listInSort(inputList);
        assertEquals(outputList, inputList);
    }

    @Test
    void listInSort_Test9() {
        List<Integer> inputList = Arrays.asList(-42);
        List<Integer> outputList = Arrays.asList(-42);
        Main.listInSort(inputList);
        assertEquals(outputList, inputList);
    }

    @Test
    void listInSort_Test10() {
        List<Integer> inputList = Arrays.asList(0, 0, 0, 0, 0);
        List<Integer> outputList = Arrays.asList(0, 0, 0, 0, 0);
        Main.listInSort(inputList);
        assertEquals(outputList, inputList);
    }

    @Test
    void listInSort_Test12() {
        List<Integer> inputList = Arrays.asList(1, 2, 3, -1, -2, -3);
        List<Integer> outputList = Arrays.asList(1, 2, 3, -1, -2, -3);
        Main.listInSort(inputList);
        assertEquals(outputList, inputList);
    }

    @Test
    void listInSort_Test13() {
        List<Integer> inputList = Arrays.asList(1, -1, 2, -2, 3, -3);
        List<Integer> outputList = Arrays.asList(1, 3, 2, -2, -1, -3);
        Main.listInSort(inputList);
        assertEquals(outputList, inputList);
    }

    @Test
    void listInSort_Test14() {
        List<Integer> inputList = new ArrayList<>(Arrays.asList(1, null, -1));
        assertThrows(NullPointerException.class, () -> Main.listInSort(inputList));
    }
}