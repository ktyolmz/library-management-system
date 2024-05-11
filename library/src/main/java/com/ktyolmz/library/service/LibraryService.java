package com.ktyolmz.library.service;

import com.ktyolmz.library.entity.Book;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface LibraryService {

    List<Book> assignBookToUser(Long id, List<String> titleList);

    Book returnBook(String title);
}
