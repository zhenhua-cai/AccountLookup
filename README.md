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
 Username:```accountLookup```  
 Password:```accountLookup```  
 MySQL scripts:
 ```
    drop database if exists account_lookup;
    
    create database if not exists account_lookup; 
    use account_lookup;
    
    drop table if exists user;
    create table user(
    	id int primary key not null auto_increment,
        username varchar(128) not null,
        `password` varchar(256) not null,
        unique key(username)
    )engine=InnoDB auto_increment=1 default charset =latin1;
    
    drop table if exists account;
    create table account(
    	id int primary key not null auto_increment,
        title varchar(128) not null,
        username varchar(128) not null,
        `password` varchar(256) not null,
        email varchar(128) default null,
        lastUpdatedTime datetime not null,
        user_id int not null,
        constraint `FK_userid` foreign key (user_id) references user(id),
        unique key(title, email)
    )engine=InnoDB auto_increment=1 default charset=latin1;

```
 3. Spring and Hibernate framework, see configuration: [Spring Configuration](https://github.com/zhenhua-cai/AccountLookup/blob/master/src/main/resources/spring-config.xml)
            


