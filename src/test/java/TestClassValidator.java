import bean.Book;
import bean.Matters;
import bean.Student;
import io.github.belmomusta.validation.criteria.Criterion;
import io.github.belmomusta.validation.exception.ValidationException;
import io.github.belmomusta.validation.validator.CriteriaValidator;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class TestClassValidator extends TestCase {
    private Student student;
    private final CriteriaValidator<Student> studentCriteriaValidator;
    private final CriteriaValidator<Book> bookCriteriaValidator;
    private final Book book;

    public TestClassValidator() {
        super();
        studentCriteriaValidator = new CriteriaValidator<>();
        bookCriteriaValidator = new CriteriaValidator<>();
        student = new Student();
        book = new Book();
    }

    /**
     * TestCriteria the validation by criteria on the given object
     *
     * @throws ValidationException if error
     */
    public void testValidation() throws ValidationException {
        studentCriteriaValidator.add(Criterion.of("name").is("mustapha"))
                .add(Criterion.of("address").notNull())
                .add(Criterion.of("age").greaterOrEquals(4))
                .add(Criterion.of("phoneNumber").matches("\\d{10}"));

        student.setName("mustapha");
        student.setAddress("wall street");
        student.setAge(4);
        student.setPhoneNumber("1234567890");
        System.out.println(studentCriteriaValidator.getValidationReport(student));
        assertTrue(studentCriteriaValidator.check(student));
    }

    /**
     * TestCriteria the greaterThan function of validation by criteria on the given object
     *
     * @throws ValidationException if error
     */
    public void testGreatherThan() throws ValidationException {
        studentCriteriaValidator.add(Criterion.of("age").greaterThan(4));
        student.setAge(5);
        assertTrue(studentCriteriaValidator.check(student));
    }

    /**
     * TestCriteria the integer lessThan function of validation by criteria on the given object
     *
     * @throws ValidationException if error
     */
    public void testLessThan() throws ValidationException {
        studentCriteriaValidator.add(Criterion.of("age").lessThan(4));
        student.setAge(10);
        assertFalse(studentCriteriaValidator.check(student));
        student.setAge(1);
        assertTrue(studentCriteriaValidator.check(student));
    }

    /**
     * TestCriteria the integer equality validation by criteria on the given object
     *
     * @throws ValidationException if error
     */
    public void testEquality() throws ValidationException {
        studentCriteriaValidator.add(Criterion.of("age").is(4));
        student.setAge(10);
        assertFalse(studentCriteriaValidator.check(student));
        student.setAge(4);
        assertTrue(studentCriteriaValidator.check(student));
    }

    /**
     * TestCriteria the float equality validation by criteria on the given object
     *
     * @throws ValidationException if error
     */
    public void testFloatEquality() throws ValidationException {

        studentCriteriaValidator.add(Criterion.of("mark").is(4.5f));
        student.setMark(10.01f);
        assertFalse(studentCriteriaValidator.check(student));
        student.setMark(4.5f);
        assertTrue(studentCriteriaValidator.check(student));
    }

    /**
     * TestCriteria the float equality validation by criteria on the given object
     *
     * @throws ValidationException if error
     */
    public void testLength() throws ValidationException {
        studentCriteriaValidator.add(Criterion.of("name").length(4));
        student.setName("1");
        assertFalse(studentCriteriaValidator.check(student));
        student.setName("1234");
        assertTrue(studentCriteriaValidator.check(student));
    }

    /**
     * TestCustomObjectsCriteria the validation by criteria on the given object
     *
     * @throws ValidationException if error
     */
    public void testCustomObjectValidation() throws ValidationException {

        studentCriteriaValidator.add(Criterion.of("matters.maths").is(20.0));
        Matters matters = new Matters();
        matters.setMaths(20.0);
        student.setMatters(matters);
        System.out.println(studentCriteriaValidator.getValidationReport(student));
        assertTrue(studentCriteriaValidator.check(student));
    }

    public void testArraysAndCollections() throws ValidationException {
        bookCriteriaValidator.add(Criterion.of("keywords").length(3))
                .add(Criterion.of("isbn").length(11));

        int[] isbn = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        Set<String> keywords = new TreeSet<>();
        keywords.add("science");
        keywords.add("earth");
        keywords.add("universe");
        book.setKeywords(keywords);
        book.setIsbn(isbn);
        System.out.println(bookCriteriaValidator.getValidationReport(book));
        assertTrue(bookCriteriaValidator.check(book));
    }

    /**
     * TestCustomObjectsCriteria the validation by criteria on the given object
     *
     * @throws ValidationException if error
     */
    public void testArrayAccess() throws ValidationException {
        bookCriteriaValidator.add(Criterion.of("isbn[1]").is(2));
        int[] isbn = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        book.setIsbn(isbn);
        System.out.println(bookCriteriaValidator.getValidationReport(book));
        assertTrue(bookCriteriaValidator.check(book));
    }

    /**
     * TestCustomObjectsCriteria the validation by criteria on the given object
     *
     * @throws ValidationException if error
     */
    @Test(expected = ValidationException.class)
    public void testListAccess() throws ValidationException {
        bookCriteriaValidator.add(Criterion.of("keywords[1]").length(5));
        Set<String> keywords = new LinkedHashSet<>();
        keywords.add("science");
        keywords.add("earth");
        keywords.add("universe");
        book.setKeywords(keywords);
        System.out.println(bookCriteriaValidator.getValidationReport(book));
        assertTrue(bookCriteriaValidator.check(book));
    }
}
