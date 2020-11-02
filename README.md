# AccountLookup
## Goal
Make a standalone app that will store personal accounts(username, password) locally.  

## Setup
1. This is an IntelliJ Maven project, see dependencies here: [pom.xml](https://github.com/zhenhua-cai/AccountLookup/blob/master/pom.xml)
2. This project uses MySQL database  
![Database-tables-relations](https://github.com/zhenhua-cai/AccountLookup/blob/master/src/main/resources/database_tables_realations.png?raw=true)  
MySQL setup 
 JDBC URL:
            ```
             jdbc:mysql://localhost:3306/account_lookup?useSSL=false&amp;serverTimezoon=UTC  
            ```  
 User name:  
    Username:```accountLookup```  
    Password:```accountLookup```  
    
 3. Spring and Hibernate framework, see configuration: [Spring Configuration](https://github.com/zhenhua-cai/AccountLookup/blob/master/src/main/resources/spring-config.xml)
            


