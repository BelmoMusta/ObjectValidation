import musta.belmo.validation.*;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {

        Validator validator = new Validator();

        Person person = new Person();

        person.setName("hvbn");
        person.setLastName("gfjhghj");
        person.setAge(-1);

        System.out.println(validator.validate(person));
        System.out.println(validator.getValidationReport(person));
    }


}

