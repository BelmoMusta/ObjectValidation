package bean;

/**
 * A Student class wrapping fields to  be used as samples in validation by Criteria.
 */
public class Student {
    private String name;
    private String lastName;
    private String address;
    private int age;
    private String phoneNumber;
    private float mark;
    private Matters matters;

    public Matters getMatters() {
        return matters;
    }

    public void setMatters(Matters matters) {
        this.matters = matters;
    }

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

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }
}
