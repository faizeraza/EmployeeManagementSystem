package com.remiges.remigesdb.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;
import com.remiges.remigesdb.customexception.InvalidTypeException;
import com.remiges.remigesdb.dto.EmployeeDTO;
import com.remiges.remigesdb.dto.Request;
import com.remiges.remigesdb.dto.Response;
import com.remiges.remigesdb.services.EmployeeService;
import com.remiges.remigesdb.utils.PdfGenerator;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/myhr")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/employees/add")
    public ResponseEntity<Response<String>> addEmployees(@Valid @RequestBody Request<List<EmployeeDTO>> employees) {

        // Collect employee IDs for potential logging or debugging purposes
        List<String> ids = employees.getData().stream()
                .map(EmployeeDTO::getEmpid)
                .toList();

        try {
            // Method handles the addition of employee data
            employeeService.addEmployees(employees.getData());

            // Success respons with HTTP 201 Created and a success message
            return ResponseEntity.created(URI.create("/myhr/employees/add"))
                    .body(Response.success("Added employees: " + ids, employees.get_reqid()));
        } catch (DataIntegrityViolationException e) {
            // Capture and format the error message for specific database constraints or violations
            String message = "Unable to add employee due to: " + e.getMostSpecificCause().getMessage();

            // Failure response with HTTP 400 Bad Request with error message
            return ResponseEntity.badRequest()
                    .body(Response.failure(message, HttpStatus.EXPECTATION_FAILED.value(), employees.get_reqid()));
        }
    }

    @PostMapping("/employee/add")
    public ResponseEntity<Response<String>> addEmployee(@Valid @RequestBody Request<EmployeeDTO> employee) {
        try {

            // Call service to add a single employee to the database
            employeeService.addEmployee(employee.getData());

            // Return success response with status 201 Created and resource location in header
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Location", "myhr/employee/add")
                    .body(Response.success("Employee added: " + employee.getData().getEmpid(), employee.get_reqid()));

        } catch (DataIntegrityViolationException e) {
            // Handle unique constraint or database constraint errors
            String msg = "Employee addition failed: " + e.getMostSpecificCause().getMessage();

            // Return failure response with custom error message and status 417 Expectation Failed
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .header("Error", msg)
                    .body(Response.failure(msg, HttpStatus.EXPECTATION_FAILED.value(), employee.get_reqid()));
        }
    }

    @GetMapping("/employee/{empid}")
    public ResponseEntity<Response<EmployeeDTO>> showEmployee(
            @PathVariable("empid") String empid,
            @RequestParam("reqID") String reqid) {

        try {
            // Retrieve emloyee details by ID
            EmployeeDTO dto = employeeService.findByEmpId(empid);

            // Return success response with status 302 Found if employee is located
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("empid", empid)
                    .body(Response.success(dto, reqid));
        } catch (IndexOutOfBoundsException e) {
            // Error message when the employee ID is not found in the database
            String message = "EMPID: " + empid + " is not present. Please check!";

            // Return failure response with 404 Not Found status and error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("empid", empid)
                    .body(Response.failure(message, HttpStatus.NOT_FOUND.value(), reqid));
        }
    }

    @GetMapping("/employees")
    public ResponseEntity<?> showEmployeeArray(
            @RequestParam(name = "reqID", required = false) String reqid,
            @RequestParam(required = false) String type,
            HttpServletResponse response) {
        try {
            // Convert type to lowercase for consistent matching
            String responseType = (type == null) ? "json" : type.toLowerCase();

            // Switch statement for each response type
            switch (responseType) {
                case "xml" -> {
                    List<EmployeeDTO> result = employeeService.employeeArray();
                    return ResponseEntity.status(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_XML)
                            .body(Response.success(result, reqid));
                }
                case "excel" -> {
                    byte[] excelBytes = employeeService.employeeExcel();
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                    headers.setContentDispositionFormData("attachment", "query_results.xlsx");
                    return ResponseEntity.ok()
                            .headers(headers)
                            .header("message", "Downloaded Successfully!!")
                            .body(excelBytes);
                }
                case "pdf" -> {
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "attachment; filename=employees.pdf");
                    List<EmployeeDTO> empList = employeeService.getAll();
                    PdfGenerator pdfGenerator = new PdfGenerator();
                    pdfGenerator.generate(empList, response);
                    return null;
                }
                case "json" -> {
                    List<EmployeeDTO> result = employeeService.employeeArray();
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(Response.success(result, reqid));
                }
                default ->
                    throw new InvalidTypeException("Type " + type + " not supported!");
            }
        } catch (IndexOutOfBoundsException e) {
            // Handles cases when no records are found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Response.failure("No Record found!", HttpStatus.NOT_FOUND.value(), reqid));
        } catch (DocumentException e) {
            // Handles PDF generation errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.failure("Error generating PDF", HttpStatus.INTERNAL_SERVER_ERROR.value(), reqid));
        } catch (IOException e) {
            // Handles general I/O exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.failure("I/O Error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value(), reqid));
        } catch (InvalidTypeException e) {
            // Handles unsupported types
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Response.failure(e.getMessage(), HttpStatus.BAD_REQUEST.value(), reqid));
        }
    }

    @GetMapping("/employees/info")
    public ResponseEntity<Response<List<EmployeeDTO>>> showEmployees(@RequestParam("reqID") String reqid) {
        try {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(Response.success(employeeService.getAll(), reqid));
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Response.failure("No Record found !!", HttpStatus.NOT_FOUND.value(), reqid));
        }
    }

    @PatchMapping("/employee/{empid}")
    public ResponseEntity<Response<String>> updateEmployee(
            @NotNull @PathVariable String empid,
            @Valid @RequestBody Request<EmployeeDTO> request) {

        try {
            // Attempt to update the employee details in the service layer
            employeeService.updateEmployee(empid, request.getData());

            // Return a success response with status 200 (OK)
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Location", "myhr/employees/add") // Set the location header to the relevant endpoint
                    .body(Response.success("Employee with ID: " + empid + " has been updated.", request.get_reqid()));
        } catch (DataIntegrityViolationException e) {
            // Handle data integrity issues, such as violating constraints
            String errorMessage = "Failed to update employee due to data integrity issues: " + e.getMostSpecificCause().getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST) // Return 400 Bad Request for invalid data
                    .body(Response.failure(errorMessage, HttpStatus.BAD_REQUEST.value(), request.get_reqid()));
        } catch (IndexOutOfBoundsException e) {
            // Handle case where the employee ID does not exist
            String errorMessage = "No employee found with ID: " + empid;
            return ResponseEntity.status(HttpStatus.NOT_FOUND) // Return 404 Not Found if employee does not exist
                    .body(Response.failure(errorMessage, HttpStatus.NOT_FOUND.value(), request.get_reqid()));
        } catch (Exception e) {
            // Catch any other exceptions and return a generic error message
            String errorMessage = "An unexpected error occurred: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // Return 500 Internal Server Error for unforeseen issues
                    .body(Response.failure(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR.value(), request.get_reqid()));
        }
    }

    @DeleteMapping("/employee/{empid}")
    public ResponseEntity<Response<String>> deleteEmployee(
            @PathVariable String empid,
            @RequestBody Request<EmployeeDTO> request) {

        try {
            // Attempt to delete the employee by their ID
            EmployeeDTO deletedEmployeeInfo = employeeService.deletByEmpId(empid);

            // Return a success response indicating the employee was deleted
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Location", "myhr/employees/add") // Set the location header to the relevant endpoint
                    .body(Response.success("Successfully deleted employee : " + deletedEmployeeInfo, request.get_reqid()));

        } catch (IndexOutOfBoundsException e) {
            // Handle case where the employee ID does not exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Response.failure("No employee found with ID: " + empid,
                            HttpStatus.NOT_FOUND.value(),
                            request.get_reqid()));
        } catch (Exception e) {
            // Catch any other exceptions and return a generic error message
            String errorMessage = "An unexpected error occurred: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.failure(errorMessage,
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            request.get_reqid()));
        }
    }

    @GetMapping("/employees/searchBy")
    public ResponseEntity<Response<List<EmployeeDTO>>> searchEmployees(
            @RequestParam("filter") String filter,
            @RequestParam("reqID") String reqid) {

        try {
            // Perform the search operation using the provided filter
            List<EmployeeDTO> employees = employeeService.searchByFilter(filter);

            // Check if the search result is empty and handle accordingly
            if (employees.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Response.failure("No records found for filter: " + filter,
                                HttpStatus.NOT_FOUND.value(),
                                reqid));
            }

            // Return the found employees in the response
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Response.success(employees, reqid));

        } catch (Exception e) {
            // Catch any unexpected exceptions and return a generic error message
            String errorMessage = "An unexpected error occurred: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.failure(errorMessage,
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            reqid));
        }
    }

}
