```
# JPA  
jpa:  
  hibernate:  
    ddl-auto: create-drop  
  open-in-view: false  
  properties:  
    hibernate:  
      dialect: org.hibernate.dialect.MySQL8Dialect  
  show-sql: true  
  
  
  
# RDB  
datasource:  
  driver-class-name: com.mysql.cj.jdbc.Driver  
  url: jdbc:mysql://localhost:3306/beforegoing?serverTimezone=Asia/Seoul&characterEncoding=UTF-8  
  username: sotogito  
  password: sotogito
```