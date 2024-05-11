package com.ktyolmz.library.service;

import com.ktyolmz.library.entity.Book;
import com.ktyolmz.library.entity.Location;
import com.ktyolmz.library.exception.BookException;
import com.ktyolmz.library.repository.LocationRepository;
import com.ktyolmz.library.service.serviceImpl.LocationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TestLocationService {
    @InjectMocks
    private LocationServiceImpl locationService;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private BookService bookService;

    private final Long BookId = 1L;

    private Location location;

    @BeforeEach
    void setUp(){
        location = createLocation(BookId, 2, 3);
    }

    private Location createLocation(Long bookId, Integer roomNumber, Integer shelfNumber){
        return Location.builder()
                .bookId(bookId)
                .roomNumber(roomNumber)
                .shelfNumber(shelfNumber)
                .build();
    }

    @Test
    void testGetLocation_WhenBookExists() {

        when(bookService.existById(BookId)).thenReturn(true);
        when(locationRepository.findByBookId(BookId)).thenReturn(Optional.of(location));

        Location result = locationService.getLocation(BookId);

        assertEquals(location, result);
        verify(bookService).existById(BookId);
        verify(locationRepository).findByBookId(BookId);
    }

    @Test
    void testGetLocation_WhenBookDoesNotExist() {

        when(bookService.existById(BookId)).thenReturn(false);

        BookException exception = assertThrows(BookException.class, () -> {
            locationService.getLocation(BookId);
        });

        assertEquals("Can not find book", exception.getMessage());

        verify(bookService).existById(BookId);
        verify(locationRepository, never()).findByBookId(anyLong());
    }

    @Test
    void testGetLocation_WhenLocationIsNotFound() {

        when(bookService.existById(BookId)).thenReturn(true);
        when(locationRepository.findByBookId(BookId)).thenReturn(Optional.empty());

        BookException exception = assertThrows(BookException.class, () -> {
            locationService.getLocation(BookId);
        });
        assertEquals("Book location can not find.", exception.getMessage());

        verify(bookService).existById(BookId);
        verify(locationRepository).findByBookId(BookId);
    }


}
