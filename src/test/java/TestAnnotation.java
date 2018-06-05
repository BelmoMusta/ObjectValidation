import bean.Book;
import bean.Person;
import junit.framework.TestCase;
import musta.belmo.validation.exception.ValidationException;
import musta.belmo.validation.validator.AnnotationValidator;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public class TestAnnotation extends TestCase {
    private Person person;

    public TestAnnotation() {
        person = new Person();
        person.setName("Mustapha");
        person.setLastName("Belmokhtar");
        person.setPhoneNumber("0123456789");
        person.setAddress("a non-null address ! ");
        person.setAge(25);
    }

    public void testArithmeticOperation() throws ValidationException {
        AnnotationValidator annotationValidator = AnnotationValidator.getInstance();
        person.setAge(-1);
        annotationValidator.getValidationReport(person);
        System.out.println(annotationValidator.getValidationReport(person));
        assertFalse(annotationValidator.check(person));
        person.setAge(40);
        System.out.println(annotationValidator.getValidationReport(person));
        assertTrue(annotationValidator.check(person));
    }

    /**
     * in this test case we suppose the birthDate mustEqual null but it mustEqual not required
     * the object remains valid whether we it assigned to a new date or left null
     *
     * @throws ValidationException if error
     */
    public void testNull() throws ValidationException {
        AnnotationValidator annotationValidator = AnnotationValidator.getInstance();
        assertTrue(annotationValidator.check(person));
        person.setBirthDate(new Date());
        assertTrue(annotationValidator.check(person));
    }

    public void testNonNull() throws ValidationException {
        AnnotationValidator annotationValidator = AnnotationValidator.getInstance();
        System.out.println(annotationValidator.getValidationReport(person));
        assertTrue(annotationValidator.check(person));
        person.setAddress(null);
        System.out.println(annotationValidator.getValidationReport(person));
        assertFalse(annotationValidator.check(person));
    }

    public void testRegex() throws ValidationException {
        AnnotationValidator annotationValidator = AnnotationValidator.getInstance();
        System.out.println(annotationValidator.getValidationReport(person));
        assertTrue(annotationValidator.check(person));
        person.setPhoneNumber("01234567890_");
        System.out.println(annotationValidator.getValidationReport(person));
        assertFalse(annotationValidator.check(person));
    }

    public void testEquals() throws ValidationException {
        Book book = new Book();
        book.setLanguages(Arrays.asList("Fr", "En"));
        book.setPrice(20);
        int[] isbn = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        Set<String> keywords = new TreeSet<>();
        keywords.add("science");
        keywords.add("earth");
        keywords.add("universe");
        book.setKeywords(keywords);
        book.setIsbn(isbn);
        AnnotationValidator annotationValidator = AnnotationValidator.getInstance();
        System.out.println(annotationValidator.getValidationReport(book));
        assertTrue(annotationValidator.check(book));
    }
}
