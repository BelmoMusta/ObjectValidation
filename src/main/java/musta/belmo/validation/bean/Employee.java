package musta.belmo.validation.bean;

import musta.belmo.validation.annotation.Length;
import musta.belmo.validation.annotation.NotNull;
import musta.belmo.validation.annotation.Regex;

/**
 * An employee class wrapping fields to  be used as samples in validation by multiple annotations.
 */
public class Employee {
    @Length(max = 10, min = 3)
    private String name;
    @NotNull
    private String lastName;
    private String address;
    private int age;
    @Regex("\\d{10}")
    private String phoneNumber;
    private float salary;

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

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }
}

