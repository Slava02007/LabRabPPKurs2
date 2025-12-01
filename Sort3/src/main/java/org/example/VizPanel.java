package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Random;

public class VizPanel extends JPanel {
    private int[] arr;
    private int n;

    public VizPanel(int[] arr){
        this.n=arr.length;
        this.arr=arr.clone();
    }

    public int[] getArr() {
        return arr;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        for (int i=0;i<n;i++){
             int h=arr[i];
             int x=(int)((double) i / n * panelWidth);
             int nextX=(int) ((double) (i + 1) / n * panelWidth);
             int w=nextX-x;
             int y=panelHeight-h;
             g.fillRect(x,y,w,h);
        }
    }



    public void bubbleSort() {
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    repaint();
                    try { Thread.sleep(15); } catch (InterruptedException e) {}
                }
            }
        }
    }

    public void gnomeSort() {
        int index = 0;

        while (index < n) {
            if (index == 0 || arr[index] >= arr[index - 1]) {
                index++;
            } else {
                int temp = arr[index];
                arr[index] = arr[index - 1];
                arr[index - 1] = temp;

                index--;

                repaint();
                try { Thread.sleep(15); } catch (InterruptedException e) {}
            }
        }
    }

    public void cocktailSort() {
        boolean swapped = true;
        int start = 0;
        int end = n - 1;

        while (swapped) {
            swapped = false;
            for (int i = start; i < end; i++) {
                if (arr[i] > arr[i + 1]) {
                    int temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = true;
                    repaint();
                    try { Thread.sleep(15); } catch (InterruptedException e) {}
                }
            }

            if (!swapped) break;
            swapped = false;
            end--;

            for (int i = end - 1; i >= start; i--) {
                if (arr[i] > arr[i + 1]) {
                    int temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = true;
                    repaint();
                    try { Thread.sleep(15); } catch (InterruptedException e) {}
                }
            }
            start++;
        }
    }



    public static void main(String[] args) {
        int[] masterArray = generateRandomArray(50);

        createWindow("Сортировка ПУЗЫРЬКОМ", 0, "bubble", masterArray);
        createWindow("Сортировка ГНОМЬЯ", 420, "gnome", masterArray);
        createWindow("Сортировка ШЕЙКЕРНАЯ", 840, "cocktail", masterArray);
    }

    public static int[] generateRandomArray(int n){
        int arr[]=new int[n];
        Random rand=new Random();
        for (int i=0;i<n;i++){
            arr[i]= rand.nextInt(250)+10;
        }
        return arr;
    }

    public static void createWindow(String name,int pozX,String nameSort,int[] data){
        JFrame window=new JFrame(name);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(400,300);
        window.setLocation(pozX,100);

        VizPanel panel = new VizPanel(data);

        window.add(panel);
        window.setVisible(true);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try { Thread.sleep(1000); } catch (InterruptedException e) {}

                if (nameSort.equals("bubble")) {
                    panel.bubbleSort();
                } else if (nameSort.equals("gnome")) {
                    panel.gnomeSort();
                } else if (nameSort.equals("cocktail")) {
                    panel.cocktailSort();
                }

            }
        });
        thread.start();
    }
}
