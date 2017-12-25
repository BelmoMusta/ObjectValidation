package musta.belmo.validation;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {

        Validator validator = new Validator();

        Person p = new Person();

        p.setAddress("");
        p.setName("fd");
        p.setLastName("");
        p.setAge(2);

        System.out.println(validator.validate(p));
        System.out.println(validator.getValidationReport(p));
    }


}



