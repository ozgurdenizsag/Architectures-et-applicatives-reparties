package modele;

import dtos.MenuDto;
import entities.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class Facade {
    @PersistenceContext
    EntityManager em;


/*    private Collection<Membre> membres;
    private Collection<Projet> projets;
    private Collection<Competence> competences;
*/

@Transactional
    public List<Projet> competentPour(String login) {
        // le left est là pour que le membre soit sélectionné à partir de son login même s'il n'a pas de compétence.
        // NB : avec le @Transactional, on n'a pas besoin du fetch, et même un find aurait suffit...
        Query q=em.createQuery("From Membre m LEFT JOIN FETCH m.declare dec WHERE m.login=:log");
        q.setParameter("log",login);
        Membre m=(Membre)q.getSingleResult();

        Query q2=em.createQuery("Select p From Projet p JOIN p.necessite nec where nec in :compm");
        q2.setParameter("compm",m.getDeclare().keySet());
        return q2.getResultList();
    }

    public List<Projet> autres(String login) {
        Query q=em.createQuery("From Membre m LEFT JOIN FETCH m.declare dec LEFT JOIN FETCH m.responsable resp LEFT JOIN FETCH m.participe part WHERE m.login=:log");
        q.setParameter("log",login);
        Membre m=(Membre)q.getSingleResult();

        Query q2=em.createQuery("Select p From Projet p LEFT JOIN FETCH p.necessite nec where (nec is null or nec not in :compm) and p not in :pr and p not in :pp");
     //   Query q2=em.createQuery("Select p From Projet p LEFT JOIN FETCH p.necessite nec where p not in :pr and p not in :pp");

        //    Query q2=em.createQuery("Select p From Projet p where p not in :pr and p not in :pp");

        System.out.println(m.getDeclare().keySet());
        q2.setParameter("compm",m.getDeclare().keySet());
        q2.setParameter("pr",m.getResponsable());
        q2.setParameter("pp",m.getParticipe());

        return q2.getResultList();
    }









    public List<Voiture> toutes() {
        EntityGraph<Voiture> eg=em.createEntityGraph(Voiture.class);
        eg.addSubgraph("passagers");
        Query q=em.createQuery("from Voiture v");
        q.setHint("javax.persistence.loadgraph",eg);
        return q.getResultList();

        //return em.createQuery("from Voiture v JOIN FETCH v.passagers p").getResultList();

    }

    @Transactional
    public void nouvelle() {
        Passager p=new Passager();
        p.setNom("Alice");
        em.persist(p);


        Voiture jbv=em.find(Voiture.class,"JB007GB");

p.setVoiture(jbv);
    }








    /* public Facade() {
        membres=new ArrayList<>();
        Membre matthieu=new Membre("Matthieu","Matthieu","Matthieu");
        membres.add(matthieu);
        Membre fred=new Membre("Fred","Fred","Fred");
        membres.add(fred);

        projets=new ArrayList<>();
        Projet allanParson=new Projet("Allan Parson","Un projet musical");
        allanParson.setDirigePar(matthieu);
        matthieu.getResponsable().add(allanParson);
        projets.add(allanParson);
        Projet xion=new Projet("Xion","Pour partager une vision");
        xion.setDirigePar(fred);
        fred.getResponsable().add(xion);
        projets.add(xion);

        competences = new ArrayList<>();
        Competence java=new Competence("Java","POO");
        competences.add(java);
        Competence management=new Competence("Management", "Gestion d'équipe, résolution de conflits");
        competences.add(management);

        CompetenceMembre javamatth=new CompetenceMembre(matthieu,java,3,"java avancé");
        matthieu.getDeclare().add(javamatth);
        java.getCompMembre().add(javamatth);
        CompetenceMembre manafred=new CompetenceMembre(fred,management,4,"tout est sous contrôle");
        fred.getDeclare().add(manafred);
        management.getCompMembre().add(manafred);


        allanParson.getNecessite().addAll(competences);
        xion.getNecessite().addAll(competences);
        java.getRequisePour().addAll(projets);
        management.getRequisePour().addAll(projets);

    }*/

    public Collection<Membre> getMembres() {
        return em.createQuery("From Membre m").getResultList();
    }

/*    public void setMembres(Collection<Membre> membres) {
        this.membres = membres;
    }
*/
    public Collection<Projet> getProjets() {
        return em.createQuery("From Projet p").getResultList();
    }

/*    public void setProjets(Collection<Projet> projets) {
        this.projets = projets;
    }
*/
    public Collection<Competence> getCompetences() {
        return em.createQuery("From Competence c").getResultList();
    }
/*
    public void setCompetences(Collection<Competence> competences) {
        this.competences = competences;
    }
*/

    public Membre testLM(String log, String mdp) {
        Query q=em.createQuery("From Membre m where m.login = :lg and m.motdepasse = :mp");
        q.setParameter("lg",log);
        q.setParameter("mp",mdp);
        try {
            return (Membre) q.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public Membre getMembreFromLogin(String log) {
        Query q=em.createQuery("From Membre m LEFT JOIN FETCH m.responsable resp " +
                "LEFT JOIN FETCH m.participe part " +
                "LEFT JOIN FETCH m.declare dec where m.login = :lg");
        q.setParameter("lg",log);
        try {
            return (Membre) q.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }


    public MenuDto getMenuDto(String log) {
        Membre m=getMembreFromLogin(log);

        List<String> competences=new ArrayList<>();
        for (Competence c : m.getDeclare().keySet()) {
            CompetenceMembre cm=m.getDeclare().get(c);
            competences.add(   c.getDescriptionC()+ ", "+cm.getNiveau()+ " ("+cm.getCommentaire()+")"   );
        }

        List<String> responsable=new ArrayList<>();
        for (Projet p:m.getResponsable()) {
            responsable.add(p.getIntituleP()+" : "+p.getDescriptionP());
        }

        List<String> participe=new ArrayList<>();
        for (Projet p:m.getParticipe()) {
            participe.add(p.getIntituleP()+" : "+p.getDescriptionP());
        }

        List<String> competentPour=new ArrayList<>();
        for (Projet p:competentPour(log)) {
            competentPour.add(p.getIntituleP()+" : "+p.getDescriptionP());
        }

        List<String> autres=new ArrayList<>();
        for (Projet p:autres(log)) {
            autres.add(p.getIntituleP()+" : "+p.getDescriptionP());
        }

        MenuDto mnu=new MenuDto(m.getLogin(),
                competences,
                responsable,
                participe,
                competentPour,
                autres
                );

        return mnu;
    }



}
