package com.example.demo.utility;

public class SalaryCalculator {

    //Ažurirati platni koeficijent kao satnicu
    public static Double calculateSalary(Integer workingHours, Double hourlyWork){
        return workingHours*hourlyWork;
    }
}
