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

    private int isDeleted;
	private String email;
    
    public Employee() {}
    public void setEmail(String email) {
        this.username = email;
    }

    public Employee(String email, String password, Department department, double salary) {
        this.email = email;
        this.password = password;
        this.department = department;
        this.salary = salary;
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
    public int getIsDeleted() {
        return isDeleted;
    }
    
    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
	public void setDateOfJoining(Date date) {
		// TODO Auto-generated method stub
		
	}
}
