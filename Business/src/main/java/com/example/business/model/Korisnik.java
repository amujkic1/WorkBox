package com.example.business.model;

import jakarta.persistence.*;

@Entity
@Table(name="Korisnik")
public class Korisnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="ime")
    private String ime;

    @Column(name="prezime")
    private String prezime;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "tim_id")
    private Tim tim;

    public Korisnik() { }

    public Korisnik(String ime, String prezime, String username, String password, Tim tim) {
        this.ime = ime;
        this.prezime = prezime;
        this.username = username;
        this.password = password;
        this.tim = tim;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }

    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Tim getTim() { return tim; }
    public void setTim(Tim tim) { this.tim = tim; }
}
