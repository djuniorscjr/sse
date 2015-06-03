package validator;

import com.avaje.ebean.Ebean;
import models.Aluno;
import play.data.validation.Constraints;
import play.libs.F;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

/**
 * Created by Domingos Junior on 30/05/2015.
 */
public class FieldUniqueValidator extends Constraints.Validator<Object> implements ConstraintValidator<FieldUnique, Object> {
    public static final String message = "error.unique";
    private Class<?> classe;
    private String campo;

    @Override
    public void initialize(FieldUnique unique) {
        this.classe = unique.classe();
        this.campo = unique.campo();
    }

    @Override
    public boolean isValid(Object var1) {
        Object object = Ebean.find(this.classe).where().eq(this.campo, var1.toString()).findUnique();
        return object == null?true:false;
    }

    @Override
    public F.Tuple<String, Object[]> getErrorMessageKey() {
        return F.Tuple("error.unique", new Object[0]);
    }
}
