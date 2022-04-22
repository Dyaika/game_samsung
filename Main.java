package com.company;
public class Main {
    static void printMain(Filter[][] matrix, int height){
        int width = 4;
        for (int y = height - 1; y >= 0; y--){
            for (int x = 0; x < width; x++){
                System.out.print(matrix[x][y].getColor() + "" + matrix[x][y].getShape() + " ");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        int height = 7;//можно менять
        Filter[][] arr = new Filter[4][height];
        for(int j = 0; j < height; j++) {
            for (int i = 0; i < 4; i++) {
                arr[i][j] = new Filter();
            }
        }
        arr[0][0].makeSpace();

        Filter.generateMatrix(arr, height);
        printMain(arr, height);
        /*
        int height = 30;//можно менять
        FilterOld[][] arr = new FilterOld[height][4];
        FilterOld[] lastRow = new FilterOld[4];
        for (int i = 0; i < 4; i++){
            arr[0][i] = new FilterOld(i, i);
            lastRow[i] = arr[0][i];//чтобы мы могли "видеть" что в дырах
        }
        int state;
        int diff;
        for (int i = 1; i < height; i++){
            int right = (int)Math.round(Math.random());//0 дырка слева 1 справа
            if (right == 1){
                for (int j = 0; j < 3; j++){
                    diff = (int)Math.round(Math.random());//0 совпадет состояние, 1 совпадет цвет
                    state = (int)Math.round(Math.random() * 3);
                    if (diff == 0){arr[i][j] = new FilterOld(lastRow[j].getSub(), state);
                    } else {arr[i][j] = new FilterOld(state, lastRow[j].getCol());}
                    lastRow[j] = arr[i][j];
                }
                arr[i][3] = new FilterOld(4, 4);//дыра
            } else {
                for (int j = 1; j < 4; j++){
                    diff = (int)Math.round(Math.random());//0 совпадет состояние, 1 совпадет цвет
                    state = (int)Math.round(Math.random() * 3);
                    if (diff == 0){
                        if (lastRow[j].getCol() == state){state = (state + 1) % 4;}
                        arr[i][j] = new FilterOld(lastRow[j].getSub(), state);
                    } else {
                        if (lastRow[j].getSub() == state){state = (state + 1) % 4;}
                        arr[i][j] = new FilterOld(state, lastRow[j].getCol());
                    }
                    lastRow[j] = arr[i][j];
                }
                arr[i][0] = new FilterOld(4, 4);//дыра
            }

        }
        for (int i = height - 1; i > -1; i--){
            for (int j = 0; j < 4; j++){
                System.out.print(arr[i][j] + "\t");
            }
            System.out.println("");
        }*/

    }

}
