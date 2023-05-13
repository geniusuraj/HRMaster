package service;

import model.Department;
import model.Employee;
import model.LeaveRequest;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

public class Admin {
    private String username;
    private String password;

    // Default constructor for the default admin
    public Admin() {
        this.username = "admin";
        this.password = "admin";
    }

    // Constructor for custom admin with username and password
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Login method for the admin
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void addDepartment(EntityManager em, String departmentName) {
        em.getTransaction().begin();
        Department department = new Department();
        department.setName(departmentName);
        em.persist(department);
        em.getTransaction().commit();
    }

    public List<Department> viewAllDepartments(EntityManager em) {
        return em.createQuery("SELECT d FROM Department d WHERE d.isDeleted = 0", Department.class).getResultList();
    }

    public void updateDepartmentName(EntityManager em, int departmentId, String newDepartmentName) {
        em.getTransaction().begin();
        Department department = em.find(Department.class, departmentId);
        if (department != null && department.getIsDeleted() == 0) {
            department.setName(newDepartmentName);
        }
        em.getTransaction().commit();
    }

    public void registerNewEmployee(EntityManager entityManager, String email, int departmentId, double salary) {
        Department department = entityManager.find(Department.class, departmentId);
        if (department == null) {
            throw new IllegalArgumentException("Invalid department ID.");
        }
        Employee employee = new Employee(email, "123456", department, salary);
        employee.setDateOfJoining(new Date(departmentId)); // Set the current date as the date of joining
        entityManager.getTransaction().begin();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
    }


    public void changeDepartmentOfEmployee(EntityManager em, int employeeId, int newDepartmentId) {
        em.getTransaction().begin();
        Employee employee = em.find(Employee.class, employeeId);
        if (employee != null && employee.getIsDeleted() == 0) {
            employee.setDepartment(em.find(Department.class, newDepartmentId));
        }
        em.getTransaction().commit();
    }

    public void updateLeaveRequest(EntityManager em, int leaveRequestId, int status, String remark) {
        em.getTransaction().begin();
        LeaveRequest leaveRequest = em.find(LeaveRequest.class, leaveRequestId);
        if (leaveRequest != null) {
            leaveRequest.setStatus(status == 1 ? "Approved" : "Denied");
            leaveRequest.setRemark(remark);
        }
        em.getTransaction().commit();
    }

    public void fireEmployee(EntityManager em, int employeeId) {
        em.getTransaction().begin();
        Employee employee = em.find(Employee.class, employeeId);
        if (employee != null) {
            employee.setIsDeleted(1);
        }
        em.getTransaction().commit();
    }
}
