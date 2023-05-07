package service;

import model.Department;
import model.Employee;
import model.LeaveRequest;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

public class Admin {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    public boolean login(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }

    public void addDepartment(EntityManager em, String departmentName) {
        Department department = new Department();
        department.setDepartmentName(departmentName);
        department.setIsDeleted(0);

        em.getTransaction().begin();
        em.persist(department);
        em.getTransaction().commit();
    }

    public List<Department> viewAllDepartments(EntityManager em) {
        TypedQuery<Department> query = em.createQuery("SELECT d FROM Department d WHERE d.isDeleted = 0", Department.class);
        return query.getResultList();
    }

    public void updateDepartmentName(EntityManager em, int departmentId, String newDepartmentName) {
        Department department = em.find(Department.class, departmentId);
        if (department != null && department.getIsDeleted() == 0) {
            em.getTransaction().begin();
            department.setDepartmentName(newDepartmentName);
            em.getTransaction().commit();
        }
    }

    public void registerNewEmployee(EntityManager em, String username, String departmentId, double salary) {
        Department department = em.find(Department.class, Integer.parseInt(departmentId));
        if (department != null && department.getIsDeleted() == 0) {
            Employee employee = new Employee();
            employee.setUsername(username);
            employee.setPassword("123456");
            employee.setDepartment(department);
            employee.setSalary(salary);
            employee.setDateOfJoining(new Date());
            employee.setIsDeleted(0);

            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
        }
    }

    public void changeEmployeeDepartment(EntityManager em, int employeeId, int newDepartmentId) {
        Employee employee = em.find(Employee.class, employeeId);
        Department newDepartment = em.find(Department.class, newDepartmentId);

        if (employee != null && employee.getIsDeleted() == 0 && newDepartment != null && newDepartment.getIsDeleted() == 0) {
            em.getTransaction().begin();
            employee.setDepartment(newDepartment);
            em.getTransaction().commit();
        }
    }

    public void approveOrDenyLeaveRequest(EntityManager em, int leaveRequestId, String status, String remark) {
        LeaveRequest leaveRequest = em.find(LeaveRequest.class, leaveRequestId);

        if (leaveRequest != null && leaveRequest.getIsDeleted() == 0) {
            em.getTransaction().begin();
            leaveRequest.setStatus(status);
            leaveRequest.setRemark(remark);
            em.getTransaction().commit();
        }
    }

    public void fireEmployee(EntityManager em, int employeeId) {
        Employee employee = em.find(Employee.class, employeeId);
        if (employee != null && employee.getIsDeleted() == 0) {
            em.getTransaction().begin();
            employee.setIsDeleted(1);
            em.getTransaction().commit();
        }
    }
}
