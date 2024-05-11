package com.ktyolmz.library.controller;


import com.ktyolmz.library.entity.Book;
import com.ktyolmz.library.request.BorrowBookRequest;

import com.ktyolmz.library.service.LibraryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
@AllArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @PutMapping("/borrow")
    public List<Book> borrowBook(@RequestBody BorrowBookRequest request) {
        return libraryService.assignBookToUser(request.getId(), request.getTitleList());
    }

    @PutMapping("/bookReturn")
    public Book returnBook(@RequestParam String title) {
        return libraryService.returnBook(title) ;
    }

}
