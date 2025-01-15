package com.android.befit;

public class users {
    public String FName, Mail, Code, DP, Steps, KCAL, Time;

    public users(String F_Name, String GMail, String PassCode) {
        FName = F_Name;
        Mail = GMail;
        Code = PassCode;
    }
    public users(String F_Name, String GMail, String PassCode,String dp,String steps,String kcal, String timeSpent) {
        FName = F_Name;
        Mail = GMail;
        Code = PassCode;
        DP = dp;
        Steps = steps;
        KCAL = kcal;
        Time = timeSpent;
    }

    public users(String dp) {
        DP = dp;
    }

    public users(String steps,int a){
        Steps = steps;
    }

    public users(String kcal, int a, int b){
        KCAL = kcal;
    }

}
