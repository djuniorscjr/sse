package util;

import com.microtripit.mandrillapp.lutung.MandrillApi;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage;

import java.util.List;

/**
 * Created by Domingos Junior on 05/06/2015.
 */
public class Email {

    private String titulo;
    private String file;
    private List<MandrillMessage.Recipient> recipients;

    public Email(String file, String titulo,List<MandrillMessage.Recipient> recipients){
        this.file = file;
        this.titulo = titulo;
        this.recipients = recipients;
    }

    public void enviaEmail(){
        MandrillApi mandrillApi = new MandrillApi("4ZLp5uKCRPn6IwMYOBgQlw");
        MandrillMessage message = new MandrillMessage();
        message.setSubject(titulo);
        message.setHtml(file);
        message.setAutoHtml(true);
        message.setFromEmail("sse@hotmail.com");
        message.setFromName("Sistema Supervisão de Estágio");
        message.setTo(recipients);

        try{
            mandrillApi.messages().send(message, false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
