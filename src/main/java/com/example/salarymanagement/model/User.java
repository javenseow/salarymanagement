package com.example.salarymanagement.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Comparator;

@Entity
@Table
public class User {
    @Id
    private String id;
    @Column(unique = true, nullable = false)
    private String login;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Double salary;
    @Column(nullable = false)
    private LocalDate startDate;

    public User() {
    }

    public User(String id, String login, String name, Double salary, LocalDate startDate) {
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

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public Double getSalary() {
        return salary;
    }

    public String getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public String toString() {
        return "{\"id\":\"" + id + '\"' +
                ",\"login\":\"" + login + '\"' +
                ",\"name\":\"" + name + '\"' +
                ",\"salary\":" + salary +
                ",\"startDate\":\"" + startDate +
                "\"}";
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
                && Double.compare(salary, u.salary) == 0
                && (startDate.compareTo(u.startDate) == 0);
    }

    @Override
    public int hashCode() {
        return id.hashCode() ^ login.hashCode() ^ name.hashCode() ^ salary.hashCode() ^ startDate.hashCode();
    }

    public static Comparator<User> UserComparator = new Comparator<User>() {
        @Override
        public int compare(User o1, User o2) {
            String user1Id = o1.getId().toLowerCase();
            String user2Id = o2.getId().toLowerCase();

            return user1Id.compareTo(user2Id);
        }
    };
}
