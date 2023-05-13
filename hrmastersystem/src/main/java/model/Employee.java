package model;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String password;
    private double salary;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    
    @Column(name = "date_of_joining", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOfJoining;


    public Employee() {}
    public void setEmail(String email) {
        this.username = email;
    }

    public Employee(String username, String password, double salary, Department department) {
        this.username = username;
        this.password = password;
        this.salary = salary;
        this.department = department;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
