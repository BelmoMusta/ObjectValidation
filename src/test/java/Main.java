import junit.framework.TestCase;
import musta.belmo.validation.Validator;
import musta.belmo.validation.bean.Person;
import musta.belmo.validation.bean.Student;
import musta.belmo.validation.bean.ValidationReport;
import musta.belmo.validation.criteria.Criteria;
import musta.belmo.validation.exception.ValidationException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Main extends TestCase {
    Person person;
    Student student;


    public Main() {
        super();
        person = new Person();
        person.setName("Mustapha");
        person.setLastName("Belmokhtar");
        person.setPhoneNumber("0123456789");
        person.setAddress("a non-null address ! ");
        person.setAge(25);


        student = new Student();
    }

    public void testArithmeticOperation() throws ValidationException {
        Validator validator = new Validator();

        person.setAge(-1);
        validator.getValidationReport(person);
        System.out.println(validator.getValidationReport(person));
        assertFalse(validator.check(person));

        person.setAge(40);
        System.out.println(validator.getValidationReport(person));
        assertTrue(validator.check(person));
    }

    /**
     * in this test case we suppose the birthDate is null but it is not required
     * the object remains valid whether we it assigned to a new date or left null
     *
     * @throws ValidationException
     */
    public void testNull() throws ValidationException {
        Validator validator = new Validator();
        assertTrue(validator.check(person));
        person.setBirthDate(new Date());
        assertTrue(validator.check(person));

    }

    public void testNonNull() throws ValidationException {
        Validator validator = new Validator();

        System.out.println(validator.getValidationReport(person));
        assertTrue(validator.check(person));

        person.setAddress(null);
        System.out.println(validator.getValidationReport(person));
        assertFalse(validator.check(person));
    }

    public void testRegex() throws ValidationException {
        Validator validator = new Validator();

        System.out.println(validator.getValidationReport(person));
        assertTrue(validator.check(person));

        person.setPhoneNumber("01234567890_");
        System.out.println(validator.getValidationReport(person));
        assertFalse(validator.check(person));
    }

    /**
     * Test the validation by criteria on the given object
     *
     * @throws ValidationException
     */
    public void testValidationByCriteria() throws ValidationException {
        Validator validator = new Validator();
        List<Criteria> criteria = new ArrayList<>();

        criteria.add(Criteria.of("name").equal("mustapha"));
        criteria.add(Criteria.of("address").notNull());
        criteria.add(Criteria.of("age").greatherThan(4));
        criteria.add(Criteria.of("phoneNumber").matches("\\d{10}"));

        student.setName("mustapha");
        student.setAddress("wall street");
        student.setAge(5);
        student.setPhoneNumber("1234567890");

        Map<String, ValidationReport> validationReport = validator.getValidationReport(student, criteria);
        System.out.println(validationReport);
        assertTrue(validator.check(student, criteria));
    }

    /**
     * Test the validation by criteria on the given object
     *
     * @throws ValidationException
     */
    public void testarithmeticByCriteria() throws ValidationException {
        Validator validator = new Validator();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.of("age").required(false).greatherThan(4));

        student.setAge(5);
        System.out.println(validator.getValidationReport(student, criteria));
        assertTrue(validator.check(student, criteria));
    }
}

