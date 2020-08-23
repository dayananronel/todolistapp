package com.ronel.todolistapp.util;

import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utilities {

    public static String detectDateFormat(String inputDate, String requiredFormat) {
        String tempDate = inputDate.replace("/", "").replace("-", "").replace(" ", "");
        String dateFormat;

        if (tempDate.matches("([0-12]{2})([0-31]{2})([0-9]{4})")) {
            dateFormat = "MMddyyyy";
        } else if (tempDate.matches("([0-31]{2})([0-12]{2})([0-9]{4})")) {
            dateFormat = "ddMMyyyy";
        } else if (tempDate.matches("([0-9]{4})([0-12]{2})([0-31]{2})")) {
            dateFormat = "yyyyMMdd";
        } else if (tempDate.matches("([0-9]{4})([0-31]{2})([0-12]{2})")) {
            dateFormat = "yyyyddMM";
        } else if (tempDate.matches("([0-31]{2})([a-z]{3})([0-9]{4})")) {
            dateFormat = "ddMMMyyyy";
        } else if (tempDate.matches("([a-z]{3})([0-31]{2})([0-9]{4})")) {
            dateFormat = "MMMddyyyy";
        } else if (tempDate.matches("([0-9]{4})([a-z]{3})([0-31]{2})")) {
            dateFormat = "yyyyMMMdd";
        } else if (tempDate.matches("([0-9]{4})([0-31]{2})([a-z]{3})")) {
            dateFormat = "yyyyddMMM";
        } else {
            return "Pattern Not Added";
//add your required regex
        }
        try {
            String formattedDate = new SimpleDateFormat(requiredFormat, Locale.ENGLISH).format(new SimpleDateFormat(dateFormat).parse(tempDate));
            return formattedDate;
        } catch (Exception e) {
            //
            return "";
        }

    }
    public static boolean validateJavaDate(String strDate)
    {
        try{
            String[] dates = strDate.split("/");
            if(Integer.parseInt(dates[2]) > 1900 && Integer.parseInt(dates[2]) <= 3000){
                /* Check if date is 'null' */
                if (strDate.trim().equals(""))
                {
                    return true;
                }
                /* Date is not 'null' */
                else
                {
                    /*
                     * Set preferred date format,
                     * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
                    SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
                    sdfrmt.setLenient(false);
                    /* Create Date object
                     * parse the string into date
                     */
                    try
                    {
                        Date javaDate = sdfrmt.parse(strDate);
                        System.out.println(strDate+" is valid date format");
                    }
                    /* Date format is invalid */
                    catch (ParseException e)
                    {
                        System.out.println(strDate+" is Invalid Date format");
                        return false;
                    }
                    /* Return true if date format is valid */
                    return true;
                }
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }
}
