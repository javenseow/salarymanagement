package com.example.salarymanagement.helper;

public class Response {

    public static final String CREATE_SUCCESS = "Successfully created";
    public static final String UPDATE_SUCCESS = "Successfully updated";
    public static final String DELETE_SUCCESS = "Successfully deleted";

    public static final String NO_SUCH_EMPLOYEE = "No such employee";
    public static final String LOGIN_NOT_UNIQUE = "Employee login not unique";
    public static final String EMPLOYEE_ID_EXISTS = "Employee ID already exists";

    public static final String SUCCESS_WITH_CREATE_OR_UPDATE = "Success with data created or updated";
    public static final String SUCCESS_WITHOUT_UPDATE = "Success with no data update";

    public static final String INVALID_SALARY = "Invalid salary";
    public static final String DUPLICATE_ROW = "Duplicate Row";
    public static final String INVALID_ROW = "Invalid Row";
    public static final String INVALID_DATE = "Invalid date";
    public static final String INVALID_NAME = "Invalid name";
    public static final String INVALID_ID = "Invalid id";
    public static final String INVALID_LOGIN = "Invalid login";
    public static final String DIFFERENT_ID = "Different id";
    public static final String INVALID_MIN_SALARY = "Invalid min salary";
    public static final String INVALID_MAX_SALARY = "Invalid max salary";
    public static final String INVALID_OFFSET = "Invalid offset";
    public static final String INVALID_LIMIT = "Invalid limit";
    public static final String INVALID_PARAMS = "Invalid paramters";

    public static final String INVALID_CSV = "File not in CSV";

    private String message;

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
