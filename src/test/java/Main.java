import junit.framework.TestCase;
import musta.belmo.validation.Validator;
import musta.belmo.validation.bean.Person;
import musta.belmo.validation.bean.Student;
import musta.belmo.validation.bean.ValidationReport;
import musta.belmo.validation.criteria.Criteria;
import musta.belmo.validation.exception.ValidationException;

import java.util.ArrayList;
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
        Criteria crit = Criteria.of("address").notNull();
        criteria.add(crit);
        criteria.add(Criteria.of("age").greatherThan(4));
        criteria.add(Criteria.of("phoneNumber").regex("\\d{10}"));

        student.setName("mustapha");
        student.setAddress("wall street");
        student.setAge(5);
        student.setPhoneNumber("1234567890");

        Map<String, ValidationReport> validationReport = validator.getValidationReport(student, criteria);
        System.out.println(validationReport);
        assertTrue(validator.check(student, criteria));
    }
}

