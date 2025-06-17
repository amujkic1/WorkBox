package com.example.demo.utility;

public class SalaryCalculator {

    //Ažurirati platni koeficijent kao satnicu
    //public static Double calculateSalary(Integer workingHours, Double hourlyWork){
    //    return workingHours*hourlyWork;
    //}

    // === Atributi sa default vrijednostima ===
    private double osnovnaSatnica = 10.0;
    private double bonusProcenat = 0.20;
    private double naknadaPrevoz = 50.0;
    private double topliObrokPoDanu = 20.0;
    private double ostaleNaknade = 30.0;

    // === Konstruktor bez argumenata (koristi default vrijednosti) ===
    public SalaryCalculator() {}

    // === Konstruktor sa mogućnošću postavljanja vrijednosti pri kreiranju ===
    public SalaryCalculator(double osnovnaSatnica, double bonusProcenat, double naknadaPrevoz,
                            double topliObrokPoDanu, double ostaleNaknade) {
        this.osnovnaSatnica = osnovnaSatnica;
        this.bonusProcenat = bonusProcenat;
        this.naknadaPrevoz = naknadaPrevoz;
        this.topliObrokPoDanu = topliObrokPoDanu;
        this.ostaleNaknade = ostaleNaknade;
    }

    // === Glavna metoda za izračun plate ===
    // Formula za bolovanje i godišnji ista, samo a blovanje se dodaje plata bez benficija za dane bolovanja
    public double izracunajPlatu(double platniKoeficijent, int brojRadnihDana, int satiPoRadnomDanu, int prekovremeniSati) {
        double satnicaZaposlenika = osnovnaSatnica * platniKoeficijent;
        double osnovnaPlata = brojRadnihDana * satiPoRadnomDanu * satnicaZaposlenika;
        double prekovremenaPlata = prekovremeniSati * satnicaZaposlenika;
        double bonus = osnovnaPlata * bonusProcenat;
        double topliObrok = brojRadnihDana * topliObrokPoDanu;

        return osnovnaPlata + prekovremenaPlata + bonus + naknadaPrevoz + topliObrok + ostaleNaknade;
    }


    public double getOsnovnaSatnica() {
        return osnovnaSatnica;
    }

    public void setOsnovnaSatnica(double osnovnaSatnica) {
        this.osnovnaSatnica = osnovnaSatnica;
    }

    public double getBonusProcenat() {
        return bonusProcenat;
    }

    public void setBonusProcenat(double bonusProcenat) {
        this.bonusProcenat = bonusProcenat;
    }

    public double getNaknadaPrevoz() {
        return naknadaPrevoz;
    }

    public void setNaknadaPrevoz(double naknadaPrevoz) {
        this.naknadaPrevoz = naknadaPrevoz;
    }

    public double getTopliObrokPoDanu() {
        return topliObrokPoDanu;
    }

    public void setTopliObrokPoDanu(double topliObrokPoDanu) {
        this.topliObrokPoDanu = topliObrokPoDanu;
    }

    public double getOstaleNaknade() {
        return ostaleNaknade;
    }

    public void setOstaleNaknade(double ostaleNaknade) {
        this.ostaleNaknade = ostaleNaknade;
    }
}
