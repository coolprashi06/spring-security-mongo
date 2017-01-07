# spring-security-mongo-sample
J2EE Web Application secured by Spring Security. Authentication data maintained in Mongo DB.

I am using RESTEasy implementation for developing RESTful resources. User and role data is maintained in Mongo DB.

**spring-security framework** is leveraged to secure RESTful resources. To include Spring Security in your application include following maven dependencies to your project pom:
1. spring-security-core
2. spring-security-web
3. spring-security-config

spring-data-mongodb library is used to provide Spring integration with MongoDB. For more information on this Spring project at http://projects.spring.io/spring-data-mongodb/
And don't forget to add mongo-java-driver to allow Java to integrate with MongoDB.

this example leverages `MongoRepository` interface to define multiple repositories mapped to Java DTO classes.
For more info on MongoRepository refer http://docs.spring.io/spring-data/mongodb/docs/1.2.0.RELEASE/reference/html/mongo.repositories.html

Mongo DB collection structure:

``` 
T_USER
    username
    firstName
    lastName
    name
    password
    roles (array with values i.e. ROLE_USER, ROLE_ADMIN)
    
```

Mongo collection to Java DTO mapping can be found in **com.prashast.dto.User** class.

##### In **com.prashast.rest** package you'd find following RESTful resources
1. **AdminRest** - this contains resources under /admin path and would only be available for users with role ROLE_ADMIN
2. **UnsecuredRest** - this contains resources under /unsecured path and would be available for any user irrespective of role.
3. **SecuredRest** - this contains resources under /account path and would be available for users with role ROLE_USER.

### Steps to configure and apply Spring Security to your application

1. Create SecurityConfig for your application
2. Configure UserDetailsService under SecurityConfig
3. Or Configure AuthenticationProvider under SecurityConfig
4. Create Mongo Configuration
5. Configure HttpSecurity for your RESTful resources
6. Add SpringSecurity Filter to your web.xml
7. Create different profiles for different authentication providers
8. Activate Spring Profile for your web application


#### Create SecurityConfig for your application

```
Create Java class extending WebSecurityConfigurerAdapter to create SecurityConfiguration.

And annotate your class with following annotations:
@Configuration
@EnableWebSecurity
```

#### Configure UserDetailsService under SecurityConfig

```
1. Create a class implementing interface UserDetailsService.
2. Implement method loadUserByUsername(String username)
    (a) Make a call to your repository to retrieve user using the username
    (b) Add user authorities to a collection(list/set)
    (c) Create org.springframework.security.core.userdetails.User object with the above information and return it.

```

#### Or Configure AuthenticationProvider under SecurityConfig

```
This can be used as an alternative to UserDetailsService and this one provides you advantage over retrieving username & password passed during login and make decisions accordingly.
For e.g. we need to have test environment where user can login with some default password. 

Here we can take advantage of AuthenticationProvider.

1. Create class implementing interface AuthenticationProvider
2. Implement method authenticate(Authentication authentication)
    (a) Retrieve username and password from authentication object
    (b) Do authentication things and assign some roles.
    (c) Return object of type Authentication created with username, password and authorities. 

```

#### Create Mongo Configuration

```
1. Create class extending AbstractMongoConfiguration
2. Annotate class with @Configuration and @EnableMongoRepositories with package name in its constructor denoting the package where all java-mongo repository classes are maintained.
3. Override following methods:
    (a) getDatabaseName() - this should return the name of DB holding collections
    (b) mongo() - this should return the MongoClient object
    (c) getMappingBasePackage() - this should return the package holding java-mongo repository classes

```

#### Configure HttpSecurity for your RESTful resources

```
1. Override method configure(HttpSecurity http)
2. Define in this method following:
    (a) requests if needs to be authorized
    (b) requests if needs to be authorized and be available to users with certain roles
    (c) form login, form logout, form login success, form login error pages
    (d) csrf enable/disable
    etc..

```

#### Add SpringSecurity Filter to your web.xml

```
Add below section to your application web.xml file:

    <filter>
        <filter-name>springSecurityFilterChain</filter-name>
        <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
    </filter>

    <filter-mapping>
        <filter-name>springSecurityFilterChain</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```

#### Create different profiles for different authentication providers

```
You can have multiple authentication providers config in your application but you want to use only one of them at a time depending on the environment.
In order to do so, you can annotate different providers with appropriate profiles.

for e.g. use @Profile("test") on authentication provider class which would be used for test environment. And annotate @Profile("live") on authentication provider class which would be used on production environment.
```

#### Activate Spring Profile for your web application

```
Add following context param to your web.xml to active appropriate spring profile during deployment on container:

    <context-param>
        <param-name>spring.profiles.active</param-name>
        <param-value>live</param-value>
    </context-param>
    
Multiple profiles can be separated with commas.
```

You're all set now. Refer to code base for understanding.
