package com.company;
import java.util.ArrayList;
import java.util.Collections;

public class Filter {
    private int color;
    private int shape;
    public Filter(int color, int shape) {
        if (color <= 3 && color >= 0){//0,1,2,3-синий, красный, зеленый, желтый
            this.color = color;
        }else{
            this.color = 4;//без цвета
        }
        if (shape <= 3 && shape >= 0){
            this.shape = shape;//0,1,2,3-пар, жидкость, твердое, частицы
        }else{
            this.shape = 4;//без формы
        }
    }
    public Filter() {
        this.shape = 4;
        this.color = 4;
    }
    public int getColor() {
        return color;
    }
    public int getShape() {
        return shape;
    }
    private void setColor(int color){
        if (color <= 3 && color >= 0){//0,1,2,3-синий, красный, зеленый, желтый
            this.color = color;
        }else{
            this.color = 4;//без цвета
        }
    }
    private void setShape(int shape){
        if (shape <= 3 && shape >= 0){
            this.shape = shape;//0,1,2,3-пар, жидкость, твердое, частицы
        }else{
            this.shape = 4;//без формы
        }
    }
    private void setColorAndShape(int color, int shape){
        setColor(color);
        setShape(shape);
    }
    public void setColorAndShape(Filter filter){
        setColorAndShape(filter.getColor(), filter.getShape());
    }
    public boolean isSameTo(Filter filter){
        if (getColor() == filter.getColor()){
            if (getShape() == filter.getShape()){
                return true;
            }
        }
        return false;
    }
    //задел для баффов
    public void trySetColor(int color) {
        if (this.color == 4 && this.shape != 4) {
            this.color = color;
        }else{
            System.out.println("There is no filter or it already has color");
        }
    }
    //задел для баффов
    public void trySetShape(int shape) {
        if (this.shape == 4 && this.color != 4) {
            this.shape = shape;
        }else{
            System.out.println("There is no filter or it already has shape");
        }
    }
    //Проверка фильтра на верность значений. Вернет true, если это фильтр или пустота, если нет - неверные поля заменятся на пустоты и вернет false
    public boolean isFilterCorrect(){
        int color, shape;
        boolean check = true;
        color = getColor();
        shape = getShape();
        if (color > 3 || color < 0){
            this.color = 4;
            check = false;
        }
        if (shape > 3 || shape < 0){
            this.shape = 4;
            check = false;
        }
        return check;
    }
    //Проверка фильтра на то что он пустота
    public boolean isSpace(){
        if (getColor() == 4 && getShape() == 0){
            return true;
        }
        return false;
    }
    //Делает фильтр пустотой
    public void makeSpace(){
        this.shape = 4;
        this.color = 4;
    }

    //0123-индексы x
    //(0-пустые ячейки,1-верхние объекты, 2-фильтры, 3-лунки)
    //1111  4
    //0222  3
    //2220  2
    //2220  1
    //3333  0-индексы y
    //Проверка матрицы на решаемость и корректность структуры. Предполагается, что размеры и сами фильтры ее верны. Пусть пока будет здесь, потом перенесем

