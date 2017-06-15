/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.puce.professorCheck.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import ec.edu.puce.professorCheck.ctrl.BaseCtrl;

/**
 *
 * @author juan
 */
@FacesValidator("MailValidator")
public class MailValidator extends BaseCtrl implements Validator {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\."
            + "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\-[A-Za-z0-9]+)*+(\\.[A-Za-z0-9]+)*"
            + "(\\.[A-Za-z]{2,})$";
    private Pattern pattern;
    private Matcher matcher;

    public MailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (value != null) {
            String aValidar = value.toString().trim();
            if (aValidar.length() > 0) {
                matcher = pattern.matcher(aValidar);
                if (!matcher.matches()) {
                    ((UIInput) component).setValid(false);

                    FacesMessage msg = new FacesMessage(getBundleMensajes("error.correo.electronico.incorrecto", null));
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);
                }
            }
        }
    }
}
