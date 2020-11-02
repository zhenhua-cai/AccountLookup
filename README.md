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
            
## Usage
#### Login/Register window:
![loginpage](https://github.com/zhenhua-cai/AccountLookup/blob/master/src/main/resources/loginwindow.png?raw=true)  
when user run this app, it asks for user credentials. if there isn't an available account, user is able to register an new acount by clicking ```Register```button  
![registerpage](https://github.com/zhenhua-cai/AccountLookup/blob/master/src/main/resources/registerwindow.png?raw=true)  

#### Main display window:
![displaypage](https://github.com/zhenhua-cai/AccountLookup/blob/master/src/main/resources/displaywindow.png?raw=true)  
From this display window, user is able to show/search previously store accounts, these accounts are only associate with logged in user. which mean
current user is NOT able to see other users' data.
![menu](https://github.com/zhenhua-cai/AccountLookup/blob/master/src/main/resources/menu.png?raw=true)  
From the menu items, user is able to perform different actions, such add new account, search account, update account, and delete account.

