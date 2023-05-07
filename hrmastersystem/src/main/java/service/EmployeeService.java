package service;

import model.Employee;
import model.LeaveRequest;

import jakarta.persistence.*;
import java.util.*;

public class EmployeeService {

    public Employee login(EntityManager em, String username, String password) {
        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.username = :username AND e.password = :password AND e.isDeleted = 0", Employee.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        return query.getSingleResult();
    }

    public void updateAccountDetails(EntityManager em, Employee employee, String newPassword) {
        em.getTransaction().begin();
        employee.setPassword(newPassword);
        em.getTransaction().commit();
    }

    public void applyForLeave(EntityManager em, Employee employee, Date startDate, Date endDate) {
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployee(employee);
        leaveRequest.setStartDate(startDate);
        leaveRequest.setEndDate(endDate);
        leaveRequest.setStatus("Pending");
        leaveRequest.setIsDeleted(0);

        em.getTransaction().begin();
        em.persist(leaveRequest);
        em.getTransaction().commit();
    }

    public List<LeaveRequest> viewLeaveStatus(EntityManager em, int employeeId) {
        TypedQuery<LeaveRequest> query = em.createQuery("SELECT lr FROM LeaveRequest lr WHERE lr.employee.id = :employeeId AND lr.isDeleted = 0", LeaveRequest.class);
        query.setParameter("employeeId", employeeId);
        return query.getResultList();
    }

    public List<LeaveRequest> viewLeaveHistory(EntityManager em, int employeeId) {
        TypedQuery<LeaveRequest> query = em.createQuery("SELECT lr FROM LeaveRequest lr WHERE lr.employee.id = :employeeId", LeaveRequest.class);
        query.setParameter("employeeId", employeeId);
        return query.getResultList();
    }

    public double getTotalSalaryForMonth(EntityManager em, int employeeId, Date month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(month);
        int monthIndex = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        Employee employee = em.find(Employee.class, employeeId);
        if (employee != null && employee.getIsDeleted() == 0) {
            return employee.getSalary();
        }
        return 0;
    }

    public double getAnnualSalaryForFinancialYear(EntityManager em, int employeeId, int financialYear) {
        Employee employee = em.find(Employee.class, employeeId);
        if (employee != null && employee.getIsDeleted() == 0) {
            return employee.getSalary() * 12;
        }
        return 0;
    }

    public void deleteAccount(EntityManager em, Employee employee) {
        em.getTransaction().begin();
        employee.setIsDeleted(1);
        em.getTransaction().commit();
    }

}
