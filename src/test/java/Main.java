import junit.framework.TestCase;
import musta.belmo.validation.Person;
import musta.belmo.validation.Validator;

public class Main extends TestCase {

    public void testValidationByAge() throws IllegalAccessException {
        Validator validator = new Validator();
        Person person = new Person();
        person.setName("mustapha");
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

    public void testValidationByaddress() throws IllegalAccessException {
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


}

