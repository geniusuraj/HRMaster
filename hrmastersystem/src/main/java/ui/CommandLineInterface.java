package ui;

import model.Department;
import model.Employee;
import model.LeaveRequest;
import service.Admin;
import service.EmployeeService;

import jakarta.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CommandLineInterface {
    private static final String PERSISTENCE_UNIT_NAME = "hrms";
    private static EntityManagerFactory factory;
    private static EntityManager entityManager;
    private static Scanner scanner;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        entityManager = factory.createEntityManager();
        scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to the Human Resource Management System!");
            System.out.println("1. Login as Admin");
            System.out.println("2. Login as Employee");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    employeeLogin();
                    break;
                case 3:
                    factory.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void adminLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Create an Admin object
        Admin admin = new Admin(username, password);
        if (admin.login(username, password)) {
            System.out.println("Logged in as admin.");
            boolean adminMenu = true;
            while (adminMenu) {
                System.out.println("\nAdmin Menu");
                System.out.println("1. Add department");
                System.out.println("2. View all departments");
                System.out.println("3. Update department name");
                System.out.println("4. Register new employee");
                System.out.println("5. Change department of employee");
                System.out.println("6. Approve or deny employee leave request");
                System.out.println("7. Fire an employee");
                System.out.println("8. Logout");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                        // Add department
                        System.out.print("Enter department name: ");
                        String departmentName = scanner.nextLine();
                        System.out.print("Enter department ID: ");
                        int departmentId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character

                        admin.addDepartment(entityManager, departmentName);

                        System.out.println("Department added successfully.");
                        break;
                    case 2:
                        // View all departments
                        List<Department> departments = admin.viewAllDepartments(entityManager);
                        System.out.println("\nDepartments:");
                        for (Department department : departments) {
                            System.out.println(department.getId() + " - " + department.getName());
                        }
                        break;
                    case 3:
                        // Update department name
                        System.out.print("Enter department ID: ");
                        int updateDepartmentId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        System.out.print("Enter new department name: ");
                        String newDepartmentName = scanner.nextLine();

                        admin.updateDepartmentName(entityManager, updateDepartmentId, newDepartmentName);
                        System.out.println("Department name updated successfully.");
                        break;
                    case 4:
                        System.out.print("Enter employee's email (username): ");
                        String email = scanner.next();
                        System.out.print("Enter employee's department ID: ");
                        departmentId = scanner.nextInt(); 
                        System.out.print("Enter employee's salary: ");
                        double salary = scanner.nextDouble();

                        try {
                            admin.registerNewEmployee(entityManager, email, departmentId, salary);
                            System.out.println("Employee registered successfully.");
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case 5:
                        // Change department of employee
                        System.out.print("Enter employee's ID: ");
                        int empId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        System.out.print("Enter new department ID: ");
                        int newDepartmentId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character

                        admin.changeDepartmentOfEmployee(entityManager, empId, newDepartmentId);
                        System.out.println("Employee's department updated successfully.");
                        break;
                    case 6:
                        // Approve or deny employee leave request
                        System.out.print("Enter leave request ID: ");
                        int leaveRequestId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        System.out.print("Enter status (1 - Approve, 2 - Deny): ");
                        int status = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        System.out.print("Enter remark: ");
                        String remark = scanner.nextLine();

                        admin.updateLeaveRequest(entityManager, leaveRequestId, status, remark);
                        System.out.println("Leave request updated successfully.");
                        break;
                    case 7:
                        // Fire an employee
                        System.out.print("Enter employee's ID: ");
                        int fireEmpId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character

                        admin.fireEmployee(entityManager, fireEmpId);
                        System.out.println("Employee fired successfully.");
                        break;
                    case 8:
                        adminMenu = false;
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void employeeLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        EmployeeService employeeService = new EmployeeService();
        try {
            Employee employee = employeeService.login(entityManager, username, password);
            System.out.println("Logged in as employee: " + employee.getUsername());

            boolean employeeMenu = true;
            while (employeeMenu) {
                System.out.println("\nEmployee Menu");
                System.out.println("1. Update account details and change password");
                System.out.println("2. Apply for leave");
                System.out.println("3. View leave status");
                System.out.println("4. View leave history");
                System.out.println("5. View total salary for a month");
                System.out.println("6. View annual salary for a financial year");
                System.out.println("7. Delete account");
                System.out.println("8. Logout");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                case 1:
                    System.out.print("Enter department name: ");
                    String departmentName = scanner.nextLine();
                    admin.addDepartment(entityManager, departmentName);
                    break;
                    case 2:
                        // Apply for leave
                        System.out.print("Enter start date (yyyy-MM-dd): ");
                        String startDateStr = scanner.nextLine();
                        Date startDate = sdf.parse(startDateStr);

                        System.out.print("Enter end date (yyyy-MM-dd): ");
                        String endDateStr = scanner.nextLine();
                        Date endDate = sdf.parse(endDateStr);

                        System.out.print("Enter reason: ");
                        String reason = scanner.nextLine();

                        employeeService.applyForLeave(entityManager, employee, startDate, endDate, reason);
                        System.out.println("Leave request submitted successfully.");
                        break;
                    case 3:
                        // View leave status
                        List<LeaveRequest> pendingLeaveRequests = employeeService.viewPendingLeaveRequests(entityManager, employee);
                        System.out.println("\nPending Leave Requests:");
                        for (LeaveRequest leaveRequest : pendingLeaveRequests) {
                            System.out.println(leaveRequest.getId() + " - " + sdf.format(leaveRequest.getStartDate()) + " to " + sdf.format(leaveRequest.getEndDate()) + " - " + leaveRequest.getReason());
                        }
                        break;
                    case 4:
                        // View leave history
                        List<LeaveRequest> leaveHistory = employeeService.viewLeaveHistory(entityManager, employee.getId());

                        System.out.println("\nLeave History:");
                        for (LeaveRequest leaveRequest : leaveHistory) {
                            System.out.println(leaveRequest.getId() + " - " + sdf.format(leaveRequest.getStartDate()) + " to " + sdf.format(leaveRequest.getEndDate()) + " - " + leaveRequest.getReason() + " - " + leaveRequest.getStatus() + " - " + leaveRequest.getRemark());
                        }
                        break;
                    case 5:
                        // View total salary for a month
                        System.out.print("Enter month (yyyy-MM): ");
                        String monthStr = scanner.nextLine();
                        Date month = new SimpleDateFormat("yyyy-MM").parse(monthStr);

                        double monthlySalary = employeeService.getTotalSalaryForMonth(entityManager, employee.getId(), month);
                        System.out.println("Total salary for the month: " + monthlySalary);
                        break;
                    case 6:
                        // View annual salary for a financial year
                        System.out.print("Enter financial year (yyyy): ");
                        int financialYear = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character

                        double annualSalary = employeeService.getAnnualSalaryForFinancialYear(entityManager, employee.getId(), financialYear);
                        System.out.println("Annual salary for the financial year: " + annualSalary);
                        break;
                    case 7:
                        // Delete account
                        employeeService.deleteAccount(entityManager, employee);
                        System.out.println("Account deleted successfully.");
                        employeeMenu = false;
                        break;
                    case 8:
                        employeeMenu = false;
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please try again.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}

