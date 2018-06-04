import bean.Book;
import bean.Employee;
import bean.Matters;
import bean.Student;
import junit.framework.TestCase;
import musta.belmo.validation.criteria.Criteria;
import musta.belmo.validation.criteria.Criterion;
import musta.belmo.validation.exception.ValidationException;
import musta.belmo.validation.validator.AnnotationValidator;
import musta.belmo.validation.validator.CriteriaValidator;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class TestCriteria extends TestCase {


    private Student student;
    private Employee employee;

    public TestCriteria() {
        super();
        employee = new Employee();
        student = new Student();
    }

    /**
     * TestCriteria the validation by criteria on the given object
     *
     * @throws ValidationException if error
     */
    public void testValidation() throws ValidationException {
        CriteriaValidator criteriaValidator = CriteriaValidator.getInstance();
        Criteria criteria = new Criteria();
        criteria.setObject(student);

        criteria.add(Criterion.of("name").is("mustapha").required());
        criteria.add(Criterion.of("address").notNull());
        criteria.add(Criterion.of("age").greaterOrEquals(4).required());
        criteria.add(Criterion.of("phoneNumber").matches("\\d{10}"));

        student.setName("mustapha");
        student.setAddress("wall street");
        student.setAge(4);
        student.setPhoneNumber("1234567890");

        System.out.println(criteriaValidator.getValidationReport(criteria));
        assertTrue(criteriaValidator.check(criteria));
    }

    /**
     * TestCriteria the greaterThan function of validation by criteria on the given object
     *
     * @throws ValidationException if error
     */
    public void testGreatherThan() throws ValidationException {
        CriteriaValidator criteriaValidator = CriteriaValidator.getInstance();

        Criteria criteria = new Criteria();
        criteria.setObject(student);
        criteria.add(Criterion.of("age").required().greaterThan(4));
        student.setAge(5);

        // System.out.println(criteriaValidator.getValidationReport(student, criteria));
        assertTrue(criteriaValidator.check(criteria));
    }

    /**
     * TestCriteria the integer lessThan function of validation by criteria on the given object
     *
     * @throws ValidationException if error
     */
    public void testLessThan() throws ValidationException {
        CriteriaValidator criteriaValidator = CriteriaValidator.getInstance();
        Criteria criteria = new Criteria();
        criteria.setObject(student);
        criteria.add(Criterion.of("age").required().lessThan(4));

        student.setAge(10);
        //  System.out.println(criteriaValidator.getValidationReport(student, criteria));
        assertFalse(criteriaValidator.check(criteria));

        student.setAge(1);
        //System.out.println(criteriaValidator.getValidationReport(student, criteria));
        assertTrue(criteriaValidator.check(criteria));
    }

    /**
     * TestCriteria the integer equality validation by criteria on the given object
     *
     * @throws ValidationException if error
     */
    public void testEquality() throws ValidationException {
        CriteriaValidator criteriaValidator = CriteriaValidator.getInstance();
        Criteria criteria = new Criteria();

        criteria.add(Criterion.of("age").required().is(4));
        student.setAge(10);
        criteria.setObject(student);
        // System.out.println(criteriaValidator.getValidationReport(student, criteria));
        assertFalse(criteriaValidator.check(criteria));

        student.setAge(4);
        criteria.setObject(student);
        // System.out.println(criteriaValidator.getValidationReport(student, criteria));
        assertTrue(criteriaValidator.check(criteria));
    }

    /**
     * TestCriteria the float equality validation by criteria on the given object
     *
     * @throws ValidationException if error
     */
    public void testFloatEquality() throws ValidationException {
        CriteriaValidator criteriaValidator = CriteriaValidator.getInstance();
        Criteria criteria = new Criteria();
        criteria.setObject(student);
        criteria.add(Criterion.of("mark").required().is(4.5f));
        student.setMark(10.01f);
        //System.out.println(criteriaValidator.getValidationReport(student, criteria));
        assertFalse(criteriaValidator.check(criteria));

        student.setMark(4.5f);
        // System.out.println(criteriaValidator.getValidationReport(student, criteria));
        assertTrue(criteriaValidator.check(criteria));
    }


    /**
     * TestCriteria the float equality validation by criteria on the given object
     *
     * @throws ValidationException if error
     */
    public void testLength() throws ValidationException {
        CriteriaValidator criteriaValidator = CriteriaValidator.getInstance();
        Criteria criteria = new Criteria();
        criteria.setObject(student);

        criteria.add(Criterion.of("name").required().length(4));
        student.setName("1");
        //System.out.println(criteriaValidator.getValidationReport(student, criteria));
        assertFalse(criteriaValidator.check(criteria));
        student.setName("1234");
        //System.out.println(criteriaValidator.getValidationReport(student, criteria));
        assertTrue(criteriaValidator.check(criteria));
    }

    /**
     * TestCustomObjectsCriteria the validation by criteria on the given object
     *
     * @throws ValidationException if error
     */
    public void testCustomObjectValidation() throws ValidationException {
        CriteriaValidator criteriaValidator = CriteriaValidator.getInstance();
        Criteria criteria = Criteria.of(student);

        criteria.add(Criterion.of("matters.maths").is(20.0).required());
        Matters matters = new Matters();
        matters.setMaths(20.0);
        student.setMatters(matters);
        System.out.println(criteriaValidator.getValidationReport(criteria));
        assertTrue(criteriaValidator.check(criteria));
    }

    public void testArraysAndCollections() throws ValidationException {
        Book book = new Book();
        CriteriaValidator criteriaValidator = CriteriaValidator.getInstance();
        Criteria criteria = Criteria.of(book);
        criteria.add(Criterion.of("keywords").length(3).required());
        criteria.add(Criterion.of("isbn").length(11).required());
        int[] isbn = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        Set<String> keywords = new TreeSet<>();
        keywords.add("science");
        keywords.add("earth");
        keywords.add("universe");

        book.setKeywords(keywords);
        book.setIsbn(isbn);

        System.out.println(criteriaValidator.getValidationReport(criteria));
        assertTrue(criteriaValidator.check(criteria));


    }
}


