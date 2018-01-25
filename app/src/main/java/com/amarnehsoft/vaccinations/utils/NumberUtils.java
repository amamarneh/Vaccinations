package com.amarnehsoft.vaccinations.utils;

/**
 * Created by jcc on 1/12/2018.
 */


/**
 * Created by Amarneh on 2/12/2017.
 */

public class NumberUtils {

    public static double getDouble(String value){
        String v = arabicToDecimal(value);
        double d = 0;
        try {
            d = Double.parseDouble(v);
            d=(double)Math.round(d * 100d) / 100d;
//            int n = (int)d;
//            d = n/100.0;
        }catch (Exception ex){
            d = 0.0;
        }
        return d;
    }

    public static double getDouble(double d){
//            d = d * 100;
//            int n = (int)d;
//            d = n/100.0;

        return (double)Math.round(d * 100d) / 100d;
    }

    public static int getInteger(String value){
        String v = arabicToDecimal(value);
        int d = 0;
        try {
            d = Integer.parseInt(v);
        }catch (Exception ex){
            d = 0;
        }
        return d;
    }

    public static long getLong(String value){
        //String v = arabicToDecimal(value);
        long d = 0;
        try {
            d = Long.parseLong(value);
        }catch (Exception e){
            d = 0;
        }
        return d;
    }

    private static final String arabic = "\u06f0\u06f1\u06f2\u06f3\u06f4\u06f5\u06f6\u06f7\u06f8\u06f9";
    public static String arabicToDecimal(String number) {
        if (number == null) return "";
        char[] chars = new char[number.length()];
        for(int i=0;i<number.length();i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }

    public static int getRandomInt(int min,int max){
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    public static int getRandomInt(){
        return getRandomInt(1,10000);
    }
}
