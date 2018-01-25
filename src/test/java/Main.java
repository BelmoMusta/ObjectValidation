import junit.framework.TestCase;
import musta.belmo.validation.validator.Validator;
import musta.belmo.validation.bean.Person;
import musta.belmo.validation.bean.Student;
import musta.belmo.validation.criteria.Criteria;
import musta.belmo.validation.exception.ValidationException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        Validator validator = Validator.getInstance();

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
        Validator validator = Validator.getInstance();
        assertTrue(validator.check(person));
        person.setBirthDate(new Date());
        assertTrue(validator.check(person));

    }

    public void testNonNull() throws ValidationException {
        Validator validator = Validator.getInstance();

        System.out.println(validator.getValidationReport(person));
        assertTrue(validator.check(person));

        person.setAddress(null);
        System.out.println(validator.getValidationReport(person));
        assertFalse(validator.check(person));
    }

    public void testRegex() throws ValidationException {
        Validator validator = Validator.getInstance();

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
        Validator validator = Validator.getInstance();
        List<Criteria> criteria = new ArrayList<>();

        criteria.add(Criteria.of("name").is("mustapha"));
        criteria.add(Criteria.of("address").notNull());
        criteria.add(Criteria.of("age").greatherThan(4));
        criteria.add(Criteria.of("phoneNumber").matches("\\d{10}"));

        student.setName("mustapha");
        student.setAddress("wall street");
        student.setAge(5);
        student.setPhoneNumber("1234567890");

        System.out.println(validator.getValidationReport(student, criteria));
        assertTrue(validator.check(student, criteria));
    }

    /**
     * Test the greatherThan function of validation by criteria on the given object
     *
     * @throws ValidationException
     */
    public void testGreatherThanByCriteria() throws ValidationException {
        Validator validator = Validator.getInstance();

        List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.of("age").required(true).greatherThan(4));
        student.setAge(5);

        System.out.println(validator.getValidationReport(student, criteria));
        assertTrue(validator.check(student, criteria));
    }

    /**
     * Test the integer lessThan function of validation by criteria on the given object
     *
     * @throws ValidationException
     */
    public void testLessThanByCriteria() throws ValidationException {
        Validator validator = Validator.getInstance();
        List<Criteria> criteria = new ArrayList<>();

        criteria.add(Criteria.of("age").required(true).lessThan(4));

        student.setAge(10);
        System.out.println(validator.getValidationReport(student, criteria));
        assertFalse(validator.check(student, criteria));

        student.setAge(1);
        System.out.println(validator.getValidationReport(student, criteria));
        assertTrue(validator.check(student, criteria));
    }

    /**
     * Test the integer equality validation by criteria on the given object
     *
     * @throws ValidationException
     */
    public void testEqualityCriteria() throws ValidationException {
        Validator validator = Validator.getInstance();
        List<Criteria> criteria = new ArrayList<>();

        criteria.add(Criteria.of("age").required(true).is(4));
        student.setAge(10);
        System.out.println(validator.getValidationReport(student, criteria));
        assertFalse(validator.check(student, criteria));

        student.setAge(4);
        System.out.println(validator.getValidationReport(student, criteria));
        assertTrue(validator.check(student, criteria));
    }

    /**
     * Test the float equality validation by criteria on the given object
     *
     * @throws ValidationException
     */
    public void testFloatEqualityCriteria() throws ValidationException {
        Validator validator = Validator.getInstance();
        List<Criteria> criteria = new ArrayList<>();

        criteria.add(Criteria.of("mark").required(true).is(4.5f));
        student.setMark(10.01f);
        System.out.println(validator.getValidationReport(student, criteria));
        assertFalse(validator.check(student, criteria));

        student.setMark(4.5f);
        System.out.println(validator.getValidationReport(student, criteria));
        assertTrue(validator.check(student, criteria));
    }


    /**
     * Test the float equality validation by criteria on the given object
     *
     * @throws ValidationException
     */
    public void testLengthCriteria() throws ValidationException {
        Validator validator = Validator.getInstance();
        List<Criteria> criteria = new ArrayList<>();

        criteria.add(Criteria.of("name").required(true).length(0));
        student.setName("1");
        System.out.println(validator.getValidationReport(student, criteria));
        assertFalse(validator.check(student, criteria));
        student.setName("");
        System.out.println(validator.getValidationReport(student, criteria));
        assertTrue(validator.check(student, criteria));
    }
}