    //Проверка матрицы на стандартность фильтров. Предполагается, что размеры ее верны.
    public static boolean standardizeMatrix(Filter[][] matrix, int height){
        int width = 4;//ширина матрицы
        boolean check = true;//вернет true если уже была стандартной
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                if (!matrix[x][y].isFilterCorrect()){
                    check = false;
                }
            }
        }
        return check;
    }
    //проверка приведенной к стандарту фильтров матрицы на корректность структуры
    public static boolean isMatrixCorrect(Filter[][] matrix, int height){
        int width = 4;
        int width_count = 0, max_count = 0;//для подряд идущих не пустот
        for (int i = 0; i < width; i++){
            if (matrix[i][0].isSpace() || matrix[i][height - 1].isSpace()){//сверху и снизу не должно быть пустот
                return false;
            }
        }
        //стартуем движение сверху вниз
        for (int y = height - 2; y > 0; y--) {//ряды фильтров
            width_count = 0;
            max_count = 0;
            for (int x = 0; x < width; x++) {

                if (matrix[x][y].isSpace()){
                    max_count = width_count;
                    width_count = 0;
                }else{
                    width_count += 1;
                }
            }
            max_count = Math.max(max_count, width_count);
            if (max_count != 3){//убеждаемся что подряд идущих непустот 3, если не так то false
                return false;
            }
        }

        return true;
    }
    //проверка структурно верной матрицы на решаемость на 4/4
    public static boolean isMatrixSolvable(Filter[][] matrix, int height){//проверка что матрица решится на 4/4
        int width = 4;
        Filter[] row = new Filter[width];//строка проверки будет идти сверху вниз, нужна для зрения сквозь пустоты
        for (int i = 0; i < width; i++){
            row[i].setColorAndShape(matrix[i][height - 1]);
        }
        for (int y = height - 2; y >= 0; y--){
            for (int x = 0; x < width; width++){
                if(!matrix[x][y].isSpace()){
                    if(row[x].getShape() != matrix[x][y].getShape() && row[x].getColor() != matrix[x][y].getColor()){//ни цвет, ни форма не совпали
                        return false;
                    }
                    row[x].setColorAndShape(matrix[x][y]);
                }
            }
        }
        return true;
    }
    //сдвиг указанного ряда матрицы вправо
    public static boolean swipeToRight(Filter[][] matrix, int height, int row){//row - индекс ряда, который смещаем
        int width = 4;
        if (row >= height - 1 || row <= 0){//не из ряда фильтров
            return false;
        }
        if (!matrix[width - 1][row].isSpace()){//уже и так справа
            return false;
        }
        for (int x = width - 1; x > 0; x--){
            matrix[x][row].setColorAndShape(matrix[x - 1][row]);
        }
        matrix[0][row].makeSpace();
        return true;
    }
    //сдвиг указанного ряда матрицы влево
    public static boolean swipeToLeft(Filter[][] matrix, int height, int row){//row - индекс ряда, который смещаем
        int width = 4;
        if (row >= height - 1 || row <= 0){//не из ряда фильтров
            return false;
        }
        if (!matrix[0][row].isSpace()){//уже и так слева
            return false;
        }
        for (int x = 0; x < width - 1; x++){
            matrix[x][row].setColorAndShape(matrix[x + 1][row]);
        }
        matrix[width - 1][row].makeSpace();
        return true;
    }
    //убирает блоки фильтров из матрицы решения и помещает их на матрицу склада
    public static void shuffleMatrix(Filter[][] main_matrix, int main_height, Filter[][] storage_matrix){
        int storage_height = 0, first_element_index = 0;
        int main_width = 4, storage_width = 3;

        ArrayList<Integer> row_numbers = new ArrayList<>();//храним номера
        for (int i = 1; i < main_height - 1; i++){
            row_numbers.add(i);
        }
        Collections.shuffle(row_numbers);//мешаем номера
        for(int i: row_numbers){
            if (main_matrix[0][i].isSpace()){
                first_element_index = 1;
            }else{
                first_element_index = 0;
            }
            for (int x = 0; x < storage_width; x++){
                storage_matrix[x][storage_height].setColorAndShape(main_matrix[first_element_index][i]);
                main_matrix[first_element_index][i].makeSpace();
                first_element_index++;
            }
            storage_height++;
        }
    }
    //перемещает фильтр с поля игры наверх склада
    public static boolean fromMainToStorage(Filter[][] main_matrix, int from_index, int main_height, Filter[][] storage_matrix, int to_index){
        if (from_index > main_height - 2 || from_index < 1){//попытка вытащить не фильтр
            return false;
        }
        int storage_width = 3, main_width = 4, first_element_index = 0;
        if (main_matrix[0][from_index].isSpace()){//чтобы забирать фильтр без пустоты
            first_element_index = 1;
        }
        for (int x = 0; x < storage_width; x++){
            storage_matrix[x][to_index].setColorAndShape(main_matrix[first_element_index][from_index]);//запись в хранилище поверх остального
            first_element_index++;
        }
        for (int y = from_index; y < main_height - 2; y++){
            for (int x = 0; x < main_width; x++){
                main_matrix[x][y].setColorAndShape(main_matrix[x][y+1]);//спуск остальных рядов игровой матрицы вниз
            }
        }
        for (int x = 0; x < main_width; x++){
            main_matrix[x][main_height - 1].makeSpace();
        }
        return true;
    }
    //перемещает фильтр со склада наверх поля игры
    public static boolean fromStorageToMain(Filter[][] main_matrix, int from_index, int storage_height, Filter[][] storage_matrix, int to_index){
        if (from_index > storage_height - 1 || from_index < 1){//попытка вытащить не фильтр
            return false;
        }
        int storage_width = 3, main_width = 4;
        for (int x = 0; x < storage_width; x++){
            main_matrix[x][to_index].setColorAndShape(storage_matrix[x][from_index]);//запись в поле игры поверх остального (сдвиг вправо)
        }
        for (int y = from_index; y < storage_height - 2; y++){
            for (int x = 0; x < storage_width; x++){
                storage_matrix[x][y].setColorAndShape(storage_matrix[x][y+1]);//спуск остальных рядов игровой матрицы вниз
            }
        }
        for (int x = 0; x < storage_width; x++){
            main_matrix[x][storage_height - 1].makeSpace();
        }
        return true;
    }
    //генерирует матрицу решения заданной высоты
    public static void generateMatrix(Filter[][] matrix, int height){
        int width = 4;
        int[] change_color_chance = new int[width];//для того чтобы уровни выглядели более красивыми
        for (int i = 0; i < width; i++){
            change_color_chance[i] = 2;
        }
        ArrayList<Integer> excl_rand = new ArrayList<>();//чтобы 1 параметр гарантированно менялся
        int start_index = 0;//чтобы определять положение дыры
        Filter[] last_row = new Filter[4];//чтобы смотреть сквозь дыры
        for (int i = 0; i < 4; i++){
            last_row[i] = new Filter();
        }
        for (int x = 0; x < width; x++) {
            matrix[x][0].setColorAndShape((int) Math.round(Math.random() * 3), (int) Math.round(Math.random() * 3));
            last_row[x].setColorAndShape(matrix[x][0]);
        }
        for (int y = 1; y < height - 1; y++){
            start_index = (int) Math.round(Math.random());
            for (int x = start_index; x < width + start_index - 1; x++){// c 0 или с 1 до 3 или 4 соответственно
                if((int) Math.round(Math.random() * 22) % change_color_chance[x] == 0){//если верно то меняется форма. цвет не меняется
                    change_color_chance[x]++;//увеличиваем вероятность смены цвета (уменьшая шанс срабатываения условия)
                    for (int i = 0; i < width; i++){
                        if (i != last_row[x].getShape()){
                            excl_rand.add(i);//будет массив без прошлого параметра
                        }
                    }
                    matrix[x][y].setColorAndShape(last_row[x].getColor(), excl_rand.get((int) Math.round(Math.random() * 2)));
                } else {
                    change_color_chance[x] = 2;//возвращаем к начальному

                    for (int i = 0; i < 4; i++){
                        if (i != last_row[x].getColor()){
                            excl_rand.add(i);//будет массив без прошлого параметра
                        }
                    }
                    matrix[x][y].setColorAndShape( excl_rand.get((int) Math.round(Math.random() * 2)), last_row[x].getShape());
                }
                last_row[x].setColorAndShape(matrix[x][y]);
                excl_rand.clear();
            }
        }
        for(int x = 0; x < width; x++){
            if((int) Math.round(Math.random() * 22) % change_color_chance[x] == 0){//если верно то меняется форма. цвет не меняется
                for (int i = 0; i < width; i++){
                    if (i != last_row[x].getShape()){
                        excl_rand.add(i);//будет массив без прошлого параметра
                    }
                }
                matrix[x][height - 1].setColorAndShape(last_row[x].getColor(), excl_rand.get((int) Math.round(Math.random() * 2)));
            } else {
                for (int i = 0; i < 4; i++){
                    if (i != last_row[x].getColor()){
                        excl_rand.add(i);//будет массив без прошлого параметра
                    }
                }
                matrix[x][height - 1].setColorAndShape( excl_rand.get((int) Math.round(Math.random() * 2)), last_row[x].getShape());
            }
            last_row[x].setColorAndShape(matrix[x][height - 1]);
            excl_rand.clear();
        }
    }
}

