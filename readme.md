Before Starting the Application

    Configure application.properties as per your local system

    //Create database employees

    spring.datasource.url=jdbc:mysql://localhost:3306/employees
    spring.datasource.username= #Your Username
    spring.datasource.password= #Your Password
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.jpa.show-sql = true
    spring.jpa.generate-ddl=true



    jpa.hibernate.ddl-auto=update
    jpa.hibernate.show-sql=true
    jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect