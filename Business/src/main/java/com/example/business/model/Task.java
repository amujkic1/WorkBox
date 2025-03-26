package com.example.business.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="Task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="datum_pocetka")
    @Temporal(TemporalType.DATE)
    private Date datumPocetka;

    @Column(name="datum_zavrsetka")
    @Temporal(TemporalType.DATE)
    private Date datumZavrsetka;

    @Column(name="datum_predaje")
    @Temporal(TemporalType.DATE)
    private Date datumPredaje;

    @Column(name="naziv")
    private String naziv;

    @Column(name="opis")
    private String opis;

    @Column(name="status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "projekat_id", nullable = false)
    private Projekat projekat;

    @ManyToOne
    @JoinColumn(name = "korisnik_id", nullable = false)
    private Korisnik korisnik;

    public Task() { }

    public Task(Date datumPocetka, Date datumZavrsetka, Date datumPredaje, String naziv, String opis, String status, Projekat projekat, Korisnik korisnik) {
        this.datumPocetka = datumPocetka;
        this.datumZavrsetka = datumZavrsetka;
        this.datumPredaje = datumPredaje;
        this.naziv = naziv;
        this.opis = opis;
        this.status = status;
        this.projekat = projekat;
        this.korisnik = korisnik;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getDatumPocetka() { return datumPocetka; }
    public void setDatumPocetka(Date datumPocetka) { this.datumPocetka = datumPocetka; }

    public Date getDatumZavrsetka() { return datumZavrsetka; }
    public void setDatumZavrsetka(Date datumZavrsetka) { this.datumZavrsetka = datumZavrsetka; }

    public Date getDatumPredaje() { return datumPredaje; }
    public void setDatumPredaje(Date datumPredaje) { this.datumPredaje = datumPredaje; }

    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }

    public String getOpis() { return opis; }
    public void setOpis(String opis) { this.opis = opis; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Projekat getProjekat() { return projekat; }
    public void setProjekat(Projekat projekat) { this.projekat = projekat; }

    public Korisnik getKorisnik() { return korisnik; }
    public void setKorisnik(Korisnik korisnik) { this.korisnik = korisnik; }
}
