package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.service.XmlLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reader")
public class ReaderController {

    @Autowired
    private XmlLibraryService xmlService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("books", xmlService.getAllBooks());
        return "reader/dashboard";
    }

    @GetMapping("/search")
    public String search(@RequestParam String criteria, @RequestParam String value, Model model) {
        model.addAttribute("books", xmlService.searchBooks(criteria, value));
        return "reader/dashboard";
    }

    @GetMapping("/account")
    public String account(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = xmlService.getUserByUsername(userDetails.getUsername());
        List<Book> myBooks = new ArrayList<>();

        for(String id : user.getBorrowedBookIds()){
            Book b = xmlService.getBookById(id);
            if(b != null) myBooks.add(b);
        }

        model.addAttribute("user", user);
        model.addAttribute("myBooks", myBooks);
        return "reader/account";
    }
}