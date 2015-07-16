package models;

/**
 * Created by Domingos Junior on 14/07/2015.
 */
public enum StatusSolicitacaoDeParticipacao {

    ACEITO              ("Aceito"),
    NEGADO              ("Negado"),
    ENVIADO             ("ENVIADO");

    public String descricao;

    private StatusSolicitacaoDeParticipacao(String descricao){
        this.descricao = descricao;
    }
}
