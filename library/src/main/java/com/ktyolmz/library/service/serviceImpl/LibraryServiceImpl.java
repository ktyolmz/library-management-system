package com.ktyolmz.library.service.serviceImpl;

import com.ktyolmz.library.entity.Book;
import com.ktyolmz.library.entity.User;
import com.ktyolmz.library.exception.BookException;
import com.ktyolmz.library.service.BookService;
import com.ktyolmz.library.service.LibraryService;
import com.ktyolmz.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final BookService bookService;
    private final UserService userService;
    @Override
    public List<Book> assignBookToUser(Long id, List<String> titleList) {

        LocalDate now = LocalDate.now();
        LocalDate dueDate = now.plusDays(15);

        User user = userService.getUserById(id);

        List<Book> bookList = bookService.getBooksByTitles(titleList);

        if (isBookTaken(bookList)) {
            throw new BookException("Book Already Taken");
        }

        bookList.forEach(book -> {
            book.setBorrowedUserId(user.getId());
            book.setBorrowDate(now);
            book.setDueDate(dueDate);
        });

        return bookService.updateBooks(bookList);
    }

    @Override
    public Book returnBook(String title) {

        List<Book> bookList  = bookService.getBooksByTitles(List.of(title));

        if(bookList.isEmpty()){
            throw new BookException("Book can not find");
        }
        Book book = bookList.get(0);

        book.setDueDate(null);
        book.setBorrowDate(null);
        book.setBorrowedUserId(null);

        bookService.saveBook(List.of(book));

        return book;
    }

    private boolean isBookTaken(List<Book> bookList) {
        for (Book book : bookList) {
            if (book.getBorrowedUserId() != null) {
                return true;
            }
        }
        return false;
    }

}
