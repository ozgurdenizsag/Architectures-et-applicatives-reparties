package entities;

import javax.persistence.*;

//@Entity
@Embeddable
public class CompetenceMembre {
/*    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cmId;
    @ManyToOne
    private Membre declareePar;
    @ManyToOne
    private Competence deType;

 */
    private int niveau;
    private String commentaire;

    // Constructeurs
    public CompetenceMembre(){}

/*    public CompetenceMembre(Membre declareePar, Competence deType, int niveau, String commentaire) {
        this.declareePar = declareePar;
        this.deType = deType;
        this.niveau = niveau;
        this.commentaire = commentaire;
    }
*/
    // Getters et setters

 /*   public int getCmId() {
        return cmId;
    }

    public Membre getDeclareePar() {
        return declareePar;
    }

    public void setDeclareePar(Membre declareePar) {
        this.declareePar = declareePar;
    }

    public Competence getDeType() {
        return deType;
    }

    public void setDeType(Competence deType) {
        this.deType = deType;
    }


  */
    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
