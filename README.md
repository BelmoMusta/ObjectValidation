# Java Object Validation

Developed in order to check if an instance of a class is valid according to the predicates
given as annotations on its fields.

The fields to be validated can also be put on a list of criteria, this can be done without the annotations aspect.

It prevents the classic control flow like the if-else blocks.

Validation process can be done using two ways:  
1. Using annotations.
2. Using customized criteria.

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
     AnnotationValidator annotationValidator = new AnnotationValidator();
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
  
     AnnotationValidator annotationValidator = AnnotationValidator.getInstance();
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
        Student student = new Student();
        CriteriaValidator criteriaValidator = CriteriaValidator.getInstance();
        Criteria criteria = new Criteria();
        criteria.setObject(student);
        criteria.add(Criterion.of("name").is("mustapha"));
        criteria.add(Criterion.of("address").notNull());
        criteria.add(Criterion.of("age").greaterOrEquals(4));
        criteria.add(Criterion.of("phoneNumber").matches("\\d{10}"));
        
        student.setName("mustapha");
        student.setAddress("wall street");
        student.setAge(4);
        student.setPhoneNumber("1234567890");
        System.out.println(criteriaValidator.getValidationReport(criteria));
        assertTrue(criteriaValidator.check(criteria));
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
  CriteriaValidator criteriaValidator = CriteriaValidator.getInstance();
        Criteria criteria = Criteria.of(student);
        criteria.add(Criterion.of("matters.maths").lessThan(20.0));
        Matters matters = new Matters();
        matters.setMaths(19.99);
        student.setMatters(matters);
        System.out.println(criteriaValidator.getValidationReport(criteria));
        assertTrue(criteriaValidator.check(criteria));
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
        CriteriaValidator criteriaValidator = CriteriaValidator.getInstance();
        Criteria criteria = Criteria.of(book);
        criteria.add(Criterion.of("keywords").length(3));
        criteria.add(Criterion.of("isbn").length(11));
        int[] isbn = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        Set<String> keywords = new TreeSet<>();
        keywords.add("science");
        keywords.add("earth");
        keywords.add("universe");
        
        book.setKeywords(keywords);
        book.setIsbn(isbn);

        System.out.println(criteriaValidator.getValidationReport(criteria));
        assertTrue(criteriaValidator.check(criteria));
```
 ##### Output
 ```Console 
 {keywords=|found=TreeSet:[earth, science, universe], expected={length}:[3], valid=true|, isbn=|found=int[]:[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11], expected={length}:[11], valid=true|}
 ```
 