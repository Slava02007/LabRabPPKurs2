package com.example.library.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private String username;
    private String password;
    private String role;
    private List<String> borrowedBookIds = new ArrayList<>();
}