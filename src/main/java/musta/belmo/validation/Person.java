package musta.belmo.validation;

public class Person {

  ///  @Validation(required = true)
    String name;

    //@Validation(required = true)
    String lastName;

   // @Validation(required = true)
    String address;

    @Validation(required = true,
            assertion = @Assertion(operator = Operator.GREATER, value = "1"))
    private int age;


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
}
