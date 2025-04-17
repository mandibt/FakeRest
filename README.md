# REST Assured API Testing Framework

A Java-based testing framework for REST APIs using **REST Assured**, **TestNG**, and **Allure** for reporting. Designed for modularity, scalability, and ease of CI/CD integration.

---

## Features

- **CRUD Testing**: Covers GET, POST, PUT, DELETE operations  
- **Data-Driven**: Dynamic test data generation  
- **Allure Reports**: Detailed request/response reporting  
- **CI/CD Ready**: Jenkins pipeline included  
- **Logging**: Configured with Log4j2  
- **Modular Design**: Organized structure for easy maintenance  

---

## Prerequisites

- Java JDK 11+  
- Maven 3.6.0+  
- Git  
- IDE (Eclipse / IntelliJ / VS Code)  

---

## Getting Started

### Clone the Repository

```bash
git clone <repository-url>
cd rest-assured-api-testing
```

### Project Structure

```
src/
  main/java/
    api/            # Configuration classes
    models/         # POJOs for request/response
    utils/          # Utility helpers + base

  test/java/
    tests/
      books/        # Book API tests
      authors/      # Author API tests
    listener/       # ITestListener interface
  resources/        # Test configs and testng.xml

pom.xml             # Maven configuration  
Jenkinsfile         # CI/CD pipeline  
README.md           # Project documentation
```

---

## IDE Setup

### Eclipse

- File → Import → Maven → Existing Maven Projects  
- Select the repo folder → Finish  

### IntelliJ IDEA

- File → Open → Choose repo folder → Open as Project  

---

## Configuration

API endpoint settings are in `src/test/resources/config.properties`:

```properties
base.url=https://fakerestapi.azurewebsites.net
books.endpoint=/api/v1/Books
authors.endpoint=/api/v1/Authors
```

---

## Running Tests

### Using Maven

```bash
# Run all tests
mvn clean test

# Run specific test class
mvn clean test -Dtest=BookApiTests
```

### Using TestNG Suite

```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

### From IDE

- Right-click test class/method → Run as TestNG Test  

---

## Allure Reports

```bash
# Generate report
mvn allure:report

# Open report in browser
mvn allure:serve
```

---

## Adding New Tests

1. Create a class in the relevant `tests` package  
2. Extend `BaseTest`  
3. Use `RequestManager` to make calls  
4. Add proper assertions  

---

## CI/CD Integration (Jenkins)

1. Create Jenkins pipeline job  
2. Point to `Jenkinsfile` in repo  
3. Pipeline stages:
   - Checkout  
   - Build  
   - Test  
   - Generate Reports  

---

## Best Practices

- Keep tests independent  
- Use descriptive test names  
- Annotate with Allure for clarity  
- Use builder pattern for test data  
- Log meaningful steps  
- Prefer soft assertions for multiple validations  

---
