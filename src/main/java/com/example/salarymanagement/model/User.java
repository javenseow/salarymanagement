package com.example.salarymanagement.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class User {
    @Id
    private String id;
    @Column(unique = true)
    private String login;
    private String name;
    private Float salary;
    private LocalDate startDate;

    public User() {
    }

    public User(String id, String login, String name, Float salary, LocalDate startDate) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.salary = salary;
        this.startDate = startDate;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public Float getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", startDate=" + startDate +
                '}';
    }
}
