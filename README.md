# Java Object Validation

Developed in order to check if an instance of a class is valid according to the predicates
given as annotations on its fields.

The fields to be validated can also be put on a list of criteria, this can be done without the annotatoins aspect.

It prevents the classic control flow like the if-else blocks.

## Example of use :

Suppose we have a Person class, and for all the instances, we want that the first name and the last
name be assigned and not left null :

 ```JAVA

class Person {

    @Validation(required = true)
    String name;

    @Validation(required = true)
    String lastName;
    // .... other fields...
    // .... getters and setters..
    }
   ```
   
   And we will create an instance, then we check if it is valid or not: 
   
   ```JAVA 
     Validator validator = new Validator();
     Person person = new Person();
            person.setName("mustapha");
            person.setLastName("Belmokhtar");

     boolean isValid = validator.checkValidation(person);
     boolean isValid = validator.getValidationReport(person); // gives the details of each field
   ```
   ## Result :

  ```Console
   true
   {name=|required=true, found=mustapha, expected={!=null}, valide=true|, lastName=|required=true, found=Belmokhtar, expected={!=null}, valide=true|}

   ## TODO:

   Validate objects with nested objects and lists/arrays/collections fields.