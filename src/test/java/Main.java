import junit.framework.TestCase;
import musta.belmo.validation.criteria.Criteria;
import musta.belmo.validation.Validator;
import musta.belmo.validation.bean.Person;
import musta.belmo.validation.bean.ValidationReport;
import musta.belmo.validation.enumeration.Operator;
import musta.belmo.validation.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main extends TestCase {

    public void testValidationByAge() throws ValidationException {
        Validator validator = new Validator();
        Person person = new Person();
        person.setName("Mustapha");
        person.setLastName("Belmokhtar");
        person.setAge(-1);

        validator.check(person);
        validator.getValidationReport(person);

        assertFalse(validator.check(person));
        System.out.println(validator.getValidationReport(person));
        person.setAge(40);
        assertTrue(validator.check(person));
        System.out.println(validator.getValidationReport(person));
    }

    public void testValidationByaddress() throws ValidationException {
        Validator validator = new Validator();
        Person person = new Person();
        person.setName("mustapha");
        person.setLastName("Belmokhtar");
        person.setAge(40);

        assertFalse(validator.check(person));
        System.out.println(validator.getValidationReport(person));

        person.setAddress("a non-null address ! ");
        assertTrue(validator.check(person));
        System.out.println(validator.getValidationReport(person));
    }

    public void testRegexValidation() throws ValidationException {
        Validator validator = new Validator();
        Person person = new Person();
        person.setPhoneNumber("0123456789");
        assertTrue(validator.check(person));
        System.out.println(validator.getValidationReport(person));
    }

    public void testValidationByCriteria() throws ValidationException {

        List<Criteria> criteria = new ArrayList<>();

        criteria.add(Criteria.of("name").operator(Operator.EQUALS).value("mustapha"));
        Criteria crit = new Criteria();
        crit.field("address").operator(Operator.NOT_NULL);
        criteria.add(crit);
        criteria.add(Criteria.of("age").operator(Operator.GREATER_THAN).value(4));
        criteria.add(Criteria.of("phoneNumber").operator(Operator.REGEX).value("\\d{10}"));

        Validator validator = new Validator();
        Person person = new Person();

        person.setName("mustapha");
        person.setAddress("wall street");
        person.setAge(5);
        person.setPhoneNumber("1234567890");
        assertTrue(validator.check(person, criteria));
        Map<String, ValidationReport> validationReport = validator.getValidationReport(person, criteria);
        System.out.println(validationReport);
        assertEquals(validationReport.size(), criteria.size());
    }
}

