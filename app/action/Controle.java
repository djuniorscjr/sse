package action;

import models.Permissao;
import play.mvc.With;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Domingos Junior on 04/06/2015.
 */
@With(ControleAction.class)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Controle {
    Permissao[] value() default Permissao.SEM_ACESSO;
}

