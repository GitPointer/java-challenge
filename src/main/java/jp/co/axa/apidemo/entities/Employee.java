package jp.co.axa.apidemo.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name="EMPLOYEE")
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="EMPLOYEE_NAME")
    @NotNull
    private String name;

    @Column(name="EMPLOYEE_SALARY")
    @NotNull
    private Integer salary;

    @Column(name="DEPARTMENT")
    @NotNull
    private String department;

    public Employee(@NotNull String name, @NotNull Integer salary, @NotNull String department) {
        this.name = name;
        this.salary = salary;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}

