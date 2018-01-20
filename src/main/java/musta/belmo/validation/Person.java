package musta.belmo.validation;

/**
 * A Person class wrapping fields to  be used as samples in validation.
 */
public class Person {

    //@Validation
    String name;

    //@Validation
    String lastName;

    //@Validation
    String address;

    //@Validation(required = true,
      //      assertion = @Assertion(operator = Operator.GREATER, value = "1"))
    private int age;

    @Validation(assertion = @Assertion(operator = Operator.REGEX, value = "\\d{10}"))
    private String phoneNumber;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
