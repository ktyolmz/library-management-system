package com.ktyolmz.library.controller;

import com.ktyolmz.library.entity.Book;
import com.ktyolmz.library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public List<Book> addBooks(@RequestBody List<Book> bookList) {
        return bookService.saveBook(bookList);
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<Book> getBooks(@RequestBody List<String> titleList) {
        return bookService.getBooksByTitles(titleList);
    }

}
