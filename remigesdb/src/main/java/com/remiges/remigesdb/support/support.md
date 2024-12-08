# Support Package

## Overview
The `support` package in the `assignments` project provides essential utilities and functionalities to handle arithmetic operations, date and time manipulations, statistical calculations, and matrix formatting. It serves as a core component for various operations in the project, making it easier to manage complex tasks efficiently.

## Key Components

### 1. **Arithmetic Operations**
- **Calculator.java**: 
  - **Purpose**: Provides methods for basic arithmetic operations (addition, subtraction, multiplication, division) using floating-point numbers.
  - **Key Methods**:
    - `add()`
    - `subtract()`
    - `multiply()`
    - `divide()`

### 2. **Date and Time Utilities**
- **Calendar.java**:
  - **Purpose**: Handles various date-related operations such as formatting dates, comparing dates, counting weekends, and detecting leap years.
  - **Key Methods**:
    - `getCurrentDateTime()`
    - `getWithZone()`
    - `dueInBetween()`
    - `isInLeapYear()`
    - `dateComparision()`
    - `countWeekEnds()`

### 3. **Statistical Operations**
- **StatisticalOperations.java**:
  - **Purpose**: Provides statistical operations on a list of floating-point numbers, including calculating mean, maximum, minimum, sum, and sorting the list.
  - **Key Methods**:
    - `getCount()`
    - `getMean()`
    - `getMaximum()`
    - `getMinimum()`
    - `getSum()`
    - `sortList()`
    - `getEvenCount()`
    - `getOddCount()`

### 4. **Matrix Operations**
- **Matrix.java**:
  - **Purpose**: Generates and prints a matrix with a specified character, rows, and columns.
  - **Key Method**: `printMatrix()`

## Sub-Packages

### **ioHandling**
- **ConsoleInputHandler.java**: Manages user input through the console, facilitating interaction with users for providing necessary inputs in various operations.

### **operations**
The `operations` package includes modules for performing arithmetic and date operations. For details, refer to the README in the `operations` package.

## Usage Example

```java
// Arithmetic Operations
Calculator calc = new Calculator(10.5f, 2.5f);
System.out.println("Sum: " + calc.add());
System.out.println("Difference: " + calc.subtract());

// Date Operations
LocalDateTime startDate = LocalDateTime.now();
LocalDateTime endDate = startDate.plusDays(10);
Calendar calendar = new Calendar(startDate, endDate);
System.out.println(calendar.dateComparision());

// Statistical Operations
ArrayList<Float> numbers = new ArrayList<>(Arrays.asList(1.2f, 3.4f, 5.6f));
StatisticalOperations stats = new StatisticalOperations(numbers);
System.out.println("Mean: " + stats.getMean());
