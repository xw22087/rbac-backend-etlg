rbac-backend-etlg
=================

The Back-end Program of the ETLG Project

A permission management system based on React + Spring Boot + Spring Security + JWT.

Project Introduction
====================

A general permission control system based on the RBAC (Role-Based Access Control) model. Users can use the management module with no or lower permission requirements to update personal information.

**Project Demo Address**

[http://13.229.229.154:9527/](http://13.229.229.154:9527/)

Old version repository of this project:

The old version of the front-end project was not built using a scaffolding tool. It was constructed using a React native project + Ant Design.

Back-end: [https://github.com/imyuanxiao/rbac-backend-old](https://github.com/imyuanxiao/rbac-backend-old)

Technology Selection
====================

Back-end
--------

*   Java Spring Boot
*   MySQL database
*   Redis database
*   MyBatis-Plus and MyBatis X auto-generation
*   Spring Security for access control
*   JWT token authentication
*   Swagger for API documentation generation
*   Utilizing libraries like Hutool

Implemented Features
====================

Back-end
--------

*   Database design
*   Parameter validation using annotations
    *   Group validation
*   Configuration of Swagger API documentation
*   Unified response structure
    *   Customized data response structure
    *   Custom business response codes
*   Global exception handling
    *   Custom exception classes
    *   Global exception handling class
*   Custom annotations
    *   Extending error codes and response messages with `@ExceptionCode`
    *   Bypassing unified response structure with `@NotResponseBody`
*   Configuration of JWT + Redis
    *   Building JWTUtil using Hutool
    *   Custom Redis utility class
*   Managing API permissions through annotations (operational permissions)
    *   Permission annotation `@Auth`
    *   Automatically adding permissions to the database through API scanning
*   Custom SQL interceptor (data permission)
    *   MyPaginationInterceptor
*   Spring Security login authentication
    *   Custom UserDetailService
    *   Login filter LoginFilter
    *   Authorization filter AuthFilter
    *   Authorization rule source (SecurityMetadataSource)
    *   Custom access decision manager (AccessDecisionManager)
    *   Custom authorization error handler (AccessDeniedHandler)
    *   Custom authentication exception handler class (AuthenticationEntryPoint)
    *   Configuration classes
*   Various functional modules

Planned Features
================

1.  WebSockets message notification
2.  User login status (online, offline, locked) heartbeat monitoring

Project Deployment
==================

Server: AWS EC2

Operating System: AWS Linux 2023

Environment Setup
-----------------

### Redis

1.  Search for software

bash

```bash
sudo yum search "redis"
```

2.  Install Redis 6

bash

```bash
sudo yum install redis6.x86_64
# or
sudo dnf install -y redis6
```

3.  Start Redis 6

bash

```bash
sudo systemctl start redis6
sudo systemctl enable redis6
```

4.  Modify configuration file and set password

bash

```bash
sudo vim /etc/redis6/redis6.conf
```

bash

```bash
# Set password
requirepass your_password

# Allow all IPs for testing purposes
bind 0.0.0.0
```

5.  Restart server

bash

```bash
sudo systemctl restart redis6
```

6.  View running processes and ports

bash

```bash
ps aux | grep redis
sudo lsof -i -P -n | grep <PID>
```

### MySQL

**Reference document: Install MySQL 8 on AWS Linux**

1.  Run the following commands in the terminal

bash

```bash
sudo wget https://dev.mysql.com/get/mysql80-community-release-el9-1.noarch.rpm 
sudo ls -lrt
sudo dnf install mysql80-community-release-el9-1.noarch.rpm
dnf repolist enabled | grep "mysql.*-community.*"
sudo dnf install mysql-community-server
sudo systemctl start mysqld
sudo mysql -V
```

2.  Enter MySQL, set the root password to empty, and enable remote login

sql

```sql
mysql -u root -p
use mysql;
# Set password to empty
UPDATE mysql.user SET authentication_string='' WHERE user='root' and host='localhost';
# Enable remote login
UPDATE mysql.user SET host = '%' WHERE user = 'root';
FLUSH PRIVILEGES;
```

If entering MySQL prompts for a password, first set it. Add `skip-grant-tables` to `my.cnf`.

bash

```bash
sudo vi /etc/my.cnf
```

3.  Exit MySQL, run the script to set the root password

bash

```bash
sudo mysql_secure_installation
```

4.  View running processes and ports

sql

```sql
ps aux | grep mysql
sudo lsof -i -P -n | grep <PID>
```

### Java

Reference document:

[Prerequisites on Amazon Linux EC2 - Amazon Neptune](https://docs.aws.amazon.com/neptune/latest/userguide/iam-auth-connect-prerq.html)

Install Java 8

bash

```bash
sudo yum install java-1.8.0-devel
```

### Nginx

1.  Install Nginx

bash

```bash
sudo yum install nginx -y
```

2.  Start Nginx

bash

```bash
sudo service nginx start
```

Deploying the Back-end
----------------------

1.  Use the Maven package command in IDEA to package the project into a JAR file (configure ports, database passwords, etc. before packaging).
    
2.  Upload the JAR file to the server and run it
    

bash

```bash
nohup java -jar your-jar-file.jar > yourJarLog.txt &
```

In the above image, 1455266 is the process ID (PID) of the project, which is the default
