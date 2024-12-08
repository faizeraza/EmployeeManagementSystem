# Operations Module (Support Package)

## Purpose
The `operations` package contains various classes that implement arithmetic operations, date handling, and other utility functions to facilitate different tasks related to arithmetic calculations, date manipulations, and other core functionalities. This package is part of the `support` sub-package under the `assignments` package.

## Key Concepts Covered
- **SOLID Principles**: Adherence to principles like interface segregation, single responsibility, and open/closed principles in the design of arithmetic and date operation classes.
- **Error Handling**: Robust error handling mechanisms, including division-by-zero exceptions.
- **Date and Time Manipulations**: Various classes handle operations like detecting date formats, converting date formats, and comparing dates.
- **Matrix Operations**: Handling basic matrix formatting and display operations.

## Code Overview
Each class in the `operations` package serves a specific purpose and adheres to Java’s object-oriented principles to promote modularity, reusability, and maintainability.

## Key Classes and Methods

### Arithmetic Operations
1. **FloatAddition.java**
   - **Purpose**: Performs addition on float numbers.
   - **Key Method**: `perform(Float, Float)` – Returns the sum of two float operands.

2. **FloatDivision.java**
   - **Purpose**: Performs division on float numbers and handles division by zero errors.
   - **Key Method**: `perform(Float, Float)` – Returns the quotient of two float operands.

3. **FloatMultiplication.java**
   - **Purpose**: Performs multiplication on float numbers.
   - **Key Method**: `perform(Float, Float)` – Returns the product of two float operands.

4. **FloatSubtraction.java**
   - **Purpose**: Performs subtraction on float numbers.
   - **Key Method**: `perform(Float, Float)` – Returns the difference between two float operands.

5. **IntAddition.java**
   - **Purpose**: Performs addition on integer numbers.
   - **Key Method**: `perform(Integer, Integer)` – Returns the sum of two integer operands.

### Date and Time Operations
1. **LeapYearDetector.java**
   - **Purpose**: Checks if a given year is a leap year.
   - **Key Method**: `isInLeapYear()` – Returns a boolean indicating whether the year is a leap year.

2. **WithZone.java**
   - **Purpose**: Converts and formats time between UTC and the specified time zone.
   - **Key Method**: `getWithZone()` – Returns the current time in both UTC and the specified zone.

3. **WeekendCounter.java**
   - **Purpose**: Counts the number of weekend days between two given dates.
   - **Key Method**: `countWeekEnds()` – Returns the count of Saturdays and Sundays between two dates.

4. **DueInBetween.java**
   - **Purpose**: Calculates the time difference between two dates in years, months, days, hours, and minutes.
   - **Key Method**: `dueInBetween()` – Returns a map of time components (years, months, days, etc.).

5. **DateComparator.java**
   - **Purpose**: Compares two dates to determine their chronological order.
   - **Key Method**: `dateComparision()` – Returns a string indicating whether dates are equal, earlier, or later.

6. **BasicDateOperations.java**
   - **Purpose**: Detects and converts date formats to a standard format.
   - **Key Methods**:
     - `detectDateTimeFormat(String)` – Detects the format of a given date string.
     - `convertToStandardFormat(String, String)` – Converts a date string to the standard format "yyyy-MM-dd HH:mm:ss".

7. **DateManipulation.java**
   - **Purpose**: Generates multiple formatted representations of a given date.
   - **Key Method**: `getDateInFormates(LocalDateTime)` – Returns an array of date strings formatted according to predefined patterns.

### Other Operations
1. **Matrix.java**
   - **Purpose**: Generates and prints a matrix of specified dimensions filled with a user-defined character.
   - **Key Method**: `printMatrix()` – Prints a matrix with the specified rows, columns, and character.

2. **Table.java**
   - **Purpose**: Prints multiplication tables for a specified number.
   - **Key Methods**:
     - `tablePrinter(int, int)` – Generates a single line of the multiplication table.
     - `printTable()` – Prints the entire table for the specified number.

## Usage
- **Arithmetic Operations**: Create instances of specific operation classes (e.g., `FloatAddition`) and call the `perform()` method with appropriate parameters.
- **Date Manipulations**: Use classes like `LeapYearDetector` and `DueInBetween` to perform operations on date inputs.
- **Matrix Operations**: Instantiate the `Matrix` class to generate and display matrices as per user inputs.

**Example:**
```java
FloatAddition addition = new FloatAddition();
Float result = addition.perform(10.5f, 5.5f);
System.out.println("Result: " + result);

Matrix matrix = new Matrix(new ConsoleInputHandler());
System.out.println(matrix.printMatrix());
