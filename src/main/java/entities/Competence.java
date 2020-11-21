package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Competence {
    @Id
    private String intituleC;
    private String descriptionC;
    @ManyToMany
    private Set<Projet> requisePour;
/*    @OneToMany(mappedBy = "deType")
    private Set<CompetenceMembre> compMembre;
*/
    // Constructeurs

    public Competence() {
        this.requisePour=new HashSet<>();
//        this.compMembre=new HashSet<>();
    }

    public Competence(String intituleC, String descriptionC) {
        this();
        this.intituleC = intituleC;
        this.descriptionC = descriptionC;
    }

    // Getters et setters

    public String getIntituleC() {
        return intituleC;
    }

    public void setIntituleC(String intituleC) {
        this.intituleC = intituleC;
    }

    public String getDescriptionC() {
        return descriptionC;
    }

    public void setDescriptionC(String descriptionC) {
        this.descriptionC = descriptionC;
    }

    public Set<Projet> getRequisePour() {
        return requisePour;
    }

    public void setRequisePour(Set<Projet> requisePour) {
        this.requisePour = requisePour;
    }

/*    public Set<CompetenceMembre> getCompMembre() {
        return compMembre;
    }

    public void setCompMembre(Set<CompetenceMembre> compMembre) {
        this.compMembre = compMembre;
    }


 */
}
