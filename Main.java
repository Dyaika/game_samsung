package com.company;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        int height = 12;//можно менять
        Filter[][] arr = new Filter[height][4];
        Filter[] lastRow = new Filter[4];
        arr[0][0] = new Filter(0, 0);
        arr[0][1] = new Filter(1, 1);
        arr[0][2] = new Filter(2, 2);
        arr[0][3] = new Filter(3, 3);//создаем лунки
        lastRow[0] = arr[0][0];
        lastRow[1] = arr[0][1];
        lastRow[2] = arr[0][2];
        lastRow[3] = arr[0][3];//чтобы мы могли "видеть" что в дырах
        for (int i = 1; i < height; i++){
            int right = (int)Math.round(Math.random()) % 2;//0 дырка слева 1 справа
            if (right == 1){
                for (int j = 0; j < 3; j++){
                    int diff = (int)Math.round(Math.random() * 10) % 2;//0 совпадет состояние, 1 совпадет цвет
                    int state = (int)Math.round(Math.random() * 10) % 4;
                    if (diff == 0){
                        arr[i][j] = new Filter(lastRow[j].getSub(), state);
                        lastRow[j] = arr[i][j];
                    } else {
                        arr[i][j] = new Filter(state, lastRow[j].getCol());
                        lastRow[j] = arr[i][j];
                    }
                }
                arr[i][3] = new Filter(4, 4);//дыра
            } else {
                for (int j = 1; j < 4; j++){
                    int diff = (int)Math.round(Math.random() * 10) % 2;//0 совпадет состояние, 1 совпадет цвет
                    int state = (int)Math.round(Math.random() * 10) % 4;
                    if (diff == 0){
                        arr[i][j] = new Filter(lastRow[j].getSub(), state);
                        lastRow[j] = arr[i][j];
                    } else {
                        arr[i][j] = new Filter(state, lastRow[j].getCol());
                        lastRow[j] = arr[i][j];
                    }
                }
                arr[i][0] = new Filter(4, 4);//дыра
            }

        }
        for (int i = height - 1; i > -1; i--){
            for (int j = 0; j < 4; j++){
                System.out.print(arr[i][j] + "    ");
            }
            System.out.println("");
        }
    }
}