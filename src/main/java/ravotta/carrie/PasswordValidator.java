package ravotta.carrie;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("passwordValidate")
public class PasswordValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        String password = value.toString();

        // Obtain the component and submitted value of the confirm password component.
        UIInput verifyComponent = (UIInput) uiComponent.findComponent("password_verify");
        String password_verify = (String) verifyComponent.getSubmittedValue();

        if (!password.equals(password_verify)) {
            String messageText = "Passwords do not match";
            verifyComponent.setValid(false); // So that it's marked invalid.
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, messageText, messageText));
        }
    }
}
