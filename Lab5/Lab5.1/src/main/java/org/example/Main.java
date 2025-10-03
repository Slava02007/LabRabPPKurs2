package org.example;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> List=new ArrayList<>();
        Scanner scanner=new Scanner(System.in);

        System.out.println("Введите числа :");
        while(true){
            String line= scanner.nextLine();
            if(line.isEmpty()){
                break;
            }

            try {
                int number = Integer.parseInt(line);
                List.add(number);
            }
            catch (NumberFormatException e){
                System.out.println("Ошибка "+line+" не является числом.");
            }
        }

        listInSort(List);
        System.out.println("Список чисел:");
        for (Integer num:List){
            System.out.println(num);
        }
    }

    public static void listInSort(List<Integer> list){
        int left=0;
        int right= list.size()-1;

        while(left <= right){
            while(left <= right && list.get(left) >= 0){
                left++;
            }
            while(left <= right && list.get(right) <= 0){
                right--;
            }
            if(left<=right){
                Collections.swap(list,left,right);
                left++;
                right--;
            }
        }
    }

}