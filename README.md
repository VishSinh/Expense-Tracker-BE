
# ExpenEase - Expense Tracker BE

Backend for ExpenEase based on SpringBoot.


## API Reference

Documentation for APIs in Postman

#### [Postman Link](https://www.postman.com/joint-operations-engineer-19861059/workspace/expense-tracker/collection/29105784-c41c0db1-663a-4190-8f15-d422d2106f4c?action=share&creator=29105784)



## Installation

### Install the project

Move to project directory

```bash
    cd Expense-Tracker-BE 
```

Add an application.properties file in src/main/resources/

Example for application.properties -

```
spring.datasource.url=jdbc:mysql://localhost:3306/your-db-name
spring.datasource.username=vyour-username
spring.datasource.password=your-password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
server.servlet.context-path=/api
app.jwt.secret=SECRET-KEY-1
app.salt=SECRET-KEY-2
app.jwt.expiration=EXPIRY-TIME
```

Run the project
```
    mvn spring-boot:run -e  
```

## Tech Stack

**Backend:** Spring Boot

**Database:** MySql

