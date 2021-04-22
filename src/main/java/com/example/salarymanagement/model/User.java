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

    public String getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof User)) {
            return false;
        }

        User u = (User) o;
        return id.compareTo(u.id) == 0
                && (login.compareTo(u.login) == 0)
                && (name.compareTo(u.name) ==  0)
                && Float.compare(salary, u.salary) == 0
                && (startDate.compareTo(u.startDate) == 0);
    }
}
