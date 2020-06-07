# Java Object Validation
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.belmomusta/validation/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.belmomusta/validation)


Developed in order to check if an instance of a class is valid according to the predicates
given as annotations on its fields.

The fields to be validated can also be put on a list of criteria, this can be done without the annotations aspect.

It prevents the classic control flow like the if-else blocks.

Validation process can be done using two ways:

1. Using annotations.
2. Using customized criteria.

## Use it as a maven dependency  :

```XML
   <dependency>
            <groupId>io.github.belmomusta</groupId>
            <artifactId>validation</artifactId>
            <version>1.1</version>
   </dependency>
```

## Examples of use :
##### 1. Using annotations

Suppose we have a `Person` class, and for all the instances, we want that the first name and the last
name be assigned and not left `null` :

 ```JAVA
class Person {

    @Validation
    private String name;

    @Validation
    private String lastName;
    // .... other fields...
    // .... getters and setters..
    }
    
   ```
  
  And we will create an instance, then we check if it is valid or not:
   
   ```JAVA 
     AnnotationValidator<Person> annotationValidator = new AnnotationValidator<>();
     Person person = new Person();
            person.setName("Mustapha");
            person.setLastName("Belmokhtar");

     boolean isValid = annotationValidator.checkValidation(person);
     ValidationReport validationReportItem = annotationValidator.getValidationReport(person); // gives the details of each field
   ```
   ##### Result :

  ```Console
   true
  {lastName=|found=String:Belmokhtar, expected={!=null}:[], valid=true|, name=|found=String:Mustapha, expected={!=null}:[], valid=true|}
   ```
   
   ##### Validation by Operators: 
```JAVA

   class Person{
   // ... others fields 
   @Validation(operator = Operator.GREATER_THAN, value = "18")
    private int age;
    // ... getters and setters ...
    }
   ```
   
  ```JAVA
     Person person = new Person(); 
     person.setAge(20);
     AnnotationValidator<Person> annotationValidator = new AnnotationValidator<>();
     boolean isValid = annotationValidator.check(person);
     ValidationReport validationReportItem = annotationValidator.getValidationReport(person);
   ```
   ##### Result :
   
   ```Console
   true
   {age=|found=Integer:20, expected={>}:[18], valid=true|}
   ```

   ##### 2.Using criteria :
   You can also perform validation  over objects using `Criteria`:
   ```JAVA
        CriteriaValidator<Student> studentCriteriaValidator = new CriteriaValidator<>();

       studentCriteriaValidator
                       .add(Criterion.of("name").is("mustapha"))
                       .add(Criterion.of("address").notNull())
                       .add(Criterion.of("age").greaterOrEquals(4))
                       .add(Criterion.of("phoneNumber").matches("\\d{10}"));

               student.setName("mustapha");
               student.setAddress("wall street");
               student.setAge(4);
               student.setPhoneNumber("1234567890");
               System.out.println(studentCriteriaValidator.getValidationReport(student));
               assertTrue(studentCriteriaValidator.check(student));
   ```
 ##### Output :
 ```Console 
 address=|found=String:wall street, expected={!=null}:[null], valid=true|, phoneNumber=|found=String:1234567890, expected={REGEX}:[\d{10}], valid=true|, name=|found=String:mustapha, expected={==}:[mustapha], valid=true|, age=|found=Integer:4, expected={>=}:[4], valid=true|}
```
##### Criteria over complex Objects: 
To perform validation by criteria over complex objects, you only have to specify the filed path of the wanted field, for example: 
```JAVA
  class Student {
   // omittd fields for brievety 
    private Matters matters
    //... getters and setters
    }
    
    //....
      class Matters {
    private double science;
    private double maths;
    private double languages;
    //...
    }
    //... 
```
##### Validation process: 

 ```JAVA 
    CriteriaValidator<Student> studentCriteriaValidator = new CriteriaValidator<>();
    studentCriteriaValidator.add(Criterion.of("matters.maths").is(20.0));
    Matters matters = new Matters();
    matters.setMaths(19.99);
    student.setMatters(matters);
    System.out.println(studentCriteriaValidator.getValidationReport(student));
 ```
 ###### Output 
 ```Console 
 {matters.maths=|found=Double:19.99, expected={<}:[20.0], valid=true|}
 ```

##### Validation overs arrays and collections:

```JAVA
    class Book {
    private int[] isbn;
    private TreeSet<String> keywords;

    // .. other fields
    //.. getters and setters
     }
```
##### Validation process:  
```JAVA
        Book book = new Book();
        CriteriaValidator<Book> bookCriteriaValidator = new CriteriaValidator<>();

       bookCriteriaValidator.add(Criterion.of("keywords").length(3))
                       .add(Criterion.of("isbn").length(11));

               int[] isbn = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
               Set<String> keywords = new TreeSet<>();
               keywords.add("science");
               keywords.add("earth");
               keywords.add("universe");
               book.setKeywords(keywords);
               book.setIsbn(isbn);
               System.out.println(bookCriteriaValidator.getValidationReport(book));
               assertTrue(bookCriteriaValidator.check(book));
```
 ##### Output
 ```Console 
 {keywords=|found=TreeSet:[earth, science, universe], expected={length}:[3], valid=true|, isbn=|found=int[]:[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11], expected={length}:[11], valid=true|}
 ```
