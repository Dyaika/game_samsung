package com.company;

public class Filter {


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    private String stSubstance, stColor;
    private int sub, col;

    public String getStSubstance() {
        return stSubstance;
    }

    public String getStColor() {
        return stColor;
    }

    public int getSub() {
        return sub;
    }

    public int getCol() {
        return col;
    }

    public Filter(int stateOfSubstance, int color) {
        switch (stateOfSubstance) {
            case 0:
                stSubstance = "vapor";
                break;
            case 1:
                stSubstance = "fluid";
                break;
            case 2:
                stSubstance = "solid";
                break;
            case 3:
                stSubstance = "crushed";
                break;
            default:
                stSubstance = "none";
                break;
        }
        switch (color) {
            case 0:
                stColor = "blue";
                break;
            case 1:
                stColor = "red";
                break;
            case 2:
                stColor = "green";
                break;
            case 3:
                stColor = "yellow";
                break;
            default:
                stColor = "none";
                break;
        }
        col = color;
        sub = stateOfSubstance;
    }

    @Override
    public String toString() {
        switch (col) {
            case 0:
                return ANSI_BLUE + stSubstance + ANSI_RESET;
                //break;
            case 1:
                return ANSI_RED + stSubstance + ANSI_RESET;
                //break;
            case 2:
                return ANSI_GREEN + stSubstance + ANSI_RESET;
                //break;
            case 3:
                return  ANSI_YELLOW + stSubstance + ANSI_RESET;
                //break;
            default:
                return "      ";
        }
    }
}
