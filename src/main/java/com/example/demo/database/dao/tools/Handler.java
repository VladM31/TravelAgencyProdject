package com.example.demo.database.dao.tools;

public class Handler {
    public static boolean arrayHasOnlyOne(int[] arr){
        for(int i: arr){
            if(i != 1){
                return false;
            }
        }
        return true;
    }
}
