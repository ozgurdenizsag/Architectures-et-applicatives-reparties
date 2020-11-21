package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Projet {
    @Id
    private String intituleP;
    private String descriptionP;
    @ManyToOne
    private Membre dirigePar;
    @ManyToMany
    private Set<Membre> contributionDe;
    @ManyToMany (mappedBy = "requisePour")
    private Set<Competence> necessite;

    // Constructeurs
    public Projet() {
        this.contributionDe=new HashSet<>();
        this.necessite=new HashSet<>();
    }

    public Projet(String intituleP, String descriptionP) {
        this();
        this.intituleP = intituleP;
        this.descriptionP = descriptionP;
    }


    // Getters et setters

    public String getIntituleP() {
        return intituleP;
    }

    public void setIntituleP(String intituleP) {
        this.intituleP = intituleP;
    }

    public String getDescriptionP() {
        return descriptionP;
    }

    public void setDescriptionP(String descriptionP) {
        this.descriptionP = descriptionP;
    }

    public Membre getDirigePar() {
        return dirigePar;
    }

    public void setDirigePar(Membre dirigePar) {
        this.dirigePar = dirigePar;
    }

    public Set<Membre> getContributionDe() {
        return contributionDe;
    }

    public void setContributionDe(Set<Membre> contributionDe) {
        this.contributionDe = contributionDe;
    }

    public Set<Competence> getNecessite() {
        return necessite;
    }

    public void setNecessite(Set<Competence> necessite) {
        this.necessite = necessite;
    }
}
