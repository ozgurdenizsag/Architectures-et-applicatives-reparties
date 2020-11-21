package entities;

import javax.persistence.*;
import java.util.*;

@Entity
public class Membre {
    @Id
    private String login;
    private String motdepasse;
    private String surnom;
    @OneToMany(mappedBy = "dirigePar")
    private Set<Projet> responsable;
    @ManyToMany(mappedBy = "contributionDe")
    private Set<Projet> participe;
/*    @OneToMany(mappedBy = "declareePar")
    private Set<CompetenceMembre> declare;
*/
    @ElementCollection
    private Map<Competence,CompetenceMembre> declare;

    // Constructeurs

    public Membre() {
        this.responsable=new HashSet<>();
        this.participe=new HashSet<>();
        this.declare=new HashMap<>();
    }

    public Membre(String login, String motdepasse, String surnom) {
        this();
        this.login = login;
        this.motdepasse = motdepasse;
        this.surnom = surnom;
    }

    // Getters / setters

    public String getLogin() {
        return login;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public String getSurnom() {
        return surnom;
    }

    public void setSurnom(String surnom) {
        this.surnom = surnom;
    }

    public Set<Projet> getResponsable() {
        return responsable;
    }

    public void setResponsable(Set<Projet> responsable) {
        this.responsable = responsable;
    }

    public Set<Projet> getParticipe() {
        return participe;
    }

    public void setParticipe(Set<Projet> participe) {
        this.participe = participe;
    }

    public Map<Competence,CompetenceMembre> getDeclare() {
        return declare;
    }

    public void setDeclare(Map<Competence,CompetenceMembre> declare) {
        this.declare = declare;
    }
}
