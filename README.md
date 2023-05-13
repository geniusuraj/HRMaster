# Human Resource Management System (HRMaster)

The Human Resource Management System(HRMaster) is a Java-based application that allows the Human Resource Department of a company to control and manage employee and department data. It provides functionalities for both the Admin and Employees to perform various tasks related to employee management, leave requests, and salary calculation.

## Features

### Admin

- Login with username and password.
- Add new departments by providing department name and ID.
- View all departments.
- Update the department name.
- Register new employees by providing email (username), default password, department, and salary.
- Change the department of an employee.
- Approve or deny employee leave requests with remarks.
- Fire an employee (delete the employee's account).
- Logout.

### Employee

- Login with username and password.
- Update account details and change the password.
- Apply for leave by providing the start date, end date, and reason.
- View the status of leave requests.
- View the history of leave requests.
- View the total salary for a specific month.
- View the annual salary for a financial year.
- Delete the employee account.
- Logout.

## Technologies Used

- Java
- Java Persistence API (JPA) with Hibernate
- MySQL database

## Setup Instructions

1. Clone the repository or download the source code.
2. Open the project in your preferred Java IDE.
3. Configure the MySQL database connection properties in the `persistence.xml` file located in the `META-INF` folder.
4. Create the necessary database tables using the provided entity classes and JPA annotations.
5. Compile and run the `CommandLineInterface` class to start the application.

## Database Schema

The application uses the following database schema:

- `departments` table:
  - `id` (Primary Key)
  - `name`
  - `is_deleted`

- `employees` table:
  - `id` (Primary Key)
  - `username` (Email)
  - `password`
  - `department_id` (Foreign Key referencing `departments.id`)
  - `salary`
  - `date_of_joining`
  - `is_deleted`

- `leave_requests` table:
  - `id` (Primary Key)
  - `employee_id` (Foreign Key referencing `employees.id`)
  - `start_date`
  - `end_date`
  - `reason`
  - `status`
  - `remark`
  - `is_deleted`

Note: Ensure that the necessary database tables are created and the database connection properties are properly configured before running the application.

## License

Feel free to modify and use this code for educational or commercial purposes.

## Contribution

Contributions to this project are welcome. If you find any issues or want to add new features, please create a pull request or submit an issue.

## Acknowledgments

This project was created as a part of the Construct Week at [Masai School](https://www.masaischool.com/), a coding bootcamp. It served as a learning opportunity to explore and implement a Human Resource Management System using Java and JPA with Hibernate.

Special thanks to the instructors and mentors at Masai School for their guidance and support throughout the development of this project.
