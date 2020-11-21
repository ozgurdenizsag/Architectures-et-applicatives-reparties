package servlets;

import entities.*;
import modele.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//@WebServlet(name = "Controleur", urlPatterns = "/")
public class Controleur extends HttpServlet {

    @Autowired
    private Facade facade;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,config.getServletContext());
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        facade.nouvelle();

        List<Voiture> lv=facade.toutes();
        for (Voiture v:lv) {
            System.out.println(v);
        }


        String todo = request.getParameter("TODO");

        if ((request.getSession().getAttribute("util")==null) && ((todo == null)
                || (todo.equals("selogguer")==false))) {
            request.getRequestDispatcher("/WEB-INF/accueil.jsp").
                    forward(request, response);
        } else {
            if (todo == null) {
                menu(request, response);

            } else {
                switch (todo) {
                    case "selogguer":
                        String log = request.getParameter("login");
                        String mdp = request.getParameter("mdp");
                        if (facade.testLM(log, mdp)!=null) {
                           // le login est mis en session pour s'en souvenir...
                           request.getSession().setAttribute("util", log);
                           menu(request, response);
                        }

                    case "participer":
                        Membre membre = getMembreFromLogin((String) request.getSession().getAttribute("util"));
                        Projet projet = getProjetFromIntituleP(request.getParameter("intituleP"));
                        if ((projet.getContributionDe().contains(membre) == false) && (projet.getDirigePar() != membre)) {
                            projet.getContributionDe().add(membre);
                            membre.getParticipe().add(projet);
                        }
                        menu(request, response);

                        break;

                    default:
                        request.getRequestDispatcher("/WEB-INF/accueil.jsp").forward(request, response);
                }
            }
        }
    }

    private Collection<Projet> competences(Membre m) {
        // On initialise les projets : tous les projets moins ceux auquel m participe
        Collection<Projet> pcomp=new ArrayList<>();
        pcomp.addAll(facade.getProjets());
        pcomp.removeAll(m.getResponsable());
        pcomp.removeAll(m.getParticipe());

        // les competences de m
        Collection<Competence> compm=new ArrayList<>();
       /* for (CompetenceMembre cm:m.getDeclare()) {
            compm.add(cm.getDeType());
        }
*/
        Collection<Projet> res=new ArrayList<>();

        // on ne garde que les projets avec des competences de m
        for (Projet p:pcomp) {
            for (Competence comp:p.getNecessite()) {
                if (compm.contains(comp)) {
                    res.add(p);
                    break;
                }
            }
        }
        return res;
    }

    private Collection<Projet> autres(Membre m, Collection<Projet> hascomp) {
        Collection<Projet> pautres=new ArrayList<>();
        pautres.addAll(facade.getProjets());
        pautres.removeAll(m.getResponsable());
        pautres.removeAll(m.getParticipe());
        pautres.removeAll(hascomp);
        return pautres;
    }

    private Membre getMembreFromLogin(String login) {
        for (Membre m: facade.getMembres()) {
            if (m.getLogin().equals(login)) {
                return m;
            }
        }
        return null;
    }

    private Projet getProjetFromIntituleP(String intituleP) {
        for (Projet p: facade.getProjets()) {
            if (p.getIntituleP().equals(intituleP)) {
                return p;
            }
        }
        return null;
    }

    private void menu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*Membre membre= facade.getMembreFromLogin((String)request.getSession().getAttribute("util"));

        // on affiche la page de menu, en lui passant le membre qui vient de se logguer
        request.setAttribute("courant",membre);

        Collection<Projet> pcomp=facade.competentPour(membre.getLogin());
        request.setAttribute("competent",pcomp);
        request.setAttribute("autres",facade.autres(membre.getLogin()));
*/
        request.setAttribute("dto",facade.getMenuDto((String)request.getSession().getAttribute("util")));

        request.getRequestDispatcher("/WEB-INF/menu.jsp").forward(request,response);
    }

}









