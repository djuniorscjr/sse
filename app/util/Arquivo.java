package util;

import play.i18n.Messages;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Domingos Junior on 21/06/2015.
 */
public class Arquivo {

    public static File returnaAnexo(String descricao, byte[] anexo) throws RegraDeNegocioException {
        File file = new File(descricao);
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(file);
            fos.write(anexo);
        }catch (FileNotFoundException e){
            throw new RegraDeNegocioException(Messages.get("op.error"));
        } catch (IOException e){
            throw new RegraDeNegocioException(Messages.get("op.error"));
        }finally {
            try{
                if(fos != null){
                    fos.close();
                }
            }catch (IOException e){
                throw new RegraDeNegocioException(Messages.get("op.error"));
            }
        }
        return file;
    }
}
