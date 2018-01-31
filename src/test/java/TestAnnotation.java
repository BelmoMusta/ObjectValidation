import junit.framework.TestCase;
import musta.belmo.validation.bean.Person;
import musta.belmo.validation.exception.ValidationException;
import musta.belmo.validation.validator.AnnotationValidator;

import java.util.Date;

public class TestAnnotation extends TestCase {
    private Person person;

    public TestAnnotation() {
        super();
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
}


