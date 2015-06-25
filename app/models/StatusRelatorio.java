package models;

/**
 * Created by Domingos Junior on 24/06/2015.
 */
public enum StatusRelatorio {
    NAO_INICIADO         ("NÃO INICIADO"),
    INICIADO             ("INICIADO"),
    FECHADO              ("FECHADO");

    public String descricao;

    private StatusRelatorio(String descricao){
        this.descricao = descricao;
    }
}
