package dtos;

import java.util.List;

public class MenuDto {
    private String nomMembre;
    private List<String> competences;
    private List<String> responsable;
    private List<String> participe;
    private List<String> competent;
    private List<String> autres;

    public MenuDto(String nomMembre, List<String> competences, List<String> responsable, List<String> participe, List<String> competent, List<String> autres) {
        this.nomMembre = nomMembre;
        this.competences = competences;
        this.responsable = responsable;
        this.participe = participe;
        this.competent = competent;
        this.autres = autres;
    }

    public String getNomMembre() {
        return nomMembre;
    }

    public List<String> getCompetences() {
        return competences;
    }

    public List<String> getResponsable() {
        return responsable;
    }

    public List<String> getParticipe() {
        return participe;
    }

    public List<String> getCompetent() {
        return competent;
    }

    public List<String> getAutres() {
        return autres;
    }
}
