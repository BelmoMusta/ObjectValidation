import junit.framework.TestCase;
import musta.belmo.validation.bean.Employee;
import musta.belmo.validation.bean.Person;
import musta.belmo.validation.bean.Student;
import musta.belmo.validation.criteria.Criteria;
import musta.belmo.validation.criteria.Criterion;
import musta.belmo.validation.exception.ValidationException;
import musta.belmo.validation.validator.Validator;

import java.util.Date;

public class Main extends TestCase {
    private Person person;
    private Student student;
    private Employee employee;


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
     * @throws ValidationException if error
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
     * @throws ValidationException if error
     */
    public void testValidationByCriteria() throws ValidationException {
        Validator validator = Validator.getInstance();
        Criteria criteria = new Criteria();
        criteria.setObject(student);

        criteria.add(Criterion.of("name").is("mustapha").required());
        criteria.add(Criterion.of("address").notNull());
        criteria.add(Criterion.of("age").greatherThan(4));
        criteria.add(Criterion.of("phoneNumber").matches("\\d{10}"));

        student.setName("mustapha");
        student.setAddress("wall street");
        student.setAge(5);
        student.setPhoneNumber("1234567890");

         System.out.println(validator.getValidationReport(student, criteria));
        assertTrue(validator.check(criteria));
    }

    /**
     * Test the greatherThan function of validation by criteria on the given object
     *
     * @throws ValidationException if error
     */
    public void testGreatherThanByCriteria() throws ValidationException {
        Validator validator = Validator.getInstance();

        Criteria criteria = new Criteria();
        criteria.add(Criterion.of("age").required().greatherThan(4));
        student.setAge(5);

       // System.out.println(validator.getValidationReport(student, criteria));
        assertTrue(validator.check(student, criteria));
    }

    /**
     * Test the integer lessThan function of validation by criteria on the given object
     *
     * @throws ValidationException if error
     */
    public void testLessThanByCriteria() throws ValidationException {
        Validator validator = Validator.getInstance();
        Criteria criteria = new Criteria();

        criteria.add(Criterion.of("age").required().lessThan(4));

        student.setAge(10);
      //  System.out.println(validator.getValidationReport(student, criteria));
        assertFalse(validator.check(student, criteria));

        student.setAge(1);
        //System.out.println(validator.getValidationReport(student, criteria));
        assertTrue(validator.check(student, criteria));
    }

    /**
     * Test the integer equality validation by criteria on the given object
     *
     * @throws ValidationException if error
     */
    public void testEqualityCriteria() throws ValidationException {
        Validator validator = Validator.getInstance();
        Criteria criteria = new Criteria();

        criteria.add(Criterion.of("age").required().is(4));
        student.setAge(10);
       // System.out.println(validator.getValidationReport(student, criteria));
        assertFalse(validator.check(student, criteria));

        student.setAge(4);
       // System.out.println(validator.getValidationReport(student, criteria));
        assertTrue(validator.check(student, criteria));
    }

    /**
     * Test the float equality validation by criteria on the given object
     *
     * @throws ValidationException if error
     */
    public void testFloatEqualityCriteria() throws ValidationException {
        Validator validator = Validator.getInstance();
        Criteria criteria = new Criteria();

        criteria.add(Criterion.of("mark").required().is(4.5f));
        student.setMark(10.01f);
        //System.out.println(validator.getValidationReport(student, criteria));
        assertFalse(validator.check(student, criteria));

        student.setMark(4.5f);
       // System.out.println(validator.getValidationReport(student, criteria));
        assertTrue(validator.check(student, criteria));
    }


    /**
     * Test the float equality validation by criteria on the given object
     *
     * @throws ValidationException if error
     */
    public void testLengthCriteria() throws ValidationException {
        Validator validator = Validator.getInstance();
        Criteria criteria = new Criteria();

        criteria.add(Criterion.of("name").required().length(4));
        student.setName("1");
        //System.out.println(validator.getValidationReport(student, criteria));
        assertFalse(validator.check(student, criteria));
        student.setName("1234");
        //System.out.println(validator.getValidationReport(student, criteria));
        assertTrue(validator.check(student, criteria));
    }

    /**
     * Test the float equality validation by criteria on the given object
     *
     * @throws ValidationException if error
     */
    public void testMultipleAnnotation() throws ValidationException {
        employee = new Employee();
        Validator validator = Validator.getInstance();
        Criteria cr = new Criteria();
        cr.add(Criterion.of("name").notNull().required());
        System.out.println(validator.getValidationReport(employee));
        assertFalse(validator.check(employee, cr));
        employee.setName("1234");
        System.out.println(validator.getValidationReportMultiAnnotations(employee));
        assertTrue(validator.check(employee, cr));
    }
}


