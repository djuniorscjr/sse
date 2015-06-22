package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by Domingos Junior on 21/06/2015.
 */
@Entity
public class Anexo {

    @Id
    @GeneratedValue
    public Long id;

    public String descricao;
    public String contentType;
    public byte[] arquivo;

    @OneToOne(mappedBy = "anexo")
    public Documento documento;
}
