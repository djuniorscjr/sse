package models;

/**
 * Created by Domingos Junior on 29/05/2015.
 */
public enum Permissao {
    ADMINISTRADOR           ("Administrador"),
    COORDENADOR             ("Coordenador"),
    PROFESSOR_DISCIPLINA    ("Professor da Disciplina"),
    PROFESSOR_ORIENTADOR    ("Professor Orientador"),
    ALUNO                   ("Aluno"),
    SEM_ACESSO              ("Sem Acesso");

    public String descricao;

    private Permissao(String descricao){
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Permissao{" +
                "descricao='" + descricao + '\'' +
                '}';
    }
}
