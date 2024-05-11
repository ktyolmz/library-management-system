package com.ktyolmz.library.service;

import com.ktyolmz.library.entity.Book;
import com.ktyolmz.library.entity.User;
import com.ktyolmz.library.exception.BookException;
import com.ktyolmz.library.exception.UserException;
import com.ktyolmz.library.service.serviceImpl.LibraryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TestLibraryService {

    @InjectMocks
    private LibraryServiceImpl libraryService;

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    private List<Book> bookList;

    private List<String> titleList;

    private User user;

    private final Long USER_ID = 1L;

    @BeforeEach
    void setUp(){
        user = createUser(USER_ID);
        bookList = List.of(createBook("John Doe", "Spring Boot Guide"),
                createBook("Jane Smith", "Hibernate Basics"));
        titleList = List.of("Spring Boot Guide", "Hibernate Basics");
    }

    private Book createBook(String author, String title){
        return Book.builder()
                .author(author)
                .title(title)
                .build();
    }

    private User createUser(Long id){
        return User.builder().id(USER_ID).build();
    }


    @Test
    void testAssignBookToUser_bookAlreadyTaken() {

        bookList.get(0).setBorrowedUserId(1L);

        when(userService.getUserById(USER_ID)).thenReturn(user);
        when(bookService.getBooksByTitles(titleList)).thenReturn(bookList);

        BookException exception = assertThrows(BookException.class, () -> {
            libraryService.assignBookToUser(USER_ID, titleList);
        });

        assertEquals("Book Already Taken", exception.getMessage());
    }

    @Test
    void testAssignBookToUser_userNotFound() {

        when(userService.getUserById(USER_ID)).thenThrow(new UserException("User Not Exist: " + USER_ID));

        UserException exception = assertThrows(UserException.class, () -> {
            libraryService.assignBookToUser(USER_ID, titleList);
        });
        assertEquals("User Not Exist: " + USER_ID, exception.getMessage());
    }

    @Test
    void testAssignBookToUser_bookNotFound() {
        when(userService.getUserById(USER_ID)).thenReturn(user);
        when(bookService.getBooksByTitles(titleList)).thenThrow(new BookException("Book(s) Not Found: " + titleList));

        BookException exception = assertThrows(BookException.class, () -> {
            libraryService.assignBookToUser(USER_ID, titleList);
        });
        assertEquals("Book(s) Not Found: " + titleList, exception.getMessage());
    }

    @Test
    void testAssignBookToUser() {
        when(userService.getUserById(USER_ID)).thenReturn(user);
        when(bookService.getBooksByTitles(titleList)).thenReturn(bookList);
        when(bookService.updateBooks(any())).thenReturn(bookList);

        List<Book> result = libraryService.assignBookToUser(USER_ID, titleList);

        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(book -> book.getBorrowedUserId() != null));
        assertTrue(result.stream().allMatch(book -> book.getBorrowDate() != null));
        assertTrue(result.stream().allMatch(book -> book.getDueDate() != null));
    }

    @Test
    void testReturnBook_bookNotFound() {
        String title = "Nonexistent Book";
        when(bookService.getBooksByTitles(List.of(title))).thenReturn(Collections.emptyList());

        BookException exception = assertThrows(BookException.class, () -> {
            libraryService.returnBook(title);
        });

        assertEquals("Book can not find", exception.getMessage());
        verify(bookService, never()).saveBook(anyList());
    }

    @Test
    void testReturnBook() {
        String title = "Spring Boot Guide";
        Book book = createBook("John Doe", title);
        when(bookService.getBooksByTitles(List.of(title))).thenReturn(Collections.singletonList(book));
        when(bookService.saveBook(anyList())).thenReturn(Collections.singletonList(book));

        Book returnedBook = libraryService.returnBook(title);

        assertNull(returnedBook.getBorrowDate());
        assertNull(returnedBook.getDueDate());
        assertNull(returnedBook.getBorrowedUserId());
        verify(bookService, times(1)).saveBook(anyList());
    }

}
