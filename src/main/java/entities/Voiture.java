package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Voiture {
    @Id
    private String immatriculation;
    private String surnom;
    @OneToMany(mappedBy = "voiture")
    private Set<Passager> passagers;


    public Voiture() {
        passagers=new HashSet<>();
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getSurnom() {
        return surnom;
    }

    public void setSurnom(String surnom) {
        this.surnom = surnom;
    }

    public Set<Passager> getPassagers() {
        return passagers;
    }

    public void setPassagers(Set<Passager> passagers) {
        this.passagers = passagers;
    }

    @Override
    public String toString() {
        return "Voiture{" +
                "immatriculation='" + immatriculation + '\'' +
                ", surnom='" + surnom + '\'' +
                ", passagers=" + passagers +
                '}';
    }
}
