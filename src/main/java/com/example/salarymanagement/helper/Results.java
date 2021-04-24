package com.example.salarymanagement.helper;

import com.example.salarymanagement.model.User;

import java.util.List;

public class Results {
    List<User> results;

    public Results(List<User> results) {
        this.results = results;
    }

    public List<User> getResults() {
        return results;
    }
}
