package com.example.library.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private String id;
    private String title;
    private String author;
    private String year;
    private String category;
    private Double price;
    private Integer count;
}