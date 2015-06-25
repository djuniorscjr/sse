package models;

/**
 * Created by Domingos Junior on 24/06/2015.
 */
public enum StatusProjeto {

    ABERTO              ("Aberto"),
    FECHADO             ("Fechado");

    public String descricao;

    private StatusProjeto(String descricao){
        this.descricao = descricao;
    }
}
