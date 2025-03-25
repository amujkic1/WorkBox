package com.example.business.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Projekat")
public class Projekat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="naslov")
    private String naslov;

    @Column(name="datum_objave")
    @Temporal(TemporalType.DATE)
    private Date datumObjave;

    @Column(name="datum_preuzimanja")
    @Temporal(TemporalType.DATE)
    private Date datumPreuzimanja;

    @Column(name="datum_pocetka")
    @Temporal(TemporalType.DATE)
    private Date datumPocetka;

    @Column(name="datum_zavrsetka")
    @Temporal(TemporalType.DATE)
    private Date datumZavrsetka;

    @Column(name="vodja_projekta")
    private String vodjaProjekta;

    @Column(name="kontakt_klijenta")
    private String kontaktKlijenta;

    @OneToOne
    @JoinColumn(name="tim_id")
    private Tim tim;

    public Projekat() { }

    public Projekat(String naslov, Date datumObjave, Date datumPreuzimanja, Date datumPocetka, Date datumZavrsetka, String vodjaProjekta, String kontaktKlijenta, Tim tim) {
        this.naslov = naslov;
        this.datumObjave = datumObjave;
        this.datumPreuzimanja = datumPreuzimanja;
        this.datumPocetka = datumPocetka;
        this.datumZavrsetka = datumZavrsetka;
        this.vodjaProjekta = vodjaProjekta;
        this.kontaktKlijenta = kontaktKlijenta;
        this.tim = tim;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNaslov() { return naslov; }
    public void setNaslov(String naslov) { this.naslov = naslov; }

    public Date getDatumObjave() { return datumObjave; }
    public void setDatumObjave(Date datumObjave) { this.datumObjave = datumObjave; }

    public Date getDatumPreuzimanja() { return datumPreuzimanja; }
    public void setDatumPreuzimanja(Date datumPreuzimanja) { this.datumPreuzimanja = datumPreuzimanja; }

    public Date getDatumPocetka() { return datumPocetka; }
    public void setDatumPocetka(Date datumPocetka) { this.datumPocetka = datumPocetka; }

    public Date getDatumZavrsetka() { return datumZavrsetka; }
    public void setDatumZavrsetka(Date datumZavrsetka) { this.datumZavrsetka = datumZavrsetka; }

    public String getVodjaProjekta() { return vodjaProjekta; }
    public void setVodjaProjekta(String vodjaProjekta) { this.vodjaProjekta = vodjaProjekta; }

    public String getKontaktKlijenta() { return kontaktKlijenta; }
    public void setKontaktKlijenta(String kontaktKlijenta) { this.kontaktKlijenta = kontaktKlijenta; }

    public Tim getTim() { return tim; }
    public void setTim(Tim tim) { this.tim = tim; }
}
