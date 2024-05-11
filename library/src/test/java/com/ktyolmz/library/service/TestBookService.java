package com.ktyolmz.library.service;

import com.ktyolmz.library.entity.Book;
import com.ktyolmz.library.exception.BookException;
import com.ktyolmz.library.repository.BookRepository;
import com.ktyolmz.library.service.serviceImpl.BookServiceImpl;
import com.ktyolmz.library.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TestBookService {

        @InjectMocks
        private BookServiceImpl bookService;
        @Mock
        private BookRepository bookRepository;

        @Mock
        private Utils utils;

        private List<Book> bookList;

        @BeforeEach
        void setUp(){
            bookList = List.of(createBook("John Doe", "Spring Boot Guide"),
                    createBook("Jane Smith", "Hibernate Basics"));
        }

        private Book createBook(String author, String title){
            return Book.builder()
                    .author(author)
                    .title(title)
                    .build();
        }

    @Test
    void testSaveBooks_withEmptyFields() {
        List<Book> bookListWithEmptyFields = List.of(
                createBook("John Doe", "Spring Boot Guide"),
                createBook(null, "Hibernate Basics")
        );

        assertThrows(BookException.class, () -> {
            bookService.saveBook(bookListWithEmptyFields);
        });
    }


    @Test
    void testSaveBooks_titlesAlreadyExist() {

        when(bookRepository.existsByTitleIn(Set.of("Spring Boot Guide", "Hibernate Basics"))).thenReturn(true);

        assertThrows(BookException.class, () -> {
            bookService.saveBook(bookList);
        });
    }

    @Test
    void testSaveBooks_duplicateTitles() {
        List<Book> bookListWithDuplicates = List.of(
                createBook("John Doe", "Spring Boot Guide"),
                createBook("Jane Smith", "Spring Boot Guide")
        );

        assertThrows(BookException.class, () -> {
            bookService.saveBook(bookListWithDuplicates);
        });
    }

    @Test
    void testSaveBooks() {
        when(bookRepository.existsByTitleIn(any())).thenReturn(false);
        when(bookRepository.saveAll(bookList)).thenReturn(bookList);

        List<Book> savedBookList = bookService.saveBook(bookList);

        assertEquals(2, savedBookList.size());
        assertEquals(bookList.get(1).getAuthor(), savedBookList.get(1).getAuthor());
    }

    @Test
    void testUpdateBooks() {
        when(bookRepository.saveAll(bookList)).thenReturn(bookList);

        List<Book> updatedBookList = bookService.updateBooks(bookList);

        assertEquals(bookList.size(), updatedBookList.size());
        assertEquals(bookList.get(0).getAuthor(), updatedBookList.get(0).getAuthor());
        assertEquals(bookList.get(1).getTitle(), updatedBookList.get(1).getTitle());
    }


    @ParameterizedTest
    @EmptySource
    void testGetBooks_bookNotExist(String title) {
        when(utils.getNonMatchingElements(any(), any())).thenReturn(List.of());
        when(bookRepository.findAllByTitleIn(List.of(title))).thenReturn(List.of());

        assertThrows(BookException.class, () -> {
            bookService.getBooksByTitles(List.of(title));
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"Spring Boot Guide"})
    void testGetBooks(String title) {
        List<Book> matchingBooks = List.of(createBook("John Doe", title));

        when(bookRepository.findAllByTitleIn(List.of(title))).thenReturn(matchingBooks);

        List<Book> retrievedBooks = bookService.getBooksByTitles(List.of(title));

        assertNotNull(retrievedBooks);
        assertEquals(1, retrievedBooks.size());
        assertEquals(matchingBooks.get(0).getAuthor(), retrievedBooks.get(0).getAuthor());
        assertEquals(matchingBooks.get(0).getTitle(), retrievedBooks.get(0).getTitle());
    }
}
