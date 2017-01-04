# spring-security-mongo-sample
J2EE Web Application secured by Spring Security. Authentication via Mongo DB.

I am using RESTEasy implementation for developing RESTful resources. User and role data is maintained in Mongo DB.
spring-security framework is leveraged to secure RESTful resources.

Mongo DB collection structure:

``` 
T_USER
    username
    firstName
    lastName
    name
    password
    roles
    
```

More to come...