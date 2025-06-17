package com.example.demo.utility;

public class SalaryCalculator {

    private double basicHourlyRate = 10.0; //Osnovna satnica
    private double bonusPercentage = 0.20;
    private double transportationFee = 50.0;
    private double mealAllowancePerDay = 20.0;
    private double otherBenefits = 30.0;

    public SalaryCalculator() {}

    public SalaryCalculator(double basicHourlyRate, double bonusPercentage, double transportationFee, double mealAllowancePerDay, double otherBenefits) {
        this.basicHourlyRate = basicHourlyRate;
        this.bonusPercentage = bonusPercentage;
        this.transportationFee = transportationFee;
        this.mealAllowancePerDay = mealAllowancePerDay;
        this.otherBenefits = otherBenefits;
    }


    public double calculateSalary(double salaryCoefficient, int numberOfWorkingDays, int workingHours, int numberOfOvertimeHours, boolean bonus, boolean transport, boolean mealAllowance, boolean other) {
        double hourlyRate = basicHourlyRate * salaryCoefficient;
        double baseSalary = numberOfWorkingDays * workingHours * hourlyRate;
        double overtimePay = numberOfOvertimeHours * hourlyRate;

        double salary = baseSalary + overtimePay;

        if (bonus){
            salary += (baseSalary * bonusPercentage);
        }
        if (transport){
            salary += transportationFee;
        }
        if (mealAllowance){
            salary += (numberOfWorkingDays * mealAllowancePerDay);
        }
        if (other){
            salary += otherBenefits;
        }

        return salary;
    }

    public double getBasicHourlyRate() {
        return basicHourlyRate;
    }

    public void setBasicHourlyRate(double basicHourlyRate) {
        this.basicHourlyRate = basicHourlyRate;
    }

    public double getBonusPercentage() {
        return bonusPercentage;
    }

    public void setBonusPercentage(double bonusPercentage) {
        this.bonusPercentage = bonusPercentage;
    }

    public double getTransportationFee() {
        return transportationFee;
    }

    public void setTransportationFee(double transportationFee) {
        this.transportationFee = transportationFee;
    }

    public double getMealAllowancePerDay() {
        return mealAllowancePerDay;
    }

    public void setMealAllowancePerDay(double mealAllowancePerDay) {
        this.mealAllowancePerDay = mealAllowancePerDay;
    }

    public double getOtherBenefits() {
        return otherBenefits;
    }

    public void setOtherBenefits(double otherBenefits) {
        this.otherBenefits = otherBenefits;
    }
}
