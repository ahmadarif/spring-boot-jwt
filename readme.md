# How to run this project?
    - clone https://github.com/ahmadarif/spring-boot-jwt
    - cd spring-boot-jwt
    - mvn clean install
    - mvn spring-boot:run

# How to test?
## Login
    POST URL: http://localhost:8080/auth/login
        
    Headers:
        Content-Type: application/x-www-form-urlencoded
        
    Body:
    {
        "username": "user",
        "password": "123"
    }

### Get User Information
    GET URL: http://localhost:8080/user
        
    Headers:
        Authorization: Bearer <YOUR_TOKEN>
        
## Logout
    POST URL: http://localhost:8080/auth/logout
            
    Headers:
        Authorization: Bearer <YOUR_TOKEN>