# Project Setup Guide - Complete Step by Step

This guide provides complete instructions for setting up and running this Spring Boot project in VS Code, IntelliJ IDEA, and Spring Tool Suite (STS).

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Project Overview](#project-overview)
3. [Initial Setup](#initial-setup)
4. [VS Code Setup](#vs-code-setup)
5. [IntelliJ IDEA Setup](#intellij-idea-setup)
6. [Spring Tool Suite (STS) Setup](#spring-tool-suite-sts-setup)
7. [Database Configuration](#database-configuration)
8. [Running the Project](#running-the-project)
9. [Troubleshooting](#troubleshooting)

---

## Prerequisites

### Required Software

1. **Java Development Kit (JDK) 21**
   - Download from: https://www.oracle.com/java/technologies/downloads/
   - Or use OpenJDK: https://adoptium.net/
   - Verify installation:
     ```bash
     java -version
     javac -version
     ```

2. **Apache Maven 3.6+**
   - Download from: https://maven.apache.org/download.cgi
   - Verify installation:
     ```bash
     mvn -version
     ```

3. **MySQL Server 8.0+**
   - Download from: https://dev.mysql.com/downloads/mysql/
   - Verify installation:
     ```bash
     mysql --version
     ```

4. **Git** (for cloning repository)
   - Download from: https://git-scm.com/downloads
   - Verify installation:
     ```bash
     git --version
     ```

### IDE Options (Choose One)

- **VS Code** with Java extensions
- **IntelliJ IDEA** (Community or Ultimate)
- **Spring Tool Suite (STS)** or **Eclipse IDE for Enterprise Java**

---

## Project Overview

**Project Name:** JavaSpringbootTableMigration  
**Framework:** Spring Boot 4.0.0  
**Java Version:** 21  
**Build Tool:** Maven  
**Database:** MySQL  
**Migration Tool:** Flyway  

**Project Structure:**
```
JavaSpringbootTableMigration/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── JavaSpringbootTableMigration/
│   │   │       └── JavaSpringbootTableMigration/
│   │   │           ├── controller/     # REST Controllers
│   │   │           ├── service/        # Service Interfaces
│   │   │           ├── service/impl/   # Service Implementations
│   │   │           ├── dao/           # Repository Interfaces
│   │   │           ├── model/         # Entity Classes
│   │   │           ├── dto/           # Data Transfer Objects
│   │   │           └── exception/     # Exception Handlers
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/          # Flyway Migrations
│   └── test/
├── pom.xml
└── README.md
```

---

## Initial Setup

### Step 1: Clone or Download the Project

```bash
# If using Git
git clone https://github.com/Msharma673/JavaSpringbootTableMigration.git
cd JavaSpringbootTableMigration

# Or download and extract the ZIP file
```

### Step 2: Verify Prerequisites

```bash
# Check Java version (should be 21)
java -version

# Check Maven version
mvn -version

# Check MySQL version
mysql --version
```

### Step 3: Set Up MySQL Database

1. **Start MySQL Server:**
   ```bash
   # On macOS/Linux
   sudo systemctl start mysql
   # Or
   brew services start mysql
   
   # On Windows
   # Start MySQL service from Services
   ```

2. **Create Database (Optional - will be created automatically):**
   ```bash
   mysql -u root -p
   ```
   ```sql
   CREATE DATABASE IF NOT EXISTS springboot_crud_db;
   USE springboot_crud_db;
   EXIT;
   ```

3. **Update Database Credentials:**
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/springboot_crud_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=YOUR_PASSWORD
   ```

---

## VS Code Setup

### Step 1: Install VS Code

Download from: https://code.visualstudio.com/

### Step 2: Install Required Extensions

1. Open VS Code
2. Go to Extensions (Ctrl+Shift+X / Cmd+Shift+X)
3. Install the following extensions:
   - **Extension Pack for Java** (by Microsoft)
   - **Spring Boot Extension Pack** (by VMware)
   - **Maven for Java** (by Microsoft)
   - **MySQL** (by cweijan)

### Step 3: Open Project

1. **File → Open Folder**
2. Select the `JavaSpringbootTableMigration` folder
3. VS Code will automatically detect it as a Java project

### Step 4: Configure Java

1. Press `Ctrl+Shift+P` (or `Cmd+Shift+P` on Mac)
2. Type: `Java: Configure Java Runtime`
3. Select JDK 21

### Step 5: Build Project

1. Open Terminal in VS Code (`Ctrl+`` ` or `View → Terminal`)
2. Run:
   ```bash
   mvn clean install
   ```

### Step 6: Run Project

**Method 1: Using Maven**
```bash
mvn spring-boot:run
```

**Method 2: Using VS Code Run Button**
1. Open `JavaSpringbootTableMigrationApplication.java`
2. Click "Run" button above `main` method
3. Or press `F5` to debug

**Method 3: Using Command Palette**
1. Press `Ctrl+Shift+P`
2. Type: `Java: Run Java`
3. Select the main class

### Step 7: Verify Application is Running

- Open browser: http://localhost:8080/api/employees
- You should see JSON response with employee data

---

## IntelliJ IDEA Setup

### Step 1: Install IntelliJ IDEA

- **Community Edition** (Free): https://www.jetbrains.com/idea/download/
- **Ultimate Edition** (Paid, 30-day trial)

### Step 2: Open Project

1. **File → Open**
2. Select the `JavaSpringbootTableMigration` folder
3. IntelliJ will detect it as a Maven project
4. Click "Trust Project" if prompted

### Step 3: Configure Project SDK

1. **File → Project Structure** (Ctrl+Alt+Shift+S)
2. Under **Project Settings → Project**
3. Set **SDK** to Java 21
4. Set **Language Level** to 21

### Step 4: Import Maven Dependencies

1. IntelliJ should automatically import Maven dependencies
2. If not, click **Maven** tab on the right side
3. Click **Reload All Maven Projects** icon

### Step 5: Enable Annotation Processing

1. **File → Settings** (Ctrl+Alt+S)
2. **Build, Execution, Deployment → Compiler → Annotation Processors**
3. Check **Enable annotation processing**

### Step 6: Configure Database

1. **View → Tool Windows → Database**
2. Click **+** → **Data Source → MySQL**
3. Enter connection details:
   - Host: `localhost`
   - Port: `3306`
   - Database: `springboot_crud_db`
   - User: `root`
   - Password: `YOUR_PASSWORD`
4. Click **Test Connection**
5. Click **OK**

### Step 7: Build Project

1. **Build → Build Project** (Ctrl+F9)
2. Or use Maven:
   ```bash
   mvn clean install
   ```

### Step 8: Run Project

**Method 1: Using Run Button**
1. Open `JavaSpringbootTableMigrationApplication.java`
2. Click green **Run** button next to `main` method
3. Or right-click → **Run 'JavaSpringbootTableMigrationApplication'**

**Method 2: Using Maven**
1. Open **Maven** tool window
2. Expand **JavaSpringbootTableMigration → Plugins → spring-boot**
3. Double-click **spring-boot:run**

**Method 3: Using Terminal**
```bash
mvn spring-boot:run
```

### Step 9: Verify Application

- Open browser: http://localhost:8080/api/employees
- Check **Run** tool window for logs

---

## Spring Tool Suite (STS) Setup

### Step 1: Install STS

Download from: https://spring.io/tools

### Step 2: Open Project

1. **File → Import**
2. Select **Maven → Existing Maven Projects**
3. Click **Next**
4. Browse to `JavaSpringbootTableMigration` folder
5. Click **Finish**

### Step 3: Configure Java Build Path

1. Right-click project → **Properties**
2. **Java Build Path → Libraries**
3. Ensure **Modulepath** has JDK 21
4. If not, click **Add Library → JRE System Library → JavaSE-21**

### Step 4: Enable Annotation Processing

1. Right-click project → **Properties**
2. **Java Compiler → Annotation Processing**
3. Check **Enable annotation processing**

### Step 5: Update Maven Project

1. Right-click project → **Maven → Update Project**
2. Check **Force Update of Snapshots/Releases**
3. Click **OK**

### Step 6: Configure Database (Optional)

1. **Window → Show View → Other**
2. **Data Management → Data Source Explorer**
3. Right-click → **New → Data Source**
4. Select **MySQL**
5. Enter connection details
6. Click **Test Connection**

### Step 7: Build Project

1. **Project → Build Project** (Ctrl+B)
2. Or use Maven:
   ```bash
   mvn clean install
   ```

### Step 8: Run Project

**Method 1: Using Run As**
1. Right-click `JavaSpringbootTableMigrationApplication.java`
2. **Run As → Spring Boot App**

**Method 2: Using Maven**
1. Right-click project → **Run As → Maven build**
2. Enter goal: `spring-boot:run`
3. Click **Run**

**Method 3: Using Terminal**
```bash
mvn spring-boot:run
```

### Step 9: Verify Application

- Open browser: http://localhost:8080/api/employees
- Check **Console** view for logs

---

## Database Configuration

### Update application.properties

Edit `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/springboot_crud_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true

# Flyway Configuration
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true
spring.flyway.locations=classpath:db/migration/tables,classpath:db/migration/seed,classpath:db/migration/alter
```

**Important:** Replace `YOUR_PASSWORD` with your MySQL root password.

---

## Running the Project

### Method 1: Using Maven Command

```bash
# Navigate to project directory
cd JavaSpringbootTableMigration

# Run application
mvn spring-boot:run
```

### Method 2: Using Maven Wrapper

```bash
# On macOS/Linux
./mvnw spring-boot:run

# On Windows
mvnw.cmd spring-boot:run
```

### Method 3: Build and Run JAR

```bash
# Build project
mvn clean package

# Run JAR file
java -jar target/JavaSpringbootTableMigration-0.0.1-SNAPSHOT.jar
```

### Method 4: Using IDE

- **VS Code:** Click Run button or press F5
- **IntelliJ IDEA:** Click Run button or right-click → Run
- **STS:** Right-click → Run As → Spring Boot App

### Verify Application is Running

1. **Check Console Logs:**
   ```
   Started JavaSpringbootTableMigrationApplication in X.XXX seconds
   ```

2. **Test API Endpoint:**
   ```bash
   curl http://localhost:8080/api/employees
   ```
   
   Or open in browser: http://localhost:8080/api/employees

3. **Check Database:**
   ```bash
   mysql -u root -p springboot_crud_db
   ```
   ```sql
   SHOW TABLES;
   SELECT * FROM employees;
   ```

---

## Troubleshooting

### Issue 1: Java Version Mismatch

**Error:** `Unsupported class file major version`

**Solution:**
- Ensure JDK 21 is installed
- Set JAVA_HOME environment variable
- Verify in IDE project settings

### Issue 2: Maven Dependencies Not Downloading

**Error:** `Could not resolve dependencies`

**Solution:**
```bash
# Clean Maven cache
mvn clean

# Update dependencies
mvn dependency:resolve

# Force update
mvn clean install -U
```

### Issue 3: Database Connection Failed

**Error:** `Communications link failure` or `Access denied`

**Solution:**
1. Verify MySQL is running:
   ```bash
   mysql -u root -p
   ```
2. Check credentials in `application.properties`
3. Ensure database exists or `createDatabaseIfNotExist=true` is set
4. Check MySQL port (default: 3306)

### Issue 4: Port Already in Use

**Error:** `Port 8080 is already in use`

**Solution:**
```bash
# Find process using port 8080
# On macOS/Linux
lsof -i :8080

# On Windows
netstat -ano | findstr :8080

# Kill the process or change port in application.properties
server.port=8081
```

### Issue 5: Flyway Migration Errors

**Error:** `Migration checksum mismatch` or `Migration failed`

**Solution:**
1. Check SQL syntax in migration files
2. Verify version numbers are sequential
3. Check `flyway_schema_history` table in database
4. See `DATABASE_MIGRATION_GUIDE.md` for details

### Issue 6: Lombok Not Working

**Error:** `Cannot find symbol` for Lombok annotations

**Solution:**
1. **IntelliJ IDEA:**
   - Install Lombok plugin
   - Enable annotation processing
   - Restart IDE

2. **VS Code:**
   - Install "Lombok Annotations Support" extension
   - Reload window

3. **STS:**
   - Install Lombok plugin
   - Enable annotation processing

### Issue 7: Build Fails

**Error:** `Compilation failure`

**Solution:**
```bash
# Clean and rebuild
mvn clean install

# Check for syntax errors in Java files
# Verify all dependencies in pom.xml
```

---

## Quick Start Commands

```bash
# Clone project
git clone https://github.com/Msharma673/JavaSpringbootTableMigration.git
cd JavaSpringbootTableMigration

# Build project
mvn clean install

# Run application
mvn spring-boot:run

# Test API
curl http://localhost:8080/api/employees

# Stop application
# Press Ctrl+C in terminal
```

---

## Project URLs

Once the application is running:

- **Base URL:** http://localhost:8080
- **Employees API:** http://localhost:8080/api/employees
- **Customers API:** http://localhost:8080/api/customers
- **Actuator Health:** http://localhost:8080/actuator/health

---

## Additional Resources

- **API Documentation:** See `API_CREATION_GUIDE.md`
- **cURL Commands:** See `API_CURL_COMMANDS.md`
- **Migration Guide:** See `DATABASE_MIGRATION_GUIDE.md`
- **Spring Boot Docs:** https://spring.io/projects/spring-boot
- **Flyway Docs:** https://flywaydb.org/documentation/

---

## Summary

✅ **Prerequisites Installed** (JDK 21, Maven, MySQL)  
✅ **Project Cloned/Downloaded**  
✅ **Database Configured**  
✅ **IDE Setup Complete** (VS Code / IntelliJ / STS)  
✅ **Project Built Successfully**  
✅ **Application Running** on http://localhost:8080  
✅ **APIs Tested and Working**  

**You're all set! Happy coding! 🚀**

