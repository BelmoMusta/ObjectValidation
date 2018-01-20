import junit.framework.TestCase;
import musta.belmo.validation.bean.Person;
import musta.belmo.validation.exception.ValidationException;
import musta.belmo.validation.Validator;

public class Main extends TestCase {

    public void testValidationByAge() throws ValidationException {
        Validator validator = new Validator();
        Person person = new Person();
        person.setName("Mustapha");
        person.setLastName("Belmokhtar");
        person.setAge(-1);

        validator.checkValidation(person);
        validator.getValidationReport(person);

        assertFalse(validator.checkValidation(person));
        System.out.println(validator.getValidationReport(person));
        person.setAge(40);
        assertTrue(validator.checkValidation(person));
        System.out.println(validator.getValidationReport(person));
    }

    public void testValidationByaddress() throws ValidationException {
        Validator validator = new Validator();
        Person person = new Person();
        person.setName("mustapha");
        person.setLastName("Belmokhtar");
        person.setAge(40);

        assertFalse(validator.checkValidation(person));
        System.out.println(validator.getValidationReport(person));

        person.setAddress("a non-null address ! ");
        assertTrue(validator.checkValidation(person));
        System.out.println(validator.getValidationReport(person));
    }

    public void testRegexValidation() throws ValidationException {
        Validator validator = new Validator();
        Person person = new Person();
        person.setPhoneNumber("0123456789");
        assertTrue(validator.checkValidation(person));
        System.out.println(validator.getValidationReport(person));
    }
}

