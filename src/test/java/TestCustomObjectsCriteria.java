import bean.Matters;
import bean.Student;
import junit.framework.TestCase;
import musta.belmo.validation.criteria.Criteria;
import musta.belmo.validation.criteria.Criterion;
import musta.belmo.validation.exception.ValidationException;
import musta.belmo.validation.validator.CustomObjectValidator;

public class TestCustomObjectsCriteria extends TestCase {


    private Student student;


    public TestCustomObjectsCriteria() {
        super();
        student = new Student();
    }


}
