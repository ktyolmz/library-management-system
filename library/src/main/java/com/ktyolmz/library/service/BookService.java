package com.ktyolmz.library.service;

import com.ktyolmz.library.entity.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {

    List<Book> saveBook(List<Book> bookList);

    List<Book> getBooksByTitles(List<String> titleList);

    List<Book> updateBooks(List<Book> bookList);

    boolean existById(Long bookId);
}
