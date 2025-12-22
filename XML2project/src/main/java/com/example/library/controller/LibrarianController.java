package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.XmlLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/librarian")
public class LibrarianController {

    @Autowired
    private XmlLibraryService xmlService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("books", xmlService.getAllBooks());
        model.addAttribute("newBook", new Book());
        return "librarian/dashboard";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book) {
        xmlService.addBook(book);
        return "redirect:/librarian/dashboard";
    }

    @PostMapping("/updatePrice")
    public String updatePrice(@RequestParam String id, @RequestParam Double price) {
        xmlService.updatePrice(id, price);
        return "redirect:/librarian/dashboard";
    }

    @PostMapping("/issue")
    public String issueBook(@RequestParam String bookId, @RequestParam String username) {
        xmlService.issueBook(bookId, username);
        return "redirect:/librarian/dashboard";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", xmlService.getAllUsers());
        return "librarian/users";
    }
}