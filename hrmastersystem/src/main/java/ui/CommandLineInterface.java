package ui;

import model.Employee;
import service.Admin;
import service.EmployeeService;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Scanner;

public class CommandLineInterface {
    private static final String PERSISTENCE_UNIT_NAME = "hrmsPU";
    private static EntityManagerFactory factory;
    private static EntityManager entityManager;
    private static Scanner scanner;

    public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        entityManager = factory.createEntityManager();
        scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the Human Resource Management System!");
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

        Admin admin = new Admin();
        if (admin.login(username, password)) {
            System.out.println("Logged in as admin.");
            // Perform admin actions
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
            // Perform employee actions
        } catch (Exception e) {
            System.out.println("Invalid username or password.");
        }
    }
}
