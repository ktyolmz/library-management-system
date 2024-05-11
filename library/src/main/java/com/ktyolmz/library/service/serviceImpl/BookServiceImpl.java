package com.ktyolmz.library.service.serviceImpl;

import com.ktyolmz.library.entity.Book;
import com.ktyolmz.library.exception.BookException;
import com.ktyolmz.library.repository.BookRepository;
import com.ktyolmz.library.service.BookService;
import com.ktyolmz.library.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final Utils utils;

    @Override
    public List<Book> saveBook(List<Book> bookList) {

        Set<String> titlesToSave = new HashSet<>();
        Set<String> duplicateTitles = new HashSet<>();

        bookList.forEach(book -> {
            if (isAnyFieldEmpty(book)) {
                throw new BookException("All fields must be filled for book: " + book);
            }
        });

        bookList.stream()
                .map(Book::getTitle)
                .forEach(title -> {
                    if (!titlesToSave.add(title)) {
                        duplicateTitles.add(title);
                    }
                });

        if (!duplicateTitles.isEmpty()) {
            throw new BookException("Books with the same title cannot be added " + duplicateTitles);
        }

        else if (bookRepository.existsByTitleIn(titlesToSave)) {
            throw new BookException("One or more books you are trying to add already exist.");
        }

        return bookRepository.saveAll(bookList);
    }

    private boolean isAnyFieldEmpty(Book book) {
        return book.getTitle() == null || book.getAuthor() == null;
    }

    @Override
    public List<Book> getBooksByTitles(List<String> titleList) {

        List<Book> bookList = bookRepository.findAllByTitleIn(titleList);
        if (bookList.size() != titleList.size()){
            List<String> nonMatchingTitleList= utils.getNonMatchingElements(titleList, bookList.stream().map(Book::getTitle).toList());
            throw new BookException("Book(s) Not Found: " + nonMatchingTitleList);
        }

        return bookList;
    }

    @Override
    public List<Book> updateBooks(List<Book> bookList) {
        return bookRepository.saveAll(bookList);
    }

    @Override
    public boolean existById(Long bookId) {
        return bookRepository.existsById(bookId);
    }
}
