package com.example.business.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Tim")
public class Tim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="naziv")
    private String naziv;

    @Column(name="vodja")
    private String vodja;

    public Tim() { }

    public Tim(String naziv, String vodja, Set<Korisnik> korisnici) {
        this.naziv = naziv;
        this.vodja = vodja;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }

    public String getVodja() { return vodja; }
    public void setVodja(String vodja) { this.vodja = vodja; }
}
