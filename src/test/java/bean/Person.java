package bean;

import io.github.belmomusta.validation.annotation.Validation;
import io.github.belmomusta.validation.enumeration.Operator;

import java.util.Date;

/**
 * A Person class wrapping fields to  be used as samples in validation by Annotation.
 */
public class Person {

    @Validation
    private Date birthDate;

    @Validation
    private String name;

    @Validation
    private String lastName;

    @Validation(operator = Operator.NOT_NULL)
    private String address;

    @Validation(operator = Operator.GREATER_THAN, value = "1")
    private int age;

    @Validation(operator = Operator.REGEX, value = "\\d{10}")
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

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }
}
